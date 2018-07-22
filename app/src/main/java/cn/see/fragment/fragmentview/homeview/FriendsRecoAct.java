package cn.see.fragment.fragmentview.homeview;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.router.Router;
import cn.see.R;
import cn.see.adapter.CommonListViewAdapter;
import cn.see.adapter.FriendsReconAdapter;
import cn.see.base.BaseActivity;
import cn.see.fragment.fragmentview.mineview.OtherMainAct;
import cn.see.model.TxtModel;
import cn.see.presenter.homep.FriendRecoPresenter;
import cn.see.util.ToastUtil;
import cn.see.util.UserUtils;
import cn.see.util.constant.IntentConstant;
import cn.see.util.glide.GlideDownLoadImage;
import cn.see.util.permosson.CamerUtils;
import cn.see.util.widet.CustomProgress;
import cn.see.util.widet.PopupWindowHelper;
import cn.see.util.widet.putorefresh.PullToRefreshBase;
import cn.see.util.widet.putorefresh.PullToRefreshListView;
import cn.see.util.widet.putorefresh.RefreshShowTime;
import cn.see.views.FriendsAndNearbyV;

/**
 * @日期：2018/6/5
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 好友推荐
 */
public class FriendsRecoAct extends BaseActivity<FriendRecoPresenter>implements  PullToRefreshBase.OnRefreshListener2<ListView> ,FriendsAndNearbyV {

    private List<TxtModel.TxtResult.Result> results = new ArrayList<>();
    private CommonListViewAdapter<TxtModel.TxtResult.Result> adapter;
    private FriendsReconAdapter reconAdapter;
    private View topView;
    private RelativeLayout  serchReal;
    private RelativeLayout tjRela;
    private RelativeLayout closeRela;
    private int page = 1;
    private RelativeLayout fjRela;
    private ImageView imgOne;
    private ImageView imgTwo;
    private ImageView imgThree;
    private ImageView imgFour;
    private GlideDownLoadImage instance;
    private LinearLayout topLin;
    private CustomProgress progress;
    private Router to;
    private View popView;
    private PopupWindowHelper helper;

    @BindView(R.id.title_tv_base)
    TextView titles;
    @BindView(R.id.image_rela)
    RelativeLayout layout;
    @BindView(R.id.right_img_top)
    ImageView imageView;

    @BindView(R.id.pull_qual_list)
    PullToRefreshListView listView;

    @OnClick(R.id.image_rela)
    void addRela(){
        helper.showAsDropDown(layout,0,0);
    }


    @OnClick(R.id.back_rela)
    void bacAct(){
        onBack();
    }

    @Override
    public void initView() {
        titles.setText("好友推荐");
        layout.setVisibility(View.VISIBLE);
        imageView.setImageResource(R.drawable.r_add_fr);
        topView = View.inflate(this,R.layout.layout_friend_reco_top_view,null);
        serchReal = (RelativeLayout) topView.findViewById(R.id.serch_rela);
        tjRela = (RelativeLayout) topView.findViewById(R.id.tj_rela);
        closeRela = (RelativeLayout) topView.findViewById(R.id.close_rela);
        fjRela = (RelativeLayout) topView.findViewById(R.id.fj_rela);
        topLin = (LinearLayout) topView.findViewById(R.id.top_lin);
        imgOne = (ImageView)topView.findViewById(R.id.img_one);
        imgTwo = (ImageView)topView.findViewById(R.id.img_two);
        imgThree = (ImageView)topView.findViewById(R.id.img_three);
        imgFour = (ImageView)topView.findViewById(R.id.img_four);
        listView.getRefreshableView().addHeaderView(topView);
        instance = GlideDownLoadImage.getInstance();
        reconAdapter = new FriendsReconAdapter(this,results);
        adapter = reconAdapter.initAdapter();
        listView.setAdapter(adapter);
        to = Router.newIntent(this)
                .to(OtherMainAct.class);
        popView = LayoutInflater.from(this).inflate(R.layout.layout_home_po, null);
        helper = new PopupWindowHelper(popView);
    }

    @Override
    public void initAfter() {
        getP().getFriends(UserUtils.getUserID(this),page);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_friends_reco;
    }

    @Override
    public FriendRecoPresenter bindPresent() {
        return new FriendRecoPresenter();
    }

    @Override
    public void setListener() {
        closeRela.setOnClickListener(this);
        fjRela.setOnClickListener(this);
        topLin.setOnClickListener(this);
        imgOne.setOnClickListener(this);
        imgTwo.setOnClickListener(this);
        imgThree.setOnClickListener(this);
        imgFour.setOnClickListener(this);
        listView.setOnRefreshListener(this);
        popView.findViewById(R.id.add_lin).setOnClickListener(this);
        popView.findViewById(R.id.sys_lin).setOnClickListener(this);
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

    /**
     * 推荐好友
     * @param result
     * @param page
     */
    @Override
    public void responseData(List<TxtModel.TxtResult.Result> result, int page,String num) {
        instance.loadCircleImage(result.get(0).getHead_img_url(),imgOne);
        instance.loadCircleImage(result.get(1).getHead_img_url(),imgTwo);
        instance.loadCircleImage(result.get(2).getHead_img_url(),imgThree);
        instance.loadCircleImage(result.get(3).getHead_img_url(),imgFour);
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
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.close_rela:
                tjRela.setVisibility(View.GONE);
                topLin.setVisibility(View.GONE);
                break;
            case R.id.fj_rela:
                openActivity(NearByUserAct.class);
                break;
            case R.id.img_one:
                to.putString(IntentConstant.OTHER_USER_ID,results.get(0).getId())
                        .launch();
                break;
            case R.id.img_two:
                to.putString(IntentConstant.OTHER_USER_ID,results.get(1).getId())
                        .launch();
                break;
            case R.id.img_three:
                to.putString(IntentConstant.OTHER_USER_ID,results.get(2).getId())
                        .launch();
                break;
            case R.id.img_four:
                to.putString(IntentConstant.OTHER_USER_ID,results.get(3).getId())
                        .launch();
                break;
            case R.id.add_lin:
                if(UserUtils.userIsLogin(this))openActivity(AddFriendsAct.class);
                helper.dismiss();
                break;
            case R.id.sys_lin:
                if(UserUtils.userIsLogin(this)) {
                    CamerUtils.doOpenCamera(this, 1, "", IntentConstant.QRCODE_PHOTO_TYPE);
                }
                helper.dismiss();
                break;
        }
    }
}
