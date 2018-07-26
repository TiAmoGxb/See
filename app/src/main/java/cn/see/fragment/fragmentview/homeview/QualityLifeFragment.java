package cn.see.fragment.fragmentview.homeview;

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
import cn.see.fragment.fragmentview.mineview.ArticleDetailsAct;
import cn.see.fragment.fragmentview.mineview.LoginAct;
import cn.see.model.TxtModel;
import cn.see.presenter.homep.QulaityLifePresenter;
import cn.see.util.ToastUtil;
import cn.see.util.UserUtils;
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
 * @说明： 品质生活
 */

public class QualityLifeFragment  extends BaseFragement<QulaityLifePresenter> implements  PullToRefreshBase.OnRefreshListener2<ListView>,OnItemClickListener, OnDismissListener {

    private CommonListViewAdapter<TxtModel.TxtResult.Result> adapter;
    private List<TxtModel.TxtResult.Result> resultsList = new ArrayList<>();
    private int page = 1;

    @BindView(R.id.pull_qual_list)
    PullToRefreshListView listView;

    @Override
    public void initView() {

    }

    @Override
    public void initAfter() {
        getP().getQuaitData(UserUtils.getUserID(getActivity()),page);
    }

    @Override
    public int bindLayout() {
        return R.layout.layout_home_fragment_life;
    }

    @Override
    public QulaityLifePresenter bindPresent() {
        return new QulaityLifePresenter();
    }

    @Override
    public void setListener() {
        listView.setOnRefreshListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(UserUtils.getLogin(getActivity())){
                    Router.newIntent(getActivity())
                            .to(ArticleDetailsAct.class)
                            .putString(IntentConstant.ARTIC_TEXT_ID,resultsList.get(position-1).getText_id())
                            .launch();
                }

            }
        });
    }

    @Override
    public void widgetClick(View v) {

    }

    public void resoponseData(TxtModel.TxtResult r, int page){
        List<TxtModel.TxtResult.Result> results = r.getResult();
        if(page>1){
            if(page>r.getTotalPage()){
                ToastUtil.showToast("暂无更多文章");
            }else{
                resultsList.addAll(results);
                adapter.notifyDataSetChanged();
            }
        }else{
            resultsList.clear();
            resultsList.addAll(results);
            adapter = getP().initAdapter(resultsList);
            listView.setAdapter(adapter);
        }
        getP().progress.dismiss();
        listView.onRefreshComplete();
    }


    @Override
    public void onPullDownToRefresh(final PullToRefreshBase<ListView> refreshView) {
        RefreshShowTime.showTime(refreshView);
        page = 1;
        getP().getQuaitData(UserUtils.getUserID(getActivity()),page);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        page++;
        getP().getQuaitData(UserUtils.getUserID(getActivity()),page);
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
        }else{
            ToastUtil.showToast("举报成功");
        }
    }
}
