package cn.see.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.zhy.autolayout.config.AutoLayoutConifg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.NetProvider;
import cn.droidlover.xdroidmvp.net.RequestHandler;
import cn.droidlover.xdroidmvp.net.XApi;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import cn.see.chat.database.UserEntry;
import cn.see.chat.entity.NotificationClickEventReceiver;
import cn.see.chat.pickerimage.utils.StorageUtil;
import cn.see.chat.utils.SharePreferenceManager;
import cn.see.chat.utils.imagepicker.GlideImageLoader;
import cn.see.chat.utils.imagepicker.ImagePicker;
import cn.see.chat.utils.imagepicker.view.CropImageView;
import cn.see.util.constant.PreferenceConstant;
import cn.see.util.version.PreferenceUtils;
import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

import static cn.see.util.glide.lookbig.MyImageAdapter.TAG;

/**
 * Created by gxb on 2017/12/6.
 */

public class App extends com.activeandroid.app.Application {
    public static Context context;
    public static App app;
    private Context mContext;
    public static final String CONV_TITLE = "conv_title";
    public static final int IMAGE_MESSAGE = 1;
    public static final int TAKE_PHOTO_MESSAGE = 2;
    public static final int TAKE_LOCATION = 3;
    public static final int FILE_MESSAGE = 4;
    public static final int TACK_VIDEO = 5;
    public static final int TACK_VOICE = 6;
    public static final int BUSINESS_CARD = 7;
    public static final int REQUEST_CODE_SEND_FILE = 26;


    public static final int RESULT_CODE_ALL_MEMBER = 22;
    public static Map<Long, Boolean> isAtMe = new HashMap<>();
    public static Map<Long, Boolean> isAtall = new HashMap<>();
    public static List<Message> forwardMsg = new ArrayList<>();

    public static long registerOrLogin = 1;
    public static final int REQUEST_CODE_TAKE_PHOTO = 4;
    public static final int REQUEST_CODE_SELECT_PICTURE = 6;
    public static final int REQUEST_CODE_CROP_PICTURE = 18;
    public static final int REQUEST_CODE_CHAT_DETAIL = 14;
    public static final int RESULT_CODE_FRIEND_INFO = 17;
    public static final int REQUEST_CODE_ALL_MEMBER = 21;
    public static final int RESULT_CODE_EDIT_NOTENAME = 29;
    public static final String NOTENAME = "notename";
    public static final int REQUEST_CODE_AT_MEMBER = 30;
    public static final int RESULT_CODE_AT_MEMBER = 31;
    public static final int RESULT_CODE_AT_ALL = 32;
    public static final int SEARCH_AT_MEMBER_CODE = 33;

    public static final int RESULT_BUTTON = 2;
    public static final int START_YEAR = 1900;
    public static final int END_YEAR = 2050;
    public static final int RESULT_CODE_SELECT_FRIEND = 23;

    public static final int REQUEST_CODE_SELECT_ALBUM = 10;
    public static final int RESULT_CODE_SELECT_ALBUM = 11;
    public static final int RESULT_CODE_SELECT_PICTURE = 8;
    public static final int REQUEST_CODE_BROWSER_PICTURE = 12;
    public static final int RESULT_CODE_BROWSER_PICTURE = 13;
    public static final int RESULT_CODE_SEND_LOCATION = 25;
    public static final int RESULT_CODE_SEND_FILE = 27;
    public static final int REQUEST_CODE_SEND_LOCATION = 24;
    public static final int REQUEST_CODE_FRIEND_INFO = 16;
    public static final int RESULT_CODE_CHAT_DETAIL = 15;
    public static final int ON_GROUP_EVENT = 3004;
    public static final String DELETE_MODE = "deleteMode";
    public static final int RESULT_CODE_ME_INFO = 20;

    public static final String DRAFT = "draft";
    public static final String GROUP_ID = "groupId";
    public static final String POSITION = "position";
    public static final String MsgIDs = "msgIDs";
    public static final String NAME = "name";
    public static final String ATALL = "atall";
    public static final String SEARCH_AT_MEMBER_NAME = "search_at_member_name";
    public static final String SEARCH_AT_MEMBER_USERNAME = "search_at_member_username";
    public static final String SEARCH_AT_APPKEY = "search_at_appkey";

    public static final String MEMBERS_COUNT = "membersCount";

    public static String PICTURE_DIR = "sdcard/JChatDemo/pictures/";
    private static final String JCHAT_CONFIGS = "JChat_configs";
    public static String FILE_DIR = "sdcard/JChatDemo/recvFiles/";
    public static String VIDEO_DIR = "sdcarVIDEOd/JChatDemo/sendFiles/";
    public static final String TARGET_ID = "targetId";
    public static final String ATUSER = "atuser";
    public static final String TARGET_APP_KEY = "targetAppKey";
    public static int maxImgCount;               //允许选择图片最大数
    public static final String GROUP_NAME = "groupName";


    public static List<GroupInfo> mGroupInfoList = new ArrayList<>();
    public static List<UserInfo> mFriendInfoList = new ArrayList<>();
    public static List<UserInfo> mSearchGroup = new ArrayList<>();
    public static List<UserInfo> mSearchAtMember = new ArrayList<>();
    public static List<Message> ids = new ArrayList<>();
    public static List<UserInfo> alreadyRead = new ArrayList<>();
    public static List<UserInfo> unRead = new ArrayList<>();
    public static List<String> forAddFriend = new ArrayList<>();

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

        //关于IM
        initIm();

        //分享登录初始化
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "");
        //平台配置
        PlatformConfig.setWeixin("wxc7b4805b625b5c3d","6cdab81c9f836a9cb39b9dfea84c0743");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad","http://sns.whalecloud.com");


        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
    }

    private void initIm() {
        StorageUtil.init(context, null);
        Fresco.initialize(getApplicationContext());
        JMessageClient.init(getApplicationContext(), true);
        JMessageClient.setDebugMode(true);
        SharePreferenceManager.init(getApplicationContext(), "JChat_configs");
        //设置Notification的模式
        JMessageClient.setNotificationFlag(JMessageClient.FLAG_NOTIFY_WITH_SOUND | JMessageClient.FLAG_NOTIFY_WITH_LED | JMessageClient.FLAG_NOTIFY_WITH_VIBRATE);
        //注册Notification点击的接收器
        new NotificationClickEventReceiver(getApplicationContext());
        initImagePicker();
    }


    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(true);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(maxImgCount);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
    }

    public static void setPicturePath(String appKey) {
        if (!SharePreferenceManager.getCachedAppKey().equals(appKey)) {
            SharePreferenceManager.setCachedAppKey(appKey);
            PICTURE_DIR = "sdcard/JChatDemo/pictures/" + appKey + "/";
        }
    }

    public static UserEntry getUserEntry() {
        return UserEntry.getUser(JMessageClient.getMyInfo().getUserName(), JMessageClient.getMyInfo().getAppKey());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }

    public static Context getContext() {
        return context;
    }

    public static App getInstance() {
        return app;
    }



}
