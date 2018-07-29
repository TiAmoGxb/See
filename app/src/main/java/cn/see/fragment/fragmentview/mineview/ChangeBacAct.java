package cn.see.fragment.fragmentview.mineview;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.see.R;
import cn.see.base.BaseActivity;
import cn.see.presenter.minep.ChangeBacPresenter;
import cn.see.util.BitmapUtils;
import cn.see.util.UserUtils;
import cn.see.util.constant.HttpConstant;
import cn.see.util.constant.IntentConstant;
import cn.see.util.glide.GlideDownLoadImage;
import cn.see.util.version.ApkUtils;
import cn.see.util.widet.CustomProgress;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 更换个人中心背景
 */
public class ChangeBacAct extends BaseActivity<ChangeBacPresenter> {

    private static final String TAG = "ChangeBacAct";
    private List<Integer> bitmapList ;
    private Uri imageUri;
    private String userName;
    private String userUrl;
    private String userSine;
    //最终上传的File
    private File upFile;

    @BindView(R.id.recyclerView)
    GridView recyclerView;
    @BindView(R.id.title_tv_base)
    TextView title;
    @BindView(R.id.show_bac)
    RelativeLayout layout;
    @BindView(R.id.user_name)
    TextView user_name;
    @BindView(R.id.user_sin)
    TextView user_sin;
    @BindView(R.id.user_img)
    ImageView userImg;
    @BindView(R.id.bac_img)
    ImageView bacImg;

    @OnClick(R.id.close_rela)
    void closeRela(){
        layout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }


    /**
     * 上传
     */
    @OnClick(R.id.com_up)
    void com(){
        release(upFile);
    }

    @OnClick(R.id.back_rela)
    void bacView(){
        onBack();
    }



    @Override
    public void initView() {
        title.setText("更换封面");
        //定义URI路径 用于存储裁剪后的图片
        String path =  this.getExternalCacheDir().getAbsolutePath() + "/bac";
        imageUri = ApkUtils.getFileUri(this, new File(path, GlideDownLoadImage.getPhotoFileName()));
    }

    @Override
    public void initAfter() {

        userName = getIntent().getStringExtra(IntentConstant.USER_NAME_TEXT);
        userUrl = getIntent().getStringExtra(IntentConstant.USER_HEAD_URL);
        userSine = getIntent().getStringExtra(IntentConstant.USER_SINE_TEXT);
        bitmapList = new ArrayList<>();
        //站位数据
        bitmapList.add(R.drawable.heise);
        bitmapList.add(R.drawable.bac_0);
        bitmapList.add(R.drawable.bac_1);
        bitmapList.add(R.drawable.bac_2);
        bitmapList.add(R.drawable.bac_3);
        bitmapList.add(R.drawable.bac_4);
        bitmapList.add(R.drawable.bac_5);
        bitmapList.add(R.drawable.bac_6);
        bitmapList.add(R.drawable.bac_7);
        bitmapList.add(R.drawable.bac_8);
        bitmapList.add(R.drawable.bac_9);
        bitmapList.add(R.drawable.bac_10);
        recyclerView.setAdapter(getP().initAdapter(bitmapList));
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_change_bac;
    }

    @Override
    public ChangeBacPresenter bindPresent() {
        return new ChangeBacPresenter();
    }

    @Override
    public void setListener() {
        recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(position == 0){
                        Intent intent1 = new Intent(Intent.ACTION_PICK);
                        intent1.setType("image/*");
                        startActivityForResult(intent1,1);
                    }else{
                        GlideDownLoadImage.getInstance().loadCircleImageRole(bitmapList.get(position),bacImg,10);
                        GlideDownLoadImage.getInstance().loadCircleImageToCust(userUrl,userImg);
                        user_name.setText(userName);
                        user_sin.setText(userSine);
                        Bitmap bmp=BitmapFactory.decodeResource(ChangeBacAct.this.getResources(), bitmapList.get(position));
                        try {
                            upFile = BitmapUtils.saveFile(bmp, "bac");
                            layout.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            }
        });
    }

    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 16);
        intent.putExtra("aspectY", 9);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 600);
        intent.putExtra("outputY", 300);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", false);
        intent.putExtra("output", imageUri);
        startActivityForResult(intent, 2);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            if(requestCode == 1){
                Uri uri = data.getData();
                if(uri!=null){
                    crop(uri);
                }
            }else if(requestCode == 2){
                try {
                    Log.i(TAG,"imageUri:"+imageUri);
                    if(imageUri!=null){
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                        upFile = BitmapUtils.saveFile(bitmap, "bac");
                        Log.i(TAG,"FILE:"+upFile);
                        GlideDownLoadImage.getInstance().loadImageRotFile(upFile,bacImg,13);
                        GlideDownLoadImage.getInstance().loadCircleImageToCust(userUrl,userImg);
                        user_name.setText(userName);
                        user_sin.setText(userSine);
                        layout.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    public void release(File file) {
        final CustomProgress progress = CustomProgress.show(this);
        OkHttpClient client = new OkHttpClient();
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("uid", UserUtils.getUserID(this));
        builder.addFormDataPart("Filedata", file.getName(), okhttp3.RequestBody.create(MediaType.parse("image/jpg"), file));
        Request request = new Request.Builder()
                .url(HttpConstant.UPDATA_BAC_IMG).post(builder.build()).build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "失败：" + e.toString());
                progress.dismiss();
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                Log.i(TAG, "成功：" + response.body().string());
                progress.dismiss();
                Intent intent = new Intent();
                setResult(2,intent);
                onBack();
                try {
                    deleteFile(new File(ChangeBacAct.this.getExternalCacheDir().getAbsolutePath() + "/bac"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void deleteFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                deleteFile(f);
            }
            file.delete();
        } else if (file.exists()) {
            boolean delete = file.delete();
            Log.i(TAG,"delete："+delete);
        }
    }
}
