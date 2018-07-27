package cn.see.fragment.fragmentview.findview;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import cn.droidlover.xdroidmvp.router.Router;
import cn.see.R;
import cn.see.adapter.CommonListViewAdapter;
import cn.see.adapter.RecryCommonAdapter;
import cn.see.base.BaseFragement;
import cn.see.event.MsgEvent;
import cn.see.fragment.fragmentview.mineview.ArticleDetailsAct;
import cn.see.model.FindTextcModel;
import cn.see.model.TabBannerModel;
import cn.see.model.TabModel;
import cn.see.model.TopicModel;
import cn.see.model.TxtModel;
import cn.see.presenter.findp.FindPresenter;
import cn.see.util.ToastUtil;
import cn.see.util.UserUtils;
import cn.see.util.VpHolder;
import cn.see.util.constant.BannerConstant;
import cn.see.util.constant.IntentConstant;
import cn.see.util.widet.AlertView.AlertView;
import cn.see.util.widet.AlertView.OnDismissListener;
import cn.see.util.widet.AlertView.OnItemClickListener;
import cn.see.util.widet.putorefresh.PullToRefreshBase;
import cn.see.util.widet.putorefresh.PullToRefreshListView;
import cn.see.util.widet.putorefresh.RefreshShowTime;

/**
 * @日期：2018/6/5
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 发现frg
 */

public class FindChildFragment extends BaseFragement<FindPresenter> implements  PullToRefreshBase.OnRefreshListener2<ListView>,OnItemClickListener, OnDismissListener {

    private List<TxtModel.TxtResult.Result> resultsList = new ArrayList<>();
    private List<TabModel.TabList> tabList = new ArrayList<>();
    private View topTableView;
    private RecyclerView recyTable;
    private RecyclerView htRecyView;
    private CommonListViewAdapter<TxtModel.TxtResult.Result> adapter;
    private VpHolder vpHolder;
    private int page = 1;
    //默认标签为精选
    private String tab_id = "0";


    @BindView(R.id.pull_find_list)
    PullToRefreshListView listView;

    public void initView() {
        if(topTableView == null){
            topTableView = View.inflate(getActivity(),R.layout.layout_find_find_top_recy,null);
            recyTable = (RecyclerView) topTableView.findViewById(R.id.top_find_recy);
            htRecyView = (RecyclerView)topTableView.findViewById(R.id.top_ht_recy);
            htRecyView.setLayoutManager(new StaggeredGridLayoutManager(1,
                    StaggeredGridLayoutManager.HORIZONTAL));
            recyTable.setLayoutManager(new StaggeredGridLayoutManager(1,
                    StaggeredGridLayoutManager.HORIZONTAL));
            adapter = getP().initAdapter(resultsList);
            listView.setAdapter(adapter);
            listView.getRefreshableView().addHeaderView(topTableView);
            listView.getRefreshableView().setDividerHeight(0);
            //注册订阅者
            EventBus.getDefault().register(this);
            //获取标签
            getP().getUserTab(UserUtils.getUserID(getActivity()),0);
        }
    }

    @Override
    public void initAfter() {
        //获取banner
        getP().getTabBanner(tab_id);
        //获取话题
        getP().getTabTopic(tab_id);
        //获取文章
        getP().getTabText(tab_id,page,UserUtils.getUserID(getActivity()));
    }

    @Override
    public int bindLayout() {
        return R.layout.layout_find_find_frg;
    }

    @Override
    public FindPresenter bindPresent() {
        return new FindPresenter();
    }

