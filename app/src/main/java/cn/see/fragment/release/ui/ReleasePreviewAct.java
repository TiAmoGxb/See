package cn.see.fragment.release.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.see.R;
import cn.see.base.BaseActivity;

/**
 * 发布预览
 */
public class ReleasePreviewAct extends BaseActivity {

    @BindView(R.id.title_tv_base)
    TextView title;

    @Override
    public void initView() {
        title.setText("发布预览");
    }

    @Override
    public void initAfter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_release_preview;
    }

    @Override
    public XPresent bindPresent() {
        return null;
    }

    @Override
    public void setListener() {

    }
}
