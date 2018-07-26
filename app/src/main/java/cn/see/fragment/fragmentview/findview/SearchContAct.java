package cn.see.fragment.fragmentview.findview;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
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
import cn.see.util.ToastUtil;
import cn.see.util.constant.IntentConstant;
import cn.see.util.version.PreferenceUtils;

/**
 * 通用搜索详情
 *
 * 搜用户
 *
 * 搜we文章*/
public class SearchContAct extends BaseActivity {

    private List<Fragment> fragmentList;
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
    @BindView(R.id.et_school)
    EditText editText;

    @OnClick(R.id.canal)
    void bacView(){
        onBack();
    }

    @OnClick(R.id.msg_tv)
    void msg_tv(){
        pager.setCurrentItem(0);
    }

    @OnClick(R.id.send_tv)
    void send_tv(){
        pager.setCurrentItem(1);
    }

    @Override
    public void initView() {
        String cont = getIntent().getStringExtra(IntentConstant.SEARCH_CONT);
        editText.setText(cont);
        views = new View[]{msgv,snedv};
        fragmentList = new ArrayList<>();
        PreferenceUtils.setString(this,"user",cont);
        PreferenceUtils.setString(this,"text",cont);
        fragmentList.add(new SearchUserFragment());
        fragmentList.add(new SearchTextFragment());
        pager.setOffscreenPageLimit(fragmentList.size());
        pager.setAdapter(new CustPagerFragmentAdapter(getSupportFragmentManager(),fragmentList));
    }

    @Override
    public void initAfter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_search_cont;
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
                views[position].setVisibility(View.VISIBLE);
                views[lastP].setVisibility(View.GONE);
                lastP  = position;
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    if(editText.getText().toString().equals("")){
                        ToastUtil.showToast("请输入搜索内容");
                    }else{
                        fragmentList.clear();
                        PreferenceUtils.setString(SearchContAct.this,"user",editText.getText().toString());
                        PreferenceUtils.setString(SearchContAct.this,"text",editText.getText().toString());
                        fragmentList.add(new SearchUserFragment());
                        fragmentList.add(new SearchTextFragment());
                        pager.setOffscreenPageLimit(fragmentList.size());
                        pager.setAdapter(new CustPagerFragmentAdapter(getSupportFragmentManager(),fragmentList));
                    }
                    return true;
                }
                return false;
            }
        });
    }
}
