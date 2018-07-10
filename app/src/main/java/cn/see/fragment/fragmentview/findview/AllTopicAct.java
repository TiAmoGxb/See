package cn.see.fragment.fragmentview.findview;

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
import cn.see.util.ToastUtil;

/**
 * 全部话题
 */
public class AllTopicAct extends BaseActivity {

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
    @OnClick(R.id.rel_tv)
    void relTopic(){
        ToastUtil.showToast("发布话题");
    }


    @Override
    public void initView() {
        titles.setText("话题");
        tabs = new TextView[]{hot_tv,news_tv};
        views = new View[]{hot_v,news_v};
    }

    @Override
    public void initAfter() {
        fragmentList = new ArrayList<>();
        fragmentList.add(AllTopicFragment.newInstance(0));
        fragmentList.add(AllTopicFragment.newInstance(1));
        pager.setOffscreenPageLimit(fragmentList.size());
        pager.setAdapter(new CustPagerFragmentAdapter(getSupportFragmentManager(),fragmentList));
        pager.setCurrentItem(lastP);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_all_topic;
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
