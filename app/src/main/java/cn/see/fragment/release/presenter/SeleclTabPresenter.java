package cn.see.fragment.release.presenter;

import android.util.Log;
import android.view.View;

import java.util.List;

import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;
import cn.see.R;
import cn.see.adapter.RecryCommonAdapter;
import cn.see.adapter.ViewHolder;
import cn.see.fragment.release.ui.SelectTabAct;
import cn.see.model.TxtModel;
import cn.see.util.ToastUtil;
import cn.see.util.constant.HttpConstant;
import cn.see.util.http.Api;

/**
 * @日期：2018/7/12
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 选择标签P
 */

public class SeleclTabPresenter extends XPresent<SelectTabAct> {

    private static final String TAG =  "SeleclTabPresenter";
    private int num = 0;

    public void getTab(){
        Api.releaseService().getReleaseTab(1,"50")
                .compose(XApi.<TxtModel>getApiTransformer())
                .compose(XApi.<TxtModel>getScheduler())
                .compose(getV().<TxtModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<TxtModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        ToastUtil.showToast(HttpConstant.NET_ERROR_MSG);
                    }

                    @Override
                    public void onNext(TxtModel txtModel) {
                        if(!txtModel.isError()){
                            getV().tabResponse(txtModel.getResult().getResult());
                        }else{
                            ToastUtil.showToast(txtModel.getErrorMsg());
                        }
                    }
                });
    }

    public RecryCommonAdapter<TxtModel.TxtResult.Result> initAdapter(List<TxtModel.TxtResult.Result> results){
        RecryCommonAdapter<TxtModel.TxtResult.Result> adapter = new RecryCommonAdapter<TxtModel.TxtResult.Result>(getV(), R.layout.layout_release_tab_rb,results) {
            @Override
            protected void convert(final ViewHolder holder, final TxtModel.TxtResult.Result result, final int position) {
                    holder.setText(R.id.tab_tv,result.getText());
                    holder.setOnClickListener(R.id.tab_tv, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(!result.isCheck()){
                                if(num<5){
                                    holder.setTextColor(R.id.tab_tv,getV().getResources().getColor(R.color.text_3d));
                                    result.setCheck(true);
                                    num++;
                                }else{
                                    ToastUtil.showToast("最多只能添加5个标签");
                                }
                            }else{
                                holder.setTextColor(R.id.tab_tv,getV().getResources().getColor(R.color.back_f));
                                result.setCheck(false);
                                num--;
                            }
                            getV().setTab(result,result.isCheck());
                        }
                    });
            }
        };
        return adapter;
    }


}
