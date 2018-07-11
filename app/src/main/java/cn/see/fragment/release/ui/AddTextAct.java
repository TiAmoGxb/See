package cn.see.fragment.release.ui;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.see.R;
import cn.see.base.BaseActivity;
import cn.see.util.ToastUtil;
import cn.see.util.constant.IntentConstant;

public class AddTextAct extends BaseActivity {
    private String type;

    @BindView(R.id.title_tv_base)
    TextView title;
    @BindView(R.id.title_tv_op_bg)
    TextView comTv;
    @BindView(R.id.et_add_cont)
    EditText editText;
    @BindView(R.id.topic_rela)
    RelativeLayout relativeLayout;
    @BindView(R.id.topic_title)
    EditText topic_title;

    @OnClick(R.id.back_rela)
    void bacView(){
        onBack();
    }
    @OnClick(R.id.title_tv_op_bg)
    void goTv(){
        String msg = editText.getText().toString();
        String title = topic_title.getText().toString();
        Intent intent = new Intent();
        intent.putExtra(IntentConstant.TEXT_ADD,msg);
        if(msg.length()!=0){
            if(type.equals("topic")){
                intent.putExtra(IntentConstant.TEXT_ADD_TITLE,title);
                if(title.length()==0){
                    ToastUtil.showToast("请输入话题标题");
                    return;
                }
            }
            setResult(ReleasePreviewAct.ADD_TEXT_CODE,intent);
            onBack();
        }else{
            ToastUtil.showToast("请输入内容");
        }

    }

    @Override
    public void initView() {
        title.setText("添加文字");
        comTv.setText("完成");
        comTv.setVisibility(View.VISIBLE);
    }

    @Override
    public void initAfter() {
        type = getIntent().getStringExtra(IntentConstant.RELEASE_TYPE);
        if(type.equals("topic")){
            relativeLayout.setVisibility(View.VISIBLE);
        }
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
