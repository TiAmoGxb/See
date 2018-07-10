package cn.see.fragment.fragmentview.findview;

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
import cn.see.main.WebAct;
import cn.see.model.FindActModel;
import cn.see.presenter.findp.ActPresenter;
import cn.see.presenter.homep.AttentionPresenter;
import cn.see.util.ToastUtil;
import cn.see.util.UserUtils;
import cn.see.util.constant.IntentConstant;
import cn.see.util.widet.putorefresh.PullToRefreshBase;
import cn.see.util.widet.putorefresh.PullToRefreshListView;
import cn.see.util.widet.putorefresh.RefreshShowTime;

/**
 * @日期：2018/6/5
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 活动frg
 */

public class FindActFragment extends BaseFragement<ActPresenter> implements  PullToRefreshBase.OnRefreshListener2<ListView>{

    private List<FindActModel.ActResult.ActList> lists = new ArrayList<>();
    private int page = 1;
    private CommonListViewAdapter<FindActModel.ActResult.ActList> adapter;
    private String url = "http://www.xintusee.com/IOS/Activity/actshare/html?activity_id=";

    @BindView(R.id.pull_act_list)
    PullToRefreshListView listView;


    public void initView() {
        adapter = getP().initAdapter(lists);
        listView.setAdapter(adapter);
    }

    @Override
    public void initAfter() {
        getP().getTextAct(UserUtils.getUserID(getActivity()),page);
    }

    @Override
    public int bindLayout() {
        return R.layout.layout_find_act_frg;
    }

    @Override
    public ActPresenter bindPresent() {
        return new ActPresenter();
    }

    @Override
    public void setListener() {
        listView.setOnRefreshListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(UserUtils.userIsLogin(getActivity())){
                    Router.newIntent(getActivity())
                            .to(WebAct.class)
                            .putString(IntentConstant.WEB_LOAD_URL,url+lists.get(position-1).getActivity_id()+"&uid="+UserUtils.getUserID(getActivity()))
                            .launch();
                }

            }
        });
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

    public void actResponse(FindActModel.ActResult actResult,int page){
        List<FindActModel.ActResult.ActList> actListList = actResult.getLists();
        if(page>1){
            if(page>actResult.getTotalPage()){
                ToastUtil.showToast("暂无更多活动");
            }else{
                this.lists.addAll(actListList);
                adapter.notifyDataSetChanged();
            }

        }else{
            this.lists.clear();
            this.lists.addAll(actListList);
            adapter = getP().initAdapter(this.lists);
            listView.setAdapter(adapter);
        }
        getP().progress.dismiss();
        listView.onRefreshComplete();
    }
}
