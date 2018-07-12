package cn.see.fragment.release.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.see.R;
import cn.see.base.BaseActivity;

/**
 * 发布隐私设置
 */
public class ReleaseSetAct extends BaseActivity {

    @BindView(R.id.title_tv_base)
    TextView title;
    @BindView(R.id.title_tv_op)
    TextView tvOP;
    @OnClick(R.id.back_rela)
    void bacView(){
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

    }
}
