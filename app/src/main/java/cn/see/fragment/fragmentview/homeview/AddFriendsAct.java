package cn.see.fragment.fragmentview.homeview;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.router.Router;
import cn.see.R;
import cn.see.base.BaseActivity;
import cn.see.fragment.fragmentview.mineview.OtherMainAct;
import cn.see.model.AddFriendModel;
import cn.see.presenter.homep.AddFriendsPresenter;
import cn.see.util.ToastUtil;
import cn.see.util.constant.IntentConstant;
import cn.see.util.permosson.CamerUtils;
import cn.see.util.widet.putorefresh.PullToRefreshBase;
import cn.see.util.widet.putorefresh.PullToRefreshListView;
import cn.see.util.widet.putorefresh.RefreshShowTime;

/**
 * 添加好友
 */
public class AddFriendsAct extends BaseActivity<AddFriendsPresenter> implements  PullToRefreshBase.OnRefreshListener2<ListView> {

    private View topView;
    private List<AddFriendModel.AddResult> results = new ArrayList<>();
    @BindView(R.id.title_tv_base)
    TextView title;
    @BindView(R.id.image_rela)
    RelativeLayout layout;
    @BindView(R.id.right_img_top)
    ImageView imageView;

    @OnClick(R.id.image_rela)
    void sys(){
        CamerUtils.doOpenCamera(this, 1, "", IntentConstant.QRCODE_PHOTO_TYPE);
    }

    @OnClick(R.id.back_rela)
    void bacView(){
        onBack();
    }

    @BindView(R.id.pull_qual_list)
    PullToRefreshListView listView;

    @Override
    public void initView() {
        title.setText("添加好友");
        layout.setVisibility(View.VISIBLE);
        imageView.setImageResource(R.drawable.sys);
        topView = View.inflate(this, R.layout.layout_home_add_r_top_recy, null);
        RefreshShowTime.showTime(listView);
        listView.getRefreshableView().addHeaderView(topView);
    }

    @Override
    public void initAfter() {
        getP().getMayknowUuser();
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_add_friends;
    }

    @Override
    public AddFriendsPresenter bindPresent() {
        return new AddFriendsPresenter();
    }

    @Override
    public void setListener() {
        topView.findViewById(R.id.book_rela).setOnClickListener(this);
        topView.findViewById(R.id.ser_rela).setOnClickListener(this);
        topView.findViewById(R.id.rz_rela).setOnClickListener(this);
        listView.setOnRefreshListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Router.newIntent(AddFriendsAct.this)
                        .putString(IntentConstant.OTHER_USER_ID,results.get(position-2).getUser_id())
                        .to(OtherMainAct.class)
                        .launch();
            }
        });
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        initAfter();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

    }

    public void dataResponse(List<AddFriendModel.AddResult> result, int position){
        results.clear();
        results.addAll(result);
        listView.setAdapter( getP().initAdapter(results,position));
        listView.onRefreshComplete();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.book_rela:
                openActivity(AddressBookAct.class);
                break;
            case R.id.rz_rela:
                openActivity(LegalizeUserAct.class);
                break;
            case R.id.ser_rela:
                ToastUtil.showToast("搜索");
                break;
        }
    }

    /**
     * 关注成功
     * @param msg
     * @param positon
     */
    public void attUserResponse(String msg,int positon){
         ToastUtil.showToast(msg);
        initAfter();
    }
}
