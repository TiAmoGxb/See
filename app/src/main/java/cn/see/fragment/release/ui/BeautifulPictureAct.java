package cn.see.fragment.release.ui;


import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.router.Router;
import cn.see.R;
import cn.see.adapter.RecryCommonAdapter;
import cn.see.base.BaseActivity;
import cn.see.fragment.fragmentview.homeview.SelectMyTableAct;
import cn.see.fragment.release.presenter.BeautifulPicturePresenter;
import cn.see.main.MainParentAct;
import cn.see.util.BitmapUtils;
import cn.see.util.GPUImageUtil;
import cn.see.util.ToastUtil;
import cn.see.util.constant.IntentConstant;
import cn.see.util.widet.CustomProgress;
import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * @日期：2018/7/4
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 美图页
 */
public class BeautifulPictureAct extends BaseActivity<BeautifulPicturePresenter> {

    public static final String TAG = "BeautifulPictureAct";
    private CustomProgress progress;
    private LinearLayoutManager manager;
    private LinearLayoutManager layoutManager;
    private ArrayList<String> paths;
    private ArrayList<Bitmap> bitmapList ;
    private RecryCommonAdapter<Bitmap> adapter;
    //全局 GPUImage 防止多次刷新滤镜 重复创建对象
    private GPUImage gpuImage;
    //记录当前选择的滤镜效果
    private int gpuPosition = -1;
    //区分是话题还是图片
    private String type;



    @BindView(R.id.title_tv_base)
    TextView titles;
    @BindView(R.id.title_tv_op_bg)
    TextView opTv;
    @BindView(R.id.img_recy)
    RecyclerView mRecyclerView;
    @BindView(R.id.tab_recy)
    RecyclerView tabRecy;
    private Intent intent;
    private Bitmap bitmap;

    @OnClick(R.id.back_rela)
    void baseView(){
        onBack();
    }

    @OnClick(R.id.title_tv_op_bg)
    void goRelese(){
        openActivity(ReleasePreviewAct.class);
    }


    /**
     * 编辑
     */
    @OnClick(R.id.edit_tv)
    void edit(){
        int firstPosition = manager.findFirstVisibleItemPosition();
        if(firstPosition != bitmapList.size()-1){
            Router.newIntent(this)
                    .to(BeautifulDetilsAct.class)
                    .putString(IntentConstant.IMAGE_BEAU_PATH,paths.get(firstPosition))
                    .putInt(IntentConstant.IMAGE_BEAU_GPU,gpuPosition)
                    .launch();
        }

    }

    @Override
    public void initView() {
        titles.setText("美图");
        opTv.setText("下一步");
        opTv.setVisibility(View.VISIBLE);
        manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(manager);
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(mRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        tabRecy.setLayoutManager(layoutManager);
    }

    @Override
    public void initAfter() {
        getP().addBeauData();
        bitmapList = new ArrayList<>();
        tabRecy.setAdapter(getP().initBeauAdapter());
        paths = (ArrayList<String>) getIntent().getSerializableExtra(IntentConstant.RELEASE_PATHS);
        type = getIntent().getStringExtra(IntentConstant.RELEASE_TYPE);


 /*       Bitmap bit = BitmapUtils.getSDCardImg(paths.get(0));

        Log.i(TAG, "压缩前图片的大小" + (bit.getByteCount() / 1024 / 1024)
                + "M宽度为" + bit.getWidth() + "高度为" + bit.getHeight());

        Bitmap bm = BitmapUtils.compressSampling(paths.get(0));

        Log.i(TAG, "压缩后图片的大小" + (bm.getByteCount() / 1024 / 1024)
                + "M宽度为" + bm.getWidth() + "高度为" + bm.getHeight());

        for (int x = 0;x<paths.size();x++){
            Bitmap sdCardImg = BitmapUtils.getSDCardImg(paths.get(x));
            int size = sdCardImg.getByteCount() / 1024 / 1024;
            if(size>1){
                bitmap = sdCardImg;
            }else{
                bitmap =  BitmapUtils.compressSampling(paths.get(x));
            }
            bitmapList.add( bitmap);
        }*/

        for (String s: paths) {
            bitmapList.add( BitmapUtils.compressSampling(s));
        }
        addInsertImg();
        Log.i(TAG,"bitmapList:"+bitmapList.size());
        adapter = getP().initAdapter(bitmapList);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_beautiful_picture;
    }

    @Override
    public BeautifulPicturePresenter bindPresent() {
        return new BeautifulPicturePresenter();
    }

    @Override
    public void setListener() {
    }


    /**
     * 图片点击
     * @param position
     */
    public void imgageItem(int position){
        if(position != bitmapList.size()-1){
            Router.newIntent(this)
                    .to(BeautifulDetilsAct.class)
                    .putString(IntentConstant.IMAGE_BEAU_PATH,paths.get(position))
                    .putInt(IntentConstant.IMAGE_BEAU_GPU,gpuPosition)
                    .launch();
        }else{
            if(bitmapList.size()<=9){
                //添加图片
                intent = new Intent(this, MultiImageSelectorActivity.class);
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 9-(bitmapList.size())+1);
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
                startActivityForResult(intent,1);
            }else{
                ToastUtil.showToast("最多只可以选择9张图片");
            }

        }

    }

    /**
     * 滤镜点击
     * @param position
     */
    public void beauItem(final int position){
        //记录被选中的滤镜
        gpuPosition = position;
        //被选中的滤镜条目在可视范围
        int firstPosition = layoutManager.findFirstVisibleItemPosition();
        int lastPosition = layoutManager.findLastVisibleItemPosition();
        int left = tabRecy.getChildAt(position - firstPosition).getLeft();
        int right = tabRecy.getChildAt(lastPosition - position).getLeft();
        tabRecy.scrollBy((left - right)/2,0);
        progress = CustomProgress.show(this);
        new DownTask().execute(GPUImageUtil.getFilter(position));

    }


    /**
     * 添加加号图片
     */
    public void addInsertImg(){
        AssetManager as = getAssets();
        InputStream is = null;
        Bitmap bitmap = null;
        try {
            is = as.open("tianjia.png");
            bitmap = BitmapFactory.decodeStream(is);
            bitmapList.add(bitmap);
            is.close();
        } catch (IOException e) {
            Log.e("GPUImage", e.toString());
        }
    }


    public class DownTask extends AsyncTask<GPUImageFilter, Void, ArrayList<Bitmap>> {
        @Override
        protected ArrayList<Bitmap> doInBackground(GPUImageFilter... gpuImageFilters) {
            bitmapList.clear();
            GPUImageFilter filter = (GPUImageFilter) gpuImageFilters[0];
            gpuImage = new GPUImage(BeautifulPictureAct.this);
            for (int i = 0; i < paths.size(); i++) {
                bitmap =BitmapUtils.getSDCardImg(paths.get(i));
                gpuImage.setImage(bitmap);
                gpuImage.setFilter(filter);
                bitmap = gpuImage.getBitmapWithFilterApplied();
                bitmapList.add(bitmap);
            }
            addInsertImg();
            return bitmapList;
        }

        //主要是更新UI
        @Override
        protected void onPostExecute(ArrayList<Bitmap> result) {
            super.onPostExecute(result);
            adapter.notifyDataSetChanged();
            progress.dismiss();
        }
    }


    /**
     * 再次添加图片回传
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            ArrayList<String> pathList = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
            bitmapList.remove(bitmapList.size()-1);
            for (String s:pathList){
                bitmapList.add(BitmapUtils.getSDCardImg(s));
            }
            addInsertImg();
            adapter.notifyDataSetChanged();
        }
    }
}
