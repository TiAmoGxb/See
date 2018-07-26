package cn.see.fragment.fragmentview.findview;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;
import cn.droidlover.xdroidmvp.router.Router;
import cn.see.R;
import cn.see.adapter.RecryCommonAdapter;
import cn.see.adapter.ViewHolder;
import cn.see.base.BaseFragement;
import cn.see.fragment.fragmentview.mineview.ArticleDetailsAct;
import cn.see.model.MineAttModel;
import cn.see.model.MineTextModel;
import cn.see.util.ToastUtil;
import cn.see.util.UserUtils;
import cn.see.util.constant.HttpConstant;
import cn.see.util.constant.IntentConstant;
import cn.see.util.glide.GlideDownLoadImage;
import cn.see.util.http.Api;
import cn.see.util.version.PreferenceUtils;
import cn.see.util.widet.CustomProgress;

/**
 * @日期：2018/7/26
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 文章
 */

public class SearchTextFragment extends BaseFragement {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;


    @Override
    public void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void initAfter() {
        String cont = PreferenceUtils.getString(getActivity(), "text");
        if(!cont.equals("")){
            delTopic(UserUtils.getUserID(getActivity()),cont);
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.layout_sear_text_fragment;
    }

    @Override
    public XPresent bindPresent() {
        return null;
    }

    @Override
    public void setListener() {

    }

    @Override
    public void widgetClick(View v) {

    }

    public void delTopic(String uid, String str){
        Api.mineService().seText(uid,1,"25",str)
                .compose(XApi.<MineTextModel>getApiTransformer())
                .compose(XApi.<MineTextModel>getScheduler())
                .subscribe(new ApiSubscriber<MineTextModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        ToastUtil.showToast(HttpConstant.NET_ERROR_MSG);
                        Log.i(TAG,"error:"+error.toString());
                    }
                    @Override
                    public void onNext(MineTextModel mineAttModel) {
                        if(!mineAttModel.isError()){
                            data(mineAttModel.getResult().getLists());
                        }else{
                            ToastUtil.showToast(mineAttModel.getErrorMsg());
                        }
                    }
                });
    }

    private void data(final List<MineTextModel.MineTextResult.ResultList> lists) {
        if(lists.size() == 0){
            ToastUtil.showToast("暂无搜索结果");
            return;
        }
        recyclerView.setAdapter(new RecryCommonAdapter<MineTextModel.MineTextResult.ResultList>(getActivity(),R.layout.layout_text_other_item,lists) {
            @Override
            protected void convert(ViewHolder holder, MineTextModel.MineTextResult.ResultList result, int position) {
                holder.setText(R.id.msg,result.getMsg());
                holder.setText(R.id.nick_name,result.getNickname());
                holder.setText(R.id.time,result.getCreate_time_info());
                ImageView userView = (ImageView) holder.getView(R.id.txt_img);
                GlideDownLoadImage.getInstance().loadImage(result.getUrl(),userView);
                setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                        Router.newIntent(getActivity())
                                .putString(IntentConstant.ARTIC_TEXT_ID,lists.get(position).getText_id())
                                .to(ArticleDetailsAct.class)
                                .launch();
                    }
                    @Override
                    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                        return false;
                    }
                });
            }
        });
    }


}
