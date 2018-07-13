package cn.see.fragment.release.ui;


import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;
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
import cn.see.fragment.release.presenter.BeautifulPicturePresenter;
import cn.see.util.BitmapUtils;
import cn.see.util.GPUImageUtil;
import cn.see.util.ToastUtil;
import cn.see.util.constant.IntentConstant;
import cn.see.util.constant.PreferenceConstant;
import cn.see.util.version.PreferenceUtils;
import cn.see.util.widet.CustomProgress;
import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;

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
    //本地图片路径
    private ArrayList<String> paths;
    //设置数据的集合
    private ArrayList<Bitmap> bitmapList ;
    //临时存放压缩过的图片集合
    private ArrayList<Bitmap> OriginalList;
    //适配器
    private RecryCommonAdapter<Bitmap> adapter;
    //全局 GPUImage 防止多次刷新滤镜 重复创建对象
    private GPUImage gpuImage;
    //记录当前选择的滤镜效果
    private int gpuPosition = -1;
    //区分是话题还是图片
    private String type;
    private Intent intent;
    private Bitmap bitmap;
    private String reviewType;


    @BindView(R.id.title_tv_base)
    TextView titles;
    @BindView(R.id.title_tv_op_bg)
    TextView opTv;
    @BindView(R.id.img_recy)
    RecyclerView mRecyclerView;
    @BindView(R.id.tab_recy)
    RecyclerView tabRecy;


    @OnClick(R.id.back_rela)
    void baseView(){
        onBack();
    }

    @OnClick(R.id.title_tv_op_bg)
    void goRelese(){
        complete();
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
                    .putInt(IntentConstant.IMAGE_SEL_POSITION,firstPosition)
                    .requestCode(2)
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
        OriginalList = new ArrayList<>();
        tabRecy.setAdapter(getP().initBeauAdapter());
        paths = (ArrayList<String>) getIntent().getSerializableExtra(IntentConstant.RELEASE_PATHS);
        type = getIntent().getStringExtra(IntentConstant.RELEASE_TYPE);
        reviewType = getIntent().getStringExtra("type");

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (String s: paths) {
                    OriginalList.add( BitmapUtils.compressBitmap(s,1024));
                    Log.i("BitmapUtils","----------------------------------");
                }
                for (Bitmap b:OriginalList){
                    bitmapList.add(b);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        addInsertImg();
                        adapter = getP().initAdapter(bitmapList);
                        mRecyclerView.setAdapter(adapter);
                    }
                });
            }
        }).start();

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
                    .putInt(IntentConstant.IMAGE_SEL_POSITION,position)
                    .requestCode(2)
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
        //点击原图
        if(position==0){
            for (int x = 0;x<OriginalList.size();x++){
                bitmapList.set(x,OriginalList.get(x));
            }
            adapter.notifyDataSetChanged();
            progress.dismiss();
        }else{
            new DownTask().execute(GPUImageUtil.getFilter(position));
        }
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
            Log.e(TAG, e.toString());
        }
    }


    public class DownTask extends AsyncTask<GPUImageFilter, Void, ArrayList<Bitmap>> {
        @Override
        protected ArrayList<Bitmap> doInBackground(GPUImageFilter... gpuImageFilters) {
            bitmapList.clear();
            GPUImageFilter filter = (GPUImageFilter) gpuImageFilters[0];
            gpuImage = new GPUImage(BeautifulPictureAct.this);
            for (int i = 0; i < OriginalList.size(); i++) {
                bitmap =OriginalList.get(i);
                gpuImage.setImage(bitmap);
                gpuImage.setFilter(filter);
                bitmap = gpuImage.getBitmapWithFilterApplied();
                bitmapList.add(bitmap);
            }
            addInsertImg();
            return bitmapList;
        }

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
        //再次添加图片回传
        if(requestCode == 1){
            if(data!=null){
                final ArrayList<String> pathList = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                bitmapList.remove(bitmapList.size()-1);
                for (String s:pathList){
                    paths.add(s);
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (String s: pathList) {
                            bitmap = BitmapUtils.compressBitmap(s, 1024);
                            OriginalList.add( bitmap);
                            bitmapList.add( bitmap);
                            Log.i("BitmapUtils","----------------------------------");
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                addInsertImg();
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                }).start();
            }
            //编辑图片之后回传
        }else if(requestCode == 2){
            if(data!=null){
                String sdPath = data.getStringExtra(IntentConstant.IMAGE_BEAU_PATH);
                int position = data.getIntExtra(IntentConstant.IMAGE_SEL_POSITION, -1);
                bitmap = BitmapUtils.getSDCardImg(sdPath);
                bitmapList.set(position,bitmap);
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void complete(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                paths.clear();
                bitmapList.remove(bitmapList.size()-1);
                for (Bitmap b:bitmapList){
                    try {
                        File file = BitmapUtils.saveFile(b, "see");
                        paths.add(file.getAbsolutePath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(reviewType!=null&&reviewType.equals("review")){
                            StringBuffer path = new StringBuffer();
                            for (String p:paths){
                                path.append(p+",");
                            }
                            PreferenceUtils.setString(BeautifulPictureAct.this, PreferenceConstant.IMGS_PATHS,path.toString());
                        }else{
                            Router.newIntent(BeautifulPictureAct.this)
                                    .to(ReleasePreviewAct.class)
                                    .putStringArrayList(IntentConstant.RELEASE_PATHS,paths)
                                    .putString(IntentConstant.RELEASE_TYPE,type)
                                    .launch();
                        }
                        onBack();
                    }
                });
            }
        }).start();
    }
}
