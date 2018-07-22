package cn.see.presenter.minep;

import android.util.Log;

import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;
import cn.see.base.BaseModel;
import cn.see.fragment.fragmentview.mineview.NoctionAct;
import cn.see.fragment.fragmentview.mineview.NoctionBasicAct;
import cn.see.util.ToastUtil;
import cn.see.util.UserUtils;
import cn.see.util.constant.HttpConstant;
import cn.see.util.http.Api;

/**
 * Created by sks on 2018/7/20.
 */

public class NoctionBasicPresenter extends XPresent<NoctionBasicAct> {


    public void isTextType(int type){
        switch (type){
            case NoctionAct.PL_TYPE:
                getV().isText("你将受到这些人的评论推送");
                break;
            case NoctionAct.DZ_TYPE:
                getV().isText("你将受到这些人的点赞推送");
                break;
            case NoctionAct.AT_TYPE:
                getV().isText("你将受到这些人的@推送");
                break;
        }
    }


    /**
     * 设置评论消息
     */
    public void setUserReview(String text){
        Api.mineService().setUserReview(UserUtils.getUserID(getV()),text)
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
     * 设置点赞消息
     */
    public void setUserLike(String text){
        Api.mineService().setUserLike(UserUtils.getUserID(getV()),text)
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
     * 设置评论消息
     */
    public void setUserCall(String text){
        Api.mineService().setUserCall(UserUtils.getUserID(getV()),text)
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
