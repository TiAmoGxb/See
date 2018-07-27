package cn.see.fragment.fragmentview.homeview;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import cn.see.R;
import cn.see.adapter.CommonListViewAdapter;
import cn.see.adapter.FriendsReconAdapter;
import cn.see.base.BaseActivity;
import cn.see.fragment.fragmentview.findview.SearchAct;
import cn.see.model.TxtModel;
import cn.see.presenter.homep.FriendRecoPresenter;
import cn.see.util.ToastUtil;
import cn.see.util.UserUtils;
import cn.see.util.widet.CustomProgress;
import cn.see.util.widet.putorefresh.PullToRefreshBase;
import cn.see.util.widet.putorefresh.PullToRefreshListView;
import cn.see.util.widet.putorefresh.RefreshShowTime;
import cn.see.views.FriendsAndNearbyV;

public class NearByUserAct extends BaseActivity<FriendRecoPresenter> implements  PullToRefreshBase.OnRefreshListener2<ListView> ,FriendsAndNearbyV {


    private List<TxtModel.TxtResult.Result> results = new ArrayList<>();
    private CommonListViewAdapter<TxtModel.TxtResult.Result> adapter;
    private FriendsReconAdapter reconAdapter;
    private View topView;
    private TextView numTv;
    private CustomProgress progress;
    private int page = 1;

    @BindView(R.id.title_tv_base)
    TextView titles;
    @BindView(R.id.image_rela)
    RelativeLayout layout;
    @BindView(R.id.right_img_top)
    ImageView imageView;

    @BindView(R.id.pull_qual_list)
    PullToRefreshListView listView;


    @OnClick(R.id.back_rela)
    void bacAct(){
        onBack();
    }

    @Override
    public void initView() {
        titles.setText("附近");
/*        layout.setVisibility(View.VISIBLE);
        imageView.setImageResource(R.drawable.r_add_fr);*/
        topView = View.inflate(this,R.layout.layout_home_naer_top_view,null);
        numTv =(TextView) topView.findViewById(R.id.num_tv);
        listView.getRefreshableView().addHeaderView(topView);
        reconAdapter = new FriendsReconAdapter(this,results);
        adapter = reconAdapter.initAdapter();
        listView.setAdapter(adapter);
    }

    @Override
    public void initAfter() {
        getP().getNearbyFriends(UserUtils.getUserID(this),page);
    }


    /**
     * 推荐好友
     * @param result
     * @param page
     */
    @Override
    public void responseData(List<TxtModel.TxtResult.Result> result, int page,String num) {
        numTv.setText(num+"人");
        if(page>1){
            if(result.size()==0){
                ToastUtil.showToast("没有更多好友推荐啦");
            }else{
                results.addAll(result);
                adapter.notifyDataSetChanged();
            }
        }else{
            results.clear();
            results.addAll(result);
            reconAdapter = new FriendsReconAdapter(this,results);
            adapter = reconAdapter.initAdapter();
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
    public int bindLayout() {
        return R.layout.activity_near_by_user;
    }

    @Override
    public FriendRecoPresenter bindPresent() {
        return new FriendRecoPresenter();
    }

    @Override
    public void setListener() {
        listView.setOnRefreshListener(this);
        topView.findViewById(R.id.serch_rela).setOnClickListener(this);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        RefreshShowTime.showTime(refreshView);
        page = 1;
        initAfter();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        page ++;
        initAfter();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.serch_rela:
                if(UserUtils.getLogin(this)){
                    openActivity(SearchAct.class);
                }
                break;
        }
    }
}
