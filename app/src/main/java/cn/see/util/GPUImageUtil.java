package cn.see.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageAddBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageAlphaBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageBilateralFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageBoxBlurFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageBrightnessFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageBulgeDistortionFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageCGAColorspaceFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageChromaKeyBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageColorBalanceFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageColorBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageColorDodgeBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageColorInvertFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageColorMatrixFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageContrastFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageDifferenceBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageDilationFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageDirectionalSobelEdgeDetectionFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageDissolveBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageEmbossFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageExclusionBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageExposureFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFalseColorFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageGammaFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageGaussianBlurFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageGlassSphereFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageGrayscaleFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHazeFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHighlightShadowFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHueBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHueFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageKuwaharaFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageLaplacianFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageLevelsFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageLightenBlendFilter;

import jp.co.cyberagent.android.gpuimage.GPUImageMonochromeFilter;

import jp.co.cyberagent.android.gpuimage.GPUImageNonMaximumSuppressionFilter;

import jp.co.cyberagent.android.gpuimage.GPUImageOpacityFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageOverlayBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImagePixelationFilter;
import jp.co.cyberagent.android.gpuimage.GPUImagePosterizeFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageRGBDilationFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSaturationFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSketchFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageToneCurveFilter;

/**
 * Created by it_zjyang on 2016/8/10.
 */
public class GPUImageUtil {

    private static GPUImageFilter filter;
    private static int count = 50;

    /**
     * 获取过滤器
     * @param GPUFlag
     * @return 滤镜类型
     */
    public static GPUImageFilter getFilter(int GPUFlag){
        switch (GPUFlag){
            case 0:
                filter = new GPUImageGrayscaleFilter();
                break;
            case 1:
                filter = new GPUImageAddBlendFilter();
                break;
            case 2:
                filter = new GPUImageAlphaBlendFilter();
                break;
            case 3:
                filter = new GPUImageBilateralFilter();
                break;
            case 4:
                filter = new GPUImageBoxBlurFilter();
                break;
            case 5:
                filter = new GPUImageBrightnessFilter();
                break;
            case 6:
                filter = new GPUImageBulgeDistortionFilter();
                break;
            case 7:
                filter = new GPUImageCGAColorspaceFilter();
                break;
            case 8:
                filter = new GPUImageChromaKeyBlendFilter();
                break;
            case 9:
                filter = new GPUImageColorBalanceFilter();
                break;
            case 10:
                filter = new GPUImageColorBlendFilter();
                break;
            case 11:
                filter = new GPUImageEmbossFilter();
                break;
            case 12:
                filter = new GPUImageColorDodgeBlendFilter();
                break;
            case 13:
                filter = new GPUImageColorInvertFilter();
                break;
            case 14:
                filter = new GPUImageColorMatrixFilter();
                break;
            case 15:
                filter = new GPUImageBrightnessFilter(0f);//亮度调节
                break;
            case 16:
                filter = new GPUImageContrastFilter(count);//对比度调节
                break;
            case 17:
                filter = new GPUImageSaturationFilter(count);//饱和度调节
                break;
            case 18:
                filter = new GPUImagePosterizeFilter(count);//色调调节
                break;

        }
        return filter;
    }



    /**
     * @param context 上下文
     * @param FilterFlag 滤镜类型
     * @return
     */
    public static Bitmap getGPUImageFromAssets(Context context,GPUImage  gpuImage,Bitmap bitmap,int FilterFlag){

        // 使用GPUImage处理图像
        gpuImage = new GPUImage(context);
        gpuImage.setImage(bitmap);
        gpuImage.setFilter(getFilter(FilterFlag));
        bitmap = gpuImage.getBitmapWithFilterApplied();
        return bitmap;
    }

    /**
     * 从网络中获取图片
     * @param url
     * @return
     */
    public static Bitmap getGPUImageFromURL(String url) {
        Bitmap bitmap = null;
        try {
            URL iconUrl = new URL(url);
            URLConnection conn = iconUrl.openConnection();
            HttpURLConnection http = (HttpURLConnection) conn;
            int length = http.getContentLength();
            conn.connect();
            // 获得图像的字符流
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is, length);
            bitmap = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();// 关闭流
            } catch (Exception e) {
                e.printStackTrace();
            }
        return bitmap;
    }


    /**
     * 调整饱和度等指数
     * @param curCount
     */
    public static void changeSaturation(int curCount){
        GPUImageUtil.count = curCount;
    }



}
