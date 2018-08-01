package cn.see.fragment.fragmentview.mineview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.router.Router;
import cn.see.R;
import cn.see.base.BaseActivity;
import cn.see.fragment.fragmentview.homeview.SelectMyTableAct;
import cn.see.util.ToastUtil;
import cn.see.util.constant.IntentConstant;

/**
 * 完善用户信息
 */
public class PrefectUserInfoAct extends BaseActivity {

    @BindView(R.id.title_tv_base)
    TextView title;
    @BindView(R.id.title_tv_op)
    TextView title_tv_op;
    @BindView(R.id.xq_tv)
    TextView xq_tv;
    @BindView(R.id.school_tv)
    TextView school_tv;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.img)
    ImageView img;


    @OnClick(R.id.title_tv_op)
    void com(){
        ToastUtil.showToast("完成");
    }

    @OnClick(R.id.xq_rela)
    void rela(){
        Router.newIntent(this)
                .to(SelectMyTableAct.class)
                .putInt(IntentConstant.SEL_TAB_TYPE,0)
                .requestCode(0)
                .launch();
    }

    @OnClick(R.id.school_rela)
    void school_rela(){
        Router.newIntent(this)
                .to(MineSchoolAct.class)
                .putInt(IntentConstant.SCHOOL_TYPE,0)
                .requestCode(1)
                .launch();
    }



    @OnClick(R.id.back_rela)
    void bacView(){
        onBack();
    }


    @Override
    public void initView() {
        title.setText("绑定账号");
        title_tv_op.setText("完成");
        title_tv_op.setVisibility(View.VISIBLE);
    }

    @Override
    public void initAfter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_prefect_user_info;
    }

    @Override
    public XPresent bindPresent() {
        return null;
    }

    @Override
    public void setListener() {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            if(requestCode == 0){
                String tab = data.getStringExtra("tab");
                xq_tv.setText(tab);
            }else if(requestCode == 1){
                String tab = data.getStringExtra("school");
                school_tv.setText(tab);
            }
        }
    }
}
