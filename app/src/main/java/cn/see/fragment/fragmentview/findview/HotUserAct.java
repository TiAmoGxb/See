package cn.see.fragment.fragmentview.findview;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.see.R;
import cn.see.adapter.CommonListViewAdapter;
import cn.see.base.BaseActivity;
import cn.see.model.TxtModel;
import cn.see.presenter.findp.HotUserPresenter;
import cn.see.util.ToastUtil;
import cn.see.util.widet.putorefresh.PullToRefreshBase;
import cn.see.util.widet.putorefresh.PullToRefreshGridView;
import cn.see.util.widet.putorefresh.RefreshShowTime;

/**
 * 热门用户
 */
public class HotUserAct extends BaseActivity<HotUserPresenter>  implements  PullToRefreshBase.OnRefreshListener2<GridView>{

    private CommonListViewAdapter<TxtModel.TxtResult.Result> adapter;
    private List<TxtModel.TxtResult.Result> resultLis = new ArrayList<>();
    private int page = 1;


    @BindView(R.id.verygridview)
    PullToRefreshGridView gridView;
    @BindView(R.id.title_tv_base)
    TextView title;


    @OnClick(R.id.back_rela)
    void bacView(){
        onBack();
    }

    @Override
    public void initView() {
        title.setText("热门用户");
        RefreshShowTime.showTime(gridView);
        adapter = getP().initAdapter(resultLis);
        gridView.setAdapter(adapter);
    }

    @Override
    public void initAfter() {
        getP().getUserHot(page,"28");

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_hot_user;
    }

    @Override
    public HotUserPresenter bindPresent() {
        return new HotUserPresenter();
    }

    @Override
    public void setListener() {
        gridView.setOnRefreshListener(this);
    }

    /**
     * 获取热门用户
     * @param result
     * @param page
     */
    public void hotUserResponse(List<TxtModel.TxtResult.Result> result, int page){
        if(page>1){
            if(result.size() == 0){
                ToastUtil.showToast("暂无更多热门用户");
            }else{
                resultLis.addAll(result);
                adapter.notifyDataSetChanged();
            }
        }else{
            resultLis.clear();
            resultLis.addAll(result);
            gridView.setAdapter(getP().initAdapter(resultLis));
        }
        gridView.onRefreshComplete();
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
        page = 1;
        getP().getUserHot(page,"28");
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
        page ++;
        getP().getUserHot(page,"28");
    }
}
