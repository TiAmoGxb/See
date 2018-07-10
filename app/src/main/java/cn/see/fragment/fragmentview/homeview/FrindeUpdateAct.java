package cn.see.fragment.fragmentview.homeview;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidbase.router.Router;
import cn.see.R;
import cn.see.adapter.CommonListViewAdapter;
import cn.see.base.BaseActivity;
import cn.see.fragment.fragmentview.mineview.ArticleDetailsAct;
import cn.see.fragment.fragmentview.mineview.OtherMainAct;
import cn.see.model.FriendsNewsModel;
import cn.see.presenter.homep.FriendsNewsPresenter;
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
 * @说明： 好友动态
 */
public class FrindeUpdateAct extends BaseActivity<FriendsNewsPresenter> implements  PullToRefreshBase.OnRefreshListener2<ListView>{
    private CommonListViewAdapter<FriendsNewsModel.NewsResult.NewsList> adapter;
    private ArrayList<FriendsNewsModel.NewsResult.NewsList> newsLists = new ArrayList<>();
    private String attentionSize = "0";
    private String fansSize = "0";
    private String likeSize = "0";
    private String reviewSize = "0";
    private boolean isRefresh = true;

    @BindView(R.id.title_tv_base)
    TextView titles;
    @BindView(R.id.pull_update_list)
    PullToRefreshListView listView;
    private Router router;


    @OnClick(R.id.back_rela)
    void bacAct(){
        onBack();
    }

    @Override
    public void initView() {
        titles.setText("好友动态");
        listView.getRefreshableView().setDividerHeight(0);
        router = Router.newIntent(this);
    }

    @Override
    public void initAfter() {
        getP().getFriendsNews(UserUtils.getUserID(this),20,likeSize,reviewSize,attentionSize,fansSize);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_frinde_update;
    }

    @Override
    public FriendsNewsPresenter bindPresent() {
        return new FriendsNewsPresenter();
    }

    @Override
    public void setListener() {
        listView.setOnRefreshListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String type = newsLists.get(position).getType();
                switch (type){
                    case "like":
                        router.to(ArticleDetailsAct.class)
                                .putString(IntentConstant.ARTIC_TEXT_ID,newsLists.get(position-1).getText_id())
                                .launch();
                        break;
                    case "review":
                        router.to(ArticleDetailsAct.class)
                                .putString(IntentConstant.ARTIC_TEXT_ID,newsLists.get(position-1).getText_id())
                                .launch();
                        break;
                    case "attention":
                        router.to(OtherMainAct.class)
                                .putString(IntentConstant.OTHER_USER_ID,newsLists.get(position-1).getFid())
                                .launch();
                        break;
                    case "fans":
                        router.to(OtherMainAct.class)
                                .putString(IntentConstant.OTHER_USER_ID,newsLists.get(position-1).getFid())
                                .launch();
                        break;
                }
            }
        });
    }

    @Override
    public void onPullDownToRefresh(final PullToRefreshBase<ListView> refreshView) {
        RefreshShowTime.showTime(refreshView);
        getP().getFriendsNews(UserUtils.getUserID(this),20,"0","0","0","0");
        isRefresh = true;
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        getP().getFriendsNews(UserUtils.getUserID(this),20,likeSize,reviewSize,attentionSize,fansSize);
        isRefresh = false;
    }

    /**
     * 好友动态回调
     */
    public void friendsNewsResponse(FriendsNewsModel.NewsResult result){
        List<FriendsNewsModel.NewsResult.NewsList> results = result.getList();
        likeSize = result.getLikeSize();
        reviewSize = result.getReviewSize();
        attentionSize = result.getAttentionSize();
        fansSize = result.getFansSize();
        if(!isRefresh){
            if(results.size()<=0){
                ToastUtil.showToast("暂无更多动态");
            }else{
                newsLists.addAll(results);
                adapter.notifyDataSetChanged();
            }
        }else{
            newsLists.clear();
            newsLists.addAll(results);
            adapter = getP().initAdapter(newsLists);
            listView.setAdapter(adapter);
        }
        getP().progress.dismiss();
        listView.onRefreshComplete();
    }
}
