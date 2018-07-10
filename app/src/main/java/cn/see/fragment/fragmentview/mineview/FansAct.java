package cn.see.fragment.fragmentview.mineview;

import android.os.Handler;
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
import cn.see.model.MineAttModel;
import cn.see.presenter.minep.FansPresenter;
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
 * @说明： 粉丝
 */
public class FansAct extends BaseActivity<FansPresenter>implements  PullToRefreshBase.OnRefreshListener2<ListView> {

    private CommonListViewAdapter<MineAttModel.AttResult> adapter;
    private List<MineAttModel.AttResult> results = new ArrayList<>();
    private int page = 1;

    @BindView(R.id.title_tv_base)
    TextView titles;
    @BindView(R.id.pull_fans_list)
    PullToRefreshListView listView;

    @OnClick(R.id.back_rela)
    void bacAct(){
        onBack();
    }

    @Override
    public void initView() {
        titles.setText("粉丝");
        listView.getRefreshableView().setDividerHeight(0);
        RefreshShowTime.showTime(listView);
    }

    @Override
    public void initAfter() {
        getP().getAttUser(page);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_fans;
    }

    @Override
    public FansPresenter bindPresent() {
        return new FansPresenter();
    }

    @Override
    public void setListener() {
        listView.setOnRefreshListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Router.newIntent(FansAct.this)
                        .putString(IntentConstant.OTHER_USER_ID,results.get(position-1).getUid())
                        .to(OtherMainAct.class)
                        .launch();
            }
        });
    }

    @Override
    public void onPullDownToRefresh(final PullToRefreshBase<ListView> refreshView) {
        page = 1;
        initAfter();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        page++;
        initAfter();
    }


    public void userFansResPonse(final List<MineAttModel.AttResult> result ){
        if(page>1){
            if(result.size()==0){
                ToastUtil.showToast("暂无更多粉丝");
            }else{
                results.addAll(result);
                adapter.notifyDataSetChanged();
            }
        }else{
            results.clear();
            results.addAll(result);
            adapter = getP().initAdapter(results);
            listView.setAdapter(adapter);
        }
        listView.onRefreshComplete();
    }

/*    @Override
    protected void onRestart() {
        super.onRestart();
        getP().getAttUser(UserUtils.getUserID(this));
    }*/

}
