package cn.see.main;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;
import cn.droidlover.xdroidmvp.router.Router;
import cn.see.R;
import cn.see.base.BaseActivity;
import cn.see.fragment.FindFragment;
import cn.see.fragment.HomeFragment;
import cn.see.fragment.MineFragment;
import cn.see.fragment.NewsFragment;
import cn.see.fragment.release.ui.BeautifulPictureAct;
import cn.see.model.FindActModel;
import cn.see.util.ToastUtil;
import cn.see.util.UserUtils;
import cn.see.util.constant.HttpConstant;
import cn.see.util.constant.IntentConstant;
import cn.see.util.glide.GlideDownLoadImage;
import cn.see.util.http.Api;
import cn.see.util.widet.BlurringView;
import cn.see.util.widet.PopupWindowHelper;
import cn.see.util.widet.XRadioGroup;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * @日期：2018/6/4
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 程序主页Act
 */
public class MainParentAct extends BaseActivity implements XRadioGroup.OnCheckedChangeListener{

    private static final String TAG = "MainParentAct" ;
    public static final int RELAEASE_IMAGE = 0;
    public static final int RELAEASE_TOPIC = 1;
    private HomeFragment homeFragment;
    private FindFragment findFragment;
    private NewsFragment newsFragment;
    private MineFragment mineFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private ArrayList<Fragment> fragments;
    private View popView;
    private PopupWindowHelper helper;
    private BlurringView blurringView;
    private TextView actName;
    private ImageView actImg;
    private String actId;
    private Intent intent;


    @BindView(R.id.main_rela)
    RelativeLayout main_rela;
    @BindView(R.id.radioGroup1)
    XRadioGroup xRadioGroup;

    @OnClick(R.id.rb_release)
    void release(){

        helper.showFromBottom(xRadioGroup);
        blurringView.setBlurredView(main_rela);
        blurringView.invalidate();
    }

    @Override
    public void initView() {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        popView = LayoutInflater.from(this).inflate(R.layout.layout_release_view, null);
        blurringView = popView.findViewById(R.id.blurring_view);
        actName =(TextView) popView.findViewById(R.id.act_name);
        actImg = (ImageView) popView.findViewById(R.id.act_img);
        fragments = new ArrayList<>();
        homeFragment = new HomeFragment();
        findFragment = new FindFragment();
        newsFragment = new NewsFragment();
        mineFragment = new MineFragment();
        fragments.add(homeFragment);
        fragments.add(findFragment);
        fragments.add(newsFragment);
        fragments.add(mineFragment);
    }

    @Override
    public void initAfter() {
        intent = new Intent(MainParentAct.this, MultiImageSelectorActivity.class);
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 9);
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
        helper = new PopupWindowHelper(popView);
        fragmentTransaction.add(R.id.content_main, homeFragment);
        fragmentTransaction.add(R.id.content_main, findFragment);
        fragmentTransaction.add(R.id.content_main, newsFragment);
        fragmentTransaction.add(R.id.content_main, mineFragment);
        fragmentTransaction.show(findFragment).hide(homeFragment).hide(newsFragment).hide(mineFragment);
        fragmentTransaction.commit();
        getTextAct();
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_main_parent;
    }

    @Override
    public XPresent bindPresent() {
        return null;
    }

    @Override
    public void setListener() {
        popView.findViewById(R.id.colose_img).setOnClickListener(this);
        popView.findViewById(R.id.photo_rela).setOnClickListener(this);
        popView.findViewById(R.id.topic_rela).setOnClickListener(this);
        popView.findViewById(R.id.video_rela).setOnClickListener(this);
        popView.findViewById(R.id.zb_rela).setOnClickListener(this);
        blurringView.setOnClickListener(this);
        actImg.setOnClickListener(this);
        xRadioGroup.setOnCheckedChangeListener(this);
    }


    @Override
    public void onCheckedChanged(XRadioGroup group, int checkedId) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (checkedId) {
            case R.id.rb_home:
                switchFragment(0);
                setTranslucentStatus(false,this);
                break;
            case R.id.rb_find:
                switchFragment(1);
                setTranslucentStatus(false,this);
                break;
            case R.id.rb_news:
                switchFragment(2);
                setTranslucentStatus(false,this);
                break;
            case R.id.rb_mine:
                switchFragment(3);
                transaction.replace(R.id.content_main, mineFragment);
                BaseActivity.setStatusBarTo(getResources().getColor(R.color.black),getWindow());
                break;
        }
    }

    /**
     * 选择隐藏与显示的Fragment
     * @param index 显示的Frgament的角标
     */
    private void switchFragment(int index){
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        for(int i = 0; i < fragments.size(); i++){
            if (index == i){
                fragmentTransaction.show(fragments.get(index));
            }else {
                fragmentTransaction.hide(fragments.get(i));
            }
        }
        fragmentTransaction.commit();
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.blurring_view:
                helper.dismiss();
                break;
            case R.id.colose_img:
                helper.dismiss();
                break;
            case R.id.photo_rela:
                helper.dismiss();
                startActivityForResult(intent, RELAEASE_IMAGE);
                break;
            case R.id.topic_rela:
                helper.dismiss();
                startActivityForResult(intent, RELAEASE_TOPIC);
                break;
            case R.id.video_rela:
                ToastUtil.showToast("程序员还在搬砖 敬请期待");
                break;
            case R.id.zb_rela:
                ToastUtil.showToast("程序员还在搬砖 敬请期待");
                break;
            case R.id.act_img:
                Router.newIntent(this)
                        .to(WebAct.class)
                        .putString(IntentConstant.WEB_LOAD_URL,HttpConstant.ACT_WEB_URL+actId)
                        .launch();
                break;
        }
    }


    /**
     * 获取活动列表
     */
    public void getTextAct(){
        Api.findService().getFindAct(UserUtils.getUserID(this),1,"1")
                .compose(XApi.<FindActModel>getApiTransformer())
                .compose(XApi.<FindActModel>getScheduler())
                .subscribe(new ApiSubscriber<FindActModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        ToastUtil.showToast(HttpConstant.NET_ERROR_MSG);
                    }
                    @Override
                    public void onNext(FindActModel tabModel) {
                        if(!tabModel.isError()){
                            actResponse(tabModel.getResult());
                        }else{
                            ToastUtil.showToast(tabModel.getErrorMsg());
                        }
                    }
                });
    }
    private void actResponse(FindActModel.ActResult actResult) {
        actId = actResult.getLists().get(0).getActivity_id();
        actName.setText(actResult.getLists().get(0).getName());
        GlideDownLoadImage.getInstance().loadCircleImageRoleFxy(actResult.getLists().get(0).getUrl(),actImg,4);
    }


    /**
     * 发布模块 选择的照片回传
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG,"requestCode:"+requestCode);
        ArrayList<String> pathList = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
        Router to = Router.newIntent(this)
                .to(BeautifulPictureAct.class);

        if(requestCode == RELAEASE_IMAGE){
            to.putString(IntentConstant.RELEASE_TYPE,"text");
        }else if(requestCode == RELAEASE_TOPIC){
            to.putString(IntentConstant.RELEASE_TYPE,"topic");
        }
        if(pathList!=null){
            to.putSerializable(IntentConstant.RELEASE_PATHS,pathList)
                    .launch();
        }
    }
}
