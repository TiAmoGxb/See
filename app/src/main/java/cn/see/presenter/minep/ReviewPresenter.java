package cn.see.presenter.minep;

import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;
import cn.see.base.BaseModel;
import cn.see.fragment.fragmentview.mineview.ReviewAct;
import cn.see.model.TextReviewModel;
import cn.see.util.ToastUtil;
import cn.see.util.constant.HttpConstant;
import cn.see.util.http.Api;
import cn.see.util.widet.CustomProgress;

/**
 * @日期：2018/6/26
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明：
 */

public class ReviewPresenter extends XPresent<ReviewAct> {

    public CustomProgress progress;

    /**
     * 获取文章评论
     * @param text_id
     * @param user_id
     */
    public void getTextReview(String text_id, String user_id, final int page, String pageSize){
        progress = CustomProgress.show(getV());
        Api.findService().getTextReviewList(user_id,text_id,page,pageSize)
                .compose(XApi.<TextReviewModel>getApiTransformer())
                .compose(XApi.<TextReviewModel>getScheduler())
                .compose(getV().<TextReviewModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<TextReviewModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        ToastUtil.showToast(HttpConstant.NET_ERROR_MSG);
                    }

                    @Override
                    public void onNext(TextReviewModel txtResult) {
                        if(!txtResult.isError()){

                            getV().textReviewResponse(txtResult.getResult().getLists(),page,txtResult.getResult().getTotalPage());
                        }else{
                            ToastUtil.showToast(txtResult.getErrorMsg());
                        }
                    }
                });
    }


    /**
     * 评论
     * @param user_id
     * @param text_id
     * @param to_id
     */
    public void setReview(String user_id, String text_id , String to_id,String content){
        Api.mineService().setReview(user_id,text_id,to_id,content)
                .compose(XApi.<BaseModel>getApiTransformer())
                .compose(XApi.<BaseModel>getScheduler())
                .compose(getV().<BaseModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel>() {
                    @Override
                    protected void onFail(NetError error) {

                        ToastUtil.showToast(HttpConstant.NET_ERROR_MSG);
                        progress.dismiss();
                    }

                    @Override
                    public void onNext(BaseModel baseModel) {
                        if(!baseModel.isError()){
                            getV().setReviewResponse(baseModel.getErrorMsg());
                        }else{
                            ToastUtil.showToast(baseModel.getErrorMsg());
                        }
                    }
                });
    }


}
