package cn.see.presenter.minep;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.List;

import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;
import cn.see.R;
import cn.see.adapter.MultiItemTypeAdapter;
import cn.see.adapter.RecryCommonAdapter;
import cn.see.adapter.ViewHolder;
import cn.see.base.BaseModel;
import cn.see.fragment.fragmentview.mineview.MineSchoolAct;
import cn.see.model.MineSchoolModel;
import cn.see.util.ToastUtil;
import cn.see.util.UserUtils;
import cn.see.util.constant.HttpConstant;
import cn.see.util.http.Api;

/**
 * Created by sks on 2018/7/17.
 */

public class MineSchoolPresenter extends XPresent<MineSchoolAct> {

    /**
     * 获取学校
     * @param text
     */
    public void getSchool(String text){
        Api.mineService().seaSchool(1,"20",text)
                .compose(XApi.<MineSchoolModel>getApiTransformer())
                .compose(XApi.<MineSchoolModel>getScheduler())
                .compose(getV().<MineSchoolModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<MineSchoolModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        ToastUtil.showToast(HttpConstant.NET_ERROR_MSG);
                        Log.i("FindChildFragment","error:"+error.getMessage());
                    }

                    @Override
                    public void onNext(MineSchoolModel txtResult) {
                        if(!txtResult.isError()){
                            getV().schoolResponse(txtResult.getResult().getList());
                        }else{
                            ToastUtil.showToast(txtResult.getErrorMsg());
                        }
                    }
                });
    }


    /**
     * 修改学校
     * @param id
     */
    public void setSchool(String id){
        Api.mineService().setSchool(UserUtils.getUserID(getV()),id)
                .compose(XApi.<BaseModel>getApiTransformer())
                .compose(XApi.<BaseModel>getScheduler())
                .compose(getV().<BaseModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        ToastUtil.showToast(HttpConstant.NET_ERROR_MSG);
                        Log.i("FindChildFragment","error:"+error.getMessage());
                    }

                    @Override
                    public void onNext(BaseModel txtResult) {
                        if(!txtResult.isError()){
                            getV().setSusess();
                        }else{
                            ToastUtil.showToast(txtResult.getErrorMsg());
                        }
                    }
                });
    }


    public RecryCommonAdapter<MineSchoolModel.SchoolResult.SchoolList> initAdapter(final List<MineSchoolModel.SchoolResult.SchoolList> schoolLists){
        RecryCommonAdapter<MineSchoolModel.SchoolResult.SchoolList> adapter = new RecryCommonAdapter<MineSchoolModel.SchoolResult.SchoolList>(getV(), R.layout.layout_mine_school_item,schoolLists) {
            @Override
            protected void convert(ViewHolder holder, MineSchoolModel.SchoolResult.SchoolList schoolList, int position) {

                if(position == 0){
                    holder.setVisible(R.id.v_one,true);
                }else{
                    holder.setVisible(R.id.v_one,false);
                }
                if(position == schoolLists.size()-1){
                    holder.setVisible(R.id.v_three,true);
                }else{
                    holder.setVisible(R.id.v_three,false);
                }

                holder.setText(R.id.s_name,schoolList.getSchool_name());
            }
        };

        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                 setSchool(schoolLists.get(position).getSchool_id());
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        return adapter;
    }

}
