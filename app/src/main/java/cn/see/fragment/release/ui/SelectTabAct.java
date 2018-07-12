package cn.see.fragment.release.ui;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.see.R;
import cn.see.adapter.RecryCommonAdapter;
import cn.see.base.BaseActivity;
import cn.see.fragment.release.presenter.SeleclTabPresenter;
import cn.see.model.TxtModel;
import cn.see.util.SpaceItemDecoration;
import cn.see.util.ToastUtil;
import cn.see.util.constant.IntentConstant;
import cn.see.util.widet.CustomGridViewLayoutManager;

/**
 * 选择标签
 */
public class SelectTabAct extends BaseActivity<SeleclTabPresenter> {


    private List<TxtModel.TxtResult.Result> results;
    private RecryCommonAdapter<TxtModel.TxtResult.Result> adapter;

    @BindView(R.id.title_tv_base)
    TextView title;
    @BindView(R.id.title_tv_op_bg)
    TextView comTv;
    @BindView(R.id.top_recy)
    RecyclerView topRecy;
    @BindView(R.id.tab_recy)
    RecyclerView tabRecy;

    @OnClick(R.id.title_tv_op_bg)
    void com(){
        if(results.size()>0){
            Intent intent = new Intent();
            intent.putExtra(IntentConstant.SEL_TAB_TEXT, (Serializable) results);
            setResult(ReleasePreviewAct.ADD_TAB_CODE,intent);
            onBack();
        }else{
            ToastUtil.showToast("还没有选择标签");
        }
    }

    @OnClick(R.id.back_rela)
    void bacView(){
        onBack();
    }

    @Override
    public void initView() {
        title.setText("选择标签");
        comTv.setText("完成");
        comTv.setVisibility(View.VISIBLE);
        CustomGridViewLayoutManager  gridViewLayoutManager = new CustomGridViewLayoutManager(context, 5);
        gridViewLayoutManager.setScrollEnabled(true);
        topRecy.addItemDecoration(new SpaceItemDecoration(5, 18, false));
        CustomGridViewLayoutManager  gridViewLayoutManager1 = new CustomGridViewLayoutManager(context, 5);
        gridViewLayoutManager1.setScrollEnabled(true);
        tabRecy.addItemDecoration(new SpaceItemDecoration(5, 18, false));
        topRecy.setLayoutManager(gridViewLayoutManager);
        tabRecy.setLayoutManager(gridViewLayoutManager1);
    }

    @Override
    public void initAfter() {
        getP().getTab();
        results = new ArrayList<>();
        adapter = getP().initAdapter(results);
        topRecy.setAdapter(adapter);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_select_tab;
    }

    @Override
    public SeleclTabPresenter bindPresent() {
        return new SeleclTabPresenter();
    }

    @Override
    public void setListener() {

    }

    /**
     * 获取标签
     * @param result
     */
    public void tabResponse(List<TxtModel.TxtResult.Result> result){
         tabRecy.setAdapter(getP().initAdapter(result));
    }

    /**
     * 选中标签
     * @param result
     * @param isCheck
     */
    public void setTab(TxtModel.TxtResult.Result result, boolean isCheck){
        if(isCheck){
            results.add(result);
        }else{
            results.remove(result);
        }
        adapter.notifyDataSetChanged();
    }
}
