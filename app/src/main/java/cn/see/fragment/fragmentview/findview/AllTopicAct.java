package cn.see.fragment.fragmentview.findview;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.router.Router;
import cn.see.R;
import cn.see.adapter.CustPagerFragmentAdapter;
import cn.see.base.BaseActivity;
import cn.see.fragment.release.ui.BeautifulPictureAct;
import cn.see.util.UserUtils;
import cn.see.util.constant.IntentConstant;
import cn.see.util.permosson.CamerUtils;
import cn.see.fragment.release.ui.MultiImageSelectorActivity;

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


    @OnClick(R.id.s_rela)
    void search(){
        if(UserUtils.getLogin(this)){
            openActivity(SearchAct.class);
        }
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
    @OnClick(R.id.rel_tv)
    void relTopic(){
        CamerUtils.doOpenCamera(this, 1, "", IntentConstant.RELEASE_PHOTO_TYPE);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            if(requestCode == 1){
                ArrayList<String> pathList = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                 Router.newIntent(this)
                        .to(BeautifulPictureAct.class)
                        .putString(IntentConstant.RELEASE_TYPE,"topic")
                         .putSerializable(IntentConstant.RELEASE_PATHS,pathList)
                         .launch();
            }
        }
    }
}
