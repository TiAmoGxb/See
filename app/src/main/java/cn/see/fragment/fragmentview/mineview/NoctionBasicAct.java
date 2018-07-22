package cn.see.fragment.fragmentview.mineview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.see.R;
import cn.see.base.BaseActivity;
import cn.see.presenter.minep.NoctionBasicPresenter;
import cn.see.util.constant.IntentConstant;

/**
 * 消息设置通用子界面
 */
public class NoctionBasicAct extends BaseActivity<NoctionBasicPresenter> {

    private int type;
    private String comText;
    private String values;


    @BindView(R.id.title_tv_base)
    TextView title;
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.all_text)
    TextView all_text;
    @BindView(R.id.hy_text)
    TextView hy_text;
    @BindView(R.id.no_text)
    TextView no_text;

    @OnClick(R.id.all_rela)
    void allRela(){
        comText = "0";
        setSet();
    }

    @OnClick(R.id.hy_rela)
    void hyRela(){
        comText = "1";
        setSet();
    }
    @OnClick(R.id.no_rela)
    void noRela(){
        comText = "2";
        setSet();
    }

    @OnClick(R.id.back_rela)
    void bacView(){
        onBack();
    }

    @Override
    public void initView() {
        title.setText("通知");
    }

    public void isText(String t){
        text.setText(t);
    }


    @Override
    public void initAfter() {
        type = getIntent().getIntExtra(IntentConstant.NOCTION_TYPE,-1);
        values= getIntent().getStringExtra(IntentConstant.NOCTION_VALUES);
        if(values.equals("0")){
            all_text.setVisibility(View.VISIBLE);
        }else if(values.equals("1")){
            hy_text.setVisibility(View.VISIBLE);
        }else if(values.equals("2")){
            no_text.setVisibility(View.VISIBLE);
        }
        getP().isTextType(type);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_noction_basic;
    }

    @Override
    public NoctionBasicPresenter bindPresent() {
        return new NoctionBasicPresenter();
    }

    @Override
    public void setListener() {

    }

    public void setNotice(){
        onBack();
    }
    private void setSet() {
        switch (type){
            case NoctionAct.PL_TYPE:
                getP().setUserReview(comText);
                break;
            case NoctionAct.DZ_TYPE:
                getP().setUserLike(comText);
                break;
            case NoctionAct.AT_TYPE:
                getP().setUserCall(comText);
                break;
        }
    }
}
