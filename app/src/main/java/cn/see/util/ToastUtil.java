package cn.see.util;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import cn.see.app.App;


/**
 * Created by gxb on 2018/5/7.
 */

public class ToastUtil {


    private static Toast toast;

    public static void showToast(int resID) {
        showToast(App.getInstance(), Toast.LENGTH_SHORT, resID);
    }

    public static void showToast(String text) {
        showToast(App.getInstance(), Toast.LENGTH_SHORT, text);
    }

    public static void showLongToast(int resID) {
        showToast(App.getInstance(), Toast.LENGTH_LONG, resID);
    }


    public static void showLongToast(String text) {
        showToast(App.getInstance(), Toast.LENGTH_LONG, text);
    }

    private static void showToast(Context ctx, String text) {
        showToast(ctx, Toast.LENGTH_LONG, text);
    }

    private static void showToast(Context ctx, int duration, int resID) {
        showToast(ctx, duration, ctx.getString(resID));
    }


    private static void showToast(final Context ctx, final int duration, final String text) {
        if (text==null||text.equals("")) {
            return;
        }
        if (toast == null) {
            toast = Toast.makeText(ctx, text, duration);
        } else {
            toast.setText(text);
        }
        toast.show();
    }

    /** 在UI线程运行弹出 */
    public static void showToastOnUiThread(final Activity ctx, final String text) {
        if (ctx != null) {
            ctx.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    showToast(ctx, text);
                }
            });
        }
    }
}
