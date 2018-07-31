package cn.see.presenter.findp;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;
import cn.see.R;
import cn.see.adapter.CommonListViewAdapter;
import cn.see.adapter.CommonViewHolder;
import cn.see.fragment.fragmentview.findview.HotUserAct;
import cn.see.model.MineTextModel;
import cn.see.model.TxtModel;
import cn.see.util.ToastUtil;
import cn.see.util.glide.GlideDownLoadImage;
import cn.see.util.http.Api;
import cn.see.util.widet.CustomProgress;

/**
 * @日期：2018/7/26
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明：
 */

public class HotUserPresenter extends XPresent<HotUserAct> {

    private CustomProgress progress;

    /**
     * 获取顶部热门用户
     * @param page
     * @param pageSize
     */
    public void getUserHot(final int page,String pageSize){
        progress = CustomProgress.show(getV());
        Api.findService().getWorldHotUser(pageSize,page)
                .compose(XApi.<TxtModel>getApiTransformer())
                .compose(XApi.<TxtModel>getScheduler())
                .compose(getV().<TxtModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<TxtModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        progress.dismiss();
                        Log.i("FindChildFragment","error:"+error.getMessage());
                    }

                    @Override
                    public void onNext(TxtModel txtModel) {
                        progress.dismiss();
                        if(!txtModel.isError()){
                            getV().hotUserResponse(txtModel.getResult().getResult(),page);
                        }else{
                            ToastUtil.showToast(txtModel.getErrorMsg());
                        }
                    }
                });
    }


    /**
     * 初始化适配器
     * @param stringList
     * @return
     */
    public CommonListViewAdapter<TxtModel.TxtResult.Result> initAdapter(final List<TxtModel.TxtResult.Result> stringList){
        CommonListViewAdapter<TxtModel.TxtResult.Result> adapter = new CommonListViewAdapter<TxtModel.TxtResult.Result>(getV(),stringList, R.layout.layout_user_item) {
            @Override
            protected void convertView(View item, final TxtModel.TxtResult.Result s, final int position) {
                Log.i("TopicApplyAct","执行");
                ImageView imageView = CommonViewHolder.get(item, R.id.img);
                TextView textView = CommonViewHolder.get(item, R.id.user_hot_name);
                textView.setText(s.getNickname());
                GlideDownLoadImage.getInstance().loadCircleImage(s.getHead_img_url(),imageView);
            }
        };
        return adapter;
    }

}
