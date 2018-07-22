package cn.see.fragment.fragmentview.mineview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.zcw.togglebutton.ToggleButton;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.router.Router;
import cn.see.R;
import cn.see.base.BaseActivity;
import cn.see.model.NoticeModel;
import cn.see.presenter.minep.NoctionActPresenter;
import cn.see.util.constant.IntentConstant;
import cn.see.util.widet.SwitchView;

/**
 * 通知
 */
public class NoctionAct extends BaseActivity<NoctionActPresenter> {

    public static final String TAG = "NoctionAct" ;

    public final static int PL_TYPE = 0;
    public final static int DZ_TYPE = 1;
    public final static int AT_TYPE = 2;
    private String[] arrs = new String[]{"所有人", "我的好友", "全部拒收"};
    private Router to;
    private boolean isTogg = false;
    private String review;
    private String like;
    private String call;


    @BindView(R.id.title_tv_base)
    TextView title;
    @BindView(R.id.pl_text)
    TextView pl_text;
    @BindView(R.id.dz_text)
    TextView dz_text;
    @BindView(R.id.at_text)
    TextView at_text;
    @BindView(R.id.toggle_att)
    SwitchView toggle_att;
    @BindView(R.id.toggle_act)
    SwitchView toggle_act;
    @BindView(R.id.toggle_system)
    SwitchView toggle_system;

    /**
     * 评论消息
     */
    @OnClick(R.id.set_pl_rela)
    void setPl() {
        to.putInt(IntentConstant.NOCTION_TYPE, PL_TYPE)
                .putString(IntentConstant.NOCTION_VALUES,review)
                .launch();
    }

    /**
     * 点赞消息
     */
    @OnClick(R.id.set_dz_rela)
    void setDz() {
        to.putInt(IntentConstant.NOCTION_TYPE, DZ_TYPE)
                .putString(IntentConstant.NOCTION_VALUES,like)
                .launch();
    }

    /**
     * @消息
     */
    @OnClick(R.id.set_xx_rela)
    void setXx() {
        to.putInt(IntentConstant.NOCTION_TYPE, AT_TYPE)
                .putString(IntentConstant.NOCTION_VALUES,call)
                .launch();
    }

    @OnClick(R.id.back_rela)
    void bacView() {
        onBack();
    }


    @Override
    public void initView() {
        title.setText("通知");
    }

    @Override
    public void initAfter() {
        to = Router.newIntent(this)
                .to(NoctionBasicAct.class);
        getP().getUserNotice();
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_noction;
    }

    @Override
    public NoctionActPresenter bindPresent() {
        return new NoctionActPresenter();
    }

    @Override
    public void setListener() {
        toggle_att.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {

            @Override
            public void toggleToOn(SwitchView view) {
                toggle_att.toggleSwitch(true);
                getP().setUserNoticeSubscribe("1");
            }
            @Override
            public void toggleToOff(SwitchView view) {
                toggle_att.toggleSwitch(false);
                getP().setUserNoticeSubscribe("0");
            }
        });
        toggle_act.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {

            @Override
            public void toggleToOn(SwitchView view) {
                toggle_act.toggleSwitch(true);
                getP().setUserNoticeAct("1");
            }
            @Override
            public void toggleToOff(SwitchView view) {
                toggle_act.toggleSwitch(false);
                getP().setUserNoticeAct("0");
            }
        });
        toggle_system.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {

            @Override
            public void toggleToOn(SwitchView view) {
                toggle_system.toggleSwitch(true);
                getP().setUserNoticeSystem("1");
            }
            @Override
            public void toggleToOff(SwitchView view) {
                toggle_system.toggleSwitch(false);
                getP().setUserNoticeSystem("0");
            }
        });

    }

    /**
     * 获取通知
     * @param result
     */
    public void getNotice(NoticeModel.NoticeResult result) {
        //评论
        review = result.getReview();
        switch (review) {
            case "0":
                pl_text.setText(arrs[0]);
                break;
            case "1":
                pl_text.setText(arrs[1]);
                break;
            case "2":
                pl_text.setText(arrs[2]);
                break;
        }
        //点赞
        like = result.getLike();
        switch (like) {
            case "0":
                dz_text.setText(arrs[0]);
                break;
            case "1":
                dz_text.setText(arrs[1]);
                break;
            case "2":
                dz_text.setText(arrs[2]);
                break;
        }
        //@消息
        call = result.getCall();
        switch (call) {
            case "0":
                at_text.setText(arrs[0]);
                break;
            case "1":
                at_text.setText(arrs[1]);
                break;
            case "2":
                at_text.setText(arrs[2]);
        }

        //被关注消息的推送
        if (result.getSubscribe().equals("0")) {
            toggle_att.toggleSwitch(false);
        } else {
            toggle_att.toggleSwitch(true);
        }


        //活动消息的推送
        if (result.getActivity().equals("0")) {
            toggle_act.toggleSwitch(false);
        } else {
            toggle_act.toggleSwitch(true);
        }

        //系统消息的推送
        if (result.getSystem().equals("0")) {
            toggle_system.toggleSwitch(false);
        } else {
            toggle_system.toggleSwitch(true);
        }
    }

    /**
     * 更新通知设置
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        getP().getUserNotice();
    }

    /**
     * 设置成功
     */
    public void setNotice(){
        //getP().getUserNotice();
    }
}
