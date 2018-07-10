package cn.see.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import cn.see.model.TabBannerModel;
import cn.see.util.constant.BannerConstant;
import cn.see.util.glide.GlideDownLoadImage;

/**
 * @日期：2018/6/19
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： Banner图片适配器
 */

public class ImageAdapter extends PagerAdapter {
    private static final String TAG =  "ImageAdapter";
    private List<ImageView> imgList = new ArrayList<ImageView>();
    private List<TabBannerModel.BannerResult.BannerList> tabBannerList;
    private List<String> urls ;
    private String flag;
    public ImageAdapter(Context context, List list, String flag) {
        this.flag = flag;
        for (int i = 0; i < list.size(); i++) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            if(flag.equals(BannerConstant.FIND_TAB_BANNER_TYPE)){
                this.tabBannerList = list;
                GlideDownLoadImage.getInstance().loadImage(tabBannerList.get(i).getUrl(), imageView);
            }else if(flag.equals(BannerConstant.TXT_ARTICL_BANNER_TYPE)){
                this.urls = list;
                GlideDownLoadImage.getInstance().loadImage(urls.get(i), imageView);
            }
            imgList.add(imageView);
        }
    }

    //监听点击接口
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener;

    //接口回调
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        Log.i(TAG,"赋值："+onItemClickListener);
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getCount() {
        return imgList.size()==1?1:Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //Warning：不要在这里调用removeView
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        position %= imgList.size();
        if (position < 0) {
            position = imgList.size() + position;
        }
        final ImageView view = imgList.get(position);
        ViewParent vp = view.getParent();
        if (vp != null) {
            ViewGroup parent = (ViewGroup) vp;
            parent.removeView(view);
        }
        container.addView(view);
        final int positionId = position;
        Log.i(TAG,"onItemClickListener："+onItemClickListener);
        if (onItemClickListener != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = positionId;
                    onItemClickListener.onItemClick(view, pos);
                }
            });
        }
        return view;
    }

}
