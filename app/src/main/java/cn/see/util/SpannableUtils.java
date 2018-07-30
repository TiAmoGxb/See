package cn.see.util;

import android.app.Activity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;

import cn.droidlover.xdroidmvp.router.Router;
import cn.see.R;
import cn.see.fragment.fragmentview.mineview.OtherMainAct;
import cn.see.fragment.fragmentview.mineview.TopicAct;
import cn.see.main.WebAct;
import cn.see.model.FindActModel;
import cn.see.util.constant.HttpConstant;
import cn.see.util.constant.IntentConstant;
import cn.see.util.glide.GlideDownLoadImage;

/**
 * @日期：2018/6/27
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 富文本
 */

public class SpannableUtils {


    private static SpannableUtils instance = new SpannableUtils();


    public static SpannableUtils getInstance() {

        return instance;
    }

    /**
     * 文章话题
     * @param item
     * @param toid
     * @param length
     * @return
     */
    public SpannableString getClickableSpan(final Activity activity, final String item, final String toid, int length) {
        final SpannableString string = new SpannableString(item);
        ClickableSpan span = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                    Router.newIntent(activity)
                            .putString(IntentConstant.ARTIC_TOPIC_ID,toid)
                            .to(TopicAct.class)
                            .launch();
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(activity.getResources().getColor(R.color.text_3f));
                ds.setUnderlineText(false);
            }
        };
        string.setSpan(span, 0, (length+2), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return string;
    }

    /**
     * 活动
     * @param activity
     * @param item
     * @param toid
     * @param length
     * @return
     */
    public SpannableString getClickableSpan(final Activity activity, final String item, final String toid, int length, final String name) {
        final SpannableString string = new SpannableString(item);
        ClickableSpan span = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Router.newIntent(activity)
                        .to(WebAct.class)
                        .putString(IntentConstant.WEB_ACTIVITY_TYPE,"act")
                        .putString(IntentConstant.WEB_ACT_IMG,"")
                        .putString(IntentConstant.WEB_ACT_TITLE,name)
                        .putString(IntentConstant.WEB_ACT_OONT,"")
                        .putString(IntentConstant.WEB_ACT_ID,toid)
                        .putString(IntentConstant.WEB_LOAD_URL, HttpConstant.ACT_WEB_URL+toid+"&uid="+UserUtils.getUserID(activity))
                        .launch();

            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(activity.getResources().getColor(R.color.text_3f));
                ds.setUnderlineText(false);
            }
        };
        string.setSpan(span, 0, (length+2), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return string;
    }
    /**
     * 主页
     * @param activity
     * @param item
     * @param toid
     * @param length
     * @return
     */
    public SpannableString getClickableSpanToMain(final Activity activity, final String item, final String toid, int length) {
        final SpannableString string = new SpannableString(item);
        ClickableSpan span = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Router.newIntent(activity)
                        .putString(IntentConstant.OTHER_USER_ID,toid)
                        .to(OtherMainAct.class)
                        .launch();
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(activity.getResources().getColor(R.color.text_3f));
                ds.setUnderlineText(false);
            }
        };
        string.setSpan(span, 0, (length+1), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return string;
    }

}
