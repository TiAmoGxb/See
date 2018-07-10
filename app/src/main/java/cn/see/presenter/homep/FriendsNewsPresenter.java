package cn.see.presenter.homep;

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
import cn.see.fragment.fragmentview.homeview.FrindeUpdateAct;
import cn.see.model.FriendsNewsModel;
import cn.see.util.ToastUtil;
import cn.see.util.glide.GlideDownLoadImage;
import cn.see.util.http.Api;
import cn.see.util.widet.CustomProgress;

/**
 * @日期：2018/6/14
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 好友动态P
 */

public class FriendsNewsPresenter extends XPresent<FrindeUpdateAct> {

    public CustomProgress progress;

    /**
     * 初始化适配器
     * @param stringList
     * @return
     */
    public CommonListViewAdapter<FriendsNewsModel.NewsResult.NewsList> initAdapter(final List<FriendsNewsModel.NewsResult.NewsList> stringList){
        CommonListViewAdapter<FriendsNewsModel.NewsResult.NewsList> adapter = new CommonListViewAdapter<FriendsNewsModel.NewsResult.NewsList>(getV(),stringList, R.layout.layout_home_att_f_update_item) {
            @Override
            protected void convertView(View item, FriendsNewsModel.NewsResult.NewsList s, int position) {
                ImageView imageView= CommonViewHolder.get(item, R.id.dt_img);
                TextView dtName = CommonViewHolder.get(item, R.id.dt_name);
                TextView dtTime = CommonViewHolder.get(item, R.id.dt_time);
                TextView dtToName = CommonViewHolder.get(item, R.id.dt_to_name);
                TextView dtType = CommonViewHolder.get(item, R.id.dt_type);
                TextView dtTypeText = CommonViewHolder.get(item, R.id.dt_type_txt);
                View viewOne = CommonViewHolder.get(item, R.id.ones_view);
                View viewTwo = CommonViewHolder.get(item, R.id.twos_view);
                View view = CommonViewHolder.get(item, R.id.botom_view);

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

                switch (s.getType()){
                    case "like":
                        dtName.setText(s.getFnickname());
                        dtToName.setText(s.getText_nickname());
                        dtType.setText("点赞了");
                        dtTypeText.setText("的文章");
                        break;
                    case "review":
                        dtName.setText(s.getNickname());
                        dtToName.setText(s.getText_nickname());
                        dtType.setText("评论了");
                        dtTypeText.setText("的文章");
                        break;
                    case "attention":
                        dtName.setText(s.getFnickname());
                        dtToName.setText(s.getTnickname());
                        dtType.setText("关注了");
                        dtTypeText.setText("");
                        break;
                    case "fans":
                        dtName.setText(s.getFnickname());
                        dtToName.setText(s.getTnickname());
                        dtType.setText("成为了");
                        dtTypeText.setText("的粉丝");
                        break;
                }
                dtTime.setText(s.getCreate_time_info());
                GlideDownLoadImage.getInstance().loadImage(getV(),s.getHead_img_url(),imageView);

            }
        };
        return adapter;
    }


    /**
     * 获取好友动态
     * @param user_id
     */
    public void getFriendsNews(String user_id,int pageSize, String likeSize, String reviewSize, String attentionSize, String fansSize){
        progress = CustomProgress.show(getV());
        Api.homeService().getFriendsNews(user_id,pageSize,likeSize,reviewSize,attentionSize,fansSize)
                .compose(XApi.<FriendsNewsModel>getApiTransformer())
                .compose(XApi.<FriendsNewsModel>getScheduler())
                .compose(getV().<FriendsNewsModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<FriendsNewsModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        progress.dismiss();
                    }

                    @Override
                    public void onNext(FriendsNewsModel txtModel) {
                        if(!txtModel.isError()){
                            getV().friendsNewsResponse(txtModel.getResult());
                        }else{
                            ToastUtil.showToast(txtModel.getErrorMsg());
                        }
                    }
                });
    }

}
