package cn.see.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * @日期：2018/7/6
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： bitmap的一些操作
 */

public class BitmapUtils {


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
        return bitmap;
    }



}
