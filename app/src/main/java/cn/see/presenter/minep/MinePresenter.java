package cn.see.presenter.minep;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.List;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;
import cn.droidlover.xdroidmvp.router.Router;
import cn.see.R;
import cn.see.adapter.CommonListViewAdapter;
import cn.see.adapter.CommonViewHolder;
import cn.see.base.BaseModel;
import cn.see.fragment.MineFragment;
import cn.see.fragment.fragmentview.mineview.ArticleDetailsAct;
import cn.see.fragment.fragmentview.mineview.OtherMainAct;
import cn.see.fragment.fragmentview.mineview.ReviewAct;
import cn.see.fragment.fragmentview.mineview.TopicAct;
import cn.see.model.LoginModel;
import cn.see.model.MineTextModel;
import cn.see.model.UserInfoModel;
import cn.see.util.SpannableUtils;
import cn.see.util.ToastUtil;
import cn.see.util.UserUtils;
import cn.see.util.constant.HttpConstant;
import cn.see.util.constant.IntentConstant;
import cn.see.util.glide.GlideDownLoadImage;
import cn.see.util.http.Api;
import cn.see.util.widet.CustomProgress;

/**
 * @日期：2018/6/6
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 个人中心对应P层
 */

public class MinePresenter extends XPresent<MineFragment>{

    public CustomProgress progress;

