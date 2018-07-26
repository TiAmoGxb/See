package cn.see.presenter.homep;

import android.support.v7.widget.RecyclerView;
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
import cn.see.adapter.MultiItemTypeAdapter;
import cn.see.adapter.RecryCommonAdapter;
import cn.see.adapter.ViewHolder;
import cn.see.base.BaseModel;
import cn.see.fragment.fragmentview.homeview.AttentionFragment;
import cn.see.fragment.fragmentview.mineview.OtherMainAct;
import cn.see.fragment.fragmentview.mineview.ReviewAct;
import cn.see.fragment.fragmentview.mineview.TopicAct;
import cn.see.model.FriendsNewsModel;
import cn.see.model.MineTextModel;
import cn.see.model.TxtModel;
import cn.see.util.ToastUtil;
import cn.see.util.UserUtils;
import cn.see.util.constant.HttpConstant;
import cn.see.util.constant.IntentConstant;
import cn.see.util.glide.GlideDownLoadImage;
import cn.see.util.http.Api;

/**
 * @日期：2018/6/7
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 关注P
 */

public class AttentionPresenter extends XPresent<AttentionFragment>{


    /**
     * 初始化适配器
     * @param stringList
     * @return
     */
    public CommonListViewAdapter<TxtModel.TxtResult.Result> initAdapter(final List<TxtModel.TxtResult.Result> stringList){
        CommonListViewAdapter<TxtModel.TxtResult.Result> adapter = new CommonListViewAdapter<TxtModel.TxtResult.Result>(getV().getActivity(),stringList, R.layout.layout_mine_list_item) {
            @Override
            protected void convertView(View item, final TxtModel.TxtResult.Result s, final int position) {
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
                createTime.setText(s.getSignature()+s.getCreate_time_info());
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
                        Router.newIntent(getV().getActivity())
                                .putString(IntentConstant.OTHER_USER_ID,s.getUser_id())
                                .to(OtherMainAct.class)
                                .launch();
                    }
                });

