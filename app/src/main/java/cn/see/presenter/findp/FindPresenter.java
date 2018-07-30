package cn.see.presenter.findp;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
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
import cn.see.adapter.MultiItemTypeAdapter;
import cn.see.adapter.RecryCommonAdapter;
import cn.see.adapter.ViewHolder;
import cn.see.base.BaseModel;
import cn.see.fragment.fragmentview.findview.FindChildFragment;
import cn.see.fragment.fragmentview.homeview.SelectMyTableAct;
import cn.see.fragment.fragmentview.mineview.OtherMainAct;
import cn.see.fragment.fragmentview.mineview.ReviewAct;
import cn.see.fragment.fragmentview.mineview.TopicAct;
import cn.see.model.FindTextcModel;
import cn.see.model.TabBannerModel;
import cn.see.model.TabModel;
import cn.see.model.TopicModel;
import cn.see.model.TxtModel;
import cn.see.util.ToastUtil;
import cn.see.util.UserUtils;
import cn.see.util.constant.HttpConstant;
import cn.see.util.constant.IntentConstant;
import cn.see.util.glide.GlideDownLoadImage;
import cn.see.util.http.Api;
import cn.see.util.widet.CustomProgress;

/**
 * @日期：2018/6/11
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 发现标签下 发现frgP
 */

public class FindPresenter extends XPresent<FindChildFragment> {
    public CustomProgress progress;
    private int currentCheckedItemPosition = 0;

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
                TextView area = CommonViewHolder.get(item, R.id.area_add);
                ImageView likeImg = CommonViewHolder.get(item, R.id.zan_img);
                TextView attTv = CommonViewHolder.get(item, R.id.att_tv);
                CommonViewHolder.get(item,R.id.comment_lin).setVisibility(View.GONE);
                AutoLinearLayout tabLin = CommonViewHolder.get(item, R.id.rgChannel);
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

                if(s.getUser_id().equals(UserUtils.getUserID(getV().getActivity()))){
                    attTv.setVisibility(View.GONE);
                }else{
                    if(s.getAttention_status().equals("1")){
                        attTv.setText("已关注");
                        attTv.setBackground(getV().getResources().getDrawable(R.drawable.shap_but_att_yes_bg));
                        attTv.setTextColor(getV().getResources().getColor(R.color.back_f));
                    }else{
                        attTv.setText("关注");
                        attTv.setBackground(getV().getResources().getDrawable(R.drawable.mis_shap_but_att_bg));
                        attTv.setTextColor(getV().getResources().getColor(R.color.text_101010));
                    }
                }