    /**
     * 初始化适配器
     * @param stringList
     * @return
     */
    public CommonListViewAdapter<MineTextModel.MineTextResult.ResultList> initAdapter(final List<MineTextModel.MineTextResult.ResultList> stringList){
        CommonListViewAdapter<MineTextModel.MineTextResult.ResultList> adapter = new CommonListViewAdapter<MineTextModel.MineTextResult.ResultList>(getV().getActivity(),stringList, R.layout.layout_mine_list_item) {
            @Override
            protected void convertView(View item, final MineTextModel.MineTextResult.ResultList s, final int position) {

                View view = CommonViewHolder.get(item, R.id.b_view);
                CommonViewHolder.get(item,R.id.att_tv).setVisibility(View.GONE);
                ImageView userIcon = CommonViewHolder.get(item, R.id.user_icon);
                ImageView thumb_image = CommonViewHolder.get(item, R.id.img_thumb);
                AutoLinearLayout comment_lin = CommonViewHolder.get(item, R.id.comment_lin);
                TextView likeCont = CommonViewHolder.get(item, R.id.like_cont);
                TextView commCont = CommonViewHolder.get(item, R.id.comm_cont);
                TextView seenCont = CommonViewHolder.get(item, R.id.seen_cont);
                TextView nickName = CommonViewHolder.get(item, R.id.nick_name);
                TextView txt = CommonViewHolder.get(item, R.id.text_content);
                txt.setMovementMethod(LinkMovementMethod.getInstance());
                TextView oneCommName = CommonViewHolder.get(item, R.id.one_comm_name);
                TextView twoCommName = CommonViewHolder.get(item, R.id.two_comm_name);
                TextView threeCommName = CommonViewHolder.get(item, R.id.three_comm_name);
                oneCommName.setMovementMethod(LinkMovementMethod.getInstance());
                twoCommName.setMovementMethod(LinkMovementMethod.getInstance());
                threeCommName.setMovementMethod(LinkMovementMethod.getInstance());

                TextView createTime = CommonViewHolder.get(item, R.id.create_time);
                TextView topicName = CommonViewHolder.get(item, R.id.text_topic_name);
                TextView tabArea = CommonViewHolder.get(item, R.id.area_add);
                ImageView likeImg = CommonViewHolder.get(item, R.id.zan_img);
                ImageView comment_img = CommonViewHolder.get(item, R.id.comment_img);
                ImageView share_img = CommonViewHolder.get(item, R.id.share_img);
                TextView aply_cont = CommonViewHolder.get(item, R.id.aply_cont);
                AutoLinearLayout tabLin = CommonViewHolder.get(item, R.id.rgChannel);
                ImageView topicImg = CommonViewHolder.get(item, R.id.topic_img);

                if(position == stringList.size()-1){
                    view.setBackgroundColor(getV().getResources().getColor(R.color.back_f));
                }else{
                    view.setBackgroundColor(getV().getResources().getColor(R.color.view_f5));
                }
                //如果是话题
                if(s.getLtype().equals("topic")){
                    topicImg.setVisibility(View.VISIBLE);
                    topicName.setVisibility(View.VISIBLE);
                    aply_cont.setVisibility(View.VISIBLE);
                    topicName.setText(s.getTname());
                    txt.setText(s.getDescription());
                    aply_cont.setText("参与人数"+s.getApply_count());
                    comment_lin.setVisibility(View.GONE);
                    likeCont.setVisibility(View.GONE);
                    commCont.setVisibility(View.GONE);
                    comment_img.setVisibility(View.GONE);
                    likeImg.setVisibility(View.GONE);
                    share_img.setVisibility(View.GONE);
                    GlideDownLoadImage.getInstance().loadCircleImageRole(s.getUrl(),thumb_image,6);
                }else{
                    topicImg.setVisibility(View.GONE);
                    comment_lin.setVisibility(View.VISIBLE);
                    likeCont.setVisibility(View.VISIBLE);
                    commCont.setVisibility(View.VISIBLE);
                    comment_img.setVisibility(View.VISIBLE);
                    likeImg.setVisibility(View.VISIBLE);
                    share_img.setVisibility(View.VISIBLE);
                    topicName.setVisibility(View.GONE);
                    aply_cont.setVisibility(View.GONE);
                    likeCont.setText(s.getLike_count());
                    commCont.setText(s.getReview_count());

                    if(s.getType().equals("topic")){
                        txt.setText(SpannableUtils.getInstance().getClickableSpan(getV().getActivity(),"#"+s.getTopic_name()+"#"+s.getMsg(),s.getTopic_id(),s.getTopic_name().length()));
                    }else if(s.getType().equals("activity")){
                        txt.setText(SpannableUtils.getInstance().getClickableSpan(getV().getActivity(),"#"+s.getActivity_name()+"#"+s.getMsg(),s.getActivity_id(),s.getActivity_name().length(),s.getActivity_name()));
                    }else{
                        txt.setText(s.getMsg());
                    }
                    GlideDownLoadImage.getInstance().loadCircleImageRole(s.getImg_lists().get(0).getUrl(),thumb_image,6);
                    int size = s.getReviewList().size();
                    if(size<3){
                        if(size == 0){
                            comment_lin.setVisibility(View.GONE);
                        }else if(size==1){
                            oneCommName.setText(SpannableUtils.getInstance().getClickableSpanToMain(getV().getActivity(),s.getReviewList().get(0).getNickname()+"："+s.getReviewList().get(0).getContent(),s.getReviewList().get(0).getFrom_id(),s.getReviewList().get(0).getNickname().length()));
                            twoCommName.setVisibility(View.GONE);
                            threeCommName.setVisibility(View.GONE);

                        }else if(size == 2){
                            oneCommName.setText(SpannableUtils.getInstance().getClickableSpanToMain(getV().getActivity(),s.getReviewList().get(0).getNickname()+"："+s.getReviewList().get(0).getContent(),s.getReviewList().get(0).getFrom_id(),s.getReviewList().get(0).getNickname().length()));
                            twoCommName.setText(SpannableUtils.getInstance().getClickableSpanToMain(getV().getActivity(),s.getReviewList().get(1).getNickname()+"："+s.getReviewList().get(1).getContent(),s.getReviewList().get(1).getFrom_id(),s.getReviewList().get(1).getNickname().length()));
                            threeCommName.setVisibility(View.GONE);
                        }
                    }else{
                        oneCommName.setText(SpannableUtils.getInstance().getClickableSpanToMain(getV().getActivity(),s.getReviewList().get(0).getNickname()+"："+s.getReviewList().get(0).getContent(),s.getReviewList().get(0).getFrom_id(),s.getReviewList().get(0).getNickname().length()));
                        twoCommName.setText(SpannableUtils.getInstance().getClickableSpanToMain(getV().getActivity(),s.getReviewList().get(1).getNickname()+"："+s.getReviewList().get(1).getContent(),s.getReviewList().get(1).getFrom_id(),s.getReviewList().get(1).getNickname().length()));
                        threeCommName.setText(SpannableUtils.getInstance().getClickableSpanToMain(getV().getActivity(),s.getReviewList().get(2).getNickname()+"："+s.getReviewList().get(2).getContent(),s.getReviewList().get(2).getFrom_id(),s.getReviewList().get(2).getNickname().length()));
                    }
                    if(s.getLike_status().equals("1")){
                        likeImg.setImageResource(R.mipmap.zan_yes);
                    }else{
                        likeImg.setImageResource(R.mipmap.zan_no);
                    }
                }
                //标签
                if(s.getTab_lists().size()>0){
                    Log.i("getTab_lists","执行："+s.getTab_lists().size());
                    tabLin.setVisibility(View.VISIBLE);
                    tabLin.removeAllViews();
                    for (int x = 0;x<s.getTab_lists().size();x++){
                        AutoRelativeLayout rb=(AutoRelativeLayout) LayoutInflater.from(getV().getActivity()).
                                inflate(R.layout.layout_tab_rb, null);
                        TextView textView = rb.findViewById(R.id.tab_tv);
                        textView.setText(s.getTab_lists().get(x).getText());
                        AutoRelativeLayout.LayoutParams params=new
                                AutoRelativeLayout.LayoutParams(AutoRelativeLayout.LayoutParams.WRAP_CONTENT,
                                AutoRelativeLayout.LayoutParams.WRAP_CONTENT);
                        params.rightMargin = 20;
                        tabLin.addView(rb,params);
                    }
                }else{
                    tabLin.setVisibility(View.GONE);
                }

                seenCont.setText(s.getSee()+"人阅读过");
                nickName.setText(s.getNickname());
                tabArea.setText(s.getArea());
                createTime.setText(s.getCreate_time_info());
                GlideDownLoadImage.getInstance().loadCircleImage(s.getHead_img_url(),userIcon);

                CommonViewHolder.get(item,R.id.like_rela).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setTextLike(UserUtils.getUserID(getV().getActivity()),s.getId(),position);
                    }
                });

