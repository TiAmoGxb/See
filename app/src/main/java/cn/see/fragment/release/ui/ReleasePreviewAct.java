package cn.see.fragment.release.ui;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.router.Router;
import cn.see.R;
import cn.see.adapter.RecryCommonAdapter;
import cn.see.base.BaseActivity;
import cn.see.fragment.release.presenter.ReleasePreviewPresenter;
import cn.see.util.BitmapUtils;
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
    //添加文字
    public static final int ADD_TEXT_CODE = 1;
    //编辑图片
    public static final int SET_IMG = 2;
    //添加话题
    public static final int ADD_TOPIC_CODE = 3;
    //添加位置
    public static final int ADD_AREA_CODE = 4;
    //添加标签
    public static final int ADD_TAB_CODE = 5;
    //发布隐私设置
    public static final int ADD_RELEASE_SET = 6;

    //活动还是图片
    private String type;

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
    @BindView(R.id.tv_topic)
    TextView topTv;
    @BindView(R.id.tv_tab)
    TextView tv_tab;
    @BindView(R.id.add_topic_rela)
    RelativeLayout addTopicRela;
    @BindView(R.id.topic_title)
    TextView topic_title;
    @BindView(R.id.topic_title_v)
    View topic_title_v;


    @OnClick(R.id.image_rela)
    void releaseSet(){
        Router.newIntent(this)
                .requestCode(ADD_RELEASE_SET)
                .to(ReleaseSetAct.class)
                .launch();
    }

    @OnClick(R.id.msg_tv)
    void addText(){
        Router.newIntent(this)
                .requestCode(ADD_TEXT_CODE)
                .putString(IntentConstant.RELEASE_TYPE,type)
                .to(AddTextAct.class)
                .launch();
    }

    @OnClick(R.id.topic_title)
    void addTextTopic(){
        Router.newIntent(this)
                .requestCode(ADD_TEXT_CODE)
                .putString(IntentConstant.RELEASE_TYPE,type)
                .to(AddTextAct.class)
                .launch();
    }

    @OnClick(R.id.tv_topic)
    void selTop(){
        Router.newIntent(this)
                .requestCode(ADD_TOPIC_CODE)
                .to(SelectTopicAct.class)
                .launch();
    }
    @OnClick(R.id.tv_tab)
    void selTab(){
        Router.newIntent(this)
                .requestCode(ADD_TAB_CODE)
                .to(SelectTabAct.class)
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

        //设置RecyclerView
        gridViewLayoutManager = new CustomGridViewLayoutManager(context, 3);
        gridViewLayoutManager.setScrollEnabled(true);
        recyclerView.addItemDecoration(new SpaceItemDecoration(3, 25, false));
        recyclerView.setLayoutManager(gridViewLayoutManager);
    }

    @Override
    public void initAfter() {
        type = getIntent().getStringExtra(IntentConstant.RELEASE_TYPE);
        getP().isTypeUi(type);
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


    /**
     * 如果是话题
     */
    public void isTopicSetUi(){
        addTopicRela.setVisibility(View.GONE);
        layout.setVisibility(View.GONE);
        topic_title.setVisibility(View.VISIBLE);
        topic_title_v.setVisibility(View.VISIBLE);
        msgTv.setText("内容");
        msgTv.setTextColor(getResources().getColor(R.color.text_101010));
    }


    /**
     * 如果是图片
     */
    public void isTextSetUi(){
        addTopicRela.setVisibility(View.VISIBLE);
        layout.setVisibility(View.VISIBLE);
        titleImg.setImageResource(R.mipmap.setting);
    }


    /**
     * 图片点击继续编辑
     * @param position
     */
    public void imgeItem(int position){
        Router.newIntent(this)
                .to(BeautifulDetilsAct.class)
                .putString(IntentConstant.IMAGE_BEAU_PATH,paths.get(position))
                .putInt(IntentConstant.IMAGE_BEAU_GPU,-1)
                .putInt(IntentConstant.IMAGE_SEL_POSITION,position)
                .requestCode(SET_IMG)
                .launch();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            String extra;
            switch (requestCode){

                case ADD_TEXT_CODE:
                    extra = data.getStringExtra(IntentConstant.TEXT_ADD);
                    msgTv.setTextColor(getResources().getColor(R.color.text_101010));
                    msgTv.setText(extra);
                    break;
                case ADD_TOPIC_CODE:
                    extra = data.getStringExtra(IntentConstant.SEL_TOPIC_NAME);
                    topTv.setTextColor(getResources().getColor(R.color.text_101010));
                    topTv.setText(extra);
                    break;
                case SET_IMG:
                    String sdPath = data.getStringExtra(IntentConstant.IMAGE_BEAU_PATH);
                    int position = data.getIntExtra(IntentConstant.IMAGE_SEL_POSITION, -1);
                    paths.set(position,sdPath);
                    adapter.notifyDataSetChanged();
                    break;
            }

        }

    }
}
