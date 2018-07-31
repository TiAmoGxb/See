package cn.see.fragment.fragmentview.mineview;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.router.Router;
import cn.see.R;
import cn.see.adapter.RecryCommonAdapter;
import cn.see.adapter.TextReviewAdapter;
import cn.see.base.BaseActivity;
import cn.see.model.MineTextModel;
import cn.see.model.TextReviewModel;
import cn.see.model.TxtModel;
import cn.see.presenter.findp.TextAriclePresenter;
import cn.see.util.CustomLinearLayoutManager;
import cn.see.util.ShareUtils;
import cn.see.util.SpannableUtils;
import cn.see.util.ToastUtil;
import cn.see.util.UserUtils;
import cn.see.util.VpHolder;
import cn.see.util.constant.BannerConstant;
import cn.see.util.constant.HttpConstant;
import cn.see.util.constant.IntentConstant;
import cn.see.util.glide.GlideDownLoadImage;
import cn.see.util.widet.AlertView.AlertView;
import cn.see.util.widet.AlertView.OnDismissListener;
import cn.see.util.widet.AlertView.OnItemClickListener;
import cn.see.util.widet.putorefresh.PullToRefreshBase;
import cn.see.util.widet.putorefresh.PullToRefreshListView;
import cn.see.util.widet.putorefresh.RefreshShowTime;

/**
 * @日期：2018/6/5
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 文章详情
 */
public class ArticleDetailsAct extends BaseActivity<TextAriclePresenter> implements PullToRefreshBase.OnRefreshListener2<ListView>,OnItemClickListener, OnDismissListener {

    private static final String TAG = "ArticleDetailsAct";
    private List<TextReviewModel.ReviewResult.ReviewList> reviewLists = new ArrayList<>();
    private TextReviewAdapter textReviewAdapter;
    private String text_id;
    private TextView reviewNumTv;
    private View topView;
    private ImageView userImg;
    private TextView userNameTv;
    private TextView timeTv;
    private TextView attTv;
    private VpHolder vpHolder;
    private TextView areaTv;
    private TextView contentTv;
    private TextView likeNumTv;
    private TextView commNumTv;
    private RecyclerView recyLike;
    private View botView;
    private AutoRelativeLayout botRela;
    private RecyclerView botRecy;
    private RecryCommonAdapter<TxtModel.TxtResult.Result> botAdapter;
    private AutoLinearLayout tabLin;
    private ImageView likeImg;
    private ImageView commImg;
    private AutoLinearLayout likeRela;
    private AutoLinearLayout commRela;
    private String to_id;
    private int screenHeight;
    private String from_id;
    private String isAtt;
    private List<String> urls;
    private boolean isInputShow = false;

    @BindView(R.id.title_tv_base)
    TextView titles;
    @BindView(R.id.right_img_top)
    ImageView rightImg;
    @BindView(R.id.image_rela)
    RelativeLayout rightRela;
    @BindView(R.id.pull_art_list)
    PullToRefreshListView listView;
    @BindView(R.id.rela_comm)
    RelativeLayout yesCommRela;
    @BindView(R.id.et_comm)
    EditText etCom;

    @OnClick(R.id.image_rela)
    void share(){
        ShareUtils.shareWeb(this, HttpConstant.SHARE_TEXT+text_id+"&uid="+UserUtils.getUserID(this),contentTv.getText().toString(),contentTv.getText().toString(),urls.get(0));
    }

    @OnClick(R.id.send_tv)
    void sendMsg(){
        if(etCom.getText().toString().trim().equals("")){
            ToastUtil.showToast("评论内容不能为空！");
            return;
        }
        getP().setReview(UserUtils.getUserID(ArticleDetailsAct.this),text_id,to_id,etCom.getText().toString());
    }

    @OnClick(R.id.back_rela)
    void bacAct(){
        onBack();
    }

