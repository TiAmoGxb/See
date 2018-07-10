package cn.see.presenter.minep;

import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;
import cn.see.fragment.fragmentview.mineview.QrCodeAct;
import cn.see.model.MineTextModel;
import cn.see.model.QrModel;
import cn.see.util.ToastUtil;
import cn.see.util.constant.HttpConstant;
import cn.see.util.http.Api;

/**
 * @日期：2018/6/27
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明：
 */

public class QrCodePresenter extends XPresent<QrCodeAct> {

    public void getUserQrCode(String user_id){
        Api.mineService().getUserQrCode(user_id)
                .compose(XApi.<QrModel>getApiTransformer())
                .compose(XApi.<QrModel>getScheduler())
                .compose(getV().<QrModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<QrModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        ToastUtil.showToast(HttpConstant.NET_ERROR_MSG);
                    }

                    @Override
                    public void onNext(QrModel qrModel) {
                        if(!qrModel.isError()){
                            getV().codeResponse(qrModel.getResult());
                        }else{
                            ToastUtil.showToast(qrModel.getErrorMsg());
                        }
                    }
                });
    }

}
