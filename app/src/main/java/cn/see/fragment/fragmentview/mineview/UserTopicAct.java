package cn.see.fragment.fragmentview.mineview;


import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.see.R;
import cn.see.adapter.CustPagerFragmentAdapter;
import cn.see.base.BaseActivity;
import cn.see.fragment.fragmentview.findview.SearchAct;
import cn.see.util.ToastUtil;

//我的话题
public class UserTopicAct extends BaseActivity {


    private List<Fragment> fragmentList;
    private TextView[] tabs;
    private View[] views;
    private int lastP = 0;


    @BindView(R.id.all_topic_vp)
    ViewPager pager;
    @BindView(R.id.hot_tv)
    TextView hot_tv;
    @BindView(R.id.news_tv)
    TextView news_tv;
    @BindView(R.id.hot_v)
    View hot_v;
    @BindView(R.id.news_v)
    View news_v;
    @BindView(R.id.title_tv_base)
    TextView titles;
    @BindView(R.id.title_tv_op_bg)
    TextView opTv;

    @OnClick(R.id.title_tv_op_bg)
    void goEditTopic(){
        ToastUtil.showToast("发起话题");
    }

    @OnClick(R.id.hot_tv)
    void hot_tv(){
        pager.setCurrentItem(0);
    }

    @OnClick(R.id.news_tv)
    void news_tv(){
        pager.setCurrentItem(1);
    }

    @OnClick(R.id.back_rela)
    void bacAct(){
        onBack();
    }

    @OnClick(R.id.s_rela)
    void search(){
        openActivity(SearchAct.class);
    }

    @Override
    public void initView() {
        titles.setText("我的话题");
        opTv.setText("发起话题");
        tabs = new TextView[]{hot_tv,news_tv};
        views = new View[]{hot_v,news_v};
        opTv.setVisibility(View.VISIBLE);

    }

    @Override
    public void initAfter() {
        fragmentList = new ArrayList<>();
        fragmentList.add(UserTopicFragment.newInstance(0));
        fragmentList.add(UserTopicFragment.newInstance(1));
        pager.setOffscreenPageLimit(fragmentList.size());
        pager.setAdapter(new CustPagerFragmentAdapter(getSupportFragmentManager(),fragmentList));
        pager.setCurrentItem(lastP);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_user_topic;
    }

    @Override
    public XPresent bindPresent() {
        return null;
    }

    @Override
    public void setListener() {
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabs[position].setTextColor(getResources().getColor(R.color.text_3d));
                tabs[lastP].setTextColor(getResources().getColor(R.color.text_101010));
                views[position].setVisibility(View.VISIBLE);
                views[lastP].setVisibility(View.GONE);
                lastP  = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
