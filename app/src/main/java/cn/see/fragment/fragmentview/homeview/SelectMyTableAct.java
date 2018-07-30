package cn.see.fragment.fragmentview.homeview;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhy.autolayout.AutoRelativeLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;
import cn.see.R;
import cn.see.adapter.CommonListViewAdapter;
import cn.see.adapter.RecryCommonAdapter;
import cn.see.adapter.ViewHolder;
import cn.see.base.BaseActivity;
import cn.see.base.BaseModel;
import cn.see.event.FindTabEvent;
import cn.see.event.MsgEvent;
import cn.see.model.AllTabModel;
import cn.see.model.TabModel;
import cn.see.util.SpaceItemDecoration;
import cn.see.util.ToastUtil;
import cn.see.util.UserUtils;
import cn.see.util.constant.HttpConstant;
import cn.see.util.glide.GlideDownLoadImage;
import cn.see.util.http.Api;
import cn.see.util.widet.CustomProgress;

/**
 * @日期：2018/6/8
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 选择我的标签
 */
public class SelectMyTableAct extends BaseActivity {

    private List<String> tabs = new ArrayList<>();
    private static final String TAG =  "SelectMyTableAct";
    private int num;

    @BindView(R.id.title_tv_base)
    TextView titles;
    @BindView(R.id.title_tv_op)
    TextView opention;
    @BindView(R.id.sel_table_recy)
    RecyclerView recyclerView;

    @OnClick(R.id.back_rela)
    void bacAct(){
        onBack();
    }

    @OnClick(R.id.title_tv_op)
    void com(){
        if(tabs.size()<1){
            ToastUtil.showToast("还未选择标签");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        for (String t:tabs){
            buffer.append(t+",");
        }
        String tabid = buffer.toString().substring(0, buffer.length() - 1);
        saveTab(tabid);
    }

    @Override
    public void initView() {
        titles.setText("选择我的标签");
        opention.setText("保存");
        opention.setVisibility(View.VISIBLE);
        recyclerView.addItemDecoration(new SpaceItemDecoration(2, 40, false));
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
    }

    @Override
    public void initAfter() {
        getTabText();
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_select_my_table;
    }

    @Override
    public XPresent bindPresent() {
        return null;
    }

    @Override
    public void setListener() {
    }


    public void getTabText(){
        final CustomProgress progress = CustomProgress.show(this);
        Api.findService().getAllTab(UserUtils.getUserID(this))
                .compose(XApi.<AllTabModel>getApiTransformer())
                .compose(XApi.<AllTabModel>getScheduler())
                .subscribe(new ApiSubscriber<AllTabModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        progress.dismiss();
                        Log.i("FindChildFragment","error:"+error.getMessage());
                    }

                    @Override
                    public void onNext(final AllTabModel findTextcModel) {
                        progress.dismiss();
                        if(!findTextcModel.isError()){
                            recyclerView.setAdapter(new RecryCommonAdapter<TabModel.TabList>(SelectMyTableAct.this,R.layout.layout_find_find_top_rec_item,findTextcModel.getResult().getList()) {
                                @Override
                                protected void convert(final ViewHolder holder, final TabModel.TabList tableBean, int position) {
                                    ImageView imageView = holder.getView(R.id.icon);
                                    imageView.setLayoutParams(new AutoRelativeLayout.LayoutParams(
                                            AutoRelativeLayout.LayoutParams.MATCH_PARENT,340
                                    ));
                                    if(position == findTextcModel.getResult().getList().size()-1){
                                        holder.setVisible(R.id.b_view,true);
                                    }else{
                                        holder.setVisible(R.id.b_view,false);
                                    }
                                    holder.setText(R.id.txt,tableBean.getText());
                                    GlideDownLoadImage.getInstance().loadCircleImageRole(tableBean.getUrl(),imageView,4);

                                    imageView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Log.i(TAG,"isFlag:"+tableBean.isFlag());
                                            if(!tableBean.isFlag()){
                                                if(num<4){
                                                    holder.setVisible(R.id.ischange,true);
                                                    tableBean.setFlag(true);
                                                    num++;
                                                    tabs.add(tableBean.getTab_id());
                                                }else{
                                                    ToastUtil.showToast("最多只能选4个标签");
                                                }
                                            }else{
                                                holder.setVisible(R.id.ischange,false);
                                                tableBean.setFlag(false);
                                                num--;
                                                tabs.remove(tableBean.getTab_id());
                                            }
                                        }
                                    });
                                }
                            });
                        }else{
                            ToastUtil.showToast(findTextcModel.getErrorMsg());
                        }
                    }
                });
    }


    public void saveTab(String tab_arr){
        Log.i(TAG,"tab_arr:"+tab_arr);
        final CustomProgress progress = CustomProgress.show(this);
        Api.findService().saveTab(UserUtils.getUserID(this),tab_arr)
                .compose(XApi.<BaseModel>getApiTransformer())
                .compose(XApi.<BaseModel>getScheduler())
                .subscribe(new ApiSubscriber<BaseModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        progress.dismiss();
                        ToastUtil.showToast(HttpConstant.NET_ERROR_MSG);
                        Log.i("FindChildFragment","error:"+error.getMessage());
                    }
                    @Override
                    public void onNext(final BaseModel findTextcModel) {
                        progress.dismiss();
                        if(!findTextcModel.isError()){
                            //发送事件
                            EventBus.getDefault().post(new FindTabEvent());
                            onBack();
                        }else{
                            ToastUtil.showToast(findTextcModel.getErrorMsg());
                        }
                    }
                });
    }
}
