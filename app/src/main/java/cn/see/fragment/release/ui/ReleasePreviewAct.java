package cn.see.fragment.release.ui;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.router.Router;
import cn.see.R;
import cn.see.adapter.RecryCommonAdapter;
import cn.see.base.BaseActivity;
import cn.see.fragment.release.presenter.ReleasePreviewPresenter;
import cn.see.util.SpaceItemDecoration;
import cn.see.util.ToastUtil;
import cn.see.util.constant.IntentConstant;
import cn.see.util.widet.CustomGridViewLayoutManager;

import static cn.see.R.mipmap.setting;

/**
 * 发布预览
 */
public class ReleasePreviewAct extends BaseActivity<ReleasePreviewPresenter> {

    private static final String TAG = "ReleasePreviewAct";
    private ArrayList<String> paths;
    private CustomGridViewLayoutManager gridViewLayoutManager;
    private RecryCommonAdapter<String> adapter;
    public static final int ADD_TEXT_CODE = 1;
    public static final int ADD_TAB_CODE = 2;
    public static final int ADD_TOPIC_CODE = 3;
    public static final int ADD_AREA_CODE = 4;

    @BindView(R.id.title_tv_base)
    TextView title;
    @BindView(R.id.image_rela)
    RelativeLayout layout;
    @BindView(R.id.right_img_top)
    ImageView titleImg;
    @BindView(R.id.img_recy)
    RecyclerView recyclerView;
    @BindView(R.id.msg_tv)
    TextView msgTv;

    @OnClick(R.id.msg_tv)
    void addText(){
        Router.newIntent(this)
                .requestCode(ADD_TEXT_CODE)
                .to(AddTextAct.class)
                .launch();
    }

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
        layout.setVisibility(View.VISIBLE);
        titleImg.setImageResource(R.mipmap.setting);

        //设置RecyclerView
        gridViewLayoutManager = new CustomGridViewLayoutManager(context, 3);
        gridViewLayoutManager.setScrollEnabled(true);
        recyclerView.addItemDecoration(new SpaceItemDecoration(3, 25, false));
        recyclerView.setLayoutManager(gridViewLayoutManager);
    }

    @Override
    public void initAfter() {
        paths = (ArrayList<String>) getIntent().getSerializableExtra(IntentConstant.RELEASE_PATHS);
        adapter = getP().initAdapter(paths);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_release_preview;
    }

    @Override
    public ReleasePreviewPresenter bindPresent() {
        return new ReleasePreviewPresenter();
    }

    @Override
    public void setListener() {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            if(requestCode == ADD_TEXT_CODE){
                String extra = data.getStringExtra(IntentConstant.TEXT_ADD);
                msgTv.setText(extra);
            }
        }

    }
}
