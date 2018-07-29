package cn.see.presenter.minep;

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
import cn.see.fragment.fragmentview.findview.FindActFragment;
import cn.see.fragment.fragmentview.mineview.MineAct;
import cn.see.model.FindActModel;
import cn.see.util.ToastUtil;
import cn.see.util.glide.GlideDownLoadImage;
import cn.see.util.http.Api;
import cn.see.util.widet.CustomProgress;

/**
 * @日期：2018/6/21
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明：
 */

public class MineActPresenter extends XPresent<MineAct> {

    public CustomProgress progress;

    /**
     * 获取活动列表
     * @param user_id
     */
    public void getTextAct(String user_id, final int page){
        progress = CustomProgress.show(getV());
        Api.mineService().getMineAct(user_id,"15",page)
                .compose(XApi.<FindActModel>getApiTransformer())
                .compose(XApi.<FindActModel>getScheduler())
                .compose(getV().<FindActModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<FindActModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        progress.dismiss();
                    }

                    @Override
                    public void onNext(FindActModel tabModel) {
                        if(!tabModel.isError()){
                            getV().actResponse(tabModel.getResult(),page);
                        }else{
                            ToastUtil.showToast(tabModel.getErrorMsg());
                        }
                    }
                });
    }


    public CommonListViewAdapter<FindActModel.ActResult.ActList> initAdapter(List<FindActModel.ActResult.ActList> actListList){

        CommonListViewAdapter<FindActModel.ActResult.ActList> adapter = new CommonListViewAdapter<FindActModel.ActResult.ActList>(getV(),actListList, R.layout.layout_find_act_item) {

            @Override
            protected void convertView(View item, FindActModel.ActResult.ActList actList, int position) {
                ImageView imageView = CommonViewHolder.get(item, R.id.img_thub);
                TextView time = CommonViewHolder.get(item, R.id.time);
                TextView cont = CommonViewHolder.get(item, R.id.cont);
                cont.setText("参与人 "+actList.getUser_count());
                time.setText(actList.getStart_time_info()+"至"+actList.getEnd_time_info());
                GlideDownLoadImage.getInstance().loadCircleImageRoleFxy(actList.getUrl(),imageView,4);
            }
        };
        return adapter;
    }

}
