package cn.see.fragment.fragmentview.homeview;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.router.Router;
import cn.see.R;
import cn.see.adapter.CommonListViewAdapter;
import cn.see.base.BaseFragement;
import cn.see.fragment.fragmentview.mineview.ArticleDetailsAct;
import cn.see.fragment.fragmentview.mineview.LoginAct;
import cn.see.fragment.fragmentview.mineview.OtherMainAct;
import cn.see.fragment.fragmentview.mineview.UserTopicAct;
import cn.see.model.FriendsNewsModel;
import cn.see.model.MineTextModel;
import cn.see.model.TopicModel;
import cn.see.model.TxtModel;
import cn.see.presenter.homep.AttentionPresenter;
import cn.see.util.ToastUtil;
import cn.see.util.UserUtils;
import cn.see.util.constant.IntentConstant;
import cn.see.util.glide.GlideDownLoadImage;
import cn.see.util.widet.CircleImageView;
import cn.see.util.widet.putorefresh.PullToRefreshBase;
import cn.see.util.widet.putorefresh.PullToRefreshListView;
import cn.see.util.widet.putorefresh.RefreshShowTime;

/**
 * @日期：2018/6/5
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 关注Frg
 */

public class AttentionFragment extends BaseFragement<AttentionPresenter> implements  PullToRefreshBase.OnRefreshListener2<ListView>{
    private ListView topListView;
    private CommonListViewAdapter<TxtModel.TxtResult.Result> adapter;
    private CommonListViewAdapter<FriendsNewsModel.NewsResult.NewsList> adapterTop;
    private List<TxtModel.TxtResult.Result> results = new ArrayList<>();
    private List<FriendsNewsModel.NewsResult.NewsList> lists = new ArrayList<>();
    private CircleImageView oneImg;
    private CircleImageView twoImg;
    private CircleImageView threeImg;
    private CircleImageView fourImg;
    private CircleImageView fiveImg;
    private int page = 1;
    private View topView;
    private Router router;
    private RecyclerView htRecyView;
    private RelativeLayout htRela;


    @BindView(R.id.pull_att_list)
    PullToRefreshListView listView;
    @BindView(R.id.no_login_rela)
    RelativeLayout noLoginRela;
    @BindView(R.id.list_rela)
    RelativeLayout listRela;

    @OnClick(R.id.login_but)
    void goLogin(){
        openActivity(LoginAct.class);
    }

    public AttentionFragment() {
    }

    @Override
    public void initView() {
        if(topView == null) {
            topView = View.inflate(getActivity(), R.layout.layout_home_att_top, null);
            findTopView();
            listView.getRefreshableView().addHeaderView(topView);
            listView.getRefreshableView().setDividerHeight(0);
            topListView.setDividerHeight(0);
            listView.setOnRefreshListener(this);
            adapter = getP().initAdapter(results);
            listView.setAdapter(adapter);

        }
    }

