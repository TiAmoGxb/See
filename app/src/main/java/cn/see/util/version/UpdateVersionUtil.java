package cn.see.util.version;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import java.io.File;
import java.lang.reflect.Method;

import cn.see.R;
import cn.see.util.constant.HttpConstant;
import cn.see.util.http.OkHttpUtils;


/**
 * 
 * @author	gxb
 *	版本更新的工具类
 */
public class UpdateVersionUtil{
	
	/**
	 * 接口回调
	 * @author wenjie
	 *
	 */
	public interface UpdateListener{
		void onUpdateReturned(int updateStatus, VersionInfo.VersionErrors.VersionMessAge versionInfo);
	}

	public UpdateListener updateListener;

	public void setUpdateListener(UpdateListener updateListener) {
		this.updateListener = updateListener;
	}

	/**
	 * 网络测试 检测版本
	 * @param context 上下文
	 */
	public static void checkVersion(final Context context,final UpdateListener updateListener) {


		OkHttpUtils.ResultCallback<VersionInfo> loadNewsCallback = new OkHttpUtils.ResultCallback<VersionInfo>() {
			@Override
			public void onSuccess(VersionInfo v) {
				if (v.getStatus_code() == HttpConstant.SUCCEED) {
					VersionInfo.VersionErrors.VersionMessAge mVersionInfo = v.getErrors().getMessage();
					int serverVersionCode = v.getErrors().getMessage().getCode();
					int clientVersionCode = ApkUtils.getVersionCode(context);
					Log.i("CURRENTY", "当前版本：" + clientVersionCode + "服务器版本：" + serverVersionCode + " 路径：" + mVersionInfo.getUrl());
					if (clientVersionCode < serverVersionCode) {
						int i = NetworkUtil.checkedNetWorkType(context);
						if (i == NetworkUtil.NOWIFI) {
							updateListener.onUpdateReturned(UpdateStatus.NOWIFI, mVersionInfo);
						} else if (i == NetworkUtil.WIFI) {
							updateListener.onUpdateReturned(UpdateStatus.YES, mVersionInfo);
						}
					} else {
						//无新本
						updateListener.onUpdateReturned(UpdateStatus.NO, null);
					}
				}
			}

			@Override
			public void onFailure(Exception e) {
				updateListener.onUpdateReturned(UpdateStatus.TIMEOUT, null);
			}
		};
		//OkHttpUtils.get(ConstantsUtils.CHECKVERSIONCODE, loadNewsCallback);
	}


	/**
	 * 弹出新版本提示
	 * @param context 上下文
	 * @param versionInfo 更新内容
	 */
	public static void showDialog(final Activity context, final VersionInfo.VersionErrors.VersionMessAge versionInfo){
		final Dialog dialog = new AlertDialog.Builder(context).create();
		final File file = new File(SDCardUtils.getRootDirectory()+"/updateVersion/bling_app.apk");
        boolean delete = file.delete();
        Log.i("DELETE","DELETE:"+delete+"---exit:"+file.exists());
        final String update = versionInfo.getUpdate();
        if(update.equals("1")){
            dialog.setCancelable(false);
        }else{
            dialog.setCancelable(true);
        }
		dialog.setCanceledOnTouchOutside(false);//
		dialog.show();
		View view = LayoutInflater.from(context).inflate(R.layout.version_update_dialog, null);
		dialog.setContentView(view);
		final TextView btnOk = (TextView) view.findViewById(R.id.btn_update_id_ok);
		final TextView btnCancel = (TextView) view.findViewById(R.id.btn_update_id_cancel);
		TextView tvContent = (TextView) view.findViewById(R.id.tv_update_content);
		TextView tvUpdateTile = (TextView) view.findViewById(R.id.tv_update_title);
		final TextView tvUpdateMsgSize = (TextView) view.findViewById(R.id.tv_update_msg_size);
        if(update.equals("1")){
            btnCancel.setText("退出");
		}
		tvContent.setText(versionInfo.getContent());
		tvUpdateTile.setText("最新版本："+versionInfo.getName());
		tvUpdateMsgSize.setText("新版大大小："+versionInfo.getSize()+"M");

		btnOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				if(v.getId() == R.id.btn_update_id_ok){//R.id.btn_update_id_ok
						Intent intent = new Intent(context,UpdateVersionService.class);
						intent.putExtra("downloadUrl", versionInfo.getUrl());
						context.startService(intent);
				}
			}
		});
		
		btnCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
                if(update.equals("1")){
                    context.finish();
                }else{
                    dialog.dismiss();
                }
			}
		});
	}
	
	/**
	 * 收起通知栏
	 * @param context
	 */
	public static void collapseStatusBar(Context context) { 
		try{
			Object statusBarManager = context.getSystemService(Context.ACCOUNT_SERVICE);
			Method collapse;
			if (Build.VERSION.SDK_INT <= 16){
				collapse = statusBarManager.getClass().getMethod("collapse"); 
			}else{ 
				collapse = statusBarManager.getClass().getMethod("collapsePanels"); 
			} 
			collapse.invoke(statusBarManager);
		}catch (Exception localException){ 
			localException.printStackTrace();
		} 
	}
}
