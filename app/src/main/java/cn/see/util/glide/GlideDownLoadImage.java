package cn.see.util.glide;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import cn.see.R;
import cn.see.app.App;


/**
 * @日期：2018/6/6
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 加载图片
 */
public class GlideDownLoadImage {

    private static final String TAG = "ImageLoader";

    private static GlideDownLoadImage instance = new GlideDownLoadImage();

    public static GlideDownLoadImage getInstance() {
        return instance;
    }

    /**
     * @param
     * @name 加载本地图片的重载方法
     * @auhtor fuduo
     * @Data 2018-1-9
     */
    public void loadImage(Context mContext, int resId, ImageView view) {
        Glide.with(mContext)
                .load(resId)
                .placeholder(R.drawable.bac_icon)
                .error(R.drawable.bac_icon)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }

    /**
     * @param
     * @name 加载本地图片的重载方法
     * @auhtor fuduo
     * @Data 2018-1-9
     */
    public void loadImage(Fragment fragment, int resId, ImageView view) {
        Glide.with(fragment)
                .load(resId)
                .placeholder(R.drawable.bac_icon)
                .error(R.drawable.bac_icon)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }


    /**
     * @param
     * @name 加载本地图片的重载方法
     * @auhtor fuduo
     * @Data 2018-1-9
     */
    public void loadImage(Activity activity, int resId, ImageView view) {
        Glide.with(activity)
                .load(resId)
                .placeholder(R.drawable.bac_icon)
                .error(R.drawable.bac_icon)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }

    /**
     * @param
     * @name 加载本地图片的重载方法 此方法慎用
     * @auhtor fuduo
     * @Data 2018-1-9
     */
    public void loadImage(int resId, ImageView view) {
        Glide.with(App.getContext())
                .load(resId)
                .placeholder(R.drawable.bac_icon)
                .error(R.drawable.bac_icon)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }

    /**
     * 加载网络图片
     *
     * @param url
     * @param view
     */
    public void loadImage(Context mContext, String url, ImageView view) {
        Glide.with(mContext)
                .load(url)
                .placeholder(R.drawable.bac_icon)
                .error(R.drawable.bac_icon)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }

    /**
     * @param activity
     * @param url
     * @param view
     * @name 加载网络图片重载方法
     * @auhtor fuduo
     * @Data 2017-9-5 11:18
     */
    public void loadImage(Activity activity, String url, ImageView view) {
        Glide.with(activity)
                .load(url)
                .placeholder(R.drawable.bac_icon)
                .error(R.drawable.bac_icon)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }

    public void loadImage( String url, ImageView view) {
        Glide.with(App.getContext())
                .load(url)
                .placeholder(R.drawable.bac_icon)
                .error(R.drawable.bac_icon)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }


    /**
     * @param fragment
     * @param url
     * @param view
     * @name 加载网络图片重载方法
     * @auhtor fuduo
     * @Data 2017-9-5 11:18
     */
    public void loadImage(Fragment fragment, String url, ImageView view) {
        Glide.with(fragment)
                .load(url)
                .placeholder(R.drawable.bac_icon)
                .error(R.drawable.bac_icon)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }


    /**
     * @param mContext
     * @param resId
     * @param view
     * @name 加载本地圆图
     * @auhtor fuduo
     * @Data 2017-9-5 11:18
     */
    public void loadCircleImage(Context mContext, int resId, ImageView view) {
        Glide.with(mContext)
                .load(resId)
                .placeholder(R.drawable.bac_icon)
                .error(R.drawable.bac_icon)
                .bitmapTransform(new GlideCircleTransform(mContext))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }

    /**
     * @param activity
     * @param resId
     * @param view
     * @name 加载本地圆图
     * @auhtor fuduo
     * @Data 2017-9-5 11:18
     */
    public void loadCircleImage(Activity activity, int resId, ImageView view) {
        Glide.with(activity)
                .load(resId)
                .placeholder(R.drawable.bac_icon)
                .error(R.drawable.bac_icon)
                .bitmapTransform(new GlideCircleTransform(activity))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }


