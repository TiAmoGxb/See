package cn.see.presenter.homep;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import cn.see.fragment.fragmentview.homeview.LegalizeUserAct;
import cn.see.fragment.fragmentview.mineview.ArticleDetailsAct;
import cn.see.fragment.fragmentview.mineview.OtherMainAct;
import cn.see.model.AddFriendModel;
import cn.see.model.TxtModel;
import cn.see.util.ToastUtil;
import cn.see.util.UserUtils;
import cn.see.util.constant.HttpConstant;
import cn.see.util.constant.IntentConstant;
import cn.see.util.glide.GlideDownLoadImage;
import cn.see.util.http.Api;
import cn.see.util.widet.CustomProgress;

/**
 * Created by sks on 2018/7/20.
 */

public class LegalizeUserPresenter extends XPresent<LegalizeUserAct> {

    private Router to;

    public CommonListViewAdapter<AddFriendModel.AddResult> initAdapter(List<AddFriendModel.AddResult> addResults){
        CommonListViewAdapter<AddFriendModel.AddResult>  adapter = new CommonListViewAdapter<AddFriendModel.AddResult>(getV(),addResults, R.layout.layout_friend_reco_list_item) {
            @Override
            protected void convertView(View item, final AddFriendModel.AddResult r, final int position) {
                ImageView userImg = CommonViewHolder.get(item, R.id.user_img);
                TextView userName = CommonViewHolder.get(item, R.id.user_name);
                ImageView imgOne = CommonViewHolder.get(item, R.id.img_one);
                ImageView imgTwo = CommonViewHolder.get(item, R.id.img_two);
                ImageView imgThree = CommonViewHolder.get(item, R.id.img_three);
                LinearLayout linearLayout = CommonViewHolder.get(item, R.id.lin);
                TextView attTv = CommonViewHolder.get(item, R.id.att_tv);
                TextView user_sin = CommonViewHolder.get(item, R.id.user_sin);

                userName.setText(r.getNickname());
                user_sin.setText(r.getSignature().replace("|",""));
                GlideDownLoadImage.getInstance().loadCircleImage(r.getHead_img_url(),userImg);
                int size = r.getLists().size();
                if(size==0){
                    linearLayout.setVisibility(View.GONE);
                }else if(size==1){
                    linearLayout.setVisibility(View.VISIBLE);
                    GlideDownLoadImage.getInstance().loadImage(r.getLists().get(0).getUrl(),imgOne);
                }else if(size==2){
                    linearLayout.setVisibility(View.VISIBLE);
                    GlideDownLoadImage.getInstance().loadImage(r.getLists().get(0).getUrl(),imgOne);
                    GlideDownLoadImage.getInstance().loadImage(r.getLists().get(1).getUrl(),imgTwo);
                }else {
                    linearLayout.setVisibility(View.VISIBLE);
                    GlideDownLoadImage.getInstance().loadImage(r.getLists().get(0).getUrl(),imgOne);
                    GlideDownLoadImage.getInstance().loadImage(r.getLists().get(1).getUrl(),imgTwo);
                    GlideDownLoadImage.getInstance().loadImage(r.getLists().get(2).getUrl(),imgThree);
                }
                imgOne.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        to = Router.newIntent(getV())
                                .to(ArticleDetailsAct.class)
                                .putString(IntentConstant.ARTIC_TEXT_ID,r.getLists().get(0).getText_id());
                        to.launch();
                    }
                });
                imgTwo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        to = Router.newIntent(getV())
                                .to(ArticleDetailsAct.class)
                                .putString(IntentConstant.ARTIC_TEXT_ID,r.getLists().get(1).getText_id());
                        to.launch();
                    }
                });
                imgThree.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        to = Router.newIntent(getV())
                                .to(ArticleDetailsAct.class)
                                .putString(IntentConstant.ARTIC_TEXT_ID,r.getLists().get(2).getText_id());
                        to.launch();
                    }
                });
                attTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setAttUser(UserUtils.getUserID(getV()),r.getUser_id(),position);
                    }
                });
                userImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        to = Router.newIntent(getV())
                                .to(OtherMainAct.class)
                                .putString(IntentConstant.OTHER_USER_ID,r.getUser_id());
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
    public void setAttUser(String uid, final String from_id, final int position){
        Api.mineService().setAtt(uid,from_id)
                .compose(XApi.<BaseModel>getApiTransformer())
                .compose(XApi.<BaseModel>getScheduler())
                .compose(getV().<BaseModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        ToastUtil.showToast(HttpConstant.NET_ERROR_MSG);
                        Log.i("MineFragment","err："+error.getMessage());
                        Log.i("MineFragment","from_id："+from_id);
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


    public void getLegUser(){
        final CustomProgress progress = CustomProgress.show(getV());
        Api.homeService().getlegalizeUser(UserUtils.getUserID(getV()))
                .compose(XApi.<AddFriendModel>getApiTransformer())
                .compose(XApi.<AddFriendModel>getScheduler())
                .subscribe(new ApiSubscriber<AddFriendModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        ToastUtil.showToast(HttpConstant.NET_ERROR_MSG);
                        progress.dismiss();
                    }

                    @Override
                    public void onNext(AddFriendModel txtModel) {
                        progress.dismiss();
                        if(!txtModel.isError()){
                            List<AddFriendModel.AddResult> result = txtModel.getResult();
                            getV().dataResponse(result);
                        }else{
                            ToastUtil.showToast(txtModel.getErrorMsg());
                        }
                    }
                });
    }


}
