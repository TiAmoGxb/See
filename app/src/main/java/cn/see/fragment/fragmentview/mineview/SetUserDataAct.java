package cn.see.fragment.fragmentview.mineview;

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
 * @日期：2018/6/5
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 修改用户信息Act
 */
public class SetUserDataAct extends BaseActivity implements OnItemClickListener, OnDismissListener {

    @BindView(R.id.title_tv_base)
    TextView titles;

    @OnClick(R.id.back_rela)
    void bacAct(){
        onBack();
    }

    @OnClick(R.id.exit_login)
    void exitLogin(){
        AlertView alertView = new AlertView(null, null, "取消", null, new String[]{"退出登录"}, this, AlertView.Style.ActionSheet, this);
        alertView.setCancelable(true);
        alertView.show();
    }

    @Override
    public void initView() {
        titles.setText("编辑个人资料");
    }

    @Override
    public void initAfter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_set_user_data;
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
        if(position == 0){
            UserUtils.removeUserLogin(this);
            UserUtils.removeUserID(this);
            onBack();
        }
    }
}
