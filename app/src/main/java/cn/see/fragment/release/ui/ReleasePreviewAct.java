package cn.see.fragment.release.ui;

import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.see.R;
import cn.see.base.BaseActivity;
import cn.see.util.ToastUtil;

/**
 * 发布预览
 */
public class ReleasePreviewAct extends BaseActivity {

    @BindView(R.id.title_tv_base)
    TextView title;

    @OnClick(R.id.rel_tv)
    void release(){
        ToastUtil.showToast("发布");
    }

    @OnClick(R.id.back_rela)
    void bacView(){
        onBack();
    }

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
