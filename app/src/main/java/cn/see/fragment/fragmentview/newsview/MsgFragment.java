package cn.see.fragment.fragmentview.newsview;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.router.Router;
import cn.see.R;
import cn.see.adapter.CommonListViewAdapter;
import cn.see.base.BaseFragement;
import cn.see.fragment.fragmentview.mineview.AttentionAct;
import cn.see.main.WebAct;
import cn.see.model.FindActModel;
import cn.see.model.MsgContModel;
import cn.see.presenter.newsp.MsgPresenter;
import cn.see.util.UserUtils;
import cn.see.util.constant.IntentConstant;
import cn.see.util.glide.GlideDownLoadImage;
import cn.see.util.widet.putorefresh.PullToRefreshBase;
import cn.see.util.widet.putorefresh.PullToRefreshListView;

/**
 * @日期：2018/7/2
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 消息Frg
 */

public class MsgFragment extends BaseFragement<MsgPresenter> implements  PullToRefreshBase.OnRefreshListener2<ListView>{


    private String url = "http://www.xintusee.com/IOS/Activity/actshare/html?activity_id=";
    private CommonListViewAdapter<MsgPresenter.MsgBean> adapter;
    private List<MsgPresenter.MsgBean> beanList = new ArrayList<>();
    private String actId;
    private View topView;
    private View botView;
    private ImageView actImg;
    private TextView actName;
    private MsgContModel.ContResult result;

    @BindView(R.id.pull_msg_list)
    PullToRefreshListView listView;


    @Override
    public void initView() {
        topView = View.inflate(getActivity(), R.layout.layout_news_msg_top_view, null);
        botView = View.inflate(getActivity(), R.layout.layout_news_msg_b_view, null);
        actImg =(ImageView) botView.findViewById(R.id.act_img);
        actName =(TextView) botView.findViewById(R.id.act_name);
        listView.getRefreshableView().addHeaderView(topView);
        listView.getRefreshableView().addFooterView(botView);
        listView.getRefreshableView().setDividerHeight(0);
        adapter = getP().initAdapter(beanList, result);
        listView.setAdapter(adapter);
        getP().getTextAct(UserUtils.getUserID(getActivity()));
    }

    @Override
    public void initAfter() {
        beanList.add(new MsgPresenter.MsgBean(R.mipmap.zanle,"赞了",""));
        beanList.add(new MsgPresenter.MsgBean(R.mipmap.pinglun,"评论",""));
        beanList.add(new MsgPresenter.MsgBean(R.mipmap.guanzhu,"关注",""));
        beanList.add(new MsgPresenter.MsgBean(R.mipmap.tongzhi,"通知",""));
        adapter = getP().initAdapter(beanList,result);
        listView.setAdapter(adapter);
    }

    @Override
    public int bindLayout() {
        return R.layout.layout_news_msg_fragment;
    }

    @Override
    public MsgPresenter bindPresent() {
        return new MsgPresenter();
    }

    @Override
    public void setListener() {
        botView.findViewById(R.id.act_rela).setOnClickListener(this);
        listView.setOnRefreshListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(UserUtils.userIsLogin(getActivity())){
                        switch (position){
                            case 2:
                                openActivity(NewsLikeAct.class);
                                break;
                            case 3:
                                openActivity(NewsReviewAct.class);
                                break;
                            case 4:
                                openActivity(AttentionAct.class);
                                break;
                        }
                    }
            }
        });
    }
    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.act_rela:
                Router.newIntent(getActivity())
                        .to(WebAct.class)
                        .putString(IntentConstant.WEB_LOAD_URL,url+actId)
                        .launch();
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if(UserUtils.userIsLogin(getActivity())){
            getP().getMsgCont();
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        listView.onRefreshComplete();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

    }

    public void actResponse(FindActModel.ActResult actResult){
        actId = actResult.getLists().get(0).getActivity_id();
        actName.setText(actResult.getLists().get(0).getName());
        GlideDownLoadImage.getInstance().loadImage(actResult.getLists().get(0).getUrl(),actImg);
    }

    /**
     * 获取消息未读数
     * @param result
     */
    public void getMsg(MsgContModel.ContResult result){
        beanList.set(0,new MsgPresenter.MsgBean(R.mipmap.zanle,"赞了",result.getLikes_count()));
        beanList.set(1,new MsgPresenter.MsgBean(R.mipmap.pinglun,"评论",result.getReview_count()));
        adapter.notifyDataSetChanged();
    }
}
