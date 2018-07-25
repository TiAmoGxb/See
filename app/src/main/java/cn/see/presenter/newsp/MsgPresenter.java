package cn.see.presenter.newsp;

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
import cn.see.fragment.fragmentview.newsview.MsgFragment;
import cn.see.model.FindActModel;
import cn.see.model.MsgContModel;
import cn.see.util.ToastUtil;
import cn.see.util.UserUtils;
import cn.see.util.constant.HttpConstant;
import cn.see.util.http.Api;
import cn.see.util.widet.CustomProgress;

/**
 * @日期：2018/7/2
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明：
 */

public class MsgPresenter extends XPresent<MsgFragment> {

    public CommonListViewAdapter<MsgBean> initAdapter(final List<MsgBean> beanList, final MsgContModel.ContResult result){

        CommonListViewAdapter<MsgBean> adapter = new CommonListViewAdapter<MsgBean>(getV().getActivity(), beanList,R.layout.layout_news_msg_list_item) {
            @Override
            protected void convertView(View item, MsgBean msgBean, int position) {
                ImageView imageView = CommonViewHolder.get(item, R.id.img);
                TextView textView = CommonViewHolder.get(item, R.id.tv);
                View tView = CommonViewHolder.get(item, R.id.t_view);
                View bView = CommonViewHolder.get(item, R.id.b_view);
                TextView num = CommonViewHolder.get(item, R.id.num);
                if(position == beanList.size()-2){
                    bView.setVisibility(View.GONE);
                }
                if(position == beanList.size()-1){
                    tView.setVisibility(View.VISIBLE);
                    bView.setVisibility(View.GONE);
                }

                if(result!=null){
                    if(position == 0){
                        if(!result.getLikes_count().equals("0")){
                            num.setVisibility(View.VISIBLE);
                            num.setText(result.getLikes_count());
                        }
                    }
                    if(position == 1){
                        if(!result.getReview_count().equals("0")){
                            num.setVisibility(View.VISIBLE);
                            num.setText(result.getReview_count());
                        }
                    }
                }

                imageView.setImageResource(msgBean.getImg());
                textView.setText(msgBean.getTv());
            }
        };
        return adapter;
    }



    public static class MsgBean{

        private int img;
        private String tv;
        private String num;

        public MsgBean(int img, String tv, String num) {
            this.img = img;
            this.tv = tv;
            this.num = num;
        }

        public int getImg() {
            return img;
        }

        public String getTv() {
            return tv;
        }

        public String getNum() {
            return num;
        }

        public void setImg(int img) {
            this.img = img;
        }

        public void setTv(String tv) {
            this.tv = tv;
        }

        public void setNum(String num) {
            this.num = num;
        }
    }

    /**
     * 获取活动列表
     * @param user_id
     */
    public void getTextAct(String user_id){
        Api.findService().getFindAct(user_id,1,"1")
                .compose(XApi.<FindActModel>getApiTransformer())
                .compose(XApi.<FindActModel>getScheduler())
                .compose(getV().<FindActModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<FindActModel>() {
                    @Override
                    protected void onFail(NetError error) {
                    }

                    @Override
                    public void onNext(FindActModel tabModel) {
                        if(!tabModel.isError()){
                            getV().actResponse(tabModel.getResult());
                        }else{
                            ToastUtil.showToast(tabModel.getErrorMsg());
                        }
                    }
                });
    }


    /**
     * 获取消息未读数量
     */
    public void getMsgCont(){
        Api.mineService().msgCont(UserUtils.getUserID(getV().getActivity()))
                .compose(XApi.<MsgContModel>getApiTransformer())
                .compose(XApi.<MsgContModel>getScheduler())
                .subscribe(new ApiSubscriber<MsgContModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        ToastUtil.showToast(HttpConstant.NET_ERROR_MSG);
                    }
                    @Override
                    public void onNext(MsgContModel tabModel) {
                        if(!tabModel.isError()){
                            getV().getMsg(tabModel.getResult());
                        }else{
                            ToastUtil.showToast(tabModel.getErrorMsg());
                        }
                    }
                });
    }



}
