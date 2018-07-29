package cn.see.fragment.release.ui;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

/**
 * 添加文字
 */
public class AddTextAct extends BaseActivity {
    private static final String TAG = "AddTextAct" ;
    private String type;
    private static final int CONT_MAX_LENGTH = 300;
    private int currentLength = CONT_MAX_LENGTH;
    private static final int CONT_MAX_LENGTH_TITLE = 15;
    private int currentLengthTitle = CONT_MAX_LENGTH_TITLE;

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
    @BindView(R.id.text_num)
    TextView textNum;
    @BindView(R.id.topic_title_num)
    TextView titleNum;

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
            String contTopic = getIntent().getStringExtra(IntentConstant.RELEASE_CONT);
            Log.i(TAG,"contTopic:"+contTopic);
            if(!contTopic.equals("内容")){
                Log.i(TAG,"执行:"+contTopic);
                currentLength = contTopic.length();
                editText.setText(contTopic);
                textNum.setText((CONT_MAX_LENGTH-currentLength)+"/"+CONT_MAX_LENGTH);
            }
            String title = getIntent().getStringExtra(IntentConstant.RELEASE_TITLE);
            if(!title.equals("标题")){
                currentLengthTitle = title.length();
                topic_title.setText(title);
                titleNum.setText((CONT_MAX_LENGTH_TITLE-currentLengthTitle)+"/"+CONT_MAX_LENGTH_TITLE);
            }
        }else{
            String cont = getIntent().getStringExtra(IntentConstant.RELEASE_CONT);
            if(!cont.equals("说点什么...")){
                currentLength = cont.length();
                editText.setText(cont);
                textNum.setText((CONT_MAX_LENGTH-currentLength)+"/"+CONT_MAX_LENGTH);
            }
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
        /**
         * 监听输入框输入字数限制*/
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                textNum.setText(currentLength+"/"+CONT_MAX_LENGTH);
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 0){
                    currentLength = CONT_MAX_LENGTH - editText.getText().length();
                    textNum.setText(currentLength+"/"+CONT_MAX_LENGTH);
                }else{
                    textNum.setText(0+"/"+CONT_MAX_LENGTH);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
                textNum.setText(currentLength+"/"+CONT_MAX_LENGTH);
            }
        });

        topic_title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                titleNum.setText(currentLengthTitle+"/"+CONT_MAX_LENGTH_TITLE);
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 0){
                    currentLengthTitle = CONT_MAX_LENGTH_TITLE - topic_title.getText().length();
                    titleNum.setText(currentLengthTitle+"/"+CONT_MAX_LENGTH_TITLE);
                }else{
                    titleNum.setText(0+"/"+CONT_MAX_LENGTH_TITLE);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
                titleNum.setText(currentLengthTitle+"/"+CONT_MAX_LENGTH_TITLE);
            }
        });
    }
}