    private void findTopView(){
        topListView =(ListView) topView.findViewById(R.id.dt_listview);
        oneImg = (CircleImageView) topView.findViewById(R.id.one_img);
        twoImg = (CircleImageView) topView.findViewById(R.id.two_img);
        threeImg = (CircleImageView) topView.findViewById(R.id.three_img);
        fourImg = (CircleImageView) topView.findViewById(R.id.four_img);
        fiveImg = (CircleImageView) topView.findViewById(R.id.five_img);
        htRela = (RelativeLayout) topView.findViewById(R.id.ht_rela);
        htRecyView = (RecyclerView)topView.findViewById(R.id.top_ht_recy);
        htRecyView.setLayoutManager(new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.HORIZONTAL));
    }

    @Override
    public void initAfter() {
        router = Router.newIntent(getActivity());
    }

    @Override
    public int bindLayout() {
        return R.layout.layout_home_fragment_att;
    }

    @Override
    public AttentionPresenter bindPresent() {
        return new AttentionPresenter();
    }

    @Override
    public void setListener() {
        topView.findViewById(R.id.rela).setOnClickListener(this);
        topView.findViewById(R.id.recommend_rela).setOnClickListener(this);
        topView.findViewById(R.id.update_rela).setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!UserUtils.userIsLogin(getActivity())){
                    ToastUtil.showToast("未登录");
                    return;
                }
                Router.newIntent(getActivity())
                        .to(ArticleDetailsAct.class)
                        .putString(IntentConstant.ARTIC_TEXT_ID,results.get(position-2).getText_id())
                        .launch();
            }
        });
        topListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String type = lists.get(position).getType();
                switch (type){
                    case "like":
                        router.to(ArticleDetailsAct.class)
                                .putString(IntentConstant.ARTIC_TEXT_ID,lists.get(position).getText_id())
                                .launch();
                        break;
                    case "review":
                        router.to(ArticleDetailsAct.class)
                                .putString(IntentConstant.ARTIC_TEXT_ID,lists.get(position).getText_id())
                                .launch();
                        break;
                    case "attention":
                        router.to(OtherMainAct.class)
                                .putString(IntentConstant.OTHER_USER_ID,lists.get(position).getFid())
                                .launch();
                        break;
                    case "fans":
                        router.to(OtherMainAct.class)
                                .putString(IntentConstant.OTHER_USER_ID,lists.get(position).getFid())
                                .launch();
                        break;
                }
            }
        });

    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.recommend_rela:
                openActivity(FriendsRecoAct.class);
                break;
            case R.id.update_rela:
                openActivity(FrindeUpdateAct.class);
                break;
            case R.id.rela:
                openActivity(UserTopicAct.class);
                break;
        }
    }

    @Override
    public void onPullDownToRefresh(final PullToRefreshBase<ListView> refreshView) {
        RefreshShowTime.showTime(refreshView);
        page = 1;
        onStart();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
       page++;
        getP().getAttTextList(UserUtils.getUserID(getActivity()),page);
    }

    /**
     * 推荐好友回调
     * @param result
     */
    public void topFriendsResponse(List<TxtModel.TxtResult.Result> result){
        GlideDownLoadImage.getInstance().loadCircleImageToCust(result.get(0).getHead_img_url(),oneImg);
        GlideDownLoadImage.getInstance().loadCircleImageToCust(result.get(1).getHead_img_url(),twoImg);
        GlideDownLoadImage.getInstance().loadCircleImageToCust(result.get(2).getHead_img_url(),threeImg);
        GlideDownLoadImage.getInstance().loadCircleImageToCust(result.get(3).getHead_img_url(),fourImg);
        GlideDownLoadImage.getInstance().loadCircleImageToCust(result.get(4).getHead_img_url(),fiveImg);
    }

    /**
     * 好友动态回调
     */
    public void friendsNewsResponse(List<FriendsNewsModel.NewsResult.NewsList> list){
        lists.clear();
        lists.addAll(list);
        adapterTop = getP().initAdapterTopUpdate(lists);
        topListView.setAdapter(adapterTop);
    }

    /**
     * 我关注的话题
     * @param topicLists \
     */
    public void getTabTopic(List<MineTextModel.MineTextResult.ResultList> topicLists){
        Log.i(TAG,"topicLists:"+topicLists.size());
        if(topicLists.size()==0){
            htRela.setVisibility(View.GONE);
        }else{
            htRela.setVisibility(View.VISIBLE);
            htRecyView.setAdapter(getP().initAdapterTopic(topicLists));
        }
    }


    /**
     * 我的关注文章回调
     */
    public void attTextResponse(TxtModel.TxtResult result){
        List<TxtModel.TxtResult.Result> lists = result.getResult();
        if(page>1){
            if(result.getPage()>result.getTotalPage()){
                ToastUtil.showToast("暂无更多文章");
            }else{
                results.addAll(lists);
                adapter.notifyDataSetChanged();
            }
        }else{
            results.clear();
            results.addAll(lists);
            adapter = getP().initAdapter(results);
            listView.setAdapter(adapter);
        }

        listView.onRefreshComplete();

    }


    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG,"执行");
            if(UserUtils.userIsLogin(getActivity())){
                noLoginRela.setVisibility(View.GONE);
                listRela.setVisibility(View.VISIBLE);
                //获取推荐好友
                getP().getFriends(UserUtils.getUserID(getActivity()));
                //获取好友动态
                getP().getFriendsNews(UserUtils.getUserID(getActivity()),3,"0","0","0","0");
                //获取关注话题
                getP().getTabTopic();
                //获取关注文章
                getP().getAttTextList(UserUtils.getUserID(getActivity()),page);
            }else{
                noLoginRela.setVisibility(View.VISIBLE);
                listRela.setVisibility(View.GONE);
            }
    }

    /**
     * 点赞成功回调
     * 更新被点赞的条目
     * @param msg
     */
    public void likeResponse(String msg,int positon){
        ToastUtil.showToast(msg);
        TxtModel.TxtResult.Result result = results.get(positon);
        int i = Integer.parseInt(result.getLike_count());
        result.setLike_count((i+1)+"");
        result.setLike_status("1");
        adapter.notifyDataSetChanged();
    }



    /**
     * 取关成功
     * @param msg
     */
    public void removeAttResponse(String msg,int position){
        ToastUtil.showToast(msg);
        page = 1;
        getP().getAttTextList(UserUtils.getUserID(getActivity()),page);
    }
}
