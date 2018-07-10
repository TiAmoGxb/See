package cn.see.presenter.minep;

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
import cn.see.fragment.fragmentview.mineview.TopicApplyAct;
import cn.see.model.MineTextModel;
import cn.see.util.ToastUtil;
import cn.see.util.constant.HttpConstant;
import cn.see.util.glide.GlideDownLoadImage;
import cn.see.util.http.Api;
import cn.see.util.widet.CustomProgress;

/**
 * @日期：2018/7/3
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明：
 */

public class TopicApplyPresenter extends XPresent<TopicApplyAct>{
    CustomProgress progress;

    public void getTopicUser(String topic_id,int page){
        progress = CustomProgress.show(getV());
        Api.mineService().getTopicApplyUser(topic_id,page,"28")
                .compose(XApi.<MineTextModel>getApiTransformer())
                .compose(XApi.<MineTextModel>getScheduler())
                .compose(getV().<MineTextModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<MineTextModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        progress.dismiss();
                        ToastUtil.showToast(HttpConstant.NET_ERROR_MSG);
                    }

                    @Override
                    public void onNext(MineTextModel tabModel) {
                        progress.dismiss();
                        if(!tabModel.isError()){
                            getV().userResponse(tabModel.getResult().getLists());
                        }else{
                            ToastUtil.showToast(tabModel.getErrorMsg());
                        }
                    }
                });
    }

    /**
     * 初始化适配器
     * @param stringList
     * @return
     */
    public CommonListViewAdapter<MineTextModel.MineTextResult.ResultList> initAdapter(final List<MineTextModel.MineTextResult.ResultList> stringList){
        CommonListViewAdapter<MineTextModel.MineTextResult.ResultList> adapter = new CommonListViewAdapter<MineTextModel.MineTextResult.ResultList>(getV(),stringList, R.layout.layout_find_world_top_item) {
            @Override
            protected void convertView(View item, final MineTextModel.MineTextResult.ResultList s, final int position) {
                CommonViewHolder.get(item,R.id.l_v).setVisibility(View.GONE);
                Log.i("TopicApplyAct","执行");
                ImageView imageView = CommonViewHolder.get(item, R.id.user_hot_img);
                TextView textView = CommonViewHolder.get(item, R.id.user_hot_name);
                textView.setText(s.getNickname());
                GlideDownLoadImage.getInstance().loadCircleImageToCust(s.getUrl(),imageView);
            }
        };
        return adapter;
    }
}
