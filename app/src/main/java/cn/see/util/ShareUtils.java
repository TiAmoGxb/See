package cn.see.util;

import android.app.Activity;
import android.text.*;
import android.util.Log;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import cn.see.util.widet.CustomProgress;

/**
 * @日期：2018/7/27
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 分享
 */

public class ShareUtils {

    private static final String TAG = "ShareUtils" ;
    private static CustomProgress proDialog;

    public static void shareWeb(final Activity activity, String WebUrl, String title, String description, String imageUrl) {
        Log.i(TAG,"URL:"+WebUrl);
        UMWeb web = new UMWeb(WebUrl);//连接地址
        web.setTitle(title);//标题
        web.setDescription(description);//描述
        web.setThumb(new UMImage(activity, imageUrl));  //网络缩略图
        new ShareAction(activity).withMedia(web)
                .setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ,SHARE_MEDIA.QZONE,SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {
                        proDialog = CustomProgress.show(activity);
                        Log.i(TAG,"开始分享");
                    }
                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        Log.i(TAG,"分享回调");
                        proDialog.dismiss();
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        Log.i(TAG,"分享失败："+throwable.toString());
                        proDialog.dismiss();
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        Log.i(TAG,"取消分享");
                        proDialog.dismiss();
                    }
                }).open();
    }

}