    @Override
    public void setListener() {
        topTableView.findViewById(R.id.update_rela).setOnClickListener(this);
        listView.setOnRefreshListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0&&position!=1){
                    Router.newIntent(getActivity())
                            .putString(IntentConstant.ARTIC_TEXT_ID,resultsList.get(position-2).getText_id())
                            .to(ArticleDetailsAct.class)
                            .launch();
                }
            }
        });
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.update_rela:
                 openActivity(AllTopicAct.class);
                 break;
        }
    }

    @Override
    public void onPullDownToRefresh(final PullToRefreshBase<ListView> refreshView) {
        RefreshShowTime.showTime(refreshView);
        page = 1;
        initAfter();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        page ++;
        initAfter();
    }

    /**
     * 获得标签
     * @param tabLists
     * @param type
     */
    public void getUserTabResponse(List<TabModel.TabList> tabLists, int type){
        Log.i(TAG,"执行："+type);
        //在最后添加一条数据
        tabLists.add(new TabModel.TabList());
        RecryCommonAdapter<TabModel.TabList> tabListRecryCommonAdapter = getP().initAdapterTopUpdate(tabLists);
        recyTable.setAdapter(tabListRecryCommonAdapter);
    }

    /**
     * 开始轮播Banner
     * @param bannerLists
     */
    public void getTabBanner(List<TabBannerModel.BannerResult.BannerList> bannerLists){
        //如该标签下暂无Banner 则隐藏
        if(bannerLists.size() == 0){
            topTableView.findViewById(R.id.cen_view).setVisibility(View.GONE);
            topTableView.findViewById(R.id.vp_rela).setVisibility(View.GONE);
        }else{
            topTableView.findViewById(R.id.cen_view).setVisibility(View.VISIBLE);
            topTableView.findViewById(R.id.vp_rela).setVisibility(View.VISIBLE);
            vpHolder = new VpHolder(getActivity(),topTableView, BannerConstant.FIND_TAB_BANNER_TYPE);
            vpHolder.setViewPager(bannerLists);
        }
    }

    /**
     * 获得话题
     * @param topicLists\
     */
    public void getTabTopic(List<TopicModel.Topicresult.TopicList> topicLists){
        htRecyView.setAdapter(getP().initAdapterTopic(topicLists));
    }


    /**
     * 获得文章
     * @param findTextResult
     * @param page
     */
    public void getTabText(FindTextcModel.FindTextResult findTextResult,int page){
        List<TxtModel.TxtResult.Result> list = findTextResult.getList();
        if(page>1){
            if(findTextResult.getPage()>findTextResult.getTotalPage()){
                ToastUtil.showToast("暂无更多文章");
            }else{
                resultsList.addAll(list);
                adapter.notifyDataSetChanged();
            }
        }else{
            resultsList.clear();
            resultsList.addAll(list);
            adapter = getP().initAdapter(resultsList);
            listView.setAdapter(adapter);
        }
        getP().progress.dismiss();
        listView.onRefreshComplete();
    }

    /**
     * 标签切换事件
     * @param tab_id
     */
    public void tabChange(String tab_id){
        this.tab_id = tab_id;
        //重新获取数据
        initAfter();
    }

    /**
     * 点赞成功回调
     * 更新被点赞的条目
     * @param msg
     */
    public void likeResponse(String msg,int positon){
        ToastUtil.showToast(msg);
        TxtModel.TxtResult.Result result = resultsList.get(positon);
        int i = Integer.parseInt(result.getLike_count());
        result.setLike_count((i+1)+"");
        result.setLike_status("1");
        adapter.notifyDataSetChanged();
    }

    /**
     * 关注成功
     * @param msg
     */
    public void attUserResponse(String msg,int position){
        ToastUtil.showToast(msg);
        resultsList.get(position).setAttention_status("1");
        adapter.notifyDataSetChanged();

    }

    /**
     * 取关成功
     * @param msg
     */
    public void removeAttResponse(String msg,int position){
        ToastUtil.showToast(msg);
        resultsList.get(position).setAttention_status("0");
        adapter.notifyDataSetChanged();
    }

    //定义处理接收的方法
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(MsgEvent userEvent){
        Log.i(TAG,"接收到了消息");
        //获取标签
        getP().getUserTab(UserUtils.getUserID(getActivity()),1);
        initAfter();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDismiss(Object o) {

    }

    public void set(){
        AlertView alertView = new AlertView(null, null, "取消", null, new String[]{"收藏","举报"},getActivity(), AlertView.Style.ActionSheet, this);
        alertView.setCancelable(true);
        alertView.show();
    }


    @Override
    public void onItemClick(Object o, int position) {
        if(position == 0){
            ToastUtil.showToast("收藏成功");
        }else if(position == -1){
            ToastUtil.showToast("举报成功");
        }
    }
}
