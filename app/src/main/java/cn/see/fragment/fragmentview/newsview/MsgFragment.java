package cn.see.fragment.fragmentview.newsview;

import android.Manifest;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.router.Router;
import cn.see.R;
import cn.see.adapter.CommonListViewAdapter;
import cn.see.app.App;
import cn.see.base.BaseFragement;
import cn.see.event.MsgEvent;
import cn.see.fragment.fragmentview.mineview.AttentionAct;
import cn.see.main.WebAct;
import cn.see.model.FindActModel;
import cn.see.model.MsgContModel;
import cn.see.presenter.newsp.MsgPresenter;
import cn.see.util.NotificationUtils;
import cn.see.util.ToastUtil;
import cn.see.util.UserUtils;
import cn.see.util.constant.IntentConstant;
import cn.see.util.glide.GlideDownLoadImage;
import cn.see.util.widet.putorefresh.PullToRefreshBase;
import cn.see.util.widet.putorefresh.PullToRefreshListView;

/**
 * @日期：2018/7/2
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 消息Frg
 */

public class MsgFragment extends BaseFragement<MsgPresenter>{


    private String url = "http://www.xintusee.com/IOS/Activity/actshare/html?activity_id=";
    public static final String  MSG_LIKE_TYPE = "likes";
    public static final String  MSG_REVIEW_TYPE = "review";
    private String actId;
    private MsgContModel.ContResult result;
    @BindView(R.id.notice_view)
    RelativeLayout notice_view;
    @BindView(R.id.act_img)
    ImageView actImg;
    @BindView(R.id.act_name)
    TextView actName;
    @BindView(R.id.num_like)
    TextView num_like;
    @BindView(R.id.num_review)
    TextView num_review;

    //点赞
    @OnClick(R.id.like_rela)
    void like(){
        if(UserUtils.getLogin(getActivity())){
            openActivity(NewsLikeAct.class);
        }

    }

    //评论
    @OnClick(R.id.review_rela)
    void review(){
        if(UserUtils.getLogin(getActivity())){
            openActivity(NewsReviewAct.class);
        }

    }
    //关注
    @OnClick(R.id.att_rela)
    void att(){
        if(UserUtils.getLogin(getActivity())){
            openActivity(AttentionAct.class);
        }

    }
    //通知
    @OnClick(R.id.notice_rela)
    void notice(){
        if(UserUtils.getLogin(getActivity())){
            ToastUtil.showToast("通知");
        }
    }

    //活动
    @OnClick(R.id.act_rela)
    void act(){
        if(UserUtils.getLogin(getActivity())){
            Router.newIntent(getActivity())
                    .to(WebAct.class)
                    .putString(IntentConstant.WEB_LOAD_URL,url+actId)
                    .launch();
        }
    }


    @OnClick(R.id.start_not)
    void statrNot(){
        NotificationUtils.goToSet(getActivity());
    }


    @Override
    public void initView() {
        getP().getTextAct(UserUtils.getUserID(getActivity()));
    }

    @Override
    public void initAfter() {
        //注册订阅者
        EventBus.getDefault().register(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.layout_news_msg_fragment;
    }

    @Override
    public MsgPresenter bindPresent() {
        return new MsgPresenter();
    }

    @Override
    public void setListener() {

    }
    @Override
    public void widgetClick(View v) {
    }

    @Override
    public void onStart() {
        super.onStart();
        if(UserUtils.userIsLogin(getActivity())){
            getP().getMsgCont();
        }
        boolean b = NotificationManagerCompat.from(getActivity()).areNotificationsEnabled();
        Log.i(TAG,"Notification:"+b);
        if(!b){
            notice_view.setVisibility(View.VISIBLE);
        }else{
            notice_view.setVisibility(View.GONE);
        }
    }


    /**
     * 活动回调
     * @param actResult
     */
    public void actResponse(FindActModel.ActResult actResult){
        actId = actResult.getLists().get(0).getActivity_id();
        actName.setText(actResult.getLists().get(0).getName());
        GlideDownLoadImage.getInstance().loadImage(actResult.getLists().get(0).getUrl(),actImg);
    }

    /**
     * 获取消息未读数
     * @param result
     */
    public void getMsg(MsgContModel.ContResult result){
        String likes_count = result.getLikes_count();
        String review_count = result.getReview_count();
        if(!likes_count.equals("0")){
            num_like.setText(likes_count);
            num_like.setVisibility(View.VISIBLE);
        }else{
            num_like.setVisibility(View.GONE);
        }
        if(!review_count.equals("0")){
            num_review.setText(review_count);
            num_review.setVisibility(View.VISIBLE);
        }else{
            num_review.setVisibility(View.GONE);
        }
    }


    //定义处理接收的方法
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(MsgEvent userEvent){
        Log.i(TAG,"接收到了消息");
        getP().getMsgCont();
    }

}
