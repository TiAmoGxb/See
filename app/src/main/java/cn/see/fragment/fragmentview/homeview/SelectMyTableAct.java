package cn.see.fragment.fragmentview.homeview;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.see.R;
import cn.see.adapter.CommonListViewAdapter;
import cn.see.adapter.RecryCommonAdapter;
import cn.see.adapter.ViewHolder;
import cn.see.base.BaseActivity;
import cn.see.presenter.findp.FindPresenter;
import cn.see.util.SpaceItemDecoration;
import cn.see.util.ToastUtil;
import cn.see.util.glide.GlideDownLoadImage;
import cn.see.util.widet.putorefresh.PullToRefreshBase;
import cn.see.util.widet.putorefresh.PullToRefreshListView;
import cn.see.util.widet.putorefresh.RefreshShowTime;

/**
 * @日期：2018/6/8
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 选择我的标签
 */
public class SelectMyTableAct extends BaseActivity {


    @BindView(R.id.title_tv_base)
    TextView titles;
    @BindView(R.id.title_tv_op)
    TextView opention;
    @BindView(R.id.sel_table_recy)
    RecyclerView recyclerView;


    @OnClick(R.id.back_rela)
    void bacAct(){
        onBack();
    }

    @Override
    public void initView() {
        titles.setText("选择我的标签");
        opention.setText("保存");
        opention.setVisibility(View.VISIBLE);
        recyclerView.addItemDecoration(new SpaceItemDecoration(2, 40, false));
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
    }

    @Override
    public void initAfter() {
        recyclerView.setAdapter(new RecryCommonAdapter<FindPresenter.TableBean>(this,R.layout.layout_find_find_top_rec_item,getTableDatas()) {
            @Override
            protected void convert(ViewHolder holder, FindPresenter.TableBean tableBean, int position) {
                ImageView imageView = holder.getView(R.id.icon);
                imageView.setLayoutParams(new AutoRelativeLayout.LayoutParams(
                        AutoRelativeLayout.LayoutParams.MATCH_PARENT,340
                ));
                if(position == getTableDatas().size()-1){
                    holder.setVisible(R.id.b_view,true);
                }else{
                    holder.setVisible(R.id.b_view,false);
                }
                holder.setText(R.id.txt,tableBean.getText());
                GlideDownLoadImage.getInstance().loadCircleImageRole(tableBean.getIcon(),imageView,4);
            }


        });
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_select_my_table;
    }

    @Override
    public XPresent bindPresent() {
        return null;
    }

    @Override
    public void setListener() {
    }

    private List<FindPresenter.TableBean> getTableDatas() {

        List<FindPresenter.TableBean> tableBeen = new ArrayList<>();
        tableBeen.add(new FindPresenter.TableBean(R.drawable.youxi,"游戏",false));
        tableBeen.add(new FindPresenter.TableBean(R.drawable.xiaoyuan,"校园",false));
        tableBeen.add(new FindPresenter.TableBean(R.drawable.mingxing,"明星",false));
        tableBeen.add(new FindPresenter.TableBean(R.drawable.meizhuang,"美装",false));
        tableBeen.add(new FindPresenter.TableBean(R.drawable.yishu,"艺术",false));
        tableBeen.add(new FindPresenter.TableBean(R.drawable.lvxing,"旅行",false));
        tableBeen.add(new FindPresenter.TableBean(R.drawable.shoukong,"手控",false));
        tableBeen.add(new FindPresenter.TableBean(R.drawable.huihua,"绘画",false));
        tableBeen.add(new FindPresenter.TableBean(R.drawable.jianfei,"减肥",false));
        tableBeen.add(new FindPresenter.TableBean(R.drawable.shishang,"时尚",false));
        tableBeen.add(new FindPresenter.TableBean(R.drawable.qinggan,"情感",false));
        tableBeen.add(new FindPresenter.TableBean(R.drawable.xueba,"学霸",false));
        tableBeen.add(new FindPresenter.TableBean(R.drawable.nvshen,"女神",false));
        tableBeen.add(new FindPresenter.TableBean(R.drawable.nanshen,"男神",false));
        tableBeen.add(new FindPresenter.TableBean(R.drawable.waiyu,"外语",false));
        tableBeen.add(new FindPresenter.TableBean(R.drawable.mengchong,"萌宠",false));
        tableBeen.add(new FindPresenter.TableBean(R.drawable.fangkong,"放空",false));
        tableBeen.add(new FindPresenter.TableBean(R.drawable.erciyuan,"二次元",false));
        tableBeen.add(new FindPresenter.TableBean(R.drawable.dushu,"读书",false));
        tableBeen.add(new FindPresenter.TableBean(R.drawable.keji,"科技",false));
        tableBeen.add(new FindPresenter.TableBean(R.drawable.huaijiu,"怀旧",false));
        tableBeen.add(new FindPresenter.TableBean(R.drawable.meishi,"美食",false));
        tableBeen.add(new FindPresenter.TableBean(R.drawable.huaijiu,"生活",false));
        tableBeen.add(new FindPresenter.TableBean(R.drawable.yundong,"运动",false));
        tableBeen.add(new FindPresenter.TableBean(R.drawable.yinyue,"音乐",false));
        tableBeen.add(new FindPresenter.TableBean(R.drawable.sheying,"摄影",false));
        tableBeen.add(new FindPresenter.TableBean(R.drawable.kaoyan,"考研",false));
        tableBeen.add(new FindPresenter.TableBean(R.drawable.liuxue,"留学",false));
        tableBeen.add(new FindPresenter.TableBean(R.drawable.yingshi,"影视",false));
        tableBeen.add(new FindPresenter.TableBean(R.drawable.huaijiu,"穿搭",false));

        return tableBeen;
    }
}
