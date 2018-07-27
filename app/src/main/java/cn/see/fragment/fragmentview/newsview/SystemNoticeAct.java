package cn.see.fragment.fragmentview.newsview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.router.Router;
import cn.see.R;
import cn.see.adapter.RecryCommonAdapter;
import cn.see.adapter.ViewHolder;
import cn.see.base.BaseActivity;
import cn.see.main.WebAct;
import cn.see.model.SystemNoticModel;
import cn.see.presenter.newsp.SystemNoticPresenter;
import cn.see.util.UserUtils;
import cn.see.util.constant.HttpConstant;
import cn.see.util.constant.IntentConstant;
import cn.see.util.glide.GlideDownLoadImage;

/**
 * 系统通知
 */
public class SystemNoticeAct extends BaseActivity<SystemNoticPresenter> {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.title_tv_base)
    TextView title;
    @OnClick(R.id.back_rela)
    void bacView(){
        onBack();
    }


    @Override
    public void initView() {
        title.setText("通知");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void initAfter() {
        getP().getNotice();
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_system_notice;
    }

    @Override
    public SystemNoticPresenter bindPresent() {
        return new SystemNoticPresenter();
    }

    @Override
    public void setListener() {

    }


    public void data(final List<SystemNoticModel.SystemModelResult.SystemMessage.SystemList> list){
        recyclerView.setAdapter(new RecryCommonAdapter<SystemNoticModel.SystemModelResult.SystemMessage.SystemList>(this,R.layout.layout_new_syste_item,list) {
            @Override
            protected void convert(ViewHolder holder, SystemNoticModel.SystemModelResult.SystemMessage.SystemList o, int position) {
                holder.setText(R.id.title,o.getTitle());
                holder.setText(R.id.time,o.getCraete_time());
                holder.setText(R.id.msg,o.getMsg());
                ImageView imageView= holder.getView(R.id.img);
                GlideDownLoadImage.getInstance().loadCircleImage(R.drawable.log1o,imageView);

                setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                        Router.newIntent(SystemNoticeAct.this)
                                .to(WebAct.class)
                                .putString(IntentConstant.WEB_LOAD_URL, HttpConstant.SYSTEM_NOTICE+list.get(position).getId())
                                .launch();
                    }

                    @Override
                    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                        return false;
                    }
                });
            }

        });
    }
}
