package cn.see.fragment.release.presenter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.see.R;
import cn.see.adapter.MultiItemTypeAdapter;
import cn.see.adapter.RecryCommonAdapter;
import cn.see.adapter.ViewHolder;
import cn.see.fragment.release.ui.ReleasePreviewAct;
import cn.see.util.glide.GlideDownLoadImage;

/**
 * @日期：2018/7/11
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 发布预览对应P
 */

public class ReleasePreviewPresenter extends XPresent<ReleasePreviewAct> {


    public RecryCommonAdapter<String> initAdapter(final List<String> urls){
        RecryCommonAdapter<String> adapter = new RecryCommonAdapter<String>(getV(), R.layout.layout_release_review_item,urls) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                ImageView imageView = holder.getView(R.id.item_img);
                if(position==urls.size()-1){
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                }else{
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                }
                GlideDownLoadImage.getInstance().loadImage(s,imageView);
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
//                getV().imgageItem(position);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        return adapter;
    }
}