    /**
     * @param resId
     * @param view
     * @name 加载本地圆图
     * @auhtor fuduo
     * @Data 2017-9-5 11:18
     */
    public void loadCircleImage(int resId, ImageView view) {
        Glide.with(App.getContext())
                .load(resId)
                .placeholder(R.drawable.bac_icon)
                .error(R.drawable.bac_icon)
                .bitmapTransform(new GlideCircleTransform(App.getContext()))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }

    /**
     * @param url
     * @param view
     * @name 加载网络圆图
     * @auhtor fuduo
     * @Data 2017-9-5 11:18
     */
    public void loadCircleImage(String url, ImageView view) {
        Glide.with(App.getContext())
                .load(url)
                .placeholder(R.drawable.bac_icon)
                .error(R.drawable.bac_icon)
                .bitmapTransform(new CenterCrop(App.getContext()),new GlideCircleTransform(App.getContext()))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }

    /**
     * @param activity
     * @param url
     * @param view
     * @name 加载网络圆图
     * @auhtor fuduo
     * @Data 2017-9-5 11:18
     */
    public void loadCircleImage(Activity activity, String url, ImageView view) {
        Glide.with(activity)
                .load(url)
                .placeholder(R.drawable.bac_icon)
                .error(R.drawable.bac_icon)
                .bitmapTransform(new GlideCircleTransform(activity))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }

    /**
     * @param mComtext
     * @param url
     * @param view
     * @name 加载网络圆图
     * @auhtor fuduo
     * @Data 2017-9-5 11:18
     */
    public void loadCircleImage(Context mComtext, String url, ImageView view) {
        Glide.with(mComtext)
                .load(url)
                .placeholder(R.drawable.bac_icon)
                .error(R.drawable.bac_icon)
                .bitmapTransform(new GlideCircleTransform(mComtext))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }

    /**
     * @param fragment
     * @param url
     * @param view
     * @name 加载网络圆图
     * @auhtor fuduo
     * @Data 2017-9-5 11:18
     */
    public void loadCircleImage(Fragment fragment, String url, ImageView view) {
        Glide.with(fragment)
                .load(url)
                .placeholder(R.drawable.bac_icon)
                .error(R.drawable.bac_icon)
                .bitmapTransform(new GlideCircleTransform(fragment.getContext()))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }

