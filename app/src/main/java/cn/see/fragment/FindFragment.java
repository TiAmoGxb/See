package cn.see.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.see.R;
import cn.see.adapter.CustPagerFragmentAdapter;
import cn.see.base.BaseFragement;
import cn.see.fragment.fragmentview.findview.FindActFragment;
import cn.see.fragment.fragmentview.findview.FindChildFragment;
import cn.see.fragment.fragmentview.findview.FindWorldFragment;
import cn.see.fragment.fragmentview.homeview.AttentionFragment;
import cn.see.fragment.fragmentview.homeview.QualityLifeFragment;

/**
 * @日期：2018/6/5
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 发现Frg
 */

public class FindFragment extends BaseFragement {

    private TextView[] titles;
    private View[] views;
    private ArrayList<Fragment> fragmentList;
    private int lastP = 1;


    @BindView(R.id.find_vp)
    ViewPager findVp;
    @BindView(R.id.find_tv)
    TextView findTv;
    @BindView(R.id.world_tv)
    TextView worldTv;
    @BindView(R.id.act_tv)
    TextView actTv;
    @BindView(R.id.find_v)
    View findV;
    @BindView(R.id.world_v)
    View worldV;
    @BindView(R.id.act_v)
    View actV;

    @OnClick(R.id.find_tv)
    void findTo(){
        findVp.setCurrentItem(0);
    }
    @OnClick(R.id.world_tv)
    void worldTo(){
        findVp.setCurrentItem(1);
    }
    @OnClick(R.id.act_tv)
    void actTo(){
        findVp.setCurrentItem(2);
    }

    @Override
    public void initView() {
        titles  = new TextView[]{findTv,worldTv,actTv};
        views = new View[]{findV,worldV,actV};
    }

    @Override
    public void initAfter() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new FindChildFragment());
        fragmentList.add(new FindWorldFragment());
        fragmentList.add(new FindActFragment());
        findVp.setOffscreenPageLimit(fragmentList.size());
        findVp.setAdapter(new CustPagerFragmentAdapter(getChildFragmentManager(),fragmentList));
        findVp.setCurrentItem(1);
    }

    @Override
    public int bindLayout() {
        return R.layout.layout_find_fragment;
    }

    @Override
    public XPresent bindPresent() {
        return null;
    }

    @Override
    public void setListener() {
        findVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                titles[position].setTextColor(getResources().getColor(R.color.text_3d));
                titles[lastP].setTextColor(getResources().getColor(R.color.text_101010));
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
    public void widgetClick(View v) {

    }
}
