package cn.see.fragment.fragmentview.mineview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.see.R;
import cn.see.base.BaseActivity;

/**
 * 我的标签
 */
public class MineTabAct extends BaseActivity {

    @BindView(R.id.title_tv_base)
    TextView title;
    @BindView(R.id.title_tv_op)
    TextView opTv;

    @Override
    public void initView() {
        title.setText("个性标签");
        opTv.setText("保存");
        opTv.setVisibility(View.VISIBLE);
    }

    @Override
    public void initAfter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_mine_tab;
    }

    @Override
    public XPresent bindPresent() {
        return null;
    }

    @Override
    public void setListener() {

    }
}
