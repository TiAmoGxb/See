package cn.see.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.see.util.glide.GlideDownLoadImage;

/**
 * @日期：2018/7/6
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： bitmap的一些操作
 */

public class BitmapUtils {


    private static final String TAG = "BitmapUtils";

    /**
     * 根据本地图片路径生成BP
     * 节省内存方式
     * @param imagePath
     * @return
     */
    public static Bitmap getSDCardImg(String imagePath) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        //获取资源图片

        return BitmapFactory.decodeFile(imagePath, opt);
    }


    /**
     * 采样率压缩
     * @param path
     * @return
     */
    public static Bitmap compressSampling(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        while(true){
            int size = bitmap.getByteCount() / 1024 / 1024;
            Log.i("BitmapUtils","size:"+size);
            if(size>1){
//                bitmap = BitmapFactory.decodeByteArray(path, options);
            }else{
                break;
            }
        }
        return bitmap;
    }

    /**
     * 根据分辨率压缩
     *
     * @param srcPath 图片路径
     * @param ImageSize 图片大小 单位kb
     * @return
     */
    public static Bitmap compressBitmap(String srcPath, int ImageSize) {
        int subtract;

        Bitmap bitmap = compressByResolution(srcPath, 1024, 720); //分辨率压缩
        Log.i(TAG, "图片处理开始.."+ bitmap.getByteCount() / 1024 + "KB");
        if (bitmap == null) {
            Log.i(TAG, "bitmap 为空");
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        Log.i(TAG, "图片分辨率压缩后：" + baos.toByteArray().length / 1024 + "KB");

        while (baos.toByteArray().length > ImageSize * 1024) { //循环判断如果压缩后图片是否大于ImageSize kb,大于继续压缩
            subtract = setSubstractSize(baos.toByteArray().length / 1024);
            baos.reset();//重置baos即清空baos
            options -= subtract;//每次都减少10
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            Log.i(TAG, "图片压缩后：" + baos.toByteArray().length / 1024 + "KB");
        }
        byte[] bytes = baos.toByteArray();
        Log.i(TAG, "字节数组大小!" + bytes.length / 1024 + "KB");
        bitmap =BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        Log.i(TAG, "图片处理完成!" + bitmap.getByteCount() / 1024 + "KB");
        return bitmap;
    }
    /**
     * 根据分辨率压缩图片比例
     *
     * @param imgPath
     * @param w
     * @param h
     * @return
     */
    private static Bitmap compressByResolution(String imgPath, int w, int h) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imgPath, opts);

        int width = opts.outWidth;
        int height = opts.outHeight;
        int widthScale = width / w;
        int heightScale = height / h;

        int scale;
        if (widthScale < heightScale) { //保留压缩比例小的
            scale = widthScale;
        } else {
            scale = heightScale;
        }

        if (scale < 1) {
            scale = 1;
        }
        Log.i(TAG,"图片分辨率压缩比例：" + scale);

        opts.inSampleSize = scale;

        opts.inJustDecodeBounds = false;

        Bitmap bitmap = BitmapFactory.decodeFile(imgPath, opts);

        return bitmap;
    }

    /**
     * 根据图片的大小设置压缩的比例，提高速度
     *
     * @param imageMB
     * @return
     */
    private static int setSubstractSize(int imageMB) {

        if (imageMB > 1000) {
            return 60;
        } else if (imageMB > 750) {
            return 40;
        } else if (imageMB > 500) {
            return 20;
        } else {
            return 10;
        }

    }


    /**
     * Bitamp转换File
     **/

    public static File saveFile(Bitmap bm, String path) throws IOException {
        String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+path;
        Log.i(TAG,"BitmapUtils:"+absolutePath);
        File dirFile = new File(absolutePath);
        if(!dirFile.exists()){
            dirFile.mkdir();
        }
        File myCaptureFile = new File(absolutePath , GlideDownLoadImage.getPhotoFileName());
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        bos.flush();
        bos.close();
        return myCaptureFile;
    }

}
