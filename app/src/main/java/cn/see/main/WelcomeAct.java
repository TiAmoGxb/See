package cn.see.main;

import android.os.Handler;
import android.util.Log;

import java.io.File;

import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.router.Router;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.android.api.options.RegisterOptionalUserInfo;
import cn.jpush.im.api.BasicCallback;
import cn.see.R;
import cn.see.app.App;
import cn.see.base.BaseActivity;
import cn.see.chat.activity.FinishRegisterActivity;
import cn.see.chat.activity.MainActivity;
import cn.see.chat.database.UserEntry;
import cn.see.chat.model.User;
import cn.see.chat.utils.SharePreferenceManager;
import cn.see.chat.utils.ThreadUtil;
import cn.see.util.UserUtils;

/**
 * @日期：2018/6/4
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 开屏欢迎页
 */

public class WelcomeAct extends BaseActivity {


    private static final String TAG =  "WelcomeAct";

    @Override
    public void initView() {
        //设置沉浸式状态栏
        setTranslucentStatus(true,this);
    }

    @Override
    public void initAfter() {

        if(UserUtils.userIsLogin(this)){

            //登录IM
            JMessageClient.login("kanjian"+UserUtils.getUserID(WelcomeAct.this), "123456", new BasicCallback() {
                @Override
                public void gotResult(int i, String s) {
                    if (i == 0) {
                        Log.i(TAG,"登录成功");
                        SharePreferenceManager.setCachedPsw("123456");
                        UserInfo myInfo = JMessageClient.getMyInfo();
                        File avatarFile = myInfo.getAvatarFile();
                        Log.i(TAG,"avatarFile:"+avatarFile);
                        //登陆成功,如果用户有头像就把头像存起来,没有就设置null
                        if (avatarFile != null) {
                            SharePreferenceManager.setCachedAvatarPath(avatarFile.getAbsolutePath());
                        } else {
                            SharePreferenceManager.setCachedAvatarPath(null);
                        }
                        String username = myInfo.getUserName();
                        String appKey = myInfo.getAppKey();
                        UserEntry user = UserEntry.getUser(username, appKey);
                        if (null == user) {
                            user = new UserEntry(username, appKey);
                            user.save();
                        }
                    }
                }
            });

            //注册IM
            JMessageClient.register("kanjian"+UserUtils.getUserID(this), "123456", new BasicCallback() {
                @Override
                public void gotResult(int i, String s) {
                    if(s.equals("Success")){
                        SharePreferenceManager.setRegisterName("kanjian"+UserUtils.getUserID(WelcomeAct.this));
                        SharePreferenceManager.setRegistePass("123456");
                    }else if(s.equals("user exist")){
                    }
                }
            });
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Router.newIntent(WelcomeAct.this)
                        .to(MainParentAct.class)
                        .launch();
                onBack();
            }
        },2000);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    public XPresent bindPresent() {
        return null;
    }

    @Override
    public void setListener() {

    }
}
