package cn.see.presenter.findp;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;
import cn.droidlover.xdroidmvp.router.Router;
import cn.see.R;
import cn.see.adapter.MultiItemTypeAdapter;
import cn.see.adapter.RecryCommonAdapter;
import cn.see.adapter.ViewHolder;
import cn.see.base.BaseModel;
import cn.see.fragment.fragmentview.mineview.ArticleDetailsAct;
import cn.see.fragment.fragmentview.mineview.OtherMainAct;
import cn.see.model.TextReviewModel;
import cn.see.model.TextTopModel;
import cn.see.model.TxtModel;
import cn.see.model.UserOtherTextModel;
import cn.see.util.ToastUtil;
import cn.see.util.constant.HttpConstant;
import cn.see.util.constant.IntentConstant;
import cn.see.util.glide.GlideDownLoadImage;
import cn.see.util.http.Api;

/**
 * @日期：2018/6/21
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 文章详情P
 */


public class TextAriclePresenter extends XPresent<ArticleDetailsAct> {


    /**
     * 获取顶部数据
     * @param text_id
     * @param user_id
     */
    public void getTopData(String text_id,String user_id){
        Api.findService().getTextTopData(user_id,text_id)
                .compose(XApi.<TextTopModel>getApiTransformer())
                .compose(XApi.<TextTopModel>getScheduler())
                .compose(getV().<TextTopModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<TextTopModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        ToastUtil.showToast(HttpConstant.NET_ERROR_MSG);
                        Log.i("FindChildFragment","error:"+error.getMessage());
                    }

                    @Override
                    public void onNext(TextTopModel txtResult) {
                        if(!txtResult.isError()){
                            getV().topResponse(txtResult.getResult());
                        }else{
                            ToastUtil.showToast(txtResult.getErrorMsg());
                        }
                    }
                });
    }


    /**
     * 获取文章评论
     * @param text_id
     * @param user_id
     */
    public void getTextReview(String text_id, String user_id, final boolean type){
        Api.findService().getTextReviewList(user_id,text_id,1,"3")
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
                            getV().textReviewResponse(txtResult.getResult().getLists(),type);
                        }else{
                            ToastUtil.showToast(txtResult.getErrorMsg());
                        }
                    }
                });
    }

    /**
     * 喜欢的用户
     * @param results
     * @return
     */
    public RecryCommonAdapter<TxtModel.TxtResult.Result.TextLikeList> initLikeAdapter(final List<TxtModel.TxtResult.Result.TextLikeList> results){
        RecryCommonAdapter<TxtModel.TxtResult.Result.TextLikeList> adapter = new RecryCommonAdapter<TxtModel.TxtResult.Result.TextLikeList>(getV(), R.layout.layout_txt_like_item,results) {
            @Override
            protected void convert(ViewHolder holder, TxtModel.TxtResult.Result.TextLikeList result, int position) {
                if(position == 0){
                    holder.setVisible(R.id.left_view,false);
                }else{
                    holder.setVisible(R.id.left_view,true);
                }
                ImageView userView = (ImageView) holder.getView(R.id.like_user_img);
                GlideDownLoadImage.getInstance().loadCircleImage(result.getHead_img_url(),userView);
            }
            @Override
            public int getItemCount() {
                return results.size()>15?15:results.size();
            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Router.newIntent(getV())
                        .to(OtherMainAct.class)
                        .putString(IntentConstant.OTHER_USER_ID,results.get(position).getUser_id())
                        .launch();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        return adapter;
    }

    /**
     * 其他两篇文章
     * @param results
     * @return
     */
    public RecryCommonAdapter<TxtModel.TxtResult.Result> initBotAdapter(final List<TxtModel.TxtResult.Result> results){
        RecryCommonAdapter<TxtModel.TxtResult.Result> adapter = new RecryCommonAdapter<TxtModel.TxtResult.Result>(getV(), R.layout.layout_text_other_item,results) {
            @Override
            protected void convert(ViewHolder holder,TxtModel.TxtResult.Result result, int position) {
                holder.setText(R.id.msg,result.getMsg());
                holder.setText(R.id.nick_name,result.getNickname());
                holder.setText(R.id.time,result.getCreate_time_info());
                ImageView userView = (ImageView) holder.getView(R.id.txt_img);
                GlideDownLoadImage.getInstance().loadImage(result.getUrl(),userView);
            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Router.newIntent(getV())
                        .putString(IntentConstant.ARTIC_TEXT_ID,results.get(position).getText_id())
                        .to(ArticleDetailsAct.class)
                        .launch();
            }
            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        return adapter;
    }

    /**
     * 获取其他两篇文章
     * @param user_id
     * @param text_id
     */
    public void getOtherText(String user_id,String text_id){
        Api.findService().getWithTwoTxt(user_id,text_id,"2",1)
                .compose(XApi.<UserOtherTextModel>getApiTransformer())
                .compose(XApi.<UserOtherTextModel>getScheduler())
                .compose(getV().<UserOtherTextModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<UserOtherTextModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        Log.i("ArticleDetailsAct","e："+error.getMessage());
                        ToastUtil.showToast(HttpConstant.NET_ERROR_MSG);
                    }

                    @Override
                    public void onNext(UserOtherTextModel txtModel) {
                        if(!txtModel.isError()){
                            getV().textOtherResponse(txtModel.getResult());
                        }else{
                            ToastUtil.showToast(txtModel.getErrorMsg());
                        }
                    }
                });
    }


    /**
     * 点赞
     * @param user_id
     */
    public void setTextLike(String user_id, final String text_id){
        Api.mineService().setLike(user_id,text_id,"0")
                .compose(XApi.<BaseModel>getApiTransformer())
                .compose(XApi.<BaseModel>getScheduler())
                .compose(getV().<BaseModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        Log.i("QulaityLifePresenter","error:"+error.getMessage());
                    }
                    @Override
                    public void onNext(BaseModel baseModel) {
                        if(!baseModel.isError()){
                            getV().likeResponse(baseModel.getErrorMsg());
                        }else{
                            ToastUtil.showToast(baseModel.getErrorMsg());
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


    /**
     * 关注
     * @param uid
     * @param from_id
     */
    public void setAttUser(String uid, String from_id){
        Api.mineService().setAtt(uid,from_id)
                .compose(XApi.<BaseModel>getApiTransformer())
                .compose(XApi.<BaseModel>getScheduler())
                .compose(getV().<BaseModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        ToastUtil.showToast(HttpConstant.NET_ERROR_MSG);
                        Log.i("MineFragment","err："+error.getMessage());
                    }

                    @Override
                    public void onNext(BaseModel baseModel) {
                        if(!baseModel.isError()){
                            getV().attUserResponse(baseModel.getErrorMsg());
                        }else{
                            ToastUtil.showToast(baseModel.getErrorMsg());
                        }
                    }
                });
    }

    /**
     * 取消关注
     * @param uid
     * @param from_id
     */
    public void removeAttUser(String uid, String from_id){
        Api.mineService().removeAtt(uid,from_id)
                .compose(XApi.<BaseModel>getApiTransformer())
                .compose(XApi.<BaseModel>getScheduler())
                .compose(getV().<BaseModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        ToastUtil.showToast(HttpConstant.NET_ERROR_MSG);
                        Log.i("MineFragment","err："+error.getMessage());
                    }

                    @Override
                    public void onNext(BaseModel baseModel) {
                        if(!baseModel.isError()){
                            getV().removeAttResponse(baseModel.getErrorMsg());
                        }else{
                            ToastUtil.showToast(baseModel.getErrorMsg());
                        }
                    }
                });
    }

}
