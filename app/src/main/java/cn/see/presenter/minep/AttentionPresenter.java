package cn.see.presenter.minep;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;
import cn.see.R;
import cn.see.adapter.CommonListViewAdapter;
import cn.see.adapter.CommonViewHolder;
import cn.see.app.App;
import cn.see.chat.activity.ChatActivity;
import cn.see.fragment.fragmentview.mineview.AttentionAct;
import cn.see.model.MineAttModel;
import cn.see.model.UserInfoModel;
import cn.see.util.ToastUtil;
import cn.see.util.UserUtils;
import cn.see.util.glide.GlideDownLoadImage;
import cn.see.util.http.Api;
import cn.see.util.widet.CustomProgress;

/**
 * @日期：2018/6/11
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 我的关注P
 */

public class AttentionPresenter extends XPresent<AttentionAct> {


    /**
     * 初始化适配器
     * @param stringList
     * @return
     */
    public CommonListViewAdapter<MineAttModel.AttResult> initAdapter(final List<MineAttModel.AttResult> stringList){
        CommonListViewAdapter<MineAttModel.AttResult> adapter = new CommonListViewAdapter<MineAttModel.AttResult>(getV(),stringList, R.layout.layout_mine_att_item) {
            @Override
            protected void convertView(View item, MineAttModel.AttResult s, final int position) {
                TextView nickName = CommonViewHolder.get(item, R.id.nick_name);
                TextView signinTxt = CommonViewHolder.get(item, R.id.signin_txt);
                View viewOne = CommonViewHolder.get(item, R.id.ones_view);
                View viewTwo = CommonViewHolder.get(item, R.id.twos_view);
                View view = CommonViewHolder.get(item, R.id.botom_view);
                ImageView userIcon = CommonViewHolder.get(item, R.id.img_icon);
                if(position == 0){
                    viewOne.setVisibility(View.VISIBLE);
                }else{
                    viewOne.setVisibility(View.GONE);
                }
                if(position == stringList.size()-1){
                    viewTwo.setVisibility(View.VISIBLE);
                }else{
                    view.setVisibility(View.VISIBLE);
                    viewTwo.setVisibility(View.GONE);
                }
                nickName.setText(s.getNickname());
                signinTxt.setText(s.getSignature());
                GlideDownLoadImage.getInstance().loadCircleImage(s.getHead_url(),userIcon);

                CommonViewHolder.get(item,R.id.send_tv).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        JMessageClient.getUserInfo("kanjian" + stringList.get(position).getUid(),new GetUserInfoCallback() {
                            @Override
                            public void gotResult(int i, String s, UserInfo userInfo) {
                                //已有用户
                                if(s.equals("Success")&&userInfo!=null){
                                    String title = userInfo.getNickname();
                                    if(TextUtils.isEmpty(title)){
                                        title = userInfo.getUserName();
                                    }
                                    Intent intent1 = new Intent(getV(), ChatActivity.class);
                                    intent1.putExtra(App.CONV_TITLE, title);
                                    intent1.putExtra(App.TARGET_ID, userInfo.getUserName());
                                    intent1.putExtra(App.TARGET_APP_KEY, userInfo.getAppKey());
                                    getV().startActivity(intent1);
                                }else{
                                    ToastUtil.showToast("该用户暂时没有开通私信服务");
                                }
                            }
                        });
                    }
                });
            }
        };
        return adapter;
    }

    /**
     * 获取我的关注
     */
    public void getAttUser(int page){
        final CustomProgress progress = CustomProgress.show(getV());
        Api.mineService().getUserAttention(UserUtils.getUserID(getV()),page,"20")
                .compose(XApi.<MineAttModel>getApiTransformer())
                .compose(XApi.<MineAttModel>getScheduler())
                .compose(getV().<MineAttModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<MineAttModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        progress.dismiss();
                    }

                    @Override
                    public void onNext(MineAttModel mineAttModel) {
                        progress.dismiss();
                        if(!mineAttModel.isError()){
                            getV().getAttUserResPonse(mineAttModel.getResult());
                        }else{
                            ToastUtil.showToast(mineAttModel.getErrorMsg());
                        }
                    }
                });
    }

}
