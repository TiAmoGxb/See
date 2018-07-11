package cn.see.fragment.release.ui;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.see.R;
import cn.see.base.BaseActivity;
import cn.see.util.constant.IntentConstant;

public class AddTextAct extends BaseActivity {

    @BindView(R.id.title_tv_base)
    TextView title;
    @BindView(R.id.title_tv_op_bg)
    TextView comTv;
    @BindView(R.id.et_add_cont)
    EditText editText;
    @OnClick(R.id.back_rela)
    void bacView(){
        onBack();
    }
    @OnClick(R.id.title_tv_op_bg)
    void goTv(){
        Intent intent = new Intent();
        intent.putExtra(IntentConstant.TEXT_ADD,editText.getText().toString());
        setResult(ReleasePreviewAct.ADD_TEXT_CODE,intent);
        onBack();
    }

    @Override
    public void initView() {
        title.setText("添加文字");
        comTv.setText("完成");
        comTv.setVisibility(View.VISIBLE);
    }

    @Override
    public void initAfter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_add_text;
    }

    @Override
    public XPresent bindPresent() {
        return null;
    }

    @Override
    public void setListener() {

    }
}
