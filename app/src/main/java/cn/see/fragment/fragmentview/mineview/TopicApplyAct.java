package cn.see.fragment.fragmentview.mineview;

import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.see.R;
import cn.see.adapter.CommonListViewAdapter;
import cn.see.base.BaseActivity;
import cn.see.model.MineTextModel;
import cn.see.presenter.minep.TopicApplyPresenter;
import cn.see.util.ToastUtil;
import cn.see.util.constant.IntentConstant;
import cn.see.util.widet.putorefresh.PullToRefreshBase;
import cn.see.util.widet.putorefresh.PullToRefreshGridView;
import cn.see.util.widet.putorefresh.RefreshShowTime;

public class TopicApplyAct extends BaseActivity<TopicApplyPresenter> implements  PullToRefreshBase.OnRefreshListener2<GridView> {


    private static final String TAG = "TopicApplyAct" ;
    private List<MineTextModel.MineTextResult.ResultList> results = new ArrayList<>();
    private CommonListViewAdapter<MineTextModel.MineTextResult.ResultList> adapter;
    private int page = 1;
    private String topic_id;


    @BindView(R.id.title_tv_base)
    TextView titles;
    @BindView(R.id.verygridview)
    PullToRefreshGridView gridView;


    @OnClick(R.id.back_rela)
    void bacAct(){
        onBack();
    }

    @Override
    public void initView() {
        titles.setText("话题参与人");
        topic_id = getIntent().getStringExtra(IntentConstant.ARTIC_TOPIC_ID);
        gridView.getRefreshableView().setVerticalScrollBarEnabled(false);
        RefreshShowTime.showTime(gridView);
    }

    @Override
    public void initAfter() {
        getP().getTopicUser(topic_id,page);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_topic_apply;
    }

    @Override
    public TopicApplyPresenter bindPresent() {
        return new TopicApplyPresenter();
    }

    @Override
    public void setListener() {
        gridView.setOnRefreshListener(this);
    }

    /**
     * 获取数据
     * @param lists
     */
    public void userResponse(List<MineTextModel.MineTextResult.ResultList> lists){
        Log.i(TAG,"SIZE:"+lists.size());
        if(page>1){
            if(lists.size()==0){
                ToastUtil.showToast("没有更多参与用户");
            }else{
                results.addAll(lists);
                adapter.notifyDataSetChanged();
            }
        }else{
            results.clear();
            results.addAll(lists);
            adapter = getP().initAdapter(results);
            gridView.setAdapter(adapter);
        }
        gridView.onRefreshComplete();
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {

        page = 1;
        initAfter();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
        page ++;
        initAfter();
    }
}
