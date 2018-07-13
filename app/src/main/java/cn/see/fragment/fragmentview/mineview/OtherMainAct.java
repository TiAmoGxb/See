package cn.see.fragment.fragmentview.mineview;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.zhy.autolayout.AutoLinearLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.router.Router;
import cn.see.R;
import cn.see.adapter.CommonListViewAdapter;
import cn.see.app.App;
import cn.see.base.BaseActivity;
import cn.see.fragment.fragmentview.findview.PhotoViewActivity;
import cn.see.model.MineTextModel;
import cn.see.model.UserInfoModel;
import cn.see.presenter.minep.OtherUserPresenter;
import cn.see.util.FastBlurUtil;
import cn.see.util.ListViewScroAplaUtil;
import cn.see.util.ToastUtil;
import cn.see.util.UserUtils;
import cn.see.util.constant.IntentConstant;
import cn.see.util.glide.GlideDownLoadImage;
import cn.see.util.glide.GlideRoundTransform;
import cn.see.util.widet.CircleImageView;
import cn.see.util.widet.putorefresh.PullToRefreshBase;
import cn.see.util.widet.putorefresh.PullToRefreshListView;
import cn.see.util.widet.putorefresh.RefreshShowTime;

/**
 * @日期：2018/6/12
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 他人主页
 */
public class OtherMainAct extends BaseActivity<OtherUserPresenter> implements PullToRefreshBase.OnRefreshListener2<ListView>  {
    private static final String TAG = "OtherMainAct" ;
    private List<MineTextModel.MineTextResult.ResultList> resultsList = new ArrayList<>();
    private CommonListViewAdapter<MineTextModel.MineTextResult.ResultList> adapter;
    private View topView;
    private TextView userName;
    private TextView userSin;
    private TextView attCont;
    private TextView fansCont;
    private TextView likeCont;
    private TextView senMsgTv;
    private TextView tvAttTv;
    private CircleImageView userImg;
    private ImageView bacImg;
    private ImageView erCodeImg;
    private String fromID;
    private int headerHeight;
    private String attention_status;
    private List<String> urls;
    private String topSize = "0";
    private String textSize = "0";
    private boolean isRefresh = true;

    @BindView(R.id.rela_title)
    RelativeLayout layout;
    @BindView(R.id.title_tv_base)
    TextView title;
    @BindView(R.id.pull_other_list)
    PullToRefreshListView listView;
    @BindView(R.id.title_img)
    ImageView titleImg;

    @OnClick(R.id.back_rela)
    void bacView(){
        onBack();
    }

    @Override
    public void initView() {
        setTranslucentStatus(true,this);
        title.setVisibility(View.GONE);
        title.setTextColor(getResources().getColor(R.color.back_f));
        topView = View.inflate(this,R.layout.layout_other_top_view,null);
        userImg = (CircleImageView) topView.findViewById(R.id.user_img);
        userName =(TextView) topView.findViewById(R.id.user_name);
        bacImg =(ImageView) topView.findViewById(R.id.mine_bac);
        userSin =(TextView) topView.findViewById(R.id.user_signature);
        attCont =(TextView) topView.findViewById(R.id.att_cont);
        fansCont =(TextView) topView.findViewById(R.id.fans_cont);
        likeCont =(TextView) topView.findViewById(R.id.like_cont);
        erCodeImg =(ImageView) topView.findViewById(R.id.set_user_data);
        senMsgTv =(TextView) topView.findViewById(R.id.tv_send_msg);
        tvAttTv =(TextView) topView.findViewById(R.id.tv_att);
        RefreshShowTime.showTime(listView);
        listView.getRefreshableView().addHeaderView(topView);
        listView.setAdapter(getP().initAdapter(resultsList));
        fromID = getIntent().getStringExtra(IntentConstant.OTHER_USER_ID);
        urls = new ArrayList<>();
        Log.i(TAG,"fromID："+fromID);
    }

    @Override
    public void initAfter() {
        getP().getUserInfo(fromID,UserUtils.getUserID(this));
        getP().getUserText(fromID,UserUtils.getUserID(this),topSize,textSize);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_other_main;
    }

    @Override
    public OtherUserPresenter bindPresent() {
        return new OtherUserPresenter();
    }

