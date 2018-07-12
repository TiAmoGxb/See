package cn.see.fragment.release.ui;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.see.R;
import cn.see.base.BaseActivity;
import cn.see.util.constant.IntentConstant;

/**
 * 发布隐私设置
 */
public class ReleaseSetAct extends BaseActivity {

    //默认是所有人可见
    private String showText = "3";

    @BindView(R.id.title_tv_base)
    TextView title;
    @BindView(R.id.title_tv_op)
    TextView tvOP;
    @BindView(R.id.ra_all)
    RadioButton raAllBt;
    @BindView(R.id.ra_my)
    RadioButton raMyBt;
    @BindView(R.id.ra_group)
    RadioGroup group;

    @OnClick(R.id.back_rela)
    void bacView(){
        onBack();
    }
    @OnClick(R.id.title_tv_op)
    void setShow(){
        Intent intent = new Intent();
        intent.putExtra(IntentConstant.RELEASE_SET_TEXT,showText);
        setResult(ReleasePreviewAct.ADD_RELEASE_SET,intent);
        onBack();
    }

    @Override
    public void initView() {
        title.setText("隐私设置");
        tvOP.setText("确定");
        tvOP.setVisibility(View.VISIBLE);
    }

    @Override
    public void initAfter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_release_set;
    }

    @Override
    public XPresent bindPresent() {
        return null;
    }

    @Override
    public void setListener() {
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                 if(checkedId == raAllBt.getId()){
                     showText = "3";
                 }else{
                     //0对应仅自己可见
                     showText = "0";
                 }
            }
        });
    }
}
