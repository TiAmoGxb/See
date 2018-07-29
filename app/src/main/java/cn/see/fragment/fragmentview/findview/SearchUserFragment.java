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
import cn.see.fragment.fragmentview.mineview.AttentionAct;
import cn.see.fragment.fragmentview.mineview.OtherMainAct;
import cn.see.model.MineAttModel;
import cn.see.util.ToastUtil;
import cn.see.util.UserUtils;
import cn.see.util.constant.HttpConstant;
import cn.see.util.constant.IntentConstant;
import cn.see.util.constant.PreferenceConstant;
import cn.see.util.glide.GlideDownLoadImage;
import cn.see.util.http.Api;
import cn.see.util.version.PreferenceUtils;
import cn.see.util.widet.CustomProgress;

/**
 * @日期：2018/7/26
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 用户
 */

public class SearchUserFragment extends BaseFragement {

    private static String TAG = "SearchUserFragment";
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;



    @Override
    public void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void initAfter() {
        String cont = PreferenceUtils.getString(getActivity(), "user");
        if(!cont.equals("")){
            delTopic(UserUtils.getUserID(getActivity()),cont);
            List<String> listData = PreferenceUtils.getListData(getActivity(), PreferenceConstant.SERCH_RECORDING, String.class);
            listData.add(cont);
            PreferenceUtils.putListData(getActivity(),PreferenceConstant.SERCH_RECORDING, listData);
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.layout_sear_user_fragment;
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
        final CustomProgress progress = CustomProgress.show(getActivity());
        Api.mineService().seUser(uid,str)
                .compose(XApi.<MineAttModel>getApiTransformer())
                .compose(XApi.<MineAttModel>getScheduler())
                .subscribe(new ApiSubscriber<MineAttModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        ToastUtil.showToast(HttpConstant.NET_ERROR_MSG);
                        Log.i(TAG,"error:"+error.toString());
                        progress.dismiss();
                    }
                    @Override
                    public void onNext(MineAttModel mineAttModel) {
                        progress.dismiss();
                        if(!mineAttModel.isError()){
                            data(mineAttModel.getResult());
                        }else{
                            ToastUtil.showToast(mineAttModel.getErrorMsg());
                        }
                    }
                });
    }

    public void data(final List<MineAttModel.AttResult> result){
        Log.i(TAG,"result:"+result.toString());
        if(result.size() == 0){
            ToastUtil.showToast("暂无搜索结果");
            return;
        }
        recyclerView.setAdapter(new RecryCommonAdapter<MineAttModel.AttResult>(getActivity(), R.layout.layout_mine_att_item,result) {
            @Override
            protected void convert(ViewHolder holder, MineAttModel.AttResult o, int position) {
                holder.setText(R.id.nick_name,o.getNickname());
                holder.setText(R.id.signin_txt,o.getSignature());
                ImageView imageView = holder.getView(R.id.img_icon);
                GlideDownLoadImage.getInstance().loadCircleImage(o.getHead_img_url(),imageView);
                holder.setOnClickListener(R.id.send_tv, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.showToast("私信");
                    }
                });
                setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                        Router.newIntent(getActivity())
                                .putString(IntentConstant.OTHER_USER_ID,result.get(position).getUser_id())
                                .to(OtherMainAct.class)
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
