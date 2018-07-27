package cn.see.presenter.newsp;

import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;
import cn.see.fragment.fragmentview.newsview.SystemNoticeAct;
import cn.see.model.FindActModel;
import cn.see.model.SystemNoticModel;
import cn.see.util.ToastUtil;
import cn.see.util.UserUtils;
import cn.see.util.constant.HttpConstant;
import cn.see.util.http.Api;

/**
 * @日期：2018/7/27
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明：
 */

public class SystemNoticPresenter extends XPresent<SystemNoticeAct> {


    public void getNotice(){
        Api.newsService().getNotice(UserUtils.getUserID(getV()),1,"20")
                .compose(XApi.<SystemNoticModel>getApiTransformer())
                .compose(XApi.<SystemNoticModel>getScheduler())
                .compose(getV().<SystemNoticModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<SystemNoticModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        ToastUtil.showToast(HttpConstant.NET_ERROR_MSG);
                    }

                    @Override
                    public void onNext(SystemNoticModel tabModel) {
                        if(!tabModel.isError()){
                            getV().data(tabModel.getResult().getSystemMsgs().getList());
                        }else{
                            ToastUtil.showToast(tabModel.getErrorMsg());
                        }
                    }
                });
    }
}
