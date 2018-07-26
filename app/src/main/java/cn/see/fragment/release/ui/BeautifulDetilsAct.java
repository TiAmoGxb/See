package cn.see.fragment.release.ui;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import cn.see.R;
import cn.see.base.BaseActivity;
import cn.see.fragment.release.presenter.BeautifulDetilsPresenter;
import cn.see.util.BitmapUtils;
import cn.see.util.GPUImageUtil;
import cn.see.util.ToastUtil;
import cn.see.util.constant.IntentConstant;
import cn.see.util.widet.putorefresh.ScreenUtils;
import jp.co.cyberagent.android.gpuimage.GPUImage;

/**
 * @日期：2018/7/4
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 美图页详细编辑
 */
public class BeautifulDetilsAct extends BaseActivity<BeautifulDetilsPresenter> {

    private static final String TAG = "BeautifulDetilsAct" ;
    private LinearLayoutManager layoutManager;
    //未经过滤镜的图片
    private Bitmap bitmapPath;
    //经过滤镜的图片
    private Bitmap bitmap;
    private GPUImage gpuImage;
    //是否开启预览
    private boolean isChange = true;
    //记录宽高 用来预览判断
    private int width;
    private int height;
    private ViewGroup.LayoutParams layoutParams;
    //记录滤镜位置
    private int gpuP;


    @BindView(R.id.title_tv_base)
    TextView titles;
    @BindView(R.id.title_tv_op_bg)
    TextView opTv;
    @BindView(R.id.tab_recy)
    RecyclerView tabRecy;
    @BindView(R.id.image_item)
    ImageView imageView;
    @BindView(R.id.edit_rela)
    RelativeLayout editRela;
    @BindView(R.id.seek_bar)
    SeekBar seekBar;
    @BindView(R.id.ld_tv)
    TextView ld_tv;
    @BindView(R.id.dbd_tv)
    TextView dbd_tv;
    @BindView(R.id.sd_tv)
    TextView sd_tv;
    @BindView(R.id.bhd_tv)
    TextView bhd_tv;

    @OnClick(R.id.back_rela)
    void baseView(){
        onBack();
    }


    @OnClick(R.id.edit_tv)
    void edit(){
        editRela.setVisibility(View.VISIBLE);
        tabRecy.setVisibility(View.GONE);
    }

    @OnClick(R.id.gpu_tv)
    void gpu_tv(){
        editRela.setVisibility(View.GONE);
        tabRecy.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.tab_tv)
    void tab_tv(){
       ToastUtil.showToast("标签");
    }

    @OnClick(R.id.title_tv_op_bg)
    void goBack(){
        try {
            File file = BitmapUtils.saveFile(bitmap, "see");
            Intent intent = new Intent();
            intent.putExtra(IntentConstant.IMAGE_BEAU_PATH,file.getAbsolutePath());
            intent.putExtra(IntentConstant.IMAGE_SEL_POSITION,getIntent().getIntExtra(IntentConstant.IMAGE_SEL_POSITION,-1));
            setResult(2,intent);
            onBack();
        } catch (IOException e) {
           Log.i("BitmapUtils","e:"+e.toString());
        }
    }


    /**
     * 该功能只为预览模式 实际上传还是原图
     */
    @OnClick(R.id.change_img)
    void changeImg(){
        if(isChange){
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = ScreenUtils.getScreenHeight(this)/2;
            imageView.setLayoutParams(layoutParams);
            isChange = false;
        }else{
            if(width>height){
                layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            }else{
                layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                layoutParams.height =ViewGroup.LayoutParams.WRAP_CONTENT;
            }
            imageView.setLayoutParams(layoutParams);
            isChange = true;
        }
    }


    @Override
    public void initView() {
        titles.setText("美图");
        opTv.setText("完成");
        opTv.setVisibility(View.VISIBLE);
        imageView.setMaxHeight(ScreenUtils.getScreenHeight(this)/2);
        imageView.setMaxWidth(ScreenUtils.getScreenWidth(this));
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        tabRecy.setLayoutManager(layoutManager);
        getP().addBeauData();
        tabRecy.setAdapter(getP().initBeauAdapter());
    }

    @Override
    public void initAfter() {

        layoutParams = imageView.getLayoutParams();
        String path = getIntent().getStringExtra(IntentConstant.IMAGE_BEAU_PATH);
        gpuP = getIntent().getIntExtra(IntentConstant.IMAGE_BEAU_GPU, -1);
        bitmapPath = BitmapUtils.compressBitmap(path, 1024);
        //gpuP>0代表该图片已被滤镜修改过
        if(gpuP>0){
            bitmap = GPUImageUtil.getGPUImageFromAssets(this,gpuImage,bitmapPath, gpuP);
        }else{
            bitmap = bitmapPath;
        }

        width = bitmap.getWidth();
        height = bitmap.getHeight();
        if(width>height){
            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            imageView.setLayoutParams(layoutParams);
        }
        imageView.setImageBitmap(bitmap);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_beautiful_detils;
    }

    @Override
    public BeautifulDetilsPresenter bindPresent() {
        return new BeautifulDetilsPresenter();
    }

    @Override
    public void setListener() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                GPUImageUtil.changeSaturation(progress);
                bitmap = GPUImageUtil.getGPUImageFromAssets(getApplicationContext(), gpuImage, bitmap, 17);
                imageView.setImageBitmap(bitmap);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

    }

    public void beauItem(int position) {
        //被选中的条目在可视范围
        int firstPosition = layoutManager.findFirstVisibleItemPosition();
        int lastPosition = layoutManager.findLastVisibleItemPosition();
        int left = tabRecy.getChildAt(position - firstPosition).getLeft();
        int right = tabRecy.getChildAt(lastPosition - position).getLeft();
        tabRecy.scrollBy((left - right) / 2, 0);
        if(position == 0){
            bitmap = bitmapPath;
        }else{
            bitmap = GPUImageUtil.getGPUImageFromAssets(this,gpuImage,bitmapPath, position);
        }
        imageView.setImageBitmap(bitmap);
    }

    /**
     * 进度值转换
     * @param percentage
     * @param start
     * @param end
     * @return
     */
    public float range(final int percentage, final float start, final float end) {
        return (end - start) * percentage / 100.0f + start;
    }
}
