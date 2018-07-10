package cn.see.fragment.fragmentview.mineview;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.router.Router;
import cn.see.R;
import cn.see.adapter.CommonListViewAdapter;
import cn.see.base.BaseActivity;
import cn.see.model.MineTextModel;
import cn.see.presenter.minep.LikePresenter;
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
 * @说明： 喜欢
 */
public class LikeAct extends BaseActivity<LikePresenter>implements  PullToRefreshBase.OnRefreshListener2<ListView> {

    private List<MineTextModel.MineTextResult.ResultList> resultsList = new ArrayList<>();
    private CommonListViewAdapter<MineTextModel.MineTextResult.ResultList> adapter;
    private int page = 1;

    @BindView(R.id.title_tv_base)
    TextView titles;
    @BindView(R.id.pull_like_list)
    PullToRefreshListView listView;

    @OnClick(R.id.back_rela)
    void bacAct(){
        onBack();
    }

    @Override
    public void initView() {
        titles.setText("我的喜欢");
        listView.getRefreshableView().setDividerHeight(0);
    }

    @Override
    public void initAfter() {
      getP().getUserLike(UserUtils.getUserID(this),page);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_like;
    }

    @Override
    public LikePresenter bindPresent() {
        return new LikePresenter();
    }

    @Override
    public void setListener() {
        listView.setOnRefreshListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Router.newIntent(LikeAct.this)
                            .to(ArticleDetailsAct.class)
                            .putString(IntentConstant.ARTIC_TEXT_ID,resultsList.get(position-1).getText_id())
                            .launch();
            }
        });
    }
    @Override
    public void onPullDownToRefresh(final PullToRefreshBase<ListView> refreshView) {
        RefreshShowTime.showTime(refreshView);
        page =1;
        getP().getUserLike(UserUtils.getUserID(this),page);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        page ++;
        getP().getUserLike(UserUtils.getUserID(this),page);
    }


    /**
     * 获取喜欢的文章回调
     * @param result
     * @param page
     */
    public void userLikeResponse(MineTextModel.MineTextResult result , int page){
        List<MineTextModel.MineTextResult.ResultList> lists = result.getLists();
        if(page>1){
            if(result.getPage()>result.getTotalPage()){
                ToastUtil.showToast("暂无更多文章");
            }else{
                resultsList.addAll(lists);
                adapter.notifyDataSetChanged();
            }
        }else{
            resultsList.clear();
            resultsList.addAll(lists);
            adapter = getP().initAdapter(resultsList);
            listView.setAdapter(adapter);
        }
        getP().progress.dismiss();
        listView.onRefreshComplete();

    }
}

