package cn.see.fragment.fragmentview.findview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import cn.see.adapter.MultiItemTypeAdapter;
import cn.see.adapter.RecryCommonAdapter;
import cn.see.adapter.ViewHolder;
import cn.see.base.BaseActivity;
import cn.see.util.ToastUtil;
import cn.see.util.constant.IntentConstant;
import cn.see.util.constant.PreferenceConstant;
import cn.see.util.version.PreferenceUtils;
import cn.see.util.widet.AlertView.AlertView;
import cn.see.util.widet.AlertView.OnDismissListener;
import cn.see.util.widet.AlertView.OnItemClickListener;

/**
 * 通用搜索
 *
 * 好友推荐
 * 发现话题
 * 世界
 */
public class SearchAct extends BaseActivity implements OnItemClickListener, OnDismissListener {

    RecryCommonAdapter<String> adapter;
    private  List<String> listData;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.title_tv_base)
    TextView title;
    @BindView(R.id.et_search)
    EditText editText;
    @BindView(R.id.clear_tv)
    TextView clear_tv;

    @OnClick(R.id.clear_tv)
    void clear(){
        AlertView exitAlView = new AlertView("", "是否清除历史记录", "否", new String[]{"是"}, null, this, AlertView.Style.Alert, this);
        exitAlView.show();

    }

    @OnClick(R.id.back_rela)
    void bacView(){
        onBack();
    }

    @Override
    public void initView() {
        title.setText("搜索");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void initAfter() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        listData = PreferenceUtils.getListData(this, PreferenceConstant.SERCH_RECORDING, String.class);
        if(listData!=null){
            if(listData.size()==0){
                clear_tv.setVisibility(View.GONE);
            }else{
                clear_tv.setVisibility(View.VISIBLE);
            }
            adapter = new RecryCommonAdapter<String>(this, R.layout.layout_search_one_item, listData) {
                @Override
                protected void convert(ViewHolder holder, String o, final int position) {
                    if (position == 0) {
                        holder.setVisible(R.id.ones_view, true);
                    } else {
                        holder.setVisible(R.id.ones_view, false);
                    }
                    if (position == listData.size() - 1) {
                        holder.setVisible(R.id.twos_view, true);
                    } else {
                        holder.setVisible(R.id.twos_view, false);
                    }
                    holder.setText(R.id.nick_name, o);
                    holder.setOnClickListener(R.id.del_rela, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listData.remove(position);
                            List<String> listData1 = PreferenceUtils.getListData(SearchAct.this, PreferenceConstant.SERCH_RECORDING, String.class);
                            listData1.remove(position);
                            PreferenceUtils.putListData(SearchAct.this,PreferenceConstant.SERCH_RECORDING,listData1);
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            };
            adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    Router.newIntent(SearchAct.this)
                            .putString(IntentConstant.SEARCH_CONT,listData.get(position))
                            .to(SearchContAct.class)
                            .launch();
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });
            recyclerView.setAdapter(adapter);
        }else{
            if(listData.size()==0){
                clear_tv.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_search;
    }

    @Override
    public XPresent bindPresent() {
        return null;
    }

    @Override
    public void setListener() {
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    if(editText.getText().toString().equals("")){
                        ToastUtil.showToast("请输入搜索内容");
                    }else{
                        Router.newIntent(SearchAct.this)
                                .putString(IntentConstant.SEARCH_CONT,editText.getText().toString())
                                .to(SearchContAct.class)
                                .launch();
                    }
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public void onDismiss(Object o) {

    }

    @Override
    public void onItemClick(Object o, int position) {
        if(position == 0){
            PreferenceUtils.remove(SearchAct.this,PreferenceConstant.SERCH_RECORDING);
            onStart();
        }
    }
}