    @Override
    public void initView() {
        titles.setText("文章详情");
        rightImg.setImageResource(R.drawable.top_share);
        text_id = getIntent().getStringExtra(IntentConstant.ARTIC_TEXT_ID);
        Log.i(TAG,"text_id:"+text_id);
        rightRela.setVisibility(View.VISIBLE);
        topView = View.inflate(this,R.layout.layout_text_arit_top_item,null);
        botView = View.inflate(this,R.layout.layout_textarit_other_txt_item,null);
        findView();
        listView.getRefreshableView().addHeaderView(topView);
        listView.getRefreshableView().addFooterView(botView);
        textReviewAdapter = new TextReviewAdapter(this,reviewLists,0,"0");
        listView.setAdapter(textReviewAdapter);
    }

    private void findView(){
        reviewNumTv = (TextView) topView.findViewById(R.id.text_review_num);
        userNameTv = (TextView) topView.findViewById(R.id.nick_name);
        contentTv = (TextView) topView.findViewById(R.id.text_content);
        areaTv = (TextView) topView.findViewById(R.id.area_add);
        attTv = (TextView) topView.findViewById(R.id.att_tv);
        timeTv = (TextView) topView.findViewById(R.id.create_time);
        likeNumTv = (TextView) topView.findViewById(R.id.like_cont);
        commNumTv = (TextView) topView.findViewById(R.id.comm_cont);
        userImg =(ImageView) topView.findViewById(R.id.user_icon);
        likeImg =(ImageView) topView.findViewById(R.id.zan_img);
        commImg =(ImageView) topView.findViewById(R.id.comment_img);
        recyLike =(RecyclerView) topView.findViewById(R.id.like_recy);
        botRela =(AutoRelativeLayout) botView.findViewById(R.id.rela);
        botRecy = (RecyclerView) botView.findViewById(R.id.txt_recy);
        tabLin = (AutoLinearLayout)topView.findViewById(R.id.rgChannel);
        likeRela = (AutoLinearLayout)topView.findViewById(R.id.like_rela);
        commRela = (AutoLinearLayout)topView.findViewById(R.id.comm_rela);
        CustomLinearLayoutManager linearLayoutManager = new CustomLinearLayoutManager(context);
        linearLayoutManager.setScrollEnabled(false);
        botRecy.setLayoutManager(linearLayoutManager);
        recyLike.setLayoutManager(new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.HORIZONTAL));
        vpHolder = new VpHolder(this,topView, BannerConstant.TXT_ARTICL_BANNER_TYPE);
    }

    @Override
    public void initAfter() {
        getP().getTopData(text_id,UserUtils.getUserID(this));
        getP().getTextReview(text_id, UserUtils.getUserID(this),true);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_article_details;
    }

    @Override
    public TextAriclePresenter bindPresent() {
        return new TextAriclePresenter();
    }

    @Override
    public void setListener() {
        listView.setOnRefreshListener(this);
        userImg.setOnClickListener(this);
        likeRela.setOnClickListener(this);
        commRela.setOnClickListener(this);
        reviewNumTv.setOnClickListener(this);
        attTv.setOnClickListener(this);
        topView.findViewById(R.id.share_img).setOnClickListener(this);
        topView.findViewById(R.id.set).setOnClickListener(this);
    }

    /**
     * 顶部数据
     * @param txtResult
     */
    public void topResponse(TxtModel.TxtResult.Result txtResult){
        from_id = txtResult.getUser_id();
        isAtt = txtResult.getAttention_status();
        //获取其他关联文章
        getP().getOtherText(txtResult.getUser_id(),text_id);
        //如果是自己 隐藏关注
        if(txtResult.getUser_id().equals(UserUtils.getUserID(this))){
            attTv.setVisibility(View.GONE);
        }else{
            if(txtResult.getAttention_status().equals("1")){
                attTv.setText("已关注");
                attTv.setBackground(getResources().getDrawable(R.drawable.shap_but_att_yes_bg));
                attTv.setTextColor(getResources().getColor(R.color.back_f));
            }else{
                attTv.setText("关注");
                attTv.setBackground(getResources().getDrawable(R.drawable.mis_shap_but_att_bg));
                attTv.setTextColor(getResources().getColor(R.color.text_101010));
            }
        }

        if(txtResult.getLike_status().equals("1")||txtResult.getLike_status().equals("2")){
            likeImg.setImageResource(R.mipmap.zan_yes);
        }else{
            likeImg.setImageResource(R.mipmap.zan_no);
        }


        if(txtResult.getType().equals("topic")){
            contentTv.setMovementMethod(LinkMovementMethod.getInstance());
            if(!txtResult.getTopic_id().equals("0")&&txtResult.getTopic_name()!=null){
                contentTv.setText(SpannableUtils.getInstance().getClickableSpan(this,"#"+txtResult.getTopic_name()+"#"+txtResult.getMsg(),txtResult.getTopic_id(),txtResult.getTopic_name().length()));
            }else{
                contentTv.setText(txtResult.getMsg());
            }
        }else if(txtResult.getType().equals("activity")){
            contentTv.setMovementMethod(LinkMovementMethod.getInstance());
            if(!txtResult.getActivity_id().equals("0")&&txtResult.getActivity_name()!=null){
                contentTv.setText(SpannableUtils.getInstance().getClickableSpan(this,"#"+txtResult.getActivity_name()+"#"+txtResult.getMsg(),txtResult.getActivity_id(),txtResult.getActivity_name().length(),txtResult.getActivity_name()));
            }else{
                contentTv.setText(txtResult.getMsg());
            }
        }else{
            contentTv.setText(txtResult.getMsg());
        }
        likeNumTv.setText(txtResult.getLike_count());
        commNumTv.setText(txtResult.getReview_count());
        reviewNumTv.setText("共"+txtResult.getReview_count()+"条评论");

        userNameTv.setText(txtResult.getNickname());
        timeTv.setText(txtResult.getCreate_time_info());
        areaTv.setText(txtResult.getArea());
        GlideDownLoadImage.getInstance().loadCircleImage(txtResult.getHead_img_url(),userImg);
        urls = new ArrayList<>();
        urls.clear();
        for (MineTextModel.MineTextResult.ResultList.ImageList url:txtResult.getImg_lists()){
            urls.add(url.getUrl());
        }
        vpHolder.setViewPager(urls);
        recyLike.setAdapter(getP().initLikeAdapter(txtResult.getLike_lists()));
        //动态添加标签
        if(txtResult.getTab_lists().size()>0){
            tabLin.setVisibility(View.VISIBLE);
            tabLin.removeAllViews();
            for (int x = 0;x<txtResult.getTab_lists().size();x++){
                AutoRelativeLayout rb=(AutoRelativeLayout) LayoutInflater.from(this).
                        inflate(R.layout.layout_tab_rb, null);
                TextView textView = rb.findViewById(R.id.tab_tv);
                textView.setText(txtResult.getTab_lists().get(x).getText());
                AutoRelativeLayout.LayoutParams params=new
                        AutoRelativeLayout.LayoutParams(AutoRelativeLayout.LayoutParams.WRAP_CONTENT,
                        AutoRelativeLayout.LayoutParams.WRAP_CONTENT);
                params.rightMargin = 20;
                tabLin.addView(rb,params);
            }
        }else{
            tabLin.setVisibility(View.GONE);
        }
    }
    /**
     * 评论数据
     * @param list
     */
    public void textReviewResponse(List<TextReviewModel.ReviewResult.ReviewList> list,boolean type){
        reviewLists.clear();
        reviewLists.addAll(list);
        if(type){
            textReviewAdapter = new TextReviewAdapter(this,reviewLists,0,text_id);
            listView.setAdapter(textReviewAdapter);
        }else{
            textReviewAdapter.notifyDataSetChanged();
        }

        listView.onRefreshComplete();
    }

    /**
     * 其他两篇文章
     * @param results
     */
    public void textOtherResponse(final List<TxtModel.TxtResult.Result> results){
        if(results.size()>0){
            botAdapter = getP().initBotAdapter(results);
            botRecy.setAdapter(botAdapter);
        }else{
            botRela.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        RefreshShowTime.showTime(refreshView);
        initAfter();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.user_icon:
                Router.newIntent(ArticleDetailsAct.this)
                        .putString(IntentConstant.OTHER_USER_ID,from_id)
                        .to(OtherMainAct.class)
                        .launch();
                break;
            case R.id.text_review_num:
                Router.newIntent(this)
                        .putString(IntentConstant.ARTIC_TEXT_ID,text_id)
                        .to(ReviewAct.class)
                        .launch();
                break;
            case R.id.like_rela:
                getP().setTextLike(UserUtils.getUserID(ArticleDetailsAct.this),text_id);
                break;

            case R.id.comm_rela:
                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                to_id = "0";
                yesCommRela.setVisibility(View.VISIBLE);
                etCom.requestFocus();
                etCom.clearComposingText();
                etCom.setText("");
                etCom.setHint("请输入评论内容");
                break;

            case R.id.att_tv:
                if(isAtt.equals("1")){
                    getP().removeAttUser(UserUtils.getUserID(ArticleDetailsAct.this),from_id);
                }else{
                    getP().setAttUser(UserUtils.getUserID(ArticleDetailsAct.this),from_id);
                }
                break;
            case R.id.set:
                AlertView alertView = new AlertView(null, null, "取消", null, new String[]{"收藏","举报"},this, AlertView.Style.ActionSheet, this);
                alertView.setCancelable(true);
                alertView.show();
                break;
            case R.id.share_img:
                ShareUtils.shareWeb(this, HttpConstant.SHARE_TEXT+text_id+"&uid="+UserUtils.getUserID(this),contentTv.getText().toString(),contentTv.getText().toString(),urls.get(0));
                break;
        }
    }

    /**
     * 点赞成功
     * @param msg
     */
    public void likeResponse(String msg){
        ToastUtil.showToast(msg);
        getP().getTopData(text_id,UserUtils.getUserID(this));
    }
    /**
     * 评论成功
     * @param msg
     */
    public void setReviewResponse(String msg){
        ToastUtil.showToast(msg);
        yesCommRela.setVisibility(View.GONE);
        etCom.setHint("");
        InputMethodManager im = (InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(etCom.getWindowToken(), 0);
        getP().getTextReview(text_id, UserUtils.getUserID(this),false);
    }

    /**
     * 回复评论
     * @param position
     */
    public void reviewItem(int position){
        InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        yesCommRela.setVisibility(View.VISIBLE);
        etCom.requestFocus();
        TextReviewModel.ReviewResult.ReviewList review = reviewLists.get(position);
        to_id = review.getUser_id();
        etCom.setText("");
        etCom.setHint("回复:"+review.getNickname());
    }

    /**
     * 关注成功
     * @param msg
     */
    public void attUserResponse(String msg){
        ToastUtil.showToast(msg);
        getP().getTopData(text_id,UserUtils.getUserID(this));
    }

    /**
     * 取关成功
     * @param msg
     */
    public void removeAttResponse(String msg){
        ToastUtil.showToast(msg);
        getP().getTopData(text_id,UserUtils.getUserID(this));
    }
    /**
     * 监听软键盘是否显示
     */
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"执行onResume");
        screenHeight = getWindow().getDecorView().getHeight();
        if(!isInputShow){
            yesCommRela.setVisibility(View.GONE);
            isInputShow = true;
        }else{
            getWindow().getDecorView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    Rect rect = new Rect();
                    Log.i(TAG,"执行onLayoutChange");
                    getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
                    if(bottom!=0 && oldBottom!=0 && bottom - rect.bottom <= 0){
                        Log.i(TAG,"隐藏");
                        yesCommRela.setVisibility(View.GONE);
                    }else {
                        Log.i(TAG,"显示");
                        yesCommRela.setVisibility(View.VISIBLE);
                        etCom.requestFocus();
                    }
                }
            });
        }

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁轮播
        vpHolder.stopVp();
    }

    @Override
    public void onDismiss(Object o) {

    }

    @Override
    public void onItemClick(Object o, int position) {
        if(position == 0){
            ToastUtil.showToast("收藏成功");
        }else if(position == -1){
            ToastUtil.showToast("举报成功");
        }
    }
}
