package cn.see.fragment.fragmentview.newsview;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.see.R;
import cn.see.adapter.NesLikeReviewAdapter;
import cn.see.base.BaseActivity;
import cn.see.model.TxtModel;
import cn.see.model.UserInfoModel;
import cn.see.presenter.newsp.NewsLikeAndPresenter;
import cn.see.util.ToastUtil;
import cn.see.util.UserUtils;
import cn.see.util.widet.CustomProgress;
import cn.see.util.widet.putorefresh.PullToRefreshBase;
import cn.see.util.widet.putorefresh.PullToRefreshListView;
import cn.see.util.widet.putorefresh.RefreshShowTime;
import cn.see.views.LikeAndReviewV;

public class NewsLikeAct extends BaseActivity<NewsLikeAndPresenter> implements  PullToRefreshBase.OnRefreshListener2<ListView> ,LikeAndReviewV {


    private int page = 1;
    private CustomProgress progress;
    private List<TxtModel.TxtResult.Result> results = new ArrayList<>();
    private NesLikeReviewAdapter adapter;



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
        titles.setText("赞");
        listView.getRefreshableView().setDividerHeight(0);
        adapter =new  NesLikeReviewAdapter (results,this,0);
        listView.setAdapter(adapter);
    }

    @Override
    public void initAfter() {
        getP().getLike(UserUtils.getUserID(this),page);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_news_like;
    }

    @Override
    public NewsLikeAndPresenter bindPresent() {
        return new NewsLikeAndPresenter();
    }

    @Override
    public void setListener() {
        listView.setOnRefreshListener(this);
    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        RefreshShowTime.showTime(refreshView);
        page = 1;
        initAfter();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        page++;
        initAfter();
    }

    @Override
    public void responseData(List<TxtModel.TxtResult.Result> result, int total) {
        if(page>1){
            if(result.size()==0){
                ToastUtil.showToast("没有更多赞啦！");
            }else{
                results.addAll(result);
                adapter.notifyDataSetChanged();
            }
        }else{
            results.clear();
            results.addAll(result);
            adapter =new  NesLikeReviewAdapter (results,this,0);
            listView.setAdapter(adapter);
        }
        listView.onRefreshComplete();
    }
    @Override
    public void hidProgress() {
        progress.dismiss();
    }

    @Override
    public void showProgerss() {
        progress = CustomProgress.show(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
        String userID = UserUtils.getUserID(this);
        getP().delMsgCont(MsgFragment.MSG_LIKE_TYPE,userID);
    }
}
