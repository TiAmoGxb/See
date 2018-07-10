package cn.see.presenter.homep;

import android.util.Log;


import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;
import cn.see.model.TxtModel;
import cn.see.util.ToastUtil;
import cn.see.util.constant.HttpConstant;
import cn.see.util.http.Api;
import cn.see.util.widet.CustomProgress;
import cn.see.views.FriendsAndNearbyV;

/**
 * @日期：2018/6/29
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明：
 */

public class FriendRecoPresenter extends XPresent<FriendsAndNearbyV> {
    public static final String TAG = "FriendRecoPresenter" ;
    /**
     * 获取好友推荐
     */
    public void getFriends(final String user_id, final int page){
        getV().showProgerss();
        Api.homeService().getFriends(user_id,page,"10")
                .compose(XApi.<TxtModel>getApiTransformer())
                .compose(XApi.<TxtModel>getScheduler())
                .subscribe(new ApiSubscriber<TxtModel>() {

                    @Override
                    protected void onFail(NetError error) {
                        getV().hidProgress();
                        ToastUtil.showToast(HttpConstant.NET_ERROR_MSG);
                        Log.i(TAG,"err："+error.getMessage());
                    }

                    @Override
                    public void onNext(TxtModel txtModel) {
                        getV().hidProgress();
                        if(!txtModel.isError()){
                            getV().responseData(txtModel.getResult().getResult(),page,txtModel.getResult().getTotal());
                        }else{
                            ToastUtil.showToast(txtModel.getErrorMsg());
                        }
                    }
                });
    }



    /**
     * 获取附近好友
     */
    public void getNearbyFriends(final String user_id, final int page){
        getV().showProgerss();
        Api.homeService().getNearbyUser(user_id,page,"10")
                .compose(XApi.<TxtModel>getApiTransformer())
                .compose(XApi.<TxtModel>getScheduler())
                .subscribe(new ApiSubscriber<TxtModel>() {

                    @Override
                    protected void onFail(NetError error) {
                        getV().hidProgress();
                        ToastUtil.showToast(HttpConstant.NET_ERROR_MSG);
                        Log.i(TAG,"err："+error.getMessage());
                    }

                    @Override
                    public void onNext(TxtModel txtModel) {
                        getV().hidProgress();
                        if(!txtModel.isError()){
                            getV().responseData(txtModel.getResult().getResult(),page,txtModel.getResult().getTotal());
                        }else{
                            ToastUtil.showToast(txtModel.getErrorMsg());
                        }
                    }
                });
    }

}
