package cn.see.util.widet;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import cn.see.R;

/**
 * @日期：2018/6/27
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 自定义加载框
 */

public class CustomProgress extends Dialog {
    public CustomProgress(Context context) {
        super(context);
    }

    public CustomProgress(Context context, int theme) {
        super(context, theme);
    }

    /**
     * 当窗口焦点改变时调用
     */
    public void onWindowFocusChanged(boolean hasFocus) {
        ImageView imageView = (ImageView) findViewById(R.id.spinnerImageView);
        // 获取ImageView上的动画背景
        AnimationDrawable spinner = (AnimationDrawable) imageView.getBackground();
        // 开始动画
        spinner.start();
    }

    /**
     * 给Dialog设置提示信息
     *
     * @param message
     */
    public void setMessage(CharSequence message) {
        if (message != null && message.length() > 0) {
            findViewById(R.id.message).setVisibility(View.VISIBLE);
            TextView txt = (TextView) findViewById(R.id.message);
            txt.setText(message);
            txt.invalidate();
        }
    }

    public void setProgress(int pro,int max){
        ProgressBar bar = (ProgressBar) findViewById(R.id.progress);
        bar.setMax(max);
        bar.setProgress(pro);
    }

    /**
     * 弹出自定义ProgressDialog
     *
     * @param context
     *            上下文
     *            提示
     * @return
     */
    public static CustomProgress show(Context context) {
        CustomProgress dialog = new CustomProgress(context, R.style.Custom_Progress);
        dialog.setTitle("");
        dialog.setContentView(R.layout.progress_custom);
      /*  if (message == null || message.length() == 0) {
            dialog.findViewById(R.id.message).setVisibility(View.GONE);
        } else {
            TextView txt = (TextView) dialog.findViewById(R.id.message);
            txt.setText(message);
        }*/
        // 按返回键是否取消
        dialog.setCancelable(true);
        // 监听返回键处理
//        dialog.setOnCancelListener(cancelListener);
        // 设置居中
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        // 设置背景层透明度
        lp.dimAmount = 0.2f;
        dialog.getWindow().setAttributes(lp);
        // dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        dialog.show();
        return dialog;
    }
}
