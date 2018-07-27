package cn.see.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.zhy.autolayout.config.AutoLayoutConifg;

import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.NetProvider;
import cn.droidlover.xdroidmvp.net.RequestHandler;
import cn.droidlover.xdroidmvp.net.XApi;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;
import cn.see.util.constant.PreferenceConstant;
import cn.see.util.version.PreferenceUtils;
import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

import static cn.see.util.glide.lookbig.MyImageAdapter.TAG;

/**
 * Created by gxb on 2017/12/6.
 */

public class App extends Application {
    public static Context context;
    public static App app;
    private Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        context = this;
        init();

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                mContext = activity;
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });

        XApi.registerProvider(new NetProvider() {

            @Override
            public Interceptor[] configInterceptors() {
                Interceptor interceptor = new TokenHeaderInterceptor();
                Interceptor resultinterceptor = new ResultInterceptor(mContext);
                return new Interceptor[]{interceptor, resultinterceptor};
            }

            @Override
            public void configHttps(OkHttpClient.Builder builder) {

            }

            @Override
            public CookieJar configCookie() {
                return null;
            }

            @Override
            public RequestHandler configHandler() {
                return null;
            }

            @Override
            public long configConnectTimeoutMills() {
                return 0;
            }

            @Override
            public long configReadTimeoutMills() {
                return 0;
            }

            @Override
            public boolean configLogEnable() {
                return true;
            }

            @Override
            public boolean handleError(NetError error) {
                return false;
            }
        });
    }

    private void init() {
        //初始化屏幕適配
        AutoLayoutConifg.getInstance().init(this);
        //初始化极光推送
        JPushInterface.setDebugMode(true);//设置调试模式，避免出现日志无打印情况
        JPushInterface.init(this);
        boolean state = JPushInterface.getConnectionState(this);
        Log.i("getRegistrationID","state:"+state);
        if(state){
            //获取RegistrationID
            String registrationId = JPushInterface.getRegistrationID(this);
            Log.i("getRegistrationID","getRegistrationID:"+registrationId);
            PreferenceUtils.setString(this, PreferenceConstant.REGISTRATION_ID,registrationId);
        }


        //分享登录初始化
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "");
        //平台配置
        PlatformConfig.setWeixin("wxc7b4805b625b5c3d","6cdab81c9f836a9cb39b9dfea84c0743");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad","http://sns.whalecloud.com");
    }

    public static Context getContext() {
        return context;
    }

    public static App getInstance() {
        return app;
    }



}
