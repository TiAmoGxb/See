package cn.see.fragment.release.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;
import cn.see.R;
import cn.see.adapter.MultiItemTypeAdapter;
import cn.see.adapter.RecryCommonAdapter;
import cn.see.adapter.ViewHolder;
import cn.see.base.BaseActivity;
import cn.see.model.AllTopicModel;
import cn.see.model.MineTextModel;
import cn.see.util.ToastUtil;
import cn.see.util.constant.HttpConstant;
import cn.see.util.constant.IntentConstant;
import cn.see.util.http.Api;

/**
 * 选择话题
 */
public class SelectTopicAct extends BaseActivity {

    @BindView(R.id.title_tv_base)
    TextView title;
    @BindView(R.id.topic_recy)
    RecyclerView recyclerView;

    @OnClick(R.id.back_rela)
    void bacView(){
        onBack();
    }

    @Override
    public void initView() {
        title.setText("话题或活动");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void initAfter() {
        getTopDataHot(1);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_select_topic;
    }

    @Override
    public XPresent bindPresent() {
        return null;
    }

    @Override
    public void setListener() {

    }


    private void getTopDataHot(int page){
        Api.findService().getTopicHot("20",page)
                .compose(XApi.<AllTopicModel>getApiTransformer())
                .compose(XApi.<AllTopicModel>getScheduler())
                .subscribe(new ApiSubscriber<AllTopicModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        ToastUtil.showToast(HttpConstant.NET_ERROR_MSG);
                        Log.i("FindChildFragment","error:"+error.getMessage());
                    }

                    @Override
                    public void onNext(final AllTopicModel txtResult) {
                        final List<MineTextModel.MineTextResult.ResultList> resultLists = txtResult.getResult().getList();
                        RecryCommonAdapter<MineTextModel.MineTextResult.ResultList> adapter = new RecryCommonAdapter<MineTextModel.MineTextResult.ResultList>(SelectTopicAct.this, R.layout.layout_release_sel_top_item,resultLists ) {
                            @Override
                            protected void convert(ViewHolder holder, MineTextModel.MineTextResult.ResultList o, int position) {
                                holder.setText(R.id.name, o.getTname());
                                holder.setText(R.id.cont, "话题·"+o.getApply()+"参与");
                            }

                        };
                        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                                Intent intent  = new Intent();
                                intent.putExtra(IntentConstant.SEL_TOPIC_NAME,resultLists.get(position).getTname());
                                setResult(ReleasePreviewAct.ADD_TOPIC_CODE,intent);
                                onBack();
                            }

                            @Override
                            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                                return false;
                            }
                        });
                        recyclerView.setAdapter(adapter);
                    }
                });
    }
}
