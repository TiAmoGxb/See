package cn.see.presenter.newsp;

import android.util.Log;

import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;
import cn.see.model.NewsLikeReviewModel;
import cn.see.util.ToastUtil;
import cn.see.util.constant.HttpConstant;
import cn.see.util.http.Api;
import cn.see.views.LikeAndReviewV;

/**
 * @日期：2018/7/2
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明：
 */

public class NewsLikeAndPresenter extends XPresent<LikeAndReviewV> {
    public static final String TAG =  "NewsLikeAndPresenter";

    /**
     * 喜欢列表
     * @param user_id
     * @param page
     */
    public void getLike(final String user_id, final int page){
        getV().showProgerss();
        Api.newsService().getNewsLikeList(user_id,page,"20")
                .compose(XApi.<NewsLikeReviewModel>getApiTransformer())
                .compose(XApi.<NewsLikeReviewModel>getScheduler())
                .subscribe(new ApiSubscriber<NewsLikeReviewModel>() {


                    @Override
                    protected void onFail(NetError error) {
                        getV().hidProgress();
                        ToastUtil.showToast(HttpConstant.NET_ERROR_MSG);
                        Log.i(TAG,"err："+error.getMessage());
                    }

                    @Override
                    public void onNext(NewsLikeReviewModel txtModel) {
                        getV().hidProgress();
                        if(!txtModel.isError()){
                            getV().responseData(txtModel.getResult().getLists(),txtModel.getTotalPage());
                        }else{
                            ToastUtil.showToast(txtModel.getErrorMsg());
                        }
                    }
                });
    }

    /**
     * 评论列表
     * @param user_id
     * @param page
     */
    public void getReview(final String user_id, final int page){
        getV().showProgerss();
        Api.newsService().getNewsreviewList(user_id,page,"20")
                .compose(XApi.<NewsLikeReviewModel>getApiTransformer())
                .compose(XApi.<NewsLikeReviewModel>getScheduler())
                .subscribe(new ApiSubscriber<NewsLikeReviewModel>() {


                    @Override
                    protected void onFail(NetError error) {
                        getV().hidProgress();
                        ToastUtil.showToast(HttpConstant.NET_ERROR_MSG);
                        Log.i(TAG,"err："+error.getMessage());
                    }

                    @Override
                    public void onNext(NewsLikeReviewModel txtModel) {
                        getV().hidProgress();
                        if(!txtModel.isError()){
                            getV().responseData(txtModel.getResult().getLists(),txtModel.getTotalPage());
                        }else{
                            ToastUtil.showToast(txtModel.getErrorMsg());
                        }
                    }
                });
    }

}
