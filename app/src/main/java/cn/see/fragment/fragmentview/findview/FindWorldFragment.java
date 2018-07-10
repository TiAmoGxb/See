package cn.see.fragment.fragmentview.findview;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.see.R;
import cn.see.adapter.FindWorldDataAdapter;
import cn.see.base.BaseFragement;
import cn.see.model.FindTextcModel;
import cn.see.model.FindWorldTextModel;
import cn.see.model.TxtModel;
import cn.see.presenter.findp.WorldPresenter;
import cn.see.util.ToastUtil;
import cn.see.util.widet.CustomProgress;
import cn.see.util.widet.putorefresh.PullToRefreshBase;
import cn.see.util.widet.putorefresh.PullToRefreshListView;
import cn.see.util.widet.putorefresh.RefreshShowTime;

/**
 * @日期：2018/6/5
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 世界frg
 */

public class FindWorldFragment extends BaseFragement<WorldPresenter> implements  PullToRefreshBase.OnRefreshListener2<ListView>{

    private List<FindWorldTextModel> results = new ArrayList<>();
    private FindWorldDataAdapter adapter;
    private int page = 1;
    private View topTableView;
    private RecyclerView recyclerView;

    @BindView(R.id.pull_world_list)
    PullToRefreshListView listView;

    public void initView() {
        if(topTableView == null){
            topTableView = View.inflate(getActivity(),R.layout.layout_find_world_top_view,null);
            recyclerView =(RecyclerView) topTableView.findViewById(R.id.find_world_recy);
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,
                    StaggeredGridLayoutManager.HORIZONTAL));
            listView.getRefreshableView().addHeaderView(topTableView);
            listView.getRefreshableView().setDividerHeight(0);
            adapter = new FindWorldDataAdapter(getActivity(),results);
            listView.setAdapter(adapter);
        }
    }

    @Override
    public void initAfter() {
        getP().getUserHot(1,"8");
        getP().getWroldText(page);
    }

    @Override
    public int bindLayout() {
        return R.layout.layout_find_world_frg;
    }

    @Override
    public WorldPresenter bindPresent() {
        return new WorldPresenter();
    }

    @Override
    public void setListener() {
        listView.setOnRefreshListener(this);
    }

    @Override
    public void widgetClick(View v) {

    }

    @Override
    public void onPullDownToRefresh(final PullToRefreshBase<ListView> refreshView) {
        RefreshShowTime.showTime(refreshView);
        page = 1;
        initAfter();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        page++;
        initAfter();
    }

    public void getWroldTextResponse(List<FindWorldTextModel> result,int page){
        if(page>1){
            results.addAll(result);
            adapter.notifyDataSetChanged();
        }else{
            results.clear();
            results.addAll(result);
            adapter = new FindWorldDataAdapter(getActivity(),results);
            listView.setAdapter(adapter);
        }
        getP().progress.dismiss();
        listView.onRefreshComplete();
    }

    public void hotUserResponse(List<TxtModel.TxtResult.Result> results,int page){
        recyclerView.setAdapter(getP().initTopAdapter(results));
    }
}
