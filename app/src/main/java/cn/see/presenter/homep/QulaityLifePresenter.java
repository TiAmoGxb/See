package cn.see.presenter.homep;

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
import cn.see.base.BaseModel;
import cn.see.fragment.fragmentview.homeview.QualityLifeFragment;
import cn.see.fragment.fragmentview.mineview.OtherMainAct;
import cn.see.fragment.fragmentview.mineview.ReviewAct;
import cn.see.model.TxtModel;
import cn.see.util.ToastUtil;
import cn.see.util.UserUtils;
import cn.see.util.constant.HttpConstant;
import cn.see.util.constant.IntentConstant;
import cn.see.util.glide.GlideDownLoadImage;
import cn.see.util.http.Api;
import cn.see.util.widet.CustomProgress;

/**
 * @日期：2018/6/7
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 品质生活P
 */

public class QulaityLifePresenter extends XPresent<QualityLifeFragment>{

    public CustomProgress progress;
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
                TextView attTv = CommonViewHolder.get(item, R.id.att_tv);
                ImageView likeImg = CommonViewHolder.get(item, R.id.zan_img);
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
                if(s.getAttention_status().equals("1")){
                    attTv.setText("已关注");
                    attTv.setBackground(getV().getResources().getDrawable(R.drawable.shap_but_att_yes_bg));
                    attTv.setTextColor(getV().getResources().getColor(R.color.back_f));
                }else{
                    attTv.setText("关注");
                    attTv.setBackground(getV().getResources().getDrawable(R.drawable.mis_shap_but_att_bg));
                    attTv.setTextColor(getV().getResources().getColor(R.color.text_101010));
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

                createTime.setText(s.getCreate_time_info());
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
                        if(UserUtils.getLogin(getV().getActivity())){
                            Router.newIntent(getV().getActivity())
                                    .putString(IntentConstant.OTHER_USER_ID,s.getUser_id())
                                    .to(OtherMainAct.class)
                                    .launch();
                        }

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
                CommonViewHolder.get(item,R.id.share_img).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(UserUtils.getLogin(getV().getActivity())){
                            ToastUtil.showToast("分享");
                        }
                    }
                });
                CommonViewHolder.get(item,R.id.set).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(UserUtils.getLogin(getV().getActivity())){
                            ToastUtil.showToast("选项");
                        }
                    }
                });

            }
        };
        return adapter;
    }

    /**
     * 获取品质生活
     */
    public void getQuaitData(String user_id,final int page){

        progress = CustomProgress.show(getV().getActivity());
        Api.homeService().getQuality(user_id,page)
                .compose(XApi.<TxtModel>getApiTransformer())
                .compose(XApi.<TxtModel>getScheduler())
                .compose(getV().<TxtModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<TxtModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        progress.dismiss();
                    }

                    @Override
                    public void onNext(TxtModel txtModel) {
                        if(!txtModel.isError()){
                            getV().resoponseData(txtModel.getResult(),page);
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
