package cn.see.fragment.fragmentview.mineview;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhy.autolayout.AutoFrameLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.router.Router;
import cn.see.R;
import cn.see.adapter.CommonListViewAdapter;
import cn.see.base.BaseActivity;
import cn.see.fragment.fragmentview.findview.PhotoViewActivity;
import cn.see.model.MineTextModel;
import cn.see.model.TopiDesitalModel;
import cn.see.presenter.minep.TopicPresenter;
import cn.see.util.ShareUtils;
import cn.see.util.ToastUtil;
import cn.see.util.UserUtils;
import cn.see.util.constant.HttpConstant;
import cn.see.util.constant.IntentConstant;
import cn.see.util.glide.GlideDownLoadImage;
import cn.see.util.widet.putorefresh.PullToRefreshBase;
import cn.see.util.widet.putorefresh.PullToRefreshListView;
import cn.see.util.widet.putorefresh.RefreshShowTime;

public class TopicAct extends BaseActivity<TopicPresenter>implements  PullToRefreshBase.OnRefreshListener2<ListView> {

    private static final String TAG = "TopicAct" ;
    private List<MineTextModel.MineTextResult.ResultList> results = new ArrayList<>();
    private CommonListViewAdapter<MineTextModel.MineTextResult.ResultList> adapter;
    private List<String> urls = new ArrayList<>();

    private int page = 1;
    private String topic_id;
    private ImageView topicImg;
    private ImageView userImg;
    private TextView topicName;
    private TextView topicCont;
    private TextView topicTime;
    private TextView topicApplyNum;
    private TextView topicAppTv;
    private TextView userName;
    private TextView userSin;
    private TextView userFans;
    private ImageView oneImg;
    private ImageView twoImg;
    private ImageView threeImg;
    private ImageView fourImg;
    private ImageView fiveImh;
    private AutoFrameLayout applyFram;
    private ImageView r_img;
    private TextView attTv;
    private String attFlag;
    private String user_id;


    @BindView(R.id.title_tv_base)
    TextView titles;
    @BindView(R.id.right_img_top)
    ImageView rightImg;
    @BindView(R.id.image_rela)
    RelativeLayout rightRela;
    @BindView(R.id.pull_topic_list)
    PullToRefreshListView listView;
    @OnClick(R.id.back_rela)
    void bacAct(){
        onBack();
    }

    @OnClick(R.id.image_rela)
    void share(){
        ShareUtils.shareWeb(this, HttpConstant.SHAR_TOPIC+topic_id+"&uid="+UserUtils.getUserID(this),topicName.getText().toString(),topicCont.getText().toString(),urls.get(0));
    }

    @Override
    public void initView() {
        titles.setText("话题");
        rightImg.setImageResource(R.drawable.top_share);
        rightRela.setVisibility(View.VISIBLE);
        topic_id = getIntent().getStringExtra(IntentConstant.ARTIC_TOPIC_ID);
        Log.i(TAG,"topic_id:"+topic_id);
        View topView = View.inflate(this,R.layout.layout_topic_top_view,null);
        findView(topView);
        listView.getRefreshableView().setDividerHeight(0);
        listView.getRefreshableView().addHeaderView(topView);
        adapter = getP().initAdapter(results);
        listView.setAdapter(adapter);
    }

    private void findView(View v){
        topicImg =  (ImageView) v.findViewById(R.id.topic_img);
        userImg =   (ImageView) v.findViewById(R.id.user_img);
        topicName = (TextView) v.findViewById(R.id.topic_name);
        topicCont = (TextView) v.findViewById(R.id.topic_cont);
        topicTime = (TextView)v.findViewById(R.id.topic_time);
        topicApplyNum = (TextView)v.findViewById(R.id.apply_num);
        topicAppTv = (TextView)v.findViewById(R.id.apply_tv);
        userName = (TextView)v.findViewById(R.id.user_name);
        userSin = (TextView)v.findViewById(R.id.user_sin);
        userFans = (TextView)v.findViewById(R.id.user_fans);
        attTv = (TextView)v.findViewById(R.id.att_tv);
        oneImg =  (ImageView) v.findViewById(R.id.one_img);
        twoImg =  (ImageView) v.findViewById(R.id.two_img);
        threeImg=  (ImageView) v.findViewById(R.id.three_img);
        fourImg =  (ImageView) v.findViewById(R.id.four_img);
        fiveImh =  (ImageView) v.findViewById(R.id.five_img);
        r_img =  (ImageView) v.findViewById(R.id.r_img);
        applyFram  =(AutoFrameLayout) v.findViewById(R.id.apply_fram);
        v.findViewById(R.id.go_apply).setOnClickListener(this);
        v.findViewById(R.id.recommend_rela).setOnClickListener(this);
    }


