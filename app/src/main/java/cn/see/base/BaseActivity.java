package cn.see.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.graphics.ColorUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.umeng.socialize.UMShareAPI;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.see.model.MineSchoolModel;
import cn.see.util.permosson.XPermissionUtils;

/**
 * @日期：2018/6/4
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 基类Act 继承XActivity 实现MVP模式绑定
 */
public abstract class BaseActivity< M extends XPresent> extends XActivity<M> implements View.OnClickListener{
    private static Stack<Activity> listActivity = new Stack<Activity>();
    private static Map<String, Activity> destoryMap = new HashMap<>();
    public static boolean isForeground = false;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (listActivity.contains(this)) {
            listActivity.remove(this);
        }
    }

    /**
     * 清空Act
     */
    public void clearAct(){
        listActivity.clear();
    }

    /**
     * 结束Act
     */
    public void onBack() {
        finish();
    }


    /**
     * 打开Act
     * @param targetActivityClass
     * @param bundle
     */
    public void openActivity(Class<?> targetActivityClass, Bundle bundle) {
        Intent intent = new Intent(this, targetActivityClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void openActivity(Class<?> targetActivityClass) {
        openActivity(targetActivityClass, null);
    }


    @Override
    protected void onResume() {
        super.onResume();
       /* JPushInterface.onResume(this);
        isForeground = true;
        //友盟统计
        UmengUtils.onResumeToActivity(this);*/

    }
    @Override
    protected void onPause() {
        super.onPause();
       /* isForeground = false;
        JPushInterface.onPause(this);
        UmengUtils.onPauseToActivity(this);*/
    }

    /**
     * 结束指定类名的Activity
     */
    public static void finishActivity(Class<?> cls) {
        for (Activity activity : listActivity) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }
    /**
     * 结束指定的Activity
     */
    public static void finishActivity(Activity activity) {
        if (activity != null) {
            listActivity.remove(activity);
            activity.finish();
            /*if(!activity.isFinishing()) {
                activity.finish();
            }*/
        }
    }

    /**
     * 6.0权限适配回调
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        XPermissionUtils.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 沉浸式状态栏
     * @param on
     * @param activity
     */
    @TargetApi(19)
    public static void setTranslucentStatus(boolean on, Activity activity) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
            win.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        } else {
            winParams.flags &= ~bits;
            setStatusBar(getStatusBarColor(),win);
        }
        win.setAttributes(winParams);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initView();
        initAfter();
        setListener();
        //将Act推入栈中
        listActivity.push(this);
    }



    @Override
    public int getLayoutId() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(false,this);
        }
        return bindLayout();
    }



    @Override
    public M newP() {
        return bindPresent();
    }
    /**
     * [找控件]
     *
     * @return
     */
    public abstract void initView();

    /**
     * [找完控件后执行]
     *
     * @return
     */
    public abstract void initAfter();

    /**
     * [绑定布局]
     *
     * @return
     */
    public abstract int bindLayout();

    /**
     * [绑定布局]
     *
     * @return
     */
    public abstract M bindPresent();

    /**
     * [设置监听]
     */
    public abstract void setListener();

    /**
     * 获取输入框的值
     * @param editText
     * @return
     */
    public String getEditText(EditText editText){
        return editText.getText().toString().trim();
    }


    /**
     * Android 6.0 以上设置状态栏颜色
     */
    protected static void setStatusBar(@ColorInt int color,Window win) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 设置状态栏底色颜色
            win.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            win.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            win.setStatusBarColor(color);
            // 如果亮色，设置状态栏文字为黑色
            if (isLightColor(color)) {
                win.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                win.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
        }
    }

    /**
     * 手动设置状态栏背景色 为默认
     * @param color
     * @param win
     */
    public static void setStatusBarTo(int color,Window win) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 设置状态栏底色颜色
            win.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            win.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色
            win.setStatusBarColor(color);
            win.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }

    }


    /**
     * 判断颜色是不是亮色
     *
     * @param color
     * @return
     */
    private static boolean isLightColor(@ColorInt int color) {
        return ColorUtils.calculateLuminance(color) >= 0.5;
    }

    /**
     * 获取StatusBar颜色，默认白色
     * @return
     */
    protected @ColorInt  static int getStatusBarColor() {
        return Color.WHITE;
    }

    @Override
    public void onClick(View v) {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
