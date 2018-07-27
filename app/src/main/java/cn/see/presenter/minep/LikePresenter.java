package cn.see.presenter.minep;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;
import cn.see.R;
import cn.see.adapter.CommonListViewAdapter;
import cn.see.adapter.CommonViewHolder;
import cn.see.fragment.fragmentview.mineview.LikeAct;
import cn.see.model.MineTextModel;
import cn.see.util.ToastUtil;
import cn.see.util.UserUtils;
import cn.see.util.glide.GlideDownLoadImage;
import cn.see.util.http.Api;
import cn.see.util.widet.CustomProgress;

/**
 * @日期：2018/6/11
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 我的喜欢P
 */

public class LikePresenter extends XPresent<LikeAct> {

    public CustomProgress progress;

    /**
     * 初始化适配器
     * @param stringList
     * @return
     */
    public CommonListViewAdapter<MineTextModel.MineTextResult.ResultList> initAdapter(final List<MineTextModel.MineTextResult.ResultList> stringList){
        CommonListViewAdapter<MineTextModel.MineTextResult.ResultList> adapter = new CommonListViewAdapter<MineTextModel.MineTextResult.ResultList>(getV(),stringList, R.layout.layout_mine_list_item) {
            @Override
            protected void convertView(View item, MineTextModel.MineTextResult.ResultList s, int position) {
                View view = CommonViewHolder.get(item, R.id.b_view);
                CommonViewHolder.get(item,R.id.comment_lin).setVisibility(View.GONE);
                ImageView userIcon = CommonViewHolder.get(item, R.id.user_icon);
                ImageView thumb_image = CommonViewHolder.get(item, R.id.img_thumb);
                TextView likeCont = CommonViewHolder.get(item, R.id.like_cont);
                TextView commCont = CommonViewHolder.get(item, R.id.comm_cont);
                TextView seenCont = CommonViewHolder.get(item, R.id.seen_cont);
                TextView nickName = CommonViewHolder.get(item, R.id.nick_name);
                TextView txt = CommonViewHolder.get(item, R.id.text_content);
                TextView createTime = CommonViewHolder.get(item, R.id.create_time);
                TextView tabArea = CommonViewHolder.get(item, R.id.area_add);
                ImageView likeImg = CommonViewHolder.get(item, R.id.zan_img);
                TextView attTv = CommonViewHolder.get(item, R.id.att_tv);

                if(position == stringList.size()-1){
                    view.setBackgroundColor(getV().getResources().getColor(R.color.back_f));
                }else{
                    view.setBackgroundColor(getV().getResources().getColor(R.color.view_f5));
                }
                if(s.getLike_status().equals("1")){
                    likeImg.setImageResource(R.mipmap.zan_yes);
                }

                if(s.getUser_id().equals(UserUtils.getUserID(getV().getApplicationContext()))){
                    attTv.setVisibility(View.GONE);
                }else{
                    attTv.setVisibility(View.VISIBLE);
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

                likeCont.setText(s.getLike_count());
                commCont.setText(s.getReview_count());
                nickName.setText(s.getNickname());
                seenCont.setText(s.getSee()+"人阅读过");
                tabArea.setText(s.getTab_area());
                txt.setText(s.getMsg());
                createTime.setText(s.getCreate_time_info());
                GlideDownLoadImage.getInstance().loadCircleImage(s.getHead_img_url(),userIcon);
                GlideDownLoadImage.getInstance().loadCircleImageRole(s.getUrl(),thumb_image,6);


                CommonViewHolder.get(item,R.id.zan_img).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.showToast("点赞");
                    }
                });
                CommonViewHolder.get(item,R.id.comment_img).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.showToast("评论");

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
     * 获取我的喜欢
     * @param uid
     * @param page
     */
    public void getUserLike(String uid, final int page){

        progress = CustomProgress.show(getV());
        Api.mineService().getUserLike(uid,page)
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
                            getV().userLikeResponse(mineTextModel.getResult(),page);
                        }else{
                            ToastUtil.showToast(mineTextModel.getErrorMsg());
                        }
                    }
                });
    }

}
