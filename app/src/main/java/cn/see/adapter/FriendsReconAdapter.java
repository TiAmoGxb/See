package cn.see.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;
import cn.droidlover.xdroidmvp.router.Router;
import cn.see.R;
import cn.see.base.BaseModel;
import cn.see.fragment.fragmentview.mineview.ArticleDetailsAct;
import cn.see.fragment.fragmentview.mineview.OtherMainAct;
import cn.see.model.TxtModel;
import cn.see.util.ToastUtil;
import cn.see.util.UserUtils;
import cn.see.util.constant.HttpConstant;
import cn.see.util.constant.IntentConstant;
import cn.see.util.glide.GlideDownLoadImage;
import cn.see.util.http.Api;

/**
 * @日期：2018/6/29
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 该类是工具类 和 附近用户通用
 */

public class FriendsReconAdapter  {
    public Router to;
    private Activity context;
    private List<TxtModel.TxtResult.Result> results;
    private CommonListViewAdapter<TxtModel.TxtResult.Result> adapter;

    public FriendsReconAdapter(Activity context,List<TxtModel.TxtResult.Result> results) {
        this.context = context;
        this.results = results;
    }

    public CommonListViewAdapter<TxtModel.TxtResult.Result> initAdapter(){
        adapter = new CommonListViewAdapter<TxtModel.TxtResult.Result >(context,results, R.layout.layout_friend_reco_list_item) {
            @Override
            protected void convertView(View item, final TxtModel.TxtResult.Result r, final int position) {
                ImageView userImg = CommonViewHolder.get(item, R.id.user_img);
                TextView userName = CommonViewHolder.get(item, R.id.user_name);
                ImageView imgOne = CommonViewHolder.get(item, R.id.img_one);
                ImageView imgTwo = CommonViewHolder.get(item, R.id.img_two);
                ImageView imgThree = CommonViewHolder.get(item, R.id.img_three);
                LinearLayout linearLayout = CommonViewHolder.get(item, R.id.lin);
                TextView attTv = CommonViewHolder.get(item, R.id.att_tv);
                TextView user_sin = CommonViewHolder.get(item, R.id.user_sin);

                if(r.getAttention_status().equals("1")){
                    attTv.setText("已关注");
                    attTv.setTextColor(context.getResources().getColor(R.color.back_f));
                    attTv.setBackground(context.getResources().getDrawable(R.drawable.shap_but_att_yes_bg));
                }else{
                    attTv.setText("关注");
                    attTv.setTextColor(context.getResources().getColor(R.color.text_101010));
                    attTv.setBackground(context.getResources().getDrawable(R.drawable.mis_shap_but_att_bg));
                }
                userName.setText(r.getNickname());
                user_sin.setText(r.getSignature().replace("|",""));
                GlideDownLoadImage.getInstance().loadCircleImage(r.getHead_img_url(),userImg);
                int size = r.getText_lists().size();
                if(size==0){
                    linearLayout.setVisibility(View.GONE);
                }else if(size==1){
                    linearLayout.setVisibility(View.VISIBLE);
                    GlideDownLoadImage.getInstance().loadImage(r.getText_lists().get(0).getUrl(),imgOne);
                }else if(size==2){
                    linearLayout.setVisibility(View.VISIBLE);
                    GlideDownLoadImage.getInstance().loadImage(r.getText_lists().get(0).getUrl(),imgOne);
                    GlideDownLoadImage.getInstance().loadImage(r.getText_lists().get(1).getUrl(),imgTwo);
                }else {
                    linearLayout.setVisibility(View.VISIBLE);
                    GlideDownLoadImage.getInstance().loadImage(r.getText_lists().get(0).getUrl(),imgOne);
                    GlideDownLoadImage.getInstance().loadImage(r.getText_lists().get(1).getUrl(),imgTwo);
                    GlideDownLoadImage.getInstance().loadImage(r.getText_lists().get(2).getUrl(),imgThree);
                }
                imgOne.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        to = Router.newIntent(context)
                                .to(ArticleDetailsAct.class)
                                .putString(IntentConstant.ARTIC_TEXT_ID,r.getText_lists().get(0).getText_id());
                        to.launch();
                    }
                });
                imgTwo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        to = Router.newIntent(context)
                                .to(ArticleDetailsAct.class)
                                .putString(IntentConstant.ARTIC_TEXT_ID,r.getText_lists().get(1).getText_id());
                        to.launch();
                    }
                });
                imgThree.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        to = Router.newIntent(context)
                                .to(ArticleDetailsAct.class)
                                .putString(IntentConstant.ARTIC_TEXT_ID,r.getText_lists().get(2).getText_id());
                        to.launch();
                    }
                });
                attTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setAttUser(UserUtils.getUserID(context),r.getId(),position);
                    }
                });
                userImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        to = Router.newIntent(context)
                                .to(OtherMainAct.class)
                                .putString(IntentConstant.OTHER_USER_ID,r.getId());
                        to.launch();
                    }
                });
            }
        };
        return adapter;
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
                .subscribe(new ApiSubscriber<BaseModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        ToastUtil.showToast(HttpConstant.NET_ERROR_MSG);
                        Log.i("MineFragment","err："+error.getMessage());
                    }
                    @Override
                    public void onNext(BaseModel baseModel) {
                        if(!baseModel.isError()){
                            ToastUtil.showToast(baseModel.getErrorMsg());
                            results.get(position).setAttention_status("1");
                            adapter.notifyDataSetChanged();
                        }else{
                            ToastUtil.showToast(baseModel.getErrorMsg());
                        }
                    }
                });
    }

}
