package cn.see.fragment.fragmentview.mineview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.feezu.liuli.timeselector.TimeSelector;
import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.see.R;
import cn.see.base.BaseActivity;
import cn.see.model.UserInfoModel;
import cn.see.presenter.minep.UserOtherBasicDataPresenter;
import cn.see.util.constant.IntentConstant;

/**
 * 修改用户信息其他通用界面
 * 昵称  0
 * 性别  1
 * 签名  2
 * 生日（星座） 3
 */
public class UserOtherBasicDataAct extends BaseActivity<UserOtherBasicDataPresenter> {

    public static final String TAG = "UserOtherBasicDataAct";
    public static final String[] contellationArr = {"白羊座","金牛座","双子座","巨蟹座","狮子座","处女座","天秤座","天蝎座","射手座","魔羯座","水瓶座","双鱼座"};
    private int type;
    private String sexText;
    private UserInfoModel.UserInfoResult result;
    @BindView(R.id.title_tv_base)
    TextView titleTv;
    @BindView(R.id.nick_name_ed)
    EditText nick_et;
    @BindView(R.id.title_tv_op)
    TextView title_tv_op;
    @BindView(R.id.nan_img)
    ImageView nan_img;
    @BindView(R.id.nv_img)
    ImageView nv_img;
    @BindView(R.id.bro_rela)
    LinearLayout bro_rela;
    @BindView(R.id.sex_rela)
    LinearLayout sex_ela;
    @BindView(R.id.sin_ed)
    EditText sin_ed;
    @BindView(R.id.bro_tv)
    TextView broTv;
    @BindView(R.id.xz_tv)
    TextView xzTv;

    @OnClick(R.id.back_rela)
    void bacView(){
        onBack();
    }

    @OnClick(R.id.nan_rela)
    void nanRela(){
        sexText = "1";
        nan_img.setVisibility(View.VISIBLE);
        nv_img.setVisibility(View.GONE);
    }
    @OnClick(R.id.nv_rela)
    void nv_rela(){
        sexText = "2";
        nan_img.setVisibility(View.GONE);
        nv_img.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.title_tv_op)
    void comte(){
        setComte();
    }

    @OnClick(R.id.bro_tv)
    void selBro(){
        TimeSelector timeSelector = new TimeSelector(this, new TimeSelector.ResultHandler() {
            @Override public void handle(String time) {
                String[] split = time.split(" ");
                broTv.setText(split[0]);
                String[] split1 = split[0].split("-");
                String constellation = getConstellation(contellationArr, Integer.parseInt(split1[1]), Integer.parseInt(split1[2]));
                xzTv.setText(constellation);

            }
        }, "1970-1-1 17:34", "2038-12-30 15:20");
        timeSelector.setMode(TimeSelector.MODE.YMD);//只显示 年月日
        timeSelector.show();
    }

    @Override
    public void initView() {
        title_tv_op.setVisibility(View.VISIBLE);
    }

    @Override
    public void initAfter() {
        result = (UserInfoModel.UserInfoResult) getIntent().getSerializableExtra(IntentConstant.SET_USER_RESULT);
        type = getIntent().getIntExtra(IntentConstant.USER_SET_DATA_BASIC_TYPE, -1);
        getP().isTypeTitle(type);
        setUi();
        Log.i(TAG,"result："+result.toString());
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_user_other_basic_data;
    }

    @Override
    public UserOtherBasicDataPresenter bindPresent() {
        return new UserOtherBasicDataPresenter();
    }

    @Override
    public void setListener() {

    }

    /**
     * 标题以及右侧文字
     */
    public void getTitle(String title,String rightText){
        titleTv.setText(title);
        title_tv_op.setText(rightText);
    }

    private void setUi(){
        if(type == SetUserDataAct.SEX_TEXT_TYPE){
            nick_et.setVisibility(View.GONE);
            sex_ela.setVisibility(View.VISIBLE);
            Log.i(TAG,"sex:"+result.getSex());
            if(result.getSex().equals("1")){
                nan_img.setVisibility(View.VISIBLE);
                nv_img.setVisibility(View.GONE);
            }else{
                nan_img.setVisibility(View.GONE);
                nv_img.setVisibility(View.VISIBLE);
            }
            sexText = result.getSex();
        }else if(type == SetUserDataAct.SIN_TEXT_TYPE){
            nick_et.setVisibility(View.GONE);
            sin_ed.setVisibility(View.VISIBLE);
            if(!result.getSignature().equals("")){
                sin_ed.setText(result.getSignature());
            }
        }else if(type == SetUserDataAct.BRO_TEXT_TYPE){
            nick_et.setVisibility(View.GONE);
            bro_rela.setVisibility(View.VISIBLE);
            if(!result.getBirthday_info().equals("")){
                broTv.setText(result.getBirthday_info());
                xzTv.setText(result.getConstellation());
            }
        }else{
            if(!result.getNickname().equals("")){
                nick_et.setText(result.getNickname());
            }
        }
    }

    private void setComte() {
        if(type == SetUserDataAct.NICK_NAME_TYPE){
            getP().seNickName(nick_et.getText().toString());
        }else if(type == SetUserDataAct.SEX_TEXT_TYPE){
            getP().seSex(sexText);
        }else if(type == SetUserDataAct.SIN_TEXT_TYPE){
            getP().seSin(sin_ed.getText().toString());
        }else if(type == SetUserDataAct.BRO_TEXT_TYPE){
            getP().seBrithday(broTv.getText().toString());
        }
    }


    /**
     * 修改成功回调
     */
    public void setSusess(){
        onBack();
    }


    /**
     * 星座转换
     * @param contellationArr
     * @param month 月
     * @param day 日
     * @return
     */
    public static String  getConstellation(String [] contellationArr,int month,int day){
        int point = -1;
        Double date = Double.parseDouble(month + "." + day);
        if (3.21 <= date && 4.19 >= date) {
            point = 0;
        } else if (4.20 <= date && 5.20 >= date) {
            point = 1;
        } else if (5.21 <= date && 6.21 >= date) {
            point = 2;
        } else if (6.22 <= date && 7.22 >= date) {
            point = 3;
        } else if (7.23 <= date && 8.22 >= date) {
            point = 4;
        } else if (8.23 <= date && 9.22 >= date) {
            point = 5;
        } else if (9.23 <= date && 10.23 >= date) {
            point = 6;
        } else if (10.24 <= date && 11.22 >= date) {
            point = 7;
        } else if (11.23 <= date && 12.21 >= date) {
            point = 8;
        } else if (12.22 <= date && 12.31 >= date) {
            point = 9;
        } else if (1.01 <= date && 1.19 >= date) {
            point = 9;
        } else if (1.20 <= date && 2.18 >= date) {
            point = 10;
        } else if (2.19 <= date && 3.20 >= date) {
            point = 11;
        }
        if(point == -1) {
            return contellationArr[2];
        }
        return contellationArr[point];
    }


}
