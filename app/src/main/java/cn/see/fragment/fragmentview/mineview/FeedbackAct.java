package cn.see.fragment.fragmentview.mineview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.see.R;
import cn.see.base.BaseActivity;
import cn.see.util.ToastUtil;

public class FeedbackAct extends BaseActivity {

    @BindView(R.id.et_pro)
    EditText editText;
    @BindView(R.id.title_tv_base)
    TextView title;
    @BindView(R.id.title_tv_op)
    TextView op;

    @OnClick(R.id.back_rela)
    void bacView(){
        onBack();
    }


    @OnClick(R.id.title_tv_op)
    void comTe(){
        if(editText.getText().toString().equals("")){
            ToastUtil.showToast("请输入您的意见");
            return;
        }
        ToastUtil.showToast("反馈成功");
        onBack();
    }

    @Override
    public void initView() {
        title.setText("意见反馈");
        op.setText("提交");
        op.setVisibility(View.VISIBLE);
    }

    @Override
    public void initAfter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_feedback;
    }

    @Override
    public XPresent bindPresent() {
        return null;
    }

    @Override
    public void setListener() {

    }
}
