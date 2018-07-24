package cn.see.presenter.minep;

import android.util.Log;

import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;
import cn.see.base.BaseModel;
import cn.see.fragment.fragmentview.mineview.UserOtherBasicDataAct;
import cn.see.model.MineTextModel;
import cn.see.util.ToastUtil;
import cn.see.util.UserUtils;
import cn.see.util.constant.HttpConstant;
import cn.see.util.http.Api;

/**
 * Created by sks on 2018/7/16.
 */

public class UserOtherBasicDataPresenter extends XPresent<UserOtherBasicDataAct> {

    /**
     * 根据TYPE更新标题
     */
    public void isTypeTitle(int type){
        switch (type){
            case 0:
                getV().getTitle("昵称","保存");
                break;
            case 1:
                getV().getTitle("性别","确定");
                break;
            case 2:
                getV().getTitle("签名","保存");
                break;
            case 3:
                getV().getTitle("选择出生日期","保存");
                break;
        }
    }

    /**
     * 修改昵称
     * @param text
     */
    public void seNickName(String text){
        if(text.equals("")){
            ToastUtil.showToast("昵称不能为空");
            return;
        }else if(text.length()>14){
            ToastUtil.showToast("昵称必须为1-14个字符");
            return;
        }
        Api.mineService().setUserNickName(UserUtils.getUserID(getV()),text)
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
                            getV().setSusess();
                        }else{
                            ToastUtil.showToast(txtResult.getErrorMsg());
                        }
                    }
                });
    }


    /**
     * 修改性别
     * @param text
     */
    public void seSex(String text){
        Log.i(getV().TAG,"text:"+text);
        Api.mineService().setUserSex(UserUtils.getUserID(getV()),text)
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
                            getV().setSusess();
                        }else{
                            ToastUtil.showToast(txtResult.getErrorMsg());
                        }
                    }
                });
    }

    /**
     * 修改生日
     * @param text
     */
    public void seBrithday(String text){
        if(text.equals("请选择")){
            ToastUtil.showToast("请选择生日");
            return;
        }
        Api.mineService().setUserBrithday(UserUtils.getUserID(getV()),text)
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
                            getV().setSusess();
                        }else{
                            ToastUtil.showToast(txtResult.getErrorMsg());
                        }
                    }
                });
    }

    /**
     * 修改签名
     * @param text
     */
    public void seSin(String text){
        if(text.trim().equals("")){
            ToastUtil.showToast("签名不能为空");
            return;
        }else if(text.length()>12){
            ToastUtil.showToast("签名只能在1-12个字符");
            return;
        }

        Api.mineService().setUserSin(UserUtils.getUserID(getV()),text)
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
                            getV().setSusess();
                        }else{
                            ToastUtil.showToast(txtResult.getErrorMsg());
                        }
                    }
                });
    }


}
