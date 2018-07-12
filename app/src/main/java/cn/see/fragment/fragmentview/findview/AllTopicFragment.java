package cn.see.fragment.fragmentview.findview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.router.Router;
import cn.see.R;
import cn.see.adapter.CommonListViewAdapter;
import cn.see.base.BaseFragement;
import cn.see.fragment.fragmentview.mineview.TopicAct;
import cn.see.model.MineTextModel;
import cn.see.presenter.findp.AllTopicPresenter;
import cn.see.util.ToastUtil;
import cn.see.util.UserUtils;
import cn.see.util.constant.IntentConstant;
import cn.see.util.widet.putorefresh.PullToRefreshBase;
import cn.see.util.widet.putorefresh.PullToRefreshListView;
import cn.see.util.widet.putorefresh.RefreshShowTime;

/**
 * @日期：2018/7/3
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 所有话题 fragment
 */

public class AllTopicFragment extends BaseFragement<AllTopicPresenter> implements  PullToRefreshBase.OnRefreshListener2<ListView>{

    private List<MineTextModel.MineTextResult.ResultList> results = new ArrayList<>();
    private CommonListViewAdapter<MineTextModel.MineTextResult.ResultList> adapter;
    private int type;
    private int page = 1;

    @BindView(R.id.pull_topic_list)
    PullToRefreshListView listView;

    public static AllTopicFragment newInstance(int type) {
        AllTopicFragment newFragment = new AllTopicFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        newFragment.setArguments(bundle);
        return newFragment;
    }
    @Override
    public void initView() {
        Bundle args = getArguments();
        if (args != null) {
            type = args.getInt("type");
        }
        listView.getRefreshableView().setDividerHeight(0);
        adapter = getP().initAdapter(results);
        listView.setAdapter(adapter);
        RefreshShowTime.showTime(listView);
    }

    @Override
    public void initAfter() {
        if (type == 0){
            getP().getTopDataHot(page);
        }else{
            getP().getTopDataNew(page);
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.layout_alltopic_frg;
    }

    @Override
    public AllTopicPresenter bindPresent() {
        return new AllTopicPresenter();
    }

    @Override
    public void setListener() {
        listView.setOnRefreshListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(UserUtils.userIsLogin(getActivity())){
                    Router.newIntent(getActivity())
                            .to(TopicAct.class)
                            .putString(IntentConstant.ARTIC_TOPIC_ID,results.get(position-1).getId())
                            .launch();
                }
            }
        });
    }

    @Override
    public void widgetClick(View v) {

    }


    /**
     * 获取数据
     * @param list
     */
    public void dataResponse(List<MineTextModel.MineTextResult.ResultList> list){
        Log.i(TAG,"list:"+list);
        if(page>1){
            if(list.size()==0){
                ToastUtil.showToast("暂无更多话题");
            }else{
                results.addAll(list);
                adapter.notifyDataSetChanged();
            }
        }else{
            results.clear();
            results.addAll(list);
            adapter = getP().initAdapter(results);
            listView.setAdapter(adapter);
        }
        listView.onRefreshComplete();
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        page = 1;
        initAfter();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        page ++;
        initAfter();
    }


}
