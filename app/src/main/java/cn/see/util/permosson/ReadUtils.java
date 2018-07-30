package cn.see.util.permosson;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;

import cn.see.util.version.UpdateStatus;
import cn.see.util.version.UpdateVersionUtil;
import cn.see.util.version.VersionInfo;


/**
 * Created by Administrator on 2018/4/13.
 */

public class ReadUtils {

    /**
     * 检查权限
     * @param activity
     */
    public static void checkVersionPer(final Activity activity){
        XPermissionUtils.requestPermissions(activity, RequestCode.EXTERNAL,
                new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE },
                new XPermissionUtils.OnPermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        if (PermissionHelper.isCameraEnable()) {
                            checkVersion(activity);
                        }else{
                            DialogUtil.showPermissionManagerDialog(activity, "存储");
                        }
                    }
                    @Override
                    public void onPermissionDenied(final String[] deniedPermissions, boolean alwaysDenied) {
                        // 拒绝后不再询问 -> 提示跳转到设置
                        if (alwaysDenied) {
                            DialogUtil.showPermissionManagerDialog(activity, "相机");
                        } else {    // 拒绝 -> 提示此公告的意义，并可再次尝试获取权限
                            new AlertDialog.Builder(activity).setTitle("温馨提示")
                                    .setMessage("我们需要读写权限才能正常使用该功能")
                                    .setNegativeButton("取消", null)
                                    .setPositiveButton("验证权限", new DialogInterface.OnClickListener() {
                                        @RequiresApi(api = Build.VERSION_CODES.M)
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            XPermissionUtils.requestPermissionsAgain(activity, deniedPermissions,
                                                    RequestCode.CAMERA);
                                        }
                                    })
                                    .show();
                        }
                    }
                });
    }

    /**
     * 检查版本
     * @param activity
     */
    private static void checkVersion(final Activity activity) {

        UpdateVersionUtil.checkVersion(activity,new UpdateVersionUtil.UpdateListener() {
            @Override
            public void onUpdateReturned(int updateStatus, VersionInfo.VersionErrors.VersionMessAge versionInfo) {
                switch (updateStatus) {
                    case UpdateStatus.YES://WIFI
                        UpdateVersionUtil.showDialog(activity,versionInfo);
                        break;
                    case UpdateStatus.NOWIFI://非WIFI
                        UpdateVersionUtil.showDialog(activity,versionInfo);
                        break;
                }
            }
        });
    }

}