    /**
     * 加载自定义原型图
     * @param url
     * @param view
     */
    public void loadCircleImageToCust( String url, final ImageView view) {
        Glide.with(App.getContext())
                .load(url)
                .placeholder(R.drawable.bac_icon)
                .error(R.drawable.bac_icon)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        view.setImageDrawable(resource);
                    }
                });
    }


    /**
     * @param fragment
     * @param resId
     * @param view
     * @param dp
     * @name 加载网络带角度的图片
     * @auhtor fuduo
     * @Data 2017-9-5 11:18
     */
    public void loadCircleImageRole(Fragment fragment, int resId, ImageView view, int dp) {
        Glide.with(fragment)
                .load(resId)
                .placeholder(R.drawable.bac_icon)
                .error(R.drawable.bac_icon)
                .bitmapTransform(new GlideRoundTransform(fragment.getContext(), dp))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }

    /**
     * @param activity
     * @param resId
     * @param view
     * @param dp
     * @name 加载网络带角度的图片
     * @auhtor fuduo
     * @Data 2017-9-5 11:18
     */
    public void loadCircleImageRole(Activity activity, int resId, ImageView view, int dp) {
        Glide.with(activity)
                .load(resId)
                .placeholder(R.drawable.bac_icon)
                .error(R.drawable.bac_icon)
                .bitmapTransform(new GlideRoundTransform(activity, dp))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }

    /**
     * @param mContext
     * @param resId
     * @param view
     * @param dp
     * @name 加载网络带角度的图片
     * @auhtor fuduo
     * @Data 2017-9-5 11:18
     */
    public void loadCircleImageRole(Context mContext, int resId, ImageView view, int dp) {
        Glide.with(mContext)
                .load(resId)
                .placeholder(R.drawable.bac_icon)
                .error(R.drawable.bac_icon)
                .bitmapTransform(new GlideRoundTransform(mContext, dp))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }

    /**
     * @param resId
     * @param view
     * @param dp
     * @name 加载本地带角度的图片
     * @auhtor fuduo
     * @Data 2017-9-5 11:18
     */
    public void loadCircleImageRole( int resId, ImageView view, int dp) {
        Glide.with(App.getContext())
                .load(resId)
                .placeholder(R.drawable.bac_icon)
                .error(R.drawable.bac_icon)
                .bitmapTransform(new CenterCrop(App.getContext()),new GlideRoundTransform(App.getContext(), dp))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }

    public void loadCircleImageRole( String url, ImageView view, int dp) {
        Glide.with(App.getContext())
                .load(url)
                .placeholder(R.drawable.bac_icon)
                .error(R.drawable.bac_icon)
                .bitmapTransform(new CenterCrop(App.getContext()),new GlideRoundTransform(App.getContext(), dp))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }


    public void loadCircleImageRoleFxy( String url, ImageView view, int dp) {
        Glide.with(App.getContext())
                .load(url)
                .placeholder(R.drawable.bac_icon)
                .error(R.drawable.bac_icon)
                .bitmapTransform(new GlideRoundTransform(App.getContext(), dp))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }

    public void loadImageRoleFxy( String url, ImageView view) {
        Glide.with(App.getContext())
                .load(url)
                .placeholder(R.drawable.bac_icon)
                .error(R.drawable.bac_icon)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }

    public void loadImageRotFile( File f, ImageView view,int dp) {
        Glide.with(App.getContext())
                .load(f)
                .placeholder(R.drawable.bac_icon)
                .error(R.drawable.bac_icon)
                .bitmapTransform(new GlideRoundTransform(App.getContext(), dp))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }



    /**
     * @name 加载bitmap带角度的图片
     * @auhtor fuduo
     * @Data 2017-9-5 11:18
     */
    public void loadBitmapCircleImageRole( ImageView view, Bitmap bitmap,int dp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes=baos.toByteArray();
        Glide.with(App.getContext())
                .load(bytes)
                .centerCrop()
                .error(R.drawable.bac_icon)
                .bitmapTransform(new GlideRoundTransform(App.getContext(),dp))
                .into(view);
    }

    /**
     * @name 加载bitmap带角度的图片
     * @auhtor fuduo
     * @Data 2017-9-5 11:18
     */
    public void loadBitmapCircleImage( ImageView view, Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes=baos.toByteArray();
        Glide.with(App.getContext())
                .load(bytes)
                .centerCrop()
                .error(R.drawable.bac_icon)
                .bitmapTransform(new GlideCircleTransform(App.getContext()))
                .into(view);
    }

    /**
     * @name 加载bitmap的图片
     * @auhtor fuduo
     * @Data 2017-9-5 11:18
     */
    public void loadBitmapImage( ImageView view, Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes=baos.toByteArray();
        Glide.with(App.getContext())
                .load(bytes)
                .centerCrop()
                .error(R.drawable.bac_icon)
                .into(view);
    }




    /**
     * 获取图片缓存路径
     * @param imgUrl
     * @return
     */
    public String getImagePath(String imgUrl) {
        String path = null;
        FutureTarget<File> future = Glide.with(App.getContext())
                .load(imgUrl)
                .downloadOnly(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL);
        try {
            File cacheFile = future.get();
            path = cacheFile.getAbsolutePath();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return path;
    }


    /**
     * 保存文件到本地
     * @param oldPath
     */
    public void copyFile(String oldPath) {
        try {

            String  newPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+getPhotoFileName();
            Log.i("GlideDownLoadImg","oldPath:"+oldPath);
            Log.i("GlideDownLoadImg","newPath:"+newPath);
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ( (byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();

        }

    }




    /**
     * 获取图片名称
     * @return
     */
    public static  String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormatData = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat dateFormatFile = new SimpleDateFormat("HHmmssSSSS");
        return "IMG_"+dateFormatData.format(date)+"_"+dateFormatFile.format(date)+".jpg";
    }

}
