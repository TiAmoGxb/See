package cn.see.presenter.minep;

import android.util.Log;

import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;
import cn.see.base.BaseModel;
import cn.see.fragment.fragmentview.mineview.PrivateAct;
import cn.see.model.PrivateModel;
import cn.see.util.ToastUtil;
import cn.see.util.UserUtils;
import cn.see.util.constant.HttpConstant;
import cn.see.util.http.Api;

/**
 * Created by sks on 2018/7/21.
 */

public class PrivatePresnter extends XPresent<PrivateAct> {


    /**
     * 获取设置
     */
    public void getUserPhoneSet(){
        Api.mineService().getUserPhoneSet(UserUtils.getUserID(getV()))
                .compose(XApi.<PrivateModel>getApiTransformer())
                .compose(XApi.<PrivateModel>getScheduler())
                .compose(getV().<PrivateModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<PrivateModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        ToastUtil.showToast(HttpConstant.NET_ERROR_MSG);
                        Log.i("FindChildFragment","error:"+error.getMessage());
                    }

                    @Override
                    public void onNext(PrivateModel txtResult) {
                        if(!txtResult.isError()){
                            getV().getSet(txtResult.getResult().get(0).getPbool());
                        }else{
                            ToastUtil.showToast(txtResult.getErrorMsg());
                        }
                    }
                });
    }


    /**
     * 设置
     * @param text
     */
    public void setUserPhoneSet(String text){
        Api.mineService().setUserPhoneSet(UserUtils.getUserID(getV()),text)
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
                            //getV().getSet(txtResult.getResult().get(0).getPbool());
                        }else{
                            ToastUtil.showToast(txtResult.getErrorMsg());
                        }
                    }
                });
    }

}
