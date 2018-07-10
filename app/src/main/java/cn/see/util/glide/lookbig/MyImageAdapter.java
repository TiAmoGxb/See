package cn.see.util.glide.lookbig;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.see.util.ToastUtil;
import cn.see.util.glide.GlideDownLoadImage;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * @日期：2018/6/25
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明：
 */

public class MyImageAdapter extends PagerAdapter {
    public static final String TAG = MyImageAdapter.class.getSimpleName();
    private List<String> imageUrls;
    private Activity activity;

    public MyImageAdapter(List<String> imageUrls, Activity activity) {
        this.imageUrls = imageUrls;
        this.activity = activity;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        String url = imageUrls.get(position);
        PhotoView photoView = new PhotoView(activity);
        GlideDownLoadImage.getInstance().loadImage(url,photoView);
        container.addView(photoView);
        photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float v, float v1) {
                activity.finish();
            }
        });
        return photoView;
    }

    @Override
    public int getCount() {
        return imageUrls != null ? imageUrls.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}