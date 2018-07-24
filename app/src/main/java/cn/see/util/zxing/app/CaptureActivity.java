/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.see.util.zxing.app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.io.IOException;
import java.util.Vector;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.router.Router;
import cn.see.R;
import cn.see.base.BaseActivity;
import cn.see.fragment.fragmentview.mineview.OtherMainAct;
import cn.see.util.constant.IntentConstant;
import cn.see.util.widet.AlertView.AlertView;
import cn.see.util.zxing.camera.CameraManager;
import cn.see.util.zxing.decoding.CaptureActivityHandler;
import cn.see.util.zxing.decoding.InactivityTimer;
import cn.see.util.zxing.util.Utils;
import cn.see.util.zxing.view.ViewfinderView;


/**
 * The barcode reader activity itself. This is loosely based on the
 * CameraPreview example included in the Android SDK.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 * @author Sean Owen
 */
public class CaptureActivity extends BaseActivity implements SurfaceHolder.Callback,
        OnClickListener  {

    private CaptureActivityHandler handler;// 消息中心
    private ViewfinderView viewfinderView;// 绘制扫描区域
    private boolean hasSurface;// 控制调用相机属性
    private Vector<BarcodeFormat> decodeFormats;// 存储二维格式的数组
    private String characterSet;// 字符集
    private InactivityTimer inactivityTimer;// 相机扫描刷新timer
    private MediaPlayer mediaPlayer;// 播放器
    private boolean playBeep;// 声音布尔
    private static final float BEEP_VOLUME = 0.10f;// 声音大小
    private boolean vibrate;// 振动布尔
    private boolean isTorchOn = true;

    @BindView(R.id.title_tv_op)TextView pt;
    @BindView(R.id.title_tv_base)
    TextView title;
    @BindView(R.id.flash_btn)ImageView flash_btn;

    @OnClick(R.id.back_rela)void bacView(){onBack();}
    @OnClick(R.id.title_tv_op)void selPhoto(){selectPhoto();}
    @OnClick(R.id.flash_btn)void flashView(){
        if (isTorchOn) {
            isTorchOn = false;
            flash_btn.setImageResource(R.mipmap.falst_yes);
            CameraManager.start();
        } else {
            isTorchOn = true;
            flash_btn.setImageResource(R.mipmap.falst_no);
            CameraManager.stop();
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        // 初始化相机画布
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;
        // 声音
        playBeep = true;
        // 初始化音频管理器
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        // 振动
        vibrate = true;

    }

    @Override
    protected void onPause() {

        // 停止相机 关闭闪光灯
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    public void initView() {
        CameraManager.init(this);
        ButterKnife.bind(this);
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        pt.setVisibility(View.VISIBLE);
        pt.setText("相册");
        title.setText("扫一扫");
        inactivityTimer = new InactivityTimer(this);
        if (ContextCompat.checkSelfPermission(CaptureActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            String[] mPermissionList = new String[]{Manifest.permission.CAMERA};
            ActivityCompat.requestPermissions(CaptureActivity.this, mPermissionList,1);
        }
    }

    @Override
    public void initAfter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_qrcode_capture_layout;
    }

    @Override
    public XPresent bindPresent() {
        return null;
    }

    @Override
    public void setListener() {

    }

    @Override
    protected void onDestroy() {

        // 停止相机扫描刷新timer
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    /**
     * 处理扫描结果
     *
     * @param result
     * @param barcode
     */
    public void handleDecode(Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String resultString = result.getText();
        Log.i("QRCODEQEQ","结果："+resultString);
        if (resultString.equals("")) {
            Toast.makeText(this, "扫描失败!", Toast.LENGTH_SHORT)
                    .show();
        } else {
            String[] split = resultString.split("=");
            Router.newIntent(this)
                    .putString(IntentConstant.OTHER_USER_ID,split[1])
                    .to(OtherMainAct.class)
                    .launch();
            onBack();
        }
    }

    /**
     * 相册选择图片
     */
    private void selectPhoto() {
/*
        Intent innerIntent = new Intent(); // "android.intent.action.GET_CONTENT"
        if (Build.VERSION.SDK_INT < 19) {
            innerIntent.setAction(Intent.ACTION_GET_CONTENT);
        } else {
            innerIntent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        }*/
        Intent innerIntent = new Intent(Intent.ACTION_GET_CONTENT); // "android.intent.action.GET_CONTENT"

        innerIntent.setType("image/*");

        Intent wrapperIntent = Intent.createChooser(innerIntent, "选择二维码图片");

        startActivityForResult(wrapperIntent,
                REQUEST_CODE);
    }

    /**
     * 初始化相机
     */
    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats,
                    characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();

    }

    /**
     * 声音设置
     */
    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    /**
     * 结束后的声音
     */
    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final MediaPlayer.OnCompletionListener beepListener = new MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    private static final int REQUEST_CODE = 234;// 相册选择code
    private String photo_path;// 选择图片的路径

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            switch (requestCode) {

                case REQUEST_CODE:
                    String[] proj = { MediaStore.Images.Media.DATA };
                    // 获取选中图片的路径
                    Cursor cursor = getContentResolver().query(data.getData(),
                            proj, null, null, null);

                    if(cursor!=null){
                        if (cursor.moveToFirst()) {
                            int column_index = cursor
                                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                            photo_path = cursor.getString(column_index);
                            if (photo_path == null) {
                                photo_path = Utils.getPath(getApplicationContext(),
                                        data.getData());
                            }
                        }
                        cursor.close();
                        releaseImgThread();
                    }else{
                        Toast.makeText(getApplicationContext(), "获取图片路径失败", Toast.LENGTH_SHORT).show();
                    }

                    break;

            }

        }

    }

    /**
     * 解析图片Thread
     */
    private void releaseImgThread() {
        new Thread(new Runnable() {

            @Override
            public void run() {

                Result result = Utils.scanningImage(photo_path);
                if (result == null) {
                    msgHandler.sendEmptyMessage(SHOW_TOAST_MSG);
                } else {
                    // 数据返回
                    String recode = Utils.recode(result.toString());
                    String[] split = recode.split("=");
                    Router.newIntent(CaptureActivity.this)
                            .putString(IntentConstant.OTHER_USER_ID,split[1])
                            .to(OtherMainAct.class)
                            .launch();
                    onBack();
                }
            }
        }).start();
    }

    private static final int SHOW_TOAST_MSG = 0;
    Handler msgHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case SHOW_TOAST_MSG:
                    Toast.makeText(getApplicationContext(), "未发现二维码图片", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

}