                //动态添加标签
                if(s.getTab_lists().size()>0){
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

                createTime.setText(s.getSignature()+s.getCreate_time_info());
                likeCont.setText(s.getLike_count());
                commCont.setText(s.getCommment_count());
                nickName.setText(s.getNickname());
                seenCont.setText(s.getSee()+"人阅读过");
                txt.setText(s.getMsg());
                area.setText(s.getTab_area());
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

                attTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(UserUtils.getLogin(getV().getActivity())){
                            if(s.getAttention_status().equals("1")){
                                removeAttUser(UserUtils.getUserID(getV().getActivity()),s.getUser_id(),position);
                            }else{
                                setAttUser(UserUtils.getUserID(getV().getActivity()),s.getUser_id(),position);
                            }
                        }
                    }
                });
                CommonViewHolder.get(item,R.id.like_rela).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(UserUtils.getLogin(getV().getActivity())){
                            setTextLike(UserUtils.getUserID(getV().getActivity()),s.getText_id(),position);
                        }
                    }
                });
                CommonViewHolder.get(item,R.id.comm_rela).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(UserUtils.getLogin(getV().getActivity())){
                            Router.newIntent(getV().getActivity())
                                    .putString(IntentConstant.ARTIC_TEXT_ID,s.getText_id())
                                    .to(ReviewAct.class)
                                    .launch();
                        }
                    }
                });

                CommonViewHolder.get(item,R.id.set).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(UserUtils.getLogin(getV().getActivity())){
                            getV().set();
                        }

                    }
                });

            }
        };
        return adapter;
    }

    /**
     * 顶部标签适配器
     * @param tableBeen
     * @return
     */
    public RecryCommonAdapter<TabModel.TabList> initAdapterTopUpdate(final List<TabModel.TabList> tableBeen){
        final RecryCommonAdapter<TabModel.TabList> adapter = new RecryCommonAdapter<TabModel.TabList>(getV().getActivity(),R.layout.layout_find_find_top_rec_item,tableBeen) {
            @Override
            protected void convert(ViewHolder holder, TabModel.TabList tableBean, int position) {
                ImageView imageView = holder.getView(R.id.icon);
                holder.setText(R.id.txt,tableBean.getText());
                if(position == tableBeen.size()-1){
                    imageView.setScaleType(ImageView.ScaleType.CENTER);
                    imageView.setImageResource(R.mipmap.options);
                }else{

                    if(currentCheckedItemPosition == position){
                        holder.setVisible(R.id.ischange,true);
                    }else{
                        holder.setVisible(R.id.ischange,false);
                    }
                    holder.setText(R.id.txt,tableBean.getText());
                    GlideDownLoadImage.getInstance().loadCircleImageRole(tableBean.getUrl(),imageView,2);
                }
            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if(position == tableBeen.size()-1){
                    if(UserUtils.getLogin(getV().getActivity())){
                        Router.newIntent(getV().getActivity())
                                .to(SelectMyTableAct.class)
                                .requestCode(0)
                                .launch();
                    }
                }else{
                    if(UserUtils.getLogin(getV().getActivity())){
                        currentCheckedItemPosition = position;
                        adapter.notifyDataSetChanged();
                        getV().tabChange(tableBeen.get(position).getTab_id());

                    }

                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        return adapter;
    }




    /**
     * 获取我的标签
     * @param user_id
     */
    public void getUserTab(String user_id, final int type){
        Api.findService().getUserTable(user_id)
                .compose(XApi.<TabModel>getApiTransformer())
                .compose(XApi.<TabModel>getScheduler())
                .compose(getV().<TabModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<TabModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        progress.dismiss();
                    }
                    @Override
                    public void onNext(TabModel tabModel) {
                        if(!tabModel.isError()){
                            getV().getUserTabResponse(tabModel.getResult(),type);
                        }else{
                            ToastUtil.showToast(tabModel.getErrorMsg());
                        }
                    }
                });
    }


    /**
     * 获取基于标签页的Banner
     * @param tab_id
     */
    public void getTabBanner(String tab_id){
        progress =  CustomProgress.show(getV().getActivity());
        Api.findService().getTabBanner(tab_id)
                .compose(XApi.<TabBannerModel>getApiTransformer())
                .compose(XApi.<TabBannerModel>getScheduler())
                .compose(getV().<TabBannerModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<TabBannerModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        progress.dismiss();
                    }

                    @Override
                    public void onNext(TabBannerModel tabBannerModel) {
                        if(!tabBannerModel.isError()){
                            getV().getTabBanner(tabBannerModel.getResult().getResult());
                        }else{
                            ToastUtil.showToast(tabBannerModel.getErrorMsg());
                        }
                    }
                });
    }
    /**
     * 获取基于标签页的话题
     * @param tab_id
     */
    public void getTabTopic(String tab_id){
        Api.findService().getTabTopic(tab_id)
                .compose(XApi.<TopicModel>getApiTransformer())
                .compose(XApi.<TopicModel>getScheduler())
                .compose(getV().<TopicModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<TopicModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        progress.dismiss();
                        Log.i("FindChildFragment","error:"+error.getMessage());
                    }

                    @Override
                    public void onNext(TopicModel topicModel) {
                        if(!topicModel.isError()){
                            Log.i("FindPresenter","getTabTopic:"+progress);
                            getV().getTabTopic(topicModel.getResult().getResult());
                        }else{
                            ToastUtil.showToast(topicModel.getErrorMsg());
                        }
                    }
                });
    }

    /**
     * 获取基于标签页的文章
     * @param tab_id
     */
    public void getTabText(String tab_id, final int page,String user_id){
        Log.i("FindChildFragment", tab_id+"--"+user_id);
        Api.findService().getTabText(tab_id,page,user_id)
                .compose(XApi.<FindTextcModel>getApiTransformer())
                .compose(XApi.<FindTextcModel>getScheduler())
                .compose(getV().<FindTextcModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<FindTextcModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        progress.dismiss();
                        Log.i("FindChildFragment","error:"+error.getMessage());
                    }

                    @Override
                    public void onNext(FindTextcModel findTextcModel) {
                        if(!findTextcModel.isError()){
                            //progress.dismiss();
                            getV().getTabText(findTextcModel.getResult(),page);
                        }else{
                            ToastUtil.showToast(findTextcModel.getErrorMsg());
                        }
                    }
                });
    }

    /**
     * 基于标签话题
     * @return
     */
    public RecryCommonAdapter<TopicModel.Topicresult.TopicList> initAdapterTopic(final List<TopicModel.Topicresult.TopicList> topicLists){
        RecryCommonAdapter<TopicModel.Topicresult.TopicList> adapter = new RecryCommonAdapter<TopicModel.Topicresult.TopicList>(getV().getActivity(),R.layout.layout_find_top_topic_item,topicLists) {
            @Override
            protected void convert(ViewHolder holder,TopicModel.Topicresult.TopicList tableBean, int position) {
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
                                .putString(IntentConstant.ARTIC_TOPIC_ID,topicLists.get(position).getTopic_id())
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


    public static class TableBean{
        private int icon;
        private String text;
        private boolean type;

        public TableBean(int icon, String text, boolean type) {
            this.icon = icon;
            this.text = text;
            this.type = type;
        }

        public int getIcon() {
            return icon;
        }

        public String getText() {
            return text;
        }

        public boolean isType() {
            return type;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }

        public void setText(String text) {
            this.text = text;
        }

        public void setType(boolean type) {
            this.type = type;
        }
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
    public void setAttUser(String uid, String from_id, final int position){
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
                            getV().attUserResponse(baseModel.getErrorMsg(),position);
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


}
