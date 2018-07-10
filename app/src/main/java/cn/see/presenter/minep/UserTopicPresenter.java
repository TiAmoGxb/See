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
import cn.see.fragment.fragmentview.mineview.UserTopicFragment;
import cn.see.model.MineTextModel;
import cn.see.util.ToastUtil;
import cn.see.util.UserUtils;
import cn.see.util.constant.HttpConstant;
import cn.see.util.glide.GlideDownLoadImage;
import cn.see.util.http.Api;

/**
 * @日期：2018/7/4
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明：
 */

public class UserTopicPresenter extends XPresent<UserTopicFragment> {


    /**
     * 我发起的话题
     * @param page
     */
    public void getTopEdit(int page){
        Api.mineService().getUserEditTopic(UserUtils.getUserID(getV().getActivity()),"20",page)
                .compose(XApi.<MineTextModel>getApiTransformer())
                .compose(XApi.<MineTextModel>getScheduler())
                .compose(getV().<MineTextModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<MineTextModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        ToastUtil.showToast(HttpConstant.NET_ERROR_MSG);
                        Log.i("FindChildFragment","error:"+error.getMessage());
                    }

                    @Override
                    public void onNext(MineTextModel txtResult) {
                        if(!txtResult.isError()){
                            getV().dataResponse(txtResult.getResult().getLists());
                        }else{
                            ToastUtil.showToast(txtResult.getErrorMsg());
                        }
                    }
                });
    }

    /**
     * 我参与的话题
     */
    public void getTopApply(int page){
        Api.homeService().getAttTopic(UserUtils.getUserID(getV().getActivity()),page,"20")
                .compose(XApi.<MineTextModel>getApiTransformer())
                .compose(XApi.<MineTextModel>getScheduler())
                .compose(getV().<MineTextModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<MineTextModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        Log.i("FindChildFragment","error:"+error.getMessage());
                    }

                    @Override
                    public void onNext(MineTextModel topicModel) {
                        if(!topicModel.isError()){
                            getV().dataResponse(topicModel.getResult().getLists());
                        }else{
                            ToastUtil.showToast(topicModel.getErrorMsg());
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