    @Override
    public void setListener() {
        listView.setOnRefreshListener(this);
        erCodeImg.setOnClickListener(this);
        senMsgTv.setOnClickListener(this);
        tvAttTv.setOnClickListener(this);
        userImg.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 //openActivity(ArticleDetailsAct.class);
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    if(firstVisibleItem>0){
                        titleImg.setVisibility(View.VISIBLE);
                        title.setVisibility(View.VISIBLE);
                    }else{
                        titleImg.setVisibility(View.GONE);
                        title.setVisibility(View.GONE);
                    }
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){

            case R.id.user_img:
                Router.newIntent(this)
                        .to(PhotoViewActivity.class)
                        .putInt(IntentConstant.LOOK_BIG_IMG_INDEX,0)
                        .putSerializable(IntentConstant.LOOK_BIG_IMG_URLS, (Serializable) urls)
                        .launch();
                break;

            case R.id.set_user_data:
                Router.newIntent(this)
                        .to(QrCodeAct.class)
                        .putString(IntentConstant.USER_ID_QRCODE,fromID)
                        .launch();
                break;
            case R.id.tv_send_msg:
                ToastUtil.showToast("私信");
                break;
            case R.id.tv_att:
                if(attention_status.equals("1")||attention_status.equals("2")){
                    getP().removeAttUser(UserUtils.getUserID(this),fromID);
                }else{
                    getP().setAttUser(UserUtils.getUserID(this),fromID);
                }
                break;
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        topSize = "0";
        textSize = "0";
        isRefresh  = true;
        initAfter();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        isRefresh  = false;
        initAfter();
    }


    public void userInfoResPonse(final UserInfoModel.UserInfoResult result){
        urls.clear();
        urls.add(result.getHead_url());
        GlideDownLoadImage.getInstance().loadImage(this,result.getBackground_url(),titleImg);
        attention_status = result.getAttention_status();
        if(attention_status.equals("1")||attention_status.equals("2")){
            tvAttTv.setText("已关注");
            tvAttTv.setBackground(getResources().getDrawable(R.drawable.shap_but_att_yes_bg));
        }else{
            tvAttTv.setText("关注");
            tvAttTv.setBackground(getResources().getDrawable(R.drawable.shap_but_att_yes_bg_oher));
        }
        title.setText(result.getNickname());
        userName.setText(result.getNickname());
        attCont.setText(result.getAttention_count());
        fansCont.setText(result.getFans_count());
        likeCont.setText(result.getLike_count());
        if(result.getSignature()==null||result.getSignature().trim().equals("")){
            userSin.setText("这家伙很懒，什么都没有留下...");
        }else{
            userSin.setText(result.getSignature());
        }
        GlideDownLoadImage.getInstance().loadCircleImageToCust(result.getHead_url(),userImg);
        GlideDownLoadImage.getInstance().loadImage(this,result.getBackground_url(),bacImg);
    }

    public void userTextResponse(MineTextModel.MineTextResult result){
        topSize = result.getTopicSize();
        textSize = result.getTextSize();
        List<MineTextModel.MineTextResult.ResultList> lists = result.getLists();
        if(!isRefresh){
            if(lists.size()==0){
                ToastUtil.showToast("暂无更多文章");
            }else{
                resultsList.addAll(lists);
                adapter.notifyDataSetChanged();
            }
        }else{
            resultsList.clear();
            resultsList.addAll(lists);
            adapter = getP().initAdapter(resultsList);
            listView.setAdapter(adapter);
        }
        getP(). progress.dismiss();
        listView.onRefreshComplete();

    }


    /**
     * 关注成功
     * @param msg
     */
    public void attUserResponse(String msg){
        ToastUtil.showToast(msg);
        getP().getUserInfo(fromID,UserUtils.getUserID(this));
        getP(). progress.dismiss();
    }

    /**
     * 取关成功
     * @param msg
     */
    public void removeAttResponse(String msg){
        ToastUtil.showToast(msg);
        getP().getUserInfo(fromID,UserUtils.getUserID(this));
        getP(). progress.dismiss();
    }

    /**
     * 点赞成功回调
     * 更新被点赞的条目
     * @param msg
     */
    public void likeResponse(String msg,int positon){
        ToastUtil.showToast(msg);
        MineTextModel.MineTextResult.ResultList resultList = resultsList.get(positon);
        int i = Integer.parseInt(resultList.getLike_count());
        resultList.setLike_count((i+1)+"");
        resultList.setLike_status("1");
        adapter.notifyDataSetChanged();
    }
}
