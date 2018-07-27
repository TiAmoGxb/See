package cn.see.presenter.minep;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import cn.see.fragment.fragmentview.mineview.OtherMainAct;
import cn.see.fragment.fragmentview.mineview.ReviewAct;
import cn.see.fragment.fragmentview.mineview.TopicAct;
import cn.see.model.MineTextModel;
import cn.see.model.TopiDesitalModel;
import cn.see.util.ToastUtil;
import cn.see.util.UserUtils;
import cn.see.util.constant.HttpConstant;
import cn.see.util.constant.IntentConstant;
import cn.see.util.glide.GlideDownLoadImage;
import cn.see.util.http.Api;
import cn.see.util.widet.CustomProgress;

/**
 * @日期：2018/7/3
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明：
 */

public class TopicPresenter extends XPresent<TopicAct> {
    CustomProgress progress;

    /**
     * 获取话题头部信息
     * @param topic_id
     */
    public void getTopInfo(String topic_id){
        progress = CustomProgress.show(getV());
        Api.mineService().getTopicInfo(UserUtils.getUserID(getV()),topic_id)
                    .compose(XApi.<TopiDesitalModel>getApiTransformer())
                    .compose(XApi.<TopiDesitalModel>getScheduler())
                    .compose(getV().<TopiDesitalModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<TopiDesitalModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            progress.dismiss();
                            ToastUtil.showToast(HttpConstant.NET_ERROR_MSG);
                        }

                        @Override
                        public void onNext(TopiDesitalModel tabModel) {

                            if(!tabModel.isError()){
                                getV().topicInfoResponse(tabModel.getResult());
                            }else{
                                ToastUtil.showToast(tabModel.getErrorMsg());
                            }
                        }
                    });
        }


    /**
     * 获取话题文章
     * @param topic_id
     */
    public void getTopicText(String topic_id,int page){
        Api.mineService().getTopicText(UserUtils.getUserID(getV()),topic_id,page,"15")
                .compose(XApi.<MineTextModel>getApiTransformer())
                .compose(XApi.<MineTextModel>getScheduler())
                .compose(getV().<MineTextModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<MineTextModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        progress.dismiss();
                        ToastUtil.showToast(HttpConstant.NET_ERROR_MSG);
                    }

                    @Override
                    public void onNext(MineTextModel tabModel) {
                        progress.dismiss();
                        if(!tabModel.isError()){
                            getV().topictxtRespose(tabModel.getResult());
                        }else{
                            ToastUtil.showToast(tabModel.getErrorMsg());
                        }
                    }
                });
    }


    /**
     * 初始化适配器
     * @param stringList
     * @return
     */
    public CommonListViewAdapter<MineTextModel.MineTextResult.ResultList> initAdapter(final List<MineTextModel.MineTextResult.ResultList> stringList){
        CommonListViewAdapter<MineTextModel.MineTextResult.ResultList> adapter = new CommonListViewAdapter<MineTextModel.MineTextResult.ResultList>(getV(),stringList, R.layout.layout_mine_list_item) {
            @Override
            protected void convertView(View item, final MineTextModel.MineTextResult.ResultList s, final int position) {
                View view = CommonViewHolder.get(item, R.id.b_view);
                View view_top = CommonViewHolder.get(item, R.id.view_);
                TextView likeCont = CommonViewHolder.get(item, R.id.like_cont);
                TextView commCont = CommonViewHolder.get(item, R.id.comm_cont);
                TextView seenCont = CommonViewHolder.get(item, R.id.seen_cont);
                ImageView userIcon = CommonViewHolder.get(item, R.id.user_icon);
                TextView createTime = CommonViewHolder.get(item, R.id.create_time);
                TextView nickName = CommonViewHolder.get(item, R.id.nick_name);
                ImageView thumb_image = CommonViewHolder.get(item, R.id.img_thumb);
                TextView txt = CommonViewHolder.get(item, R.id.text_content);
                CommonViewHolder.get(item,R.id.comment_lin).setVisibility(View.GONE);
                TextView tabArea = CommonViewHolder.get(item, R.id.area_add);
                ImageView likeImg = CommonViewHolder.get(item, R.id.zan_img);
                TextView attTv = CommonViewHolder.get(item, R.id.att_tv);

                if(position == 0){
                    view_top.setVisibility(View.VISIBLE);
                }else{
                    view_top.setVisibility(View.GONE);
                }

                if(position == stringList.size()-1){
                    view.setBackgroundColor(getV().getResources().getColor(R.color.back_f));
                }else{
                    view.setBackgroundColor(getV().getResources().getColor(R.color.view_f5));
                }

                if(s.getLike_status().equals("1")){
                    likeImg.setImageResource(R.mipmap.zan_yes);
                }else{
                    likeImg.setImageResource(R.mipmap.zan_no);
                }
                if(s.getAttention_status().equals("1")){
                    attTv.setText("已关注");
                    attTv.setBackground(getV().getResources().getDrawable(R.drawable.shap_but_att_yes_bg));
                    attTv.setTextColor(getV().getResources().getColor(R.color.back_f));
                }else{
                    attTv.setText("关注");
                    attTv.setBackground(getV().getResources().getDrawable(R.drawable.mis_shap_but_att_bg));
                    attTv.setTextColor(getV().getResources().getColor(R.color.text_101010));
                }
                tabArea.setText(s.getTab_area());
                createTime.setText(s.getCreate_time_info());
                likeCont.setText(s.getLike_count());
                commCont.setText(s.getReview_count());
                nickName.setText(s.getNickname());
                seenCont.setText(s.getSee()+"人阅读过");
                txt.setText(s.getMsg());
                GlideDownLoadImage.getInstance().loadCircleImage(s.getHead_img_url(),userIcon);
                GlideDownLoadImage.getInstance().loadCircleImageRole(s.getUrl(),thumb_image,6);

                userIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Router.newIntent(getV())
                                .putString(IntentConstant.OTHER_USER_ID,s.getUser_id())
                                .to(OtherMainAct.class)
                                .launch();
                    }
                });

                //取关
                attTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(UserUtils.getLogin(getV())){
                            if(s.getAttention_status().equals("1")){
                                removeAttUser(UserUtils.getUserID(getV()),s.getUser_id(),position,0);
                            }else{
                                setAttUser(UserUtils.getUserID(getV()),s.getUser_id(),position,0);
                            }
                        }
                    }
                });

                CommonViewHolder.get(item,R.id.like_rela).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setTextLike(UserUtils.getUserID(getV()),s.getText_id(),position);
                    }
                });
                CommonViewHolder.get(item,R.id.comm_rela).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Router.newIntent(getV())
                                .putString(IntentConstant.ARTIC_TEXT_ID,s.getText_id())
                                .to(ReviewAct.class)
                                .launch();
                    }
                });

                CommonViewHolder.get(item,R.id.set).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.showToast("选项");

                    }
                });

            }
        };
        return adapter;
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
     * 关注
     * @param uid
     * @param from_id
     */
    public void setAttUser(String uid, String from_id, final int position, final int type){
        Api.mineService().setAtt(uid,from_id)
                .compose(XApi.<BaseModel>getApiTransformer())
                .compose(XApi.<BaseModel>getScheduler())
                .compose(getV().<BaseModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        ToastUtil.showToast(HttpConstant.NET_ERROR_MSG);
                        Log.i("MineFragment","err："+error.getMessage());
                    }

                    @Override
                    public void onNext(BaseModel baseModel) {
                        if(!baseModel.isError()){
                            if(type == 1){
                                getV().attUserResponseUser(baseModel.getErrorMsg());
                            }else{
                                getV().attUserResponse(baseModel.getErrorMsg(),position);

                            }

                        }else{
                            ToastUtil.showToast(baseModel.getErrorMsg());
                        }
                    }
                });
    }
    /**
     * 取消关注
     * @param uid
     * @param from_id
     */
    public void removeAttUser(String uid, String from_id, final int position, final int type){
        Api.mineService().removeAtt(uid,from_id)
                .compose(XApi.<BaseModel>getApiTransformer())
                .compose(XApi.<BaseModel>getScheduler())
                .compose(getV().<BaseModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        ToastUtil.showToast(HttpConstant.NET_ERROR_MSG);
                        Log.i("MineFragment","err："+error.getMessage());
                    }

                    @Override
                    public void onNext(BaseModel baseModel) {
                        if(!baseModel.isError()){
                            if(type == 1){
                                getV().removeAttUser(baseModel.getErrorMsg());
                            }else{
                                getV().removeAttResponse(baseModel.getErrorMsg(),position);
                            }
                        }else{
                            ToastUtil.showToast(baseModel.getErrorMsg());
                        }
                    }
                });
    }

}
