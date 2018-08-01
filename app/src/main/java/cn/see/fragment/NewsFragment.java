package cn.see.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.see.R;
import cn.see.adapter.CustPagerFragmentAdapter;
import cn.see.base.BaseFragement;
import cn.see.chat.activity.fragment.ConversationListFragment;
import cn.see.fragment.fragmentview.homeview.AddFriendsAct;
import cn.see.fragment.fragmentview.homeview.AttentionFragment;
import cn.see.fragment.fragmentview.homeview.QualityLifeFragment;
import cn.see.fragment.fragmentview.mineview.AttentionAct;
import cn.see.fragment.fragmentview.newsview.MsgFragment;
import cn.see.fragment.fragmentview.newsview.PrivateLetterFragment;
import cn.see.util.UserUtils;
import cn.see.util.constant.IntentConstant;
import cn.see.util.permosson.CamerUtils;
import cn.see.util.widet.PopupWindowHelper;

/**
 * @日期：2018/6/5
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 消息通知Frg
 */

public class NewsFragment extends BaseFragement {
    private View popView;
    private PopupWindowHelper helper;
    private List<Fragment> fragmentList;
    private TextView[] tabs;
    private View[] views;
    private int lastP = 0;

    @BindView(R.id.nes_vp)
    ViewPager pager;
    @BindView(R.id.msg_tv)
    TextView msgTv;
    @BindView(R.id.send_tv)
    TextView sendTv;
    @BindView(R.id.msg_v)
    View msgv;
    @BindView(R.id.send_v)
    View snedv;
    @BindView(R.id.add_rela)
    RelativeLayout add_rela;


    @OnClick(R.id.msg_tv)
    void msg_tv(){
        pager.setCurrentItem(0);
    }

    @OnClick(R.id.send_tv)
    void send_tv(){
        pager.setCurrentItem(1);
    }

    @OnClick(R.id.add_rela)
    void addRela(){
        helper.showAsDropDown(add_rela,0,0);
    }

    @Override
    public void initView() {
        tabs = new TextView[]{msgTv,sendTv};
        views = new View[]{msgv,snedv};

    }

    @Override
    public void initAfter() {
        popView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_home_po, null);
        popView.findViewById(R.id.my_lin).setVisibility(View.VISIBLE);
        popView.findViewById(R.id.my_v).setVisibility(View.VISIBLE);
        ImageView imageView =(ImageView) popView.findViewById(R.id.my_lin_img);
        imageView.setImageResource(R.drawable.wodehaoyou);
        helper = new PopupWindowHelper(popView);
        fragmentList = new ArrayList<>();
        fragmentList.add(new MsgFragment());
        fragmentList.add(new ConversationListFragment());
        pager.setOffscreenPageLimit(fragmentList.size());
        pager.setAdapter(new CustPagerFragmentAdapter(getChildFragmentManager(),fragmentList));
        pager.setCurrentItem(lastP);
    }

    @Override
    public int bindLayout() {
        return R.layout.layout_news_fragment;
    }

    @Override
    public XPresent bindPresent() {
        return null;
    }

    @Override
    public void setListener() {
        popView.findViewById(R.id.my_lin).setOnClickListener(this);
        popView.findViewById(R.id.add_lin).setOnClickListener(this);
        popView.findViewById(R.id.sys_lin).setOnClickListener(this);
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
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.my_lin:
                if(UserUtils.getLogin(getActivity()))openActivity(AttentionAct.class);
                helper.dismiss();
                break;
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
}