                CommonViewHolder.get(item,R.id.comm_rela).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       Router.newIntent(getV().getActivity())
                               .putString(IntentConstant.ARTIC_TEXT_ID,s.getId())
                               .to(ReviewAct.class)
                               .launch();

                    }
                });

                CommonViewHolder.get(item,R.id.set).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getV().Option(stringList.get(position));
                    }
                });

                CommonViewHolder.get(item,R.id.mine_item).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Router router = Router.newIntent(getV().getActivity());
                        if(stringList.get(position).getLtype().equals("topic")){
                            router.putString(IntentConstant.ARTIC_TOPIC_ID,stringList.get(position).getId())
                                    .to(TopicAct.class);
                        }else{
                            router.putString(IntentConstant.ARTIC_TEXT_ID,stringList.get(position).getId())
                                    .to(ArticleDetailsAct.class);
                        }
                        router.launch();
                    }
                });
            }
        };
        return adapter;
    }

    /**
     * 获取用户信息
     * @param uid
     */
    public void getUserInfo(String uid){

        Api.mineService().getUserInfo(uid,"")
                .compose(XApi.<UserInfoModel>getApiTransformer())
                .compose(XApi.<UserInfoModel>getScheduler())
                .compose(getV().<UserInfoModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<UserInfoModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        progress.dismiss();
                        Log.i("MineFragment","err："+error.getMessage());
                    }

                    @Override
                    public void onNext(UserInfoModel infoModel) {
                        if(!infoModel.isError()){
                            getV().userInfoResPonse(infoModel.getResult());
                        }else{
                            ToastUtil.showToast(infoModel.getErrorMsg());
                        }
                    }
                });
    }

    /**
     * 获取我的发布文章列表
     * @param uid
     */
    public void getUserText(String uid, String topicSize,String textSize){
        progress = CustomProgress.show(getV().getActivity());
        Api.mineService().getUserTextList(uid,"",topicSize,textSize)
                .compose(XApi.<MineTextModel>getApiTransformer())
                .compose(XApi.<MineTextModel>getScheduler())
                .compose(getV().<MineTextModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<MineTextModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        progress.dismiss();
                    }

                    @Override
                    public void onNext(MineTextModel mineTextModel) {
                        if(!mineTextModel.isError()){
                                getV().userTextResponse(mineTextModel.getResult());
                        }else{
                            ToastUtil.showToast(mineTextModel.getErrorMsg());
                        }
                    }
                });
    }
    /**
     * 点赞
     * @param user_id
     */
    public void setTextLike(String user_id, final String text_id, final int positon){
        Api.mineService().setLike(user_id,text_id,"0")
                .compose(XApi.<BaseModel>getApiTransformer())
                .compose(XApi.<BaseModel>getScheduler())
                .compose(getV().<BaseModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        Log.i("QulaityLifePresenter","error:"+error.getMessage());
                    }
                    @Override
                    public void onNext(BaseModel baseModel) {
                        if(!baseModel.isError()){
                            getV().likeResponse(baseModel.getErrorMsg(),positon);
                        }else{
                            ToastUtil.showToast(baseModel.getErrorMsg());
                        }
                    }
                });
    }

    /**
     * 签到
     */
    public void setUserSin(){
        Api.mineService().setUserSin(UserUtils.getUserID(getV().getActivity()))
                .compose(XApi.<BaseModel>getApiTransformer())
                .compose(XApi.<BaseModel>getScheduler())
                .compose(getV().<BaseModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        ToastUtil.showToast(HttpConstant.NET_ERROR_MSG);
                        Log.i("FindChildFragment","error:"+error.getMessage());
                    }

                    @Override
                    public void onNext(BaseModel txtResult) {
                        if(!txtResult.isError()){
                            ToastUtil.showToast(txtResult.getErrorMsg());
                            //getV().getSet(txtResult.getResult().get(0).getPbool());
                        }else{
                            ToastUtil.showToast(txtResult.getErrorMsg());
                        }
                    }
                });
    }

    /**
     * 删除文章
     * @param uid
     */
    public void delText(String uid, String text_id){
        progress = CustomProgress.show(getV().getActivity());
        Api.mineService().delText(uid,text_id)
                .compose(XApi.<BaseModel>getApiTransformer())
                .compose(XApi.<BaseModel>getScheduler())
                .compose(getV().<BaseModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        progress.dismiss();
                    }

                    @Override
                    public void onNext(BaseModel mineTextModel) {
                        progress.dismiss();
                        if(!mineTextModel.isError()){
                            getV().delSu();
                        }else{
                            ToastUtil.showToast(mineTextModel.getErrorMsg());
                        }
                    }
                });
    }
    /**
     * 删除话题
     * @param uid
     */
    public void delTopic(String uid, String text_id){
        progress = CustomProgress.show(getV().getActivity());
        Api.mineService().delTopic(uid,text_id)
                .compose(XApi.<BaseModel>getApiTransformer())
                .compose(XApi.<BaseModel>getScheduler())
                .compose(getV().<BaseModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        progress.dismiss();
                    }

                    @Override
                    public void onNext(BaseModel mineTextModel) {
                        progress.dismiss();
                        if(!mineTextModel.isError()){
                            getV().delSu();
                        }else{
                            ToastUtil.showToast(mineTextModel.getErrorMsg());
                        }
                    }
                });
    }
}
