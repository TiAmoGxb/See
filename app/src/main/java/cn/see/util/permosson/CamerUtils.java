package cn.see.util.permosson;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import java.io.File;


/**
 * Created by gxb on 2018/1/19.
 * 打开相机时检查权限 适配6.0版本以下
 */

public class CamerUtils {

    public static int PHOTO_MAIN_CAMER = 0;
    public static int PHOTO_OPEN_CAMER = 1;
    private static File tempFile;

    public static File doOpenCamera(final Activity context, final int requestCode, final String photoName, final int type) {
        XPermissionUtils.requestPermissions(context, RequestCode.CAMERA, new String[] { Manifest.permission.CAMERA },
                new XPermissionUtils.OnPermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        if (PermissionHelper.isCameraEnable()) {
                            if(type==PHOTO_MAIN_CAMER){
                                //context.startActivityForResult(new Intent(context, CaptureActivity.class),4);//扫一扫
                            }else{
                                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                                if (hasSdcard()) {
                                    tempFile = new File( Environment.getExternalStorageDirectory() +
                                            File.separator + Environment.DIRECTORY_DCIM + File.separator, photoName);
                                    Log.i("DemoActivity","tempFile:"+tempFile);
                                    //intent.putExtra(MediaStore.EXTRA_OUTPUT, CommonlyUtil.getFileUri(context,tempFile.getPath(),tempFile.getName()));
                                }
                                context.startActivityForResult(intent, requestCode);
                            }
                        } else {
                            DialogUtil.showPermissionManagerDialog(context, "相机");
                        }
                    }

                    @Override
                    public void onPermissionDenied(final String[] deniedPermissions, boolean alwaysDenied) {
                        // 拒绝后不再询问 -> 提示跳转到设置
                        if (alwaysDenied) {
                            DialogUtil.showPermissionManagerDialog(context, "相机");
                        } else {    // 拒绝 -> 提示此公告的意义，并可再次尝试获取权限
                            new AlertDialog.Builder(context).setTitle("温馨提示")
                                    .setMessage("我们需要相机权限才能正常使用该功能")
                                    .setNegativeButton("取消", null)
                                    .setPositiveButton("验证权限", new DialogInterface.OnClickListener() {
                                        @RequiresApi(api = Build.VERSION_CODES.M)
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            XPermissionUtils.requestPermissionsAgain(context, deniedPermissions,
                                                    RequestCode.CAMERA);
                                        }
                                    })
                                    .show();
                        }
                    }
                });
        return tempFile;
    }
    public static boolean hasSdcard() {
        //判断ＳＤ卡手否是安装好的　　　media_mounted
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }
}