    @Override
    public void initAfter() {
        getP().getTopInfo(topic_id);
        getP().getTopicText(topic_id,page);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_topic;
    }

    @Override
    public TopicPresenter bindPresent() {
        return new TopicPresenter();
    }

    @Override
    public void setListener() {
        topicImg.setOnClickListener(this);
        userImg.setOnClickListener(this);
        attTv.setOnClickListener(this);
        listView.setOnRefreshListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(results.size()>0){
                    Router.newIntent(TopicAct.this)
                            .to(ArticleDetailsAct.class)
                            .putString(IntentConstant.ARTIC_TEXT_ID,results.get(position-2).getText_id())
                            .launch();
                }

            }
        });
    }

    /**
     * 头部信息
     */
    public void topicInfoResponse(TopiDesitalModel.TopicDeresult topicDeresult){
        //话题基本数据
        TopiDesitalModel.TopicDeresult.TopicDe topic = topicDeresult.getTopic();
        topicName.setText(topic.getTname());
        topicCont.setText(topic.getDescription());
        topicTime.setText(topic.getCreate_time_info());
        topicApplyNum.setText(topic.getApply()+"参与");
        urls.clear();
        urls.add(topic.getUrl().get(0));
        GlideDownLoadImage.getInstance().loadCircleImageRole(topic.getUrl().get(0),topicImg,5);
        //发起人数据
        TopiDesitalModel.TopicDeresult.TopicUser user = topicDeresult.getUser();
        user_id = user.getId();
        attFlag = user.getAttention_status();
        if(user_id.equals(UserUtils.getUserID(this))){
            attTv.setVisibility(View.GONE);
        }
        if(attFlag.equals("1")){
            attTv.setText("已关注");
        }else{
            attTv.setText("关注");
        }
        userName.setText(user.getNickname());
        userSin.setText(user.getSignature());
        userFans.setText("粉丝数："+user.getFans());
        GlideDownLoadImage.getInstance().loadCircleImage(user.getUrl(),userImg);
        //参与者数据
        List<TopiDesitalModel.TopicDeresult.TopicApply> apply = topicDeresult.getApply();
        topicAppTv.setText(topic.getApply()+"参与");
        applyUserImgs(apply);
    }

    //参与者覆盖图判断
    private void applyUserImgs(List<TopiDesitalModel.TopicDeresult.TopicApply> apply) {
        Log.i(TAG,"size："+apply.size());
        int size = apply.size();
        if(size>=5){
            size = 5;
        }
        switch (size){
            case 0:
                applyFram.setVisibility(View.GONE);
                r_img.setVisibility(View.GONE);
                break;
            case 1:
                twoImg.setVisibility(View.GONE);
                threeImg.setVisibility(View.GONE);
                fourImg.setVisibility(View.GONE);
                fiveImh.setVisibility(View.GONE);
                GlideDownLoadImage.getInstance().loadCircleImageToCust(apply.get(0).getUrl(),oneImg);
                break;
            case 2:
                threeImg.setVisibility(View.GONE);
                fourImg.setVisibility(View.GONE);
                fiveImh.setVisibility(View.GONE);
                GlideDownLoadImage.getInstance().loadCircleImageToCust(apply.get(0).getUrl(),oneImg);
                GlideDownLoadImage.getInstance().loadCircleImageToCust(apply.get(1).getUrl(),twoImg);
                break;
            case 3:
                fourImg.setVisibility(View.GONE);
                fiveImh.setVisibility(View.GONE);
                GlideDownLoadImage.getInstance().loadCircleImageToCust(apply.get(0).getUrl(),oneImg);
                GlideDownLoadImage.getInstance().loadCircleImageToCust(apply.get(1).getUrl(),twoImg);
                GlideDownLoadImage.getInstance().loadCircleImageToCust(apply.get(2).getUrl(),threeImg);
                break;
            case 4:
                fiveImh.setVisibility(View.GONE);
                GlideDownLoadImage.getInstance().loadCircleImageToCust(apply.get(0).getUrl(),oneImg);
                GlideDownLoadImage.getInstance().loadCircleImageToCust(apply.get(1).getUrl(),twoImg);
                GlideDownLoadImage.getInstance().loadCircleImageToCust(apply.get(2).getUrl(),threeImg);
                GlideDownLoadImage.getInstance().loadCircleImageToCust(apply.get(3).getUrl(),fourImg);
                break;
            case 5:
                Log.i(TAG,"执行"+apply.get(4).getUrl());
                GlideDownLoadImage.getInstance().loadCircleImageToCust(apply.get(0).getUrl(),oneImg);
                GlideDownLoadImage.getInstance().loadCircleImageToCust(apply.get(1).getUrl(),twoImg);
                GlideDownLoadImage.getInstance().loadCircleImageToCust(apply.get(2).getUrl(),threeImg);
                GlideDownLoadImage.getInstance().loadCircleImageToCust(apply.get(3).getUrl(),fourImg);
                GlideDownLoadImage.getInstance().loadCircleImageToCust(apply.get(4).getUrl(),fiveImh);
                break;
        }
    }


    /**
     * 话题下文章
     * @param result
     */
    public void topictxtRespose(MineTextModel.MineTextResult result){
        List<MineTextModel.MineTextResult.ResultList> lists = result.getLists();
        if(page>1){
            if(lists.size()==0){
                ToastUtil.showToast("暂无更多文章");
            }else{
                results.addAll(lists);
                adapter.notifyDataSetChanged();
            }
        }else{
            results.clear();
            results.addAll(lists);
            adapter = getP().initAdapter(results);
            listView.setAdapter(adapter);
        }
        listView.onRefreshComplete();
    }

    /**
     * 点赞成功回调
     * 更新被点赞的条目
     * @param msg
     */
    public void likeResponse(String msg,int positon){
        ToastUtil.showToast(msg);
        MineTextModel.MineTextResult.ResultList resultList = results.get(positon);
        int i = Integer.parseInt(resultList.getLike_count());
        resultList.setLike_count((i+1)+"");
        resultList.setLike_status("1");
        adapter.notifyDataSetChanged();
    }


    /**
     * 关注成功(列表)
     * @param msg
     */
    public void attUserResponse(String msg,int position){
        ToastUtil.showToast(msg);
        results.get(position).setAttention_status("1");
        adapter.notifyDataSetChanged();

    }
    /**
     * 关注成功(用户)
     * @param msg
     */
    public void attUserResponseUser(String msg){
        ToastUtil.showToast(msg);
        attTv.setText("已关注");
        attFlag = "1";
    }

    public void removeAttUser(String msg){
        ToastUtil.showToast(msg);
        attTv.setText("关注");
        attFlag = "0";
    }


    /**
     * 取关成功
     * @param msg
     */
    public void removeAttResponse(String msg,int position){
        ToastUtil.showToast(msg);
        results.get(position).setAttention_status("0");
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        RefreshShowTime.showTime(refreshView);
        page = 1;
        initAfter();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        page++;
        getP().getTopicText(topic_id,page);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.go_apply:
                ToastUtil.showToast("参与话题");
                break;
            case R.id.att_tv:
                if(attFlag.equals("1")){
                    getP().removeAttUser(UserUtils.getUserID(TopicAct.this),user_id,0,1);
                }else{
                    getP().setAttUser(UserUtils.getUserID(TopicAct.this),user_id,0,1);
                }
                break;
            case R.id.user_img:
                Router.newIntent(TopicAct.this)
                        .to(OtherMainAct.class)
                        .putString(IntentConstant.OTHER_USER_ID,user_id)
                        .launch();
                break;
            case R.id.topic_img:
                Router.newIntent(this)
                        .to(PhotoViewActivity.class)
                        .putInt(IntentConstant.LOOK_BIG_IMG_INDEX,0)
                        .putSerializable(IntentConstant.LOOK_BIG_IMG_URLS, (Serializable) urls)
                        .launch();
                break;
            case R.id.recommend_rela:
//                Router.newIntent(this)
//                        .putString(IntentConstant.ARTIC_TOPIC_ID,topic_id)
//                        .to(TopicApplyAct.class)
//                        .launch();
                break;
        }
    }
}
