package cn.see.fragment.fragmentview.mineview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.router.Router;
import cn.see.R;
import cn.see.adapter.CommonListViewAdapter;
import cn.see.base.BaseActivity;
import cn.see.main.WebAct;
import cn.see.model.FindActModel;
import cn.see.presenter.minep.MineActPresenter;
import cn.see.util.ToastUtil;
import cn.see.util.UserUtils;
import cn.see.util.constant.IntentConstant;
import cn.see.util.widet.putorefresh.PullToRefreshBase;
import cn.see.util.widet.putorefresh.PullToRefreshListView;
import cn.see.util.widet.putorefresh.RefreshShowTime;

/**
 * 我的活动
 */
public class MineAct extends BaseActivity<MineActPresenter>implements  PullToRefreshBase.OnRefreshListener2<ListView> {

    private List<FindActModel.ActResult.ActList> lists = new ArrayList<>();
    private int page = 1;
    private CommonListViewAdapter<FindActModel.ActResult.ActList> adapter;
    private String url = "http://www.xintusee.com/IOS/Activity/actshare/html?activity_id=";


    @BindView(R.id.pull_act_list)
    PullToRefreshListView listView;
    @BindView(R.id.title_tv_base)
    TextView title;

    @OnClick(R.id.back_rela)
    void bacView(){
        onBack();
    }


    public void initView() {
        title.setText("我的活动");
        adapter = getP().initAdapter(lists);
        listView.setAdapter(adapter);
    }


    @Override
    public void initAfter() {
        getP().getTextAct(UserUtils.getUserID(this),page);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_mine;
    }

    @Override
    public MineActPresenter bindPresent() {
        return new MineActPresenter();
    }

    @Override
    public void setListener() {
        listView.setOnRefreshListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(UserUtils.getLogin(MineAct.this)){
                    Router.newIntent(MineAct.this)
                            .to(WebAct.class)
                            .putString(IntentConstant.WEB_LOAD_URL,url+lists.get(position-1).getActivity_id()+"&uid="+UserUtils.getUserID(MineAct.this))
                            .launch();
                }

            }
        });
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
            if(actListList.size() == 0){
                ToastUtil.showToast("暂无活动");
            }
            this.lists.clear();
            this.lists.addAll(actListList);
            adapter = getP().initAdapter(this.lists);
            listView.setAdapter(adapter);
        }
        getP().progress.dismiss();
        listView.onRefreshComplete();
    }
}
