package cn.see.fragment.release.presenter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;
import cn.see.R;
import cn.see.adapter.MultiItemTypeAdapter;
import cn.see.adapter.RecryCommonAdapter;
import cn.see.adapter.ViewHolder;
import cn.see.base.BaseModel;
import cn.see.fragment.release.ui.ReleasePreviewAct;
import cn.see.model.TxtModel;
import cn.see.util.ToastUtil;
import cn.see.util.UserUtils;
import cn.see.util.constant.HttpConstant;
import cn.see.util.glide.GlideDownLoadImage;
import cn.see.util.http.Api;
import cn.see.util.widet.CustomProgress;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * @日期：2018/7/11
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 发布预览对应P
 */

public class ReleasePreviewPresenter extends XPresent<ReleasePreviewAct> {

    public static final String TAG = "ReleasePreviewPresenter" ;
    public CustomProgress progress;

    /**
     * 根据类型判断UI
     * @param type
     */
    public void isTypeUi(String type){
        if(type.equals("topic")){
            getV().isTopicSetUi();
        }else{
            getV().isTextSetUi();
        }
    }

    public RecryCommonAdapter<String> initAdapter(final List<String> urls){
        RecryCommonAdapter<String> adapter = new RecryCommonAdapter<String>(getV(), R.layout.layout_release_review_item,urls) {
            @Override
            protected void convert(ViewHolder holder, String s, final int position) {
                ImageView imageView = holder.getView(R.id.item_img);
                if(urls.get(urls.size()-1).equals("")){
                    if(position==urls.size()-1){
                        holder.setVisible(R.id.close_rela,false);
                        holder.setVisible(R.id.position_tv,false);
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        imageView.setImageResource(R.drawable.tianjia);
                    }else{
                        holder.setVisible(R.id.close_rela,true);
                        holder.setVisible(R.id.position_tv,true);
                        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        GlideDownLoadImage.getInstance().loadImage(s,imageView);
                    }
                }else{
                    holder.setVisible(R.id.close_rela,true);
                    holder.setVisible(R.id.position_tv,true);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    GlideDownLoadImage.getInstance().loadImage(s,imageView);
                }

                holder.setOnClickListener(R.id.close_rela, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getV().removeItem(position);
                    }
                });
            }

            @Override
            public int getItemViewType(int position) {
                return super.getItemViewType(position);
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                getV().imgeItem(position);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        return adapter;
    }

    public void release(Map<String,String> map,List<String> list,String url) {
        progress = CustomProgress.show(getV());
        OkHttpClient client = new OkHttpClient();
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        if(map!=null){
            for (String key:map.keySet()) {
                builder.addFormDataPart(key,map.get(key));
            }
        }
        if(list!=null){
            for(int x = 0;x<list.size();x++){
                File f = new File(list.get(x));
                Log.i(TAG,"FILE:"+f.getAbsolutePath());
                builder.addFormDataPart("img"+x,f.getName(), okhttp3.RequestBody.create(MediaType.parse("image/jpg"), f));
            }
        }
        Request request = new Request.Builder()
                .url(url).post(builder.build()).build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG,"失败："+e.toString());
                progress.dismiss();
            }
            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                Log.i(TAG,"成功："+response.body().string());
                getV().releaseResponse("发布成功");
            }
        });
    }
}
