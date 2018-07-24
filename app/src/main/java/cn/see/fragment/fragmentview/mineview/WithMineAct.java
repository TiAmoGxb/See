package cn.see.fragment.fragmentview.mineview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.see.R;
import cn.see.base.BaseActivity;
import cn.see.util.glide.GlideDownLoadImage;
import cn.see.util.version.ApkUtils;

/**
 * 关于我们
 */
public class WithMineAct extends BaseActivity {


    @BindView(R.id.title_tv_base)
    TextView title;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.text)
    TextView text;

    @OnClick(R.id.back_rela)
    void bacView(){
        onBack();
    }

    @Override
    public void initView() {
        title.setText("关于我们");
        text.setText("Android "+ ApkUtils.getVersionName(this)+"版");
        GlideDownLoadImage.getInstance().loadCircleImageRole(this,R.drawable.log1o,img,4
        );
    }

    @Override
    public void initAfter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_with_mine;
    }

    @Override
    public XPresent bindPresent() {
        return null;
    }

    @Override
    public void setListener() {

    }
}
