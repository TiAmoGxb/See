package cn.see.fragment.fragmentview.mineview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.see.R;
import cn.see.base.BaseActivity;
import cn.see.util.UserUtils;
import cn.see.util.widet.AlertView.AlertView;
import cn.see.util.widet.AlertView.OnDismissListener;
import cn.see.util.widet.AlertView.OnItemClickListener;

/**
 * 设置
 */
public class SettIngAct extends BaseActivity implements OnItemClickListener, OnDismissListener {



    @BindView(R.id.title_tv_base)
    TextView title;

    @OnClick(R.id.set_pwd_rela)
    void setPwd(){
        openActivity(SetPwdAct.class);
    }

    @OnClick(R.id.set_not_rela)
    void setNot(){
        openActivity(NoctionAct.class);
    }
    @OnClick(R.id.set_ys_rela)
    void setYs(){
        openActivity(PrivateAct.class);
    }
    @OnClick(R.id.set_with_rela)
    void setWith(){
        openActivity(WithMineAct.class);
    }
    @OnClick(R.id.set_pro_rela)
    void setPro(){
        openActivity(FeedbackAct.class);
    }
    @OnClick(R.id.exit_login)
    void exitLogin(){
        AlertView alertView = new AlertView(null, null, "取消", null, new String[]{"退出登录"}, this, AlertView.Style.ActionSheet, this);
        alertView.setCancelable(true);
        alertView.show();
    }

    @OnClick(R.id.back_rela)
    void bacView(){
        onBack();
    }

    @Override
    public void initView() {
        title.setText("设置");

    }

    @Override
    public void initAfter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_sett_ing;
    }

    @Override
    public XPresent bindPresent() {
        return null;
    }

    @Override
    public void setListener() {

    }

    @Override
    public void onDismiss(Object o) {

    }

    @Override
    public void onItemClick(Object o, int position) {
        UserUtils.removeUserLogin(this);
        UserUtils.removeUserID(this);
        UserUtils.removeUserPhone(this);
        onBack();
    }
}
