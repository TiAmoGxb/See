package cn.see.util.version;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import java.io.File;


/**
 *
 */
public class ApkUtils {
	private static final String TAG = ApkUtils.class.getSimpleName();

	/**
	 * 获取应用程序名称
	 */
	public static String getAppName(Context context) {
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			int labelRes = packageInfo.applicationInfo.labelRes;
			return context.getResources().getString(labelRes);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取应用程序版本名称信息
	 * 
	 * @param context
	 * @return 当前应用的版本名称
	 */
	public static String getVersionName(Context context) {
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionName;

		} catch (NameNotFoundException e) {

		}
		return null;
	}

	/**
	 * @return 当前程序的版本号
	 */
	public static int getVersionCode(Context context) {
		int version;
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
			version = packageInfo.versionCode;
		} catch (Exception e) {
			e.printStackTrace();
			version = 0;
		}
		return version;
	}

	/**
	 * 得到安装的intent
	 * @param apkFile
	 * @return
	 */
	public static Intent getInstallIntent(Context context,File apkFile) {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
        Uri fileUri = getFileUri(context,apkFile);
		Log.i("FILEURI","fileUri:"+fileUri);
		intent.setDataAndType(fileUri,
				"application/vnd.android.package-archive");
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		return intent;
	}


	private static Uri getFileUri(Context context,File apkFile){
		Uri uri;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//如果是7.0android系统
			ContentValues contentValues = new ContentValues(1);
			contentValues.put(MediaStore.Images.Media.DATA,apkFile.getAbsolutePath());
			uri= context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);
		}else{
			uri = Uri.fromFile(apkFile);
		}
		return uri;
	}
	

}
