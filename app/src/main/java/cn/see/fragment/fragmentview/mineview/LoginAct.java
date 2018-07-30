package cn.see.fragment.fragmentview.mineview;

import android.content.Intent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.router.Router;
import cn.see.R;
import cn.see.base.BaseActivity;
import cn.see.main.WebAct;
import cn.see.model.LoginModel;
import cn.see.presenter.minep.LoginPresenter;
import cn.see.util.ToastUtil;
import cn.see.util.UserUtils;
import cn.see.util.constant.HttpConstant;
import cn.see.util.constant.IntentConstant;
import cn.see.util.constant.PreferenceConstant;
import cn.see.util.version.ApkUtils;
import cn.see.util.version.PreferenceUtils;

/**
 * @日期：2018/6/5
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 登录
 */
public class LoginAct extends BaseActivity<LoginPresenter> {


    private static final String TAG = "LoginAct" ;
    @BindView(R.id.title_tv_base)
    TextView titles;
    @BindView(R.id.right_img_top)
    ImageView rightImg;
    @BindView(R.id.image_rela)
    RelativeLayout rightRela;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @OnClick(R.id.reg_tv)
    void reg(){
        Router.newIntent(this)
                .to(WebAct.class)
                .putString(IntentConstant.WEB_LOAD_URL, HttpConstant.REG_USER)
                .launch();
    }
    @OnClick(R.id.rtp_tv)
    void rtp(){
        Router.newIntent(this)
                .to(WebAct.class)
                .putString(IntentConstant.WEB_LOAD_URL, HttpConstant.REG_RTP)
                .launch();
    }

    @OnClick(R.id.back_rela)
    void bacAct(){
        onBack();
    }

    @OnClick(R.id.login_but)
    void login(){
        String phone = getEditText(etPhone);
        String pwd = getEditText(etPwd);
        if(getP().isCheckInput(phone,pwd)){
            getP().login(phone,pwd,"");
        }
    }
    @Override
    public void initView() {
        titles.setText("登录");
    }

    @Override
    public void initAfter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_login;
    }

    @Override
    public LoginPresenter bindPresent() {
        return new LoginPresenter();
    }

    @Override
    public void setListener() {

    }

    public void loginResponse(LoginModel model){
        getP().progress.dismiss();
        //存入已登录标记
        UserUtils.setLoginFlag(this);
        UserUtils.setUserID(this,model.getResult().getUid());
        String string = PreferenceUtils.getString(this, PreferenceConstant.REGISTRATION_ID);
        //绑定设备唯一标识
        getP().setCid(string);
        //存入用户名this
        UserUtils.setUserPhone(this,getEditText(etPhone));
        Intent intent = new Intent();
        setResult(1,intent);
        onBack();
    }

    public void showInputError(String error){
        ToastUtil.showToast(error);
    }
}