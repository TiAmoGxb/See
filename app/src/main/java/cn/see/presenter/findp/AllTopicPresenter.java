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
import cn.see.fragment.fragmentview.findview.AllTopicFragment;
import cn.see.model.AllTopicModel;
import cn.see.model.MineTextModel;
import cn.see.util.ToastUtil;
import cn.see.util.constant.HttpConstant;
import cn.see.util.glide.GlideDownLoadImage;
import cn.see.util.http.Api;

/**
 * @日期：2018/7/3
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明：
 */

public class AllTopicPresenter extends XPresent<AllTopicFragment> {


    public void getTopDataHot(int page){
        Api.findService().getTopicHot("20",page)
                .compose(XApi.<AllTopicModel>getApiTransformer())
                .compose(XApi.<AllTopicModel>getScheduler())
                .compose(getV().<AllTopicModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<AllTopicModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        ToastUtil.showToast(HttpConstant.NET_ERROR_MSG);
                        Log.i("FindChildFragment","error:"+error.getMessage());
                    }

                    @Override
                    public void onNext(AllTopicModel txtResult) {
                        if(!txtResult.isError()){
                            getV().dataResponse(txtResult.getResult().getList());
                        }else{
                            ToastUtil.showToast(txtResult.getErrorMsg());
                        }
                    }
                });
    }

    public void getTopDataNew(int page){
        Api.findService().getTopicNews("20",page)
                .compose(XApi.<AllTopicModel>getApiTransformer())
                .compose(XApi.<AllTopicModel>getScheduler())
                .compose(getV().<AllTopicModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<AllTopicModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        ToastUtil.showToast(HttpConstant.NET_ERROR_MSG);
                        Log.i("FindChildFragment","error:"+error.getMessage());
                    }

                    @Override
                    public void onNext(AllTopicModel txtResult) {
                        if(!txtResult.isError()){
                            getV().dataResponse(txtResult.getResult().getList());
                        }else{
                            ToastUtil.showToast(txtResult.getErrorMsg());
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
        CommonListViewAdapter<MineTextModel.MineTextResult.ResultList> adapter = new CommonListViewAdapter<MineTextModel.MineTextResult.ResultList>(getV().getActivity(),stringList, R.layout.layout_alltopic_list_item) {
            @Override
            protected void convertView(View item, final MineTextModel.MineTextResult.ResultList s, final int position) {
                ImageView topicImg = CommonViewHolder.get(item, R.id.topic_img);
                TextView tName = CommonViewHolder.get(item, R.id.topic_name);
                TextView tCont = CommonViewHolder.get(item, R.id.topic_cont);
                TextView seeNum = CommonViewHolder.get(item, R.id.see_num);
                TextView comNum = CommonViewHolder.get(item, R.id.com_num);

                tName.setText(s.getTname());
                tCont.setText(s.getDescription());
                seeNum.setText(s.getSee());
                comNum.setText(s.getApply());
                GlideDownLoadImage.getInstance().loadImage(s.getUrl(),topicImg);
            }
        };
        return adapter;
    }

}
