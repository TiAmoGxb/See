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
import cn.see.R;
import cn.see.adapter.CommonListViewAdapter;
import cn.see.adapter.CommonViewHolder;
import cn.see.adapter.RecryCommonAdapter;
import cn.see.adapter.ViewHolder;
import cn.see.base.BaseModel;
import cn.see.fragment.fragmentview.homeview.AddFriendsAct;
import cn.see.fragment.release.ui.AddTextAct_ViewBinding;
import cn.see.model.AddFriendModel;
import cn.see.model.TxtModel;
import cn.see.util.ToastUtil;
import cn.see.util.UserUtils;
import cn.see.util.constant.HttpConstant;
import cn.see.util.glide.GlideDownLoadImage;
import cn.see.util.http.Api;
import cn.see.util.widet.CustomProgress;

/**
 * Created by sks on 2018/7/19.
 */

public class AddFriendsPresenter extends XPresent<AddFriendsAct> {

     CustomProgress progress;

    /**
     * 可能认识的人
     */
    public void getMayknowUuser(){
        progress = CustomProgress.show(getV());
        Api.homeService().getMayknowUuser(UserUtils.getUserID(getV()))
                .compose(XApi.<AddFriendModel>getApiTransformer())
                .compose(XApi.<AddFriendModel>getScheduler())
                .subscribe(new ApiSubscriber<AddFriendModel>() {

                    @Override
                    protected void onFail(NetError error) {
                        progress.dismiss();
                        ToastUtil.showToast(HttpConstant.NET_ERROR_MSG);
                    }

                    @Override
                    public void onNext(AddFriendModel txtModel) {
                        if(!txtModel.isError()){
                            getHotuser(txtModel.getResult());
                        }else{
                            ToastUtil.showToast(txtModel.getErrorMsg());
                        }
                    }
                });
    }


    /**
     * 热门用户
     */
    public void getHotuser(final List<AddFriendModel.AddResult> resultList){
        Api.homeService().gethotUser(UserUtils.getUserID(getV()))
                .compose(XApi.<AddFriendModel>getApiTransformer())
                .compose(XApi.<AddFriendModel>getScheduler())
                .subscribe(new ApiSubscriber<AddFriendModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        progress.dismiss();
                        ToastUtil.showToast(HttpConstant.NET_ERROR_MSG);
                }
                    @Override
                    public void onNext(AddFriendModel txtModel) {
                        progress.dismiss();
                        if(!txtModel.isError()){
                            List<AddFriendModel.AddResult> result = txtModel.getResult();
                            int position = resultList.size();
                            resultList.addAll(result);
                            getV().dataResponse(resultList,position);
                        }else{
                            ToastUtil.showToast(txtModel.getErrorMsg());
                        }
                    }
                });
    }



    /**
     * 感兴趣的人
     */
    public void getInterestedUser(){
        Api.homeService().getInterestedUser(UserUtils.getUserID(getV()))
                .compose(XApi.<AddFriendModel>getApiTransformer())
                .compose(XApi.<AddFriendModel>getScheduler())
                .subscribe(new ApiSubscriber<AddFriendModel>() {

                    @Override
                    protected void onFail(NetError error) {
                        ToastUtil.showToast(HttpConstant.NET_ERROR_MSG);
                    }

                    @Override
                    public void onNext(AddFriendModel txtModel) {
                        if(!txtModel.isError()){
                        }else{
                            ToastUtil.showToast(txtModel.getErrorMsg());
                        }
                    }
                });
    }

    public CommonListViewAdapter<AddFriendModel.AddResult> initAdapter(List<AddFriendModel.AddResult> resultList, final int listPosition){

        CommonListViewAdapter<AddFriendModel.AddResult> adapter = new CommonListViewAdapter<AddFriendModel.AddResult>(getV(),resultList,R.layout.layout_add_r_item) {
            @Override
            protected void convertView(View item, final AddFriendModel.AddResult addResult, final int position) {
                TextView textView= CommonViewHolder.get(item, R.id.nick_name);
                RelativeLayout linearLayout = CommonViewHolder.get(item, R.id.lin);
                View view = CommonViewHolder.get(item, R.id.botom_view);
                TextView iconText = CommonViewHolder.get(item, R.id.text);
                if(position == 0){
                    iconText.setText("可能认识的人");
                    linearLayout.setVisibility(View.VISIBLE);
                    view.setVisibility(View.GONE);
                }else{
                    linearLayout.setVisibility(View.GONE);
                    view.setVisibility(View.VISIBLE);
                }
                if(position == listPosition){
                    iconText.setText("热门用户");
                    view.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.VISIBLE);
                }else{
                    if(position!=0){
                        linearLayout.setVisibility(View.GONE);
                        view.setVisibility(View.VISIBLE);
                    }
                }
                CommonViewHolder.get(item,R.id.send_tv).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setAttUser(UserUtils.getUserID(getV()),addResult.getUser_id(),position);
                    }
                });
                ImageView imageView = CommonViewHolder.get(item, R.id.img_icon);
                textView.setText(addResult.getNickname());
                GlideDownLoadImage.getInstance().loadCircleImage(addResult.getHead_img_url(),imageView);
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

}
