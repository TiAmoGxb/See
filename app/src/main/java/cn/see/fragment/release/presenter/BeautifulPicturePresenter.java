package cn.see.fragment.release.presenter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.see.R;
import cn.see.adapter.MultiItemTypeAdapter;
import cn.see.adapter.RecryCommonAdapter;
import cn.see.adapter.ViewHolder;
import cn.see.fragment.release.bean.BeauBean;
import cn.see.fragment.release.ui.BeautifulPictureAct;
import cn.see.util.glide.GlideDownLoadImage;

/**
 * @日期：2018/7/5
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 美图对应P
 */

public class BeautifulPicturePresenter extends XPresent<BeautifulPictureAct> {

    List<BeauBean> beauBeen;

    public RecryCommonAdapter<Bitmap> initAdapter(List<Bitmap> urls){
        RecryCommonAdapter<Bitmap> adapter = new RecryCommonAdapter<Bitmap>(getV(), R.layout.layout_release_beauti_recy_item,urls) {
            @Override
            protected void convert(ViewHolder holder, Bitmap s, int position) {
                ImageView imageView = holder.getView(R.id.image_item);
                imageView.setImageBitmap(s);
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                getV().imgageItem(position);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        return adapter;
    }

   public void addBeauData(){
       beauBeen = new ArrayList<>();
       beauBeen.add(new BeauBean(R.drawable.bac_image,"高雅"));
       beauBeen.add(new BeauBean(R.drawable.bac_image,"美白"));
       beauBeen.add(new BeauBean(R.drawable.bac_image,"清新"));
       beauBeen.add(new BeauBean(R.drawable.bac_image,"雾化"));
       beauBeen.add(new BeauBean(R.drawable.bac_image,"伦敦"));
       beauBeen.add(new BeauBean(R.drawable.bac_image,"淡雅"));
       beauBeen.add(new BeauBean(R.drawable.bac_image,"青春"));
       beauBeen.add(new BeauBean(R.drawable.bac_image,"加深轮廓"));
       beauBeen.add(new BeauBean(R.drawable.bac_image,"盛夏"));
       beauBeen.add(new BeauBean(R.drawable.bac_image,"玛罗"));
       beauBeen.add(new BeauBean(R.drawable.bac_image,"清凉"));
       beauBeen.add(new BeauBean(R.drawable.bac_image,"朴素"));
       beauBeen.add(new BeauBean(R.drawable.bac_image,"萌新"));
       beauBeen.add(new BeauBean(R.drawable.bac_image,"清凉"));
       beauBeen.add(new BeauBean(R.drawable.bac_image,"晨光"));
   }


   public  RecryCommonAdapter<BeauBean> initBeauAdapter (){
       RecryCommonAdapter<BeauBean>  adapter = new RecryCommonAdapter<BeauBean>(getV(),R.layout.layout_release_beau_item,beauBeen) {
           @Override
           protected void convert(ViewHolder holder, BeauBean beauBean, int position) {

               ImageView imageView = holder.getView(R.id.tab_img);
               holder.setText(R.id.tab_name,beauBean.getText());
               GlideDownLoadImage.getInstance().loadImage(beauBean.getImg(),imageView);

           }
       };
       adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
           @Override
           public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
             getV().beauItem(position);
           }

           @Override
           public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
               return false;
           }
       });

       return adapter;
   }




}
