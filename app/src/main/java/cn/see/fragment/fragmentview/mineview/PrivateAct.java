package cn.see.fragment.fragmentview.mineview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.see.R;
import cn.see.base.BaseActivity;
import cn.see.presenter.minep.PrivatePresnter;
import cn.see.util.widet.SwitchView;

/***
 * 隐私
 */
public class PrivateAct extends BaseActivity<PrivatePresnter> {


    @BindView(R.id.toggle_att)
    SwitchView switchView;
    @BindView(R.id.title_tv_base)
    TextView title;

    @OnClick(R.id.back_rela)
    void bacView(){
        onBack();
    }

    @Override
    public void initView() {
        title.setText("隐私");
    }

    @Override
    public void initAfter() {
            getP().getUserPhoneSet();
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_private;
    }

    @Override
    public PrivatePresnter bindPresent() {
        return new PrivatePresnter();
    }

    @Override
    public void setListener() {
        switchView.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                switchView.toggleSwitch(true);
                getP().setUserPhoneSet("1");
            }

            @Override
            public void toggleToOff(SwitchView view) {
                switchView.toggleSwitch(false);
                getP().setUserPhoneSet("0");
            }
        });
    }


    public void getSet(String set){
        if(set.equals("0")){
            switchView.toggleSwitch(false);
        }else{
            switchView.toggleSwitch(true);
        }

    }
}
