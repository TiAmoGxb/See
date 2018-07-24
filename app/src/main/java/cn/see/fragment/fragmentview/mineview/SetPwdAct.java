package cn.see.fragment.fragmentview.mineview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.see.R;
import cn.see.base.BaseActivity;
import cn.see.presenter.minep.SetPwdPresnter;

public class SetPwdAct extends BaseActivity<SetPwdPresnter>{

    private static final String TAG = "SetPwdAct" ;
    @BindView(R.id.title_tv_base)
    TextView title;
    @BindView(R.id.title_tv_op)
    TextView opTv;
    @BindView(R.id.et_pwdc_current)
    EditText et_pwdc_current;
    @BindView(R.id.et_pwd_q)
    EditText et_pwd_q;
    @BindView(R.id.et_pwd_new)
    EditText et_pwd_new;

    @OnClick(R.id.title_tv_op)
    void comte(){
        String et_c = et_pwdc_current.getText().toString().trim();
        String et_q = et_pwd_q.getText().toString().trim();
        String et_n = et_pwd_new.getText().toString().trim();
        if(!et_c.equals("")&&!et_q.equals("")&&!et_n.equals("")){
            getP().setPwd(et_c,et_q,et_n);
        }
    }

    @OnClick(R.id.back_rela)
    void bacView(){
        onBack();
    }

    @Override
    public void initView() {
        title.setText("修改密码");
        opTv.setText("确定");
        opTv.setVisibility(View.VISIBLE);
    }

    @Override
    public void initAfter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_set_pwd;
    }

    @Override
    public SetPwdPresnter bindPresent() {
        return new SetPwdPresnter();
    }

    @Override
    public void setListener() {

    }
    /**
     * 修改成功
     */
    public void setSusess(){
        onBack();
    }
}
