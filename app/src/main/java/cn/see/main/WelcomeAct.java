package cn.see.main;

import android.os.Handler;

import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.router.Router;
import cn.see.R;
import cn.see.base.BaseActivity;

/**
 * @日期：2018/6/4
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 开屏欢迎页
 */

public class WelcomeAct extends BaseActivity {


    @Override
    public void initView() {
        //设置沉浸式状态栏
        setTranslucentStatus(true,this);
    }

    @Override
    public void initAfter() {
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