                //取关
                attTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(UserUtils.getLogin(getV().getActivity())){
                            if(s.getAttention_status().equals("1")){
                                removeAttUser(UserUtils.getUserID(getV().getActivity()),s.getUser_id(),position);
                            }
                        }
                    }
                });

                CommonViewHolder.get(item,R.id.like_rela).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            setTextLike(UserUtils.getUserID(getV().getActivity()),s.getText_id(),position);
                    }
                });
                CommonViewHolder.get(item,R.id.comm_rela).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            Router.newIntent(getV().getActivity())
                                    .putString(IntentConstant.ARTIC_TEXT_ID,s.getText_id())
                                    .to(ReviewAct.class)
                                    .launch();
                    }
                });
                CommonViewHolder.get(item,R.id.share_img).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.showToast("分享");

                    }
                });
                CommonViewHolder.get(item,R.id.set).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getV().set();

                    }
                });

            }
        };
        return adapter;
    }

    public CommonListViewAdapter<FriendsNewsModel.NewsResult.NewsList> initAdapterTopUpdate(final List<FriendsNewsModel.NewsResult.NewsList> stringList){
      CommonListViewAdapter<FriendsNewsModel.NewsResult.NewsList> adapter = new CommonListViewAdapter<FriendsNewsModel.NewsResult.NewsList>(getV().getActivity(),stringList,R.layout.layout_home_att_f_update_item) {
          @Override
          protected void convertView(View item, final FriendsNewsModel.NewsResult.NewsList s, int position) {
              ImageView  imageView= CommonViewHolder.get(item, R.id.dt_img);
              TextView dtName = CommonViewHolder.get(item, R.id.dt_name);
              TextView dtTime = CommonViewHolder.get(item, R.id.dt_time);
              TextView dtToName = CommonViewHolder.get(item, R.id.dt_to_name);
              TextView dtType = CommonViewHolder.get(item, R.id.dt_type);
              TextView dtTypeText = CommonViewHolder.get(item, R.id.dt_type_txt);
              CommonViewHolder.get(item, R.id.botom_view).setVisibility(View.GONE);
              switch (s.getType()){
                  case "like":
                      dtName.setText(s.getFnickname());
                      dtToName.setText(s.getText_nickname());
                      dtType.setText("点赞了");
                      dtTypeText.setText("的文章");
                      break;
                  case "review":
                      dtName.setText(s.getNickname());
                      dtToName.setText(s.getText_nickname());
                      dtType.setText("评论了");
                      dtTypeText.setText("的文章");
                      break;
                  case "attention":
                      dtName.setText(s.getFnickname());
                      dtToName.setText(s.getTnickname());
                      dtType.setText("成为了");
                      dtTypeText.setText("的粉丝");
                      break;
                  case "fans":
                      dtName.setText(s.getFnickname());
                      dtToName.setText(s.getTnickname());
                      dtType.setText("成为了");
                      dtTypeText.setText("的粉丝");
                      break;
              }
              dtTime.setText(s.getCreate_time_info());
              GlideDownLoadImage.getInstance().loadImage(getV(),s.getHead_img_url(),imageView);
              imageView.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      Router.newIntent(getV().getActivity())
                              .to(OtherMainAct.class)
                              .putString(IntentConstant.OTHER_USER_ID,s.getFrom_id())
                              .launch();
                  }
              });
          }
      };
      return adapter;
    }


    /**
     * 获取好友推荐
     */
    public void getFriends(final String user_id){
        Api.homeService().getFriends(user_id,1,"5")
                .compose(XApi.<TxtModel>getApiTransformer())
                .compose(XApi.<TxtModel>getScheduler())
                .compose(getV().<TxtModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<TxtModel>() {
                    @Override
                    protected void onFail(NetError error) {
                    }

                    @Override
                    public void onNext(TxtModel txtModel) {
                        if(!txtModel.isError()){
                            getV().topFriendsResponse(txtModel.getResult().getResult());
                        }else{
                            ToastUtil.showToast(txtModel.getErrorMsg());
                        }
                    }
                });
    }

    /**
     * 获取好友动态
     * @param user_id
     */
    public void getFriendsNews(String user_id,int pageSize, String likeSize, String reviewSize, String attentionSize, String fansSize){
        Api.homeService().getFriendsNews(user_id,pageSize,likeSize,reviewSize,attentionSize,fansSize)
                .compose(XApi.<FriendsNewsModel>getApiTransformer())
                .compose(XApi.<FriendsNewsModel>getScheduler())
                .compose(getV().<FriendsNewsModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<FriendsNewsModel>() {
                    @Override
                    protected void onFail(NetError error) {
                    }

                    @Override
                    public void onNext(FriendsNewsModel txtModel) {
                        if(!txtModel.isError()){
                            getV().friendsNewsResponse(txtModel.getResult().getList());
                        }else{
                            ToastUtil.showToast(txtModel.getErrorMsg());
                        }
                    }
                });
    }


    /**
     * 获取关注文章列表
     */
    public void getAttTextList(final String user_id,int page){
        Api.homeService().getAttTxtList(user_id,page)
                .compose(XApi.<TxtModel>getApiTransformer())
                .compose(XApi.<TxtModel>getScheduler())
                .compose(getV().<TxtModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<TxtModel>() {
                    @Override
                    protected void onFail(NetError error) {
                    }

                    @Override
                    public void onNext(TxtModel txtModel) {
                        if(!txtModel.isError()){
                            getV().attTextResponse(txtModel.getResult());
                        }else{
                            ToastUtil.showToast(txtModel.getErrorMsg());
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
     * 取消关注
     * @param uid
     * @param from_id
     */
    public void removeAttUser(String uid, String from_id, final int position){
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
                            getV().removeAttResponse(baseModel.getErrorMsg(),position);
                        }else{
                            ToastUtil.showToast(baseModel.getErrorMsg());
                        }
                    }
                });
    }

    /**
     * 我关注的话题
     */
    public void getTabTopic(){
        Api.homeService().getAttTopic(UserUtils.getUserID(getV().getActivity()),1,"4")
                .compose(XApi.<MineTextModel>getApiTransformer())
                .compose(XApi.<MineTextModel>getScheduler())
                .compose(getV().<MineTextModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<MineTextModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        Log.i("FindChildFragment","error:"+error.getMessage());
                    }

                    @Override
                    public void onNext(MineTextModel topicModel) {
                        if(!topicModel.isError()){
                            getV().getTabTopic(topicModel.getResult().getLists());
                        }else{
                            ToastUtil.showToast(topicModel.getErrorMsg());
                        }
                    }
                });
    }

    /**
     * 基于标签话题
     * @return
     * @param topicLists
     */
    public RecryCommonAdapter<MineTextModel.MineTextResult.ResultList> initAdapterTopic(final List<MineTextModel.MineTextResult.ResultList> topicLists){
        RecryCommonAdapter<MineTextModel.MineTextResult.ResultList> adapter = new RecryCommonAdapter<MineTextModel.MineTextResult.ResultList>(getV().getActivity(),R.layout.layout_find_top_topic_item,topicLists) {
            @Override
            protected void convert(ViewHolder holder, MineTextModel.MineTextResult.ResultList tableBean, int position) {
                ImageView imageView = holder.getView(R.id.topic_img);
                holder.setText(R.id.tname,tableBean.getTname());
                holder.setText(R.id.hot_num,tableBean.getSee());
                GlideDownLoadImage.getInstance().loadImage(tableBean.getUrl(),imageView);
            }

        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if(UserUtils.userIsLogin(getV().getActivity())){
                    Router.newIntent(getV().getActivity())
                            .to(TopicAct.class)
                            .putString(IntentConstant.ARTIC_TOPIC_ID,topicLists.get(position).getId())
                            .launch();
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        return adapter;
    }

}
