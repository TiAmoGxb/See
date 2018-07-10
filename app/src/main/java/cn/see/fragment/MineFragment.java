package cn.see.fragment;


import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhy.autolayout.AutoLinearLayout;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import cn.see.R;
import cn.see.adapter.CommonListViewAdapter;
import cn.see.base.BaseFragement;
import cn.see.fragment.fragmentview.mineview.AttentionAct;
import cn.see.fragment.fragmentview.mineview.FansAct;
import cn.see.fragment.fragmentview.mineview.LikeAct;
import cn.see.fragment.fragmentview.mineview.LoginAct;
import cn.see.fragment.fragmentview.mineview.SetUserDataAct;
import cn.see.model.MineTextModel;
import cn.see.model.TxtModel;
import cn.see.model.UserInfoModel;
import cn.see.presenter.minep.MinePresenter;
import cn.see.util.ToastUtil;
import cn.see.util.UserUtils;
import cn.see.util.glide.GlideDownLoadImage;
import cn.see.util.widet.CircleImageView;
import cn.see.util.widet.putorefresh.PullToRefreshBase;
import cn.see.util.widet.putorefresh.PullToRefreshListView;
import cn.see.util.widet.putorefresh.RefreshShowTime;

/**
 * @日期：2018/6/5
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 我的Frg
 */

public class MineFragment extends BaseFragement<MinePresenter> implements  PullToRefreshBase.OnRefreshListener2<ListView> {

    private List<MineTextModel.MineTextResult.ResultList> resultsList = new ArrayList<>();
    private CommonListViewAdapter<MineTextModel.MineTextResult.ResultList> adapter;
    private View topView;
    private ImageView setUser;
    private AutoLinearLayout singinView;
    private AutoLinearLayout collLin;
    private AutoLinearLayout flowsLin;
    private AutoLinearLayout likeLin;
    private CircleImageView userImg;
    private TextView userName;
    private TextView userSin;
    private TextView attCont;
    private TextView fansCont;
    private TextView likeCont;
    private TextView textNum;
    private ImageView bacImg;
    private String topSize = "0";
    private String textSize = "0";
    private boolean isRefresh = true;


    @BindView(R.id.no_login_rela)
    RelativeLayout noLoginRela;
    @BindView(R.id.pull_mine_list)
    PullToRefreshListView listView;
    @BindView(R.id.list_rela)
    RelativeLayout listRela;

    @OnClick(R.id.login_but)
    void goLogin(){
        openActivity(LoginAct.class);
    }


    @Override
    public void initView() {
        Log.i(TAG,"执行：initView:");
        if(topView == null){
            topView = View.inflate(getActivity(), R.layout.layout_mine_top_item, null);
            findView();
        }
    }

    /**
     * 顶部View
     */
    private void findView(){
        setUser =(ImageView) topView.findViewById(R.id.set_user_data);
        singinView = (AutoLinearLayout)topView.findViewById(R.id.signin_img);
        collLin =(AutoLinearLayout) topView.findViewById(R.id.collection_lin);
        flowsLin =(AutoLinearLayout) topView.findViewById(R.id.flows_lin);
        likeLin =(AutoLinearLayout) topView.findViewById(R.id.like_lin);
        userImg = (CircleImageView) topView.findViewById(R.id.user_img);
        userName =(TextView) topView.findViewById(R.id.user_name);
        bacImg =(ImageView) topView.findViewById(R.id.mine_bac);
        userSin =(TextView) topView.findViewById(R.id.user_signature);
        attCont =(TextView) topView.findViewById(R.id.att_cont);
        fansCont =(TextView) topView.findViewById(R.id.fans_cont);
        textNum =(TextView) topView.findViewById(R.id.tv_text_num);
        likeCont =(TextView) topView.findViewById(R.id.like_cont);
        RefreshShowTime.showTime(listView);
        listView.getRefreshableView().addHeaderView(topView);
        listView.setOnRefreshListener(this);
        adapter = getP().initAdapter(resultsList);
        listView.setAdapter(adapter);
    }

    @Override
    public void initAfter() {
        Log.i(TAG,"执行：initAfter");
        getP().getUserText(UserUtils.getUserID(getActivity()),topSize,textSize);
    }

    @Override
    public int bindLayout() {
        return R.layout.layout_mine_fragment;
    }

    @Override
    public MinePresenter bindPresent() {
        return new MinePresenter();
    }

    @Override
    public void setListener() {
        setUser.setOnClickListener(this);
        collLin.setOnClickListener(this);
        flowsLin.setOnClickListener(this);
        likeLin.setOnClickListener(this);
        singinView.setOnClickListener(this);

    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.collection_lin:
                openActivity(AttentionAct.class);
                break;
            case R.id.signin_img:
                ToastUtil.showToast("签到");
                break;
            case R.id.set_user_data:
                openActivity(SetUserDataAct.class);
                break;
            case R.id.flows_lin:
                openActivity(FansAct.class);
                break;
            case R.id.like_lin:
                openActivity(LikeAct.class);
                break;
        }
    }

    @Override
    public void onPullDownToRefresh(final PullToRefreshBase<ListView> refreshView) {
        isRefresh  = true;
        topSize = "0";
        textSize = "0";
        getP().getUserInfo(UserUtils.getUserID(getActivity()));
        getP().getUserText(UserUtils.getUserID(getActivity()),topSize,textSize);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        isRefresh  = false;
        getP().getUserText(UserUtils.getUserID(getActivity()),topSize,textSize);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG,"执行：onStart:"+UserUtils.userIsLogin(getActivity()));
            if(UserUtils.userIsLogin(getActivity())){
                noLoginRela.setVisibility(View.GONE);
                listRela.setVisibility(View.VISIBLE);
                getP().getUserInfo(UserUtils.getUserID(getActivity()));
            }else{
                noLoginRela.setVisibility(View.VISIBLE);
                listRela.setVisibility(View.GONE);
            }
    }

    public void userInfoResPonse(UserInfoModel.UserInfoResult result){
        userName.setText(result.getNickname());
        attCont.setText(result.getAttention_count());
        fansCont.setText(result.getFans_count());
        likeCont.setText(result.getLike_count());

        if(result.getSignature()==null||result.getSignature().trim().equals("")){
            userSin.setText("这家伙很懒，什么都没有留下...");
        }else{
            userSin.setText(result.getSignature());
        }
        GlideDownLoadImage.getInstance().loadCircleImageToCust(result.getHead_url(),userImg);
        GlideDownLoadImage.getInstance().loadImage(this,result.getBackground_url(),bacImg);
    }

    public void userTextResponse(MineTextModel.MineTextResult result ){
        topSize = result.getTopicSize();
        textSize = result.getTextSize();
        List<MineTextModel.MineTextResult.ResultList> lists = result.getLists();
        textNum.setText(result.getTotal()+"篇文章");
        if(!isRefresh){
            if(lists.size()==0){
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


    /**
     * 点赞成功回调
     * 更新被点赞的条目
     * @param msg
     */
    public void likeResponse(String msg,int positon){
        ToastUtil.showToast(msg);
        MineTextModel.MineTextResult.ResultList resultList = resultsList.get(positon);
        int i = Integer.parseInt(resultList.getLike_count());
        resultList.setLike_count((i+1)+"");
        resultList.setLike_status("1");
        adapter.notifyDataSetChanged();
    }
}
