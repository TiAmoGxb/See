package cn.see.util;

import android.app.Activity;
import android.content.Context;

import cn.droidlover.xdroidmvp.router.Router;
import cn.see.base.BaseActivity;
import cn.see.fragment.fragmentview.mineview.LoginAct;
import cn.see.util.constant.PreferenceConstant;
import cn.see.util.version.PreferenceUtils;

/**
 * @日期：2018/6/13
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 用户相关工具类
 */

public class UserUtils {

    /**
     * 存入用户ID
     * @param context
     * @param uid
     */
    public static void setUserID(Context context,String uid){
        PreferenceUtils.setString(context, PreferenceConstant.USER_UID,uid);

    }

    /**
     * 取出用户ID
     */
    public static String getUserID(Context context){
        return PreferenceUtils.getString(context,PreferenceConstant.USER_UID,"");
    }

    /**
     * 删除用户ID
     */
    public static void removeUserID(Context context){
        PreferenceUtils.remove(context,PreferenceConstant.USER_UID);
    }

    /**
     * 存入用户名（手机号）
     */
    public static void setUserPhone(Context context,String userPhone){
        PreferenceUtils.setString(context, PreferenceConstant.USER_PHONE,userPhone);
    }

    /**
     * 删除用户手机号
     */
    public static void removeUserPhone(Context context){
        PreferenceUtils.remove(context,PreferenceConstant.USER_PHONE);
    }

    /**
     * 取出用户名
     */
    public static String getUserPhone(Context context){
        return PreferenceUtils.getString(context,PreferenceConstant.USER_PHONE,"");
    }


    /**
     * 存入用户已登录
     */
    public static void setLoginFlag(Context context){
        PreferenceUtils.setBoolean(context, PreferenceConstant.USER_IS_LOGIN,true);
    }

    /**
     * 删除用户登录标记
     */
    public static void removeUserLogin(Context context){
        PreferenceUtils.remove(context, PreferenceConstant.USER_IS_LOGIN);
    }

    /**
     * 用户是否登录
     */
    public static boolean userIsLogin(Context context){
        return PreferenceUtils.getBoolean(context,PreferenceConstant.USER_IS_LOGIN);
    }

    /**
     * 登录状态返回
     * @param context
     */
    public static boolean getLogin(Activity context){
        if(!userIsLogin(context)){
            Router.newIntent(context)
                    .to(LoginAct.class)
                    .launch();
            return false;
        }else{
            return true;
        }
    }

}
