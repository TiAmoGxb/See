package cn.see.fragment.fragmentview.mineview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.see.R;
import cn.see.adapter.RecryCommonAdapter;
import cn.see.base.BaseActivity;
import cn.see.model.MineSchoolModel;
import cn.see.presenter.minep.MineSchoolPresenter;
import cn.see.util.ToastUtil;

/**
 * 我的大学
 */
public class MineSchoolAct extends BaseActivity<MineSchoolPresenter> {
    public static final String TAG ="MineSchoolAct" ;
    private RecryCommonAdapter<MineSchoolModel.SchoolResult.SchoolList> adapter;
    private boolean isFrist = true;
    private List<MineSchoolModel.SchoolResult.SchoolList> lists = new ArrayList<>();

    @BindView(R.id.title_tv_base)
    TextView title;
    @BindView(R.id.school_recy)
    RecyclerView recyclerView;
    @BindView(R.id.et_school)
    EditText editText;

    @OnClick(R.id.canal)
    void canal(){
        onBack();
    }

    @OnClick(R.id.back_rela)
    void bacView(){
        onBack();
    }

    @Override
    public void initView() {
        title.setText("选择学校");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void initAfter() {
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_mine_school;
    }

    @Override
    public MineSchoolPresenter bindPresent() {
        return new MineSchoolPresenter();
    }

    @Override
    public void setListener() {
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i(TAG,"s:"+s);
                String text = s.toString();
                if(!text.equals("")){
                    getP().getSchool(text);
                    recyclerView.setVisibility(View.VISIBLE);
                }else{
                    recyclerView.setVisibility(View.GONE);
                }
            }
        });
    }


    /**
     * 获取学校回调
     * @param list
     */
    public  void schoolResponse(List<MineSchoolModel.SchoolResult.SchoolList> list){
        InputMethodManager im = (InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        lists.clear();
        if(list.size() == 0){
           ToastUtil.showToast("没有搜索到您要找的学校");
            if(adapter!=null){
                adapter.notifyDataSetChanged();
            }
        }else{
            if(isFrist){
                lists.addAll(list);
                adapter = getP().initAdapter(lists);
                recyclerView.setAdapter(adapter);
                isFrist = false;
            }else{
                lists.addAll(list);
                adapter.notifyDataSetChanged();
            }
        }

    }


    public void setSusess(){
        onBack();
    }
}
