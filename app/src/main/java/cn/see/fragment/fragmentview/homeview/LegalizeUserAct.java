package cn.see.fragment.fragmentview.homeview;

import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import cn.see.R;
import cn.see.base.BaseActivity;
import cn.see.model.AddFriendModel;
import cn.see.presenter.homep.LegalizeUserPresenter;
import cn.see.util.ToastUtil;
import cn.see.util.widet.putorefresh.PullToRefreshBase;
import cn.see.util.widet.putorefresh.PullToRefreshListView;
import cn.see.util.widet.putorefresh.RefreshShowTime;

/**
 * 认证的用户(暂时只展示10个)
 */
public class LegalizeUserAct extends BaseActivity<LegalizeUserPresenter> implements  PullToRefreshBase.OnRefreshListener2<ListView>{

    private List<AddFriendModel.AddResult> results = new ArrayList<>();
    @BindView(R.id.title_tv_base)
    TextView title;
    @BindView(R.id.pull_qual_list)
    PullToRefreshListView listView;

    @OnClick(R.id.back_rela)
    void bacView(){
        onBack();
    }

    @Override
    public void initView() {
        title.setText("认证用户");
        RefreshShowTime.showTime(listView);
    }

    @Override
    public void initAfter() {
        getP().getLegUser();
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_legalize_user;
    }

    @Override
    public LegalizeUserPresenter bindPresent() {
        return new LegalizeUserPresenter();
    }

    @Override
    public void setListener() {
        listView.setOnRefreshListener(this);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        initAfter();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

    }

    public void attUserResponse(String msg,int position){
        ToastUtil.showToast(msg);
        initAfter();
    }

    public void dataResponse(List<AddFriendModel.AddResult> result){
        listView.setAdapter(getP().initAdapter(result));
        listView.onRefreshComplete();
    }

}
