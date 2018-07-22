package cn.see.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.internal.bind.util.ISO8601Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.see.R;
import cn.see.adapter.CustPagerFragmentAdapter;
import cn.see.base.BaseFragement;
import cn.see.fragment.fragmentview.homeview.AddFriendsAct;
import cn.see.fragment.fragmentview.homeview.AttentionFragment;
import cn.see.fragment.fragmentview.homeview.QualityLifeFragment;
import cn.see.util.ToastUtil;
import cn.see.util.UserUtils;
import cn.see.util.constant.IntentConstant;
import cn.see.util.permosson.CamerUtils;
import cn.see.util.widet.PopupWindowHelper;
import cn.see.util.zxing.app.CaptureActivity;

/**
 * @日期：2018/6/5
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 首页Frg
 */

public class HomeFragment extends BaseFragement {
    private View popView;
    private List<Fragment> fragmentList;
    private TextView[] titles;
    private View[] views;
    private int lastP = 0;
    private PopupWindowHelper helper;


    @BindView(R.id.home_vp)
    ViewPager homeVp;
    @BindView(R.id.life_tv)
    TextView lifeTv;
    @BindView(R.id.att_tv)
    TextView attTv;
    @BindView(R.id.life_v)
    View lifeV;
    @BindView(R.id.att_v)
    View attV;
    @BindView(R.id.gray_layout)
    RelativeLayout bacView;
    @BindView(R.id.add_rela)
    RelativeLayout add_rela;
    @OnClick(R.id.add_rela)
    void addRela(){
        helper.showAsDropDown(add_rela,0,0);
    }

    @OnClick(R.id.life_tv)
    void lifeTo(){
        homeVp.setCurrentItem(0);
    }
    @OnClick(R.id.att_tv)
    void attTo(){
        homeVp.setCurrentItem(1);
    }
    @Override
    public void initView() {
        titles = new TextView[]{lifeTv,attTv};
        views = new View[] {lifeV,attV};
    }

    @Override
    public void initAfter() {
        popView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_home_po, null);
        helper = new PopupWindowHelper(popView);
        fragmentList = new ArrayList<>();
        fragmentList.add(new QualityLifeFragment());
        fragmentList.add(new AttentionFragment());
        homeVp.setOffscreenPageLimit(fragmentList.size());
        homeVp.setAdapter(new CustPagerFragmentAdapter(getChildFragmentManager(),fragmentList));
        homeVp.setCurrentItem(lastP);
    }

    @Override
    public int bindLayout() {
        return R.layout.layout_home_fragment;
    }

    @Override
    public XPresent bindPresent() {
        return null;
    }

    @Override
    public void setListener() {

        popView.findViewById(R.id.add_lin).setOnClickListener(this);
        popView.findViewById(R.id.sys_lin).setOnClickListener(this);
        homeVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        switch (v.getId()){
            case R.id.add_lin:
                if(UserUtils.getLogin(getActivity()))openActivity(AddFriendsAct.class);
                helper.dismiss();
                break;
            case R.id.sys_lin:
                if(UserUtils.getLogin(getActivity())) {
                    CamerUtils.doOpenCamera(getActivity(), 1, "", IntentConstant.QRCODE_PHOTO_TYPE);
                }
                helper.dismiss();
                break;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

}
