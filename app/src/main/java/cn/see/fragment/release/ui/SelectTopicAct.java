package cn.see.fragment.release.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
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
import cn.see.model.ReleaseTopicModel;
import cn.see.util.ToastUtil;
import cn.see.util.constant.HttpConstant;
import cn.see.util.constant.IntentConstant;
import cn.see.util.http.Api;

/**
 * 选择话题
 */
public class SelectTopicAct extends BaseActivity {


    private static final String TAG =  "SelectTopicAct";
    private List<ReleaseTopicModel.TopicResult.TopicList> resultLists = new ArrayList<>();

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
        Api.releaseService().getTopicHot()
                .compose(XApi.<ReleaseTopicModel>getApiTransformer())
                .compose(XApi.<ReleaseTopicModel>getScheduler())
                .subscribe(new ApiSubscriber<ReleaseTopicModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        ToastUtil.showToast(HttpConstant.NET_ERROR_MSG);
                        Log.i("FindChildFragment","error:"+error.getMessage());
                    }

                    @Override
                    public void onNext(final ReleaseTopicModel txtResult) {
                        resultLists.clear();
                        if(txtResult.getResult().getActivity().size()>0){
                            resultLists.addAll(txtResult.getResult().getActivity());
                        }
                        resultLists.addAll(txtResult.getResult().getTopic());
                        RecryCommonAdapter<ReleaseTopicModel.TopicResult.TopicList> adapter = new RecryCommonAdapter<ReleaseTopicModel.TopicResult.TopicList>(SelectTopicAct.this, R.layout.layout_release_sel_top_item,resultLists ) {
                            @Override
                            protected void convert(ViewHolder holder, ReleaseTopicModel.TopicResult.TopicList o, int position) {

                                if(o.getActivity_id()!=null&&o.getName()!=null){
                                    holder.setText(R.id.name, o.getName());
                                    holder.setText(R.id.cont, "活动");
                                }else{
                                    holder.setText(R.id.name, o.getTname());
                                    holder.setText(R.id.cont, "话题·"+o.getApply_count()+"参与");
                                }
                            }

                        };
                        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                                Intent intent  = new Intent();
                                if(resultLists.get(position).getActivity_id()!=null&&resultLists.get(position).getName()!=null){
                                    intent.putExtra(IntentConstant.SEL_TOPIC_NAME,resultLists.get(position).getName());
                                    intent.putExtra(IntentConstant.SEL_TOPIC_ID,resultLists.get(position).getActivity_id());
                                    intent.putExtra("type","activity");
                                }else{
                                    intent.putExtra(IntentConstant.SEL_TOPIC_NAME,resultLists.get(position).getTname());
                                    intent.putExtra(IntentConstant.SEL_TOPIC_ID,resultLists.get(position).getTopic_id());
                                    intent.putExtra("type","topic");
                                }
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
