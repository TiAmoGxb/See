package cn.see.fragment.fragmentview.mineview;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.see.R;
import cn.see.adapter.TextReviewAdapter;
import cn.see.base.BaseActivity;
import cn.see.model.TextReviewModel;
import cn.see.presenter.minep.ReviewPresenter;
import cn.see.util.ToastUtil;
import cn.see.util.UserUtils;
import cn.see.util.constant.IntentConstant;
import cn.see.util.widet.putorefresh.PullToRefreshBase;
import cn.see.util.widet.putorefresh.PullToRefreshListView;
import cn.see.util.widet.putorefresh.RefreshShowTime;

/**
 * @日期：2018/6/5
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 评论
 */
public class ReviewAct extends BaseActivity<ReviewPresenter> implements PullToRefreshBase.OnRefreshListener2<ListView>{

    private static final String TAG = "ReviewAct" ;
    private List<TextReviewModel.ReviewResult.ReviewList> reviewLists = new ArrayList<>();
    private String text_id;
    private int page = 1;
    private String to_id = "0";
    private TextReviewAdapter textReviewAdapter;
    private int screenHeight;


    @BindView(R.id.title_tv_base)
    TextView titles;
    @BindView(R.id.pull_rew_list)
    PullToRefreshListView listView;
    @BindView(R.id.rela)
    RelativeLayout noCommRela;
    @BindView(R.id.rela_comm)
    RelativeLayout yesCommRela;
    @BindView(R.id.et_comm)
    EditText etCom;

    @OnClick(R.id.send_tv)
    void sendMsg(){
        if(etCom.getText().toString().trim().equals("")){
            ToastUtil.showToast("评论内容不能为空！");
            return;
        }
        getP().setReview(UserUtils.getUserID(ReviewAct.this),text_id,to_id,etCom.getText().toString());
    }

    @OnClick(R.id.rela)
    void showComm(){
        InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        to_id = "0";
        etCom.setHint("请输入评论内容");
    }


    @OnClick(R.id.back_rela)
    void bacAct(){
        onBack();
    }

    @Override
    public void initView() {
        titles.setText("评论");
        listView.getRefreshableView().setDividerHeight(0);
        text_id = getIntent().getStringExtra(IntentConstant.ARTIC_TEXT_ID);

    }

    @Override
    public void initAfter() {
        getP().getTextReview(text_id, UserUtils.getUserID(this),page,"20");
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_review;
    }

    @Override
    public ReviewPresenter bindPresent() {
        return new ReviewPresenter();
    }

    @Override
    public void setListener() {
        listView.setOnRefreshListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToastUtil.showToast("p:"+position);
            }
        });
    }

    /**
     * 评论数据
     * @param list
     */
    public void textReviewResponse(List<TextReviewModel.ReviewResult.ReviewList> list ,int page ,int tpage){
        if(page>1){
            if(page > tpage ){
                ToastUtil.showToast("暂无更多评论");
            }else{
                reviewLists.addAll(list);
                textReviewAdapter.notifyDataSetChanged();
            }

        }else{
            reviewLists.clear();
            reviewLists.addAll(list);
            textReviewAdapter = new TextReviewAdapter(this,reviewLists,1,text_id);
            listView.setAdapter(textReviewAdapter);
        }
        getP().progress.dismiss();
        listView.onRefreshComplete();
    }

    /**
     * 评论回调
     * @param msg
     */
    public void setReviewResponse(String msg){
        ToastUtil.showToast(msg);
        etCom.setHint("");
        page = 1;
        initAfter();
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        RefreshShowTime.showTime(refreshView);
        page = 1;
        initAfter();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        page ++;
        initAfter();
    }


    public static boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            v.getLocationInWindow(leftTop);
            int left = leftTop[0], top = leftTop[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
    /**
     * 监听软键盘是否显示
     */
    @Override
    protected void onResume() {
        super.onResume();
        //获取当前屏幕内容的高度
        screenHeight = getWindow().getDecorView().getHeight();
        getWindow().getDecorView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                //获取View可见区域的bottom
                Rect rect = new Rect();
                getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
                if(bottom!=0 && oldBottom!=0 && bottom - rect.bottom <= 0){
                    yesCommRela.setVisibility(View.GONE);
                    noCommRela.setVisibility(View.VISIBLE);
                }else {
                    etCom.setText("");
                    noCommRela.setVisibility(View.GONE);
                    yesCommRela.setVisibility(View.VISIBLE);
                    etCom.requestFocus();
                }
            }
        });
    }

    /**
     * 条目点击
     * @param position
     */
    public void reviewItem(int position){
        InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        TextReviewModel.ReviewResult.ReviewList review = reviewLists.get(position);
        to_id = review.getUser_id();
        etCom.setHint("回复:"+review.getNickname());
    }
}
