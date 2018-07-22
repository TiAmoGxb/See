package cn.see.presenter.minep;

import android.util.Log;

import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;
import cn.see.base.BaseModel;
import cn.see.fragment.fragmentview.mineview.NoctionAct;
import cn.see.model.NoticeModel;
import cn.see.util.ToastUtil;
import cn.see.util.UserUtils;
import cn.see.util.constant.HttpConstant;
import cn.see.util.http.Api;

/**
 * Created by sks on 2018/7/20.
 */

public class NoctionActPresenter extends XPresent<NoctionAct> {


    /**
     * 获取通知
     */
    public void getUserNotice(){
        Api.mineService().getUserNotice(UserUtils.getUserID(getV()))
                .compose(XApi.<NoticeModel>getApiTransformer())
                .compose(XApi.<NoticeModel>getScheduler())
                .compose(getV().<NoticeModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<NoticeModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        ToastUtil.showToast(HttpConstant.NET_ERROR_MSG);
                        Log.i("FindChildFragment","error:"+error.getMessage());
                    }

                    @Override
                    public void onNext(NoticeModel txtResult) {
                        if(!txtResult.isError()){
                            getV().getNotice(txtResult.getResult());
                        }else{
                            ToastUtil.showToast(txtResult.getErrorMsg());
                        }
                    }
                });
    }

    /**
     * 设置活动消息
     */
    public void setUserNoticeAct(String text){
        Api.mineService().setUserNoticeAct(UserUtils.getUserID(getV()),text)
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
                            getV().setNotice();
                        }else{
                            ToastUtil.showToast(txtResult.getErrorMsg());
                        }
                    }
                });
    }


    /**
     * 设置被关注消息
     */
    public void setUserNoticeSubscribe(String text){
        Api.mineService().setUserNoticeSubscribe(UserUtils.getUserID(getV()),text)
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
                            getV().setNotice();
                        }else{
                            ToastUtil.showToast(txtResult.getErrorMsg());
                        }
                    }
                });
    }



    /**
     * 设置系统消息
     */
    public void setUserNoticeSystem(String text){
        Api.mineService().setUserNoticeSystem(UserUtils.getUserID(getV()),text)
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
                            getV().setNotice();
                        }else{
                            ToastUtil.showToast(txtResult.getErrorMsg());
                        }
                    }
                });
    }





}
