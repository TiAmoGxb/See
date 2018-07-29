package cn.see.presenter.findp;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;
import cn.droidlover.xdroidmvp.router.Router;
import cn.see.R;
import cn.see.adapter.MultiItemTypeAdapter;
import cn.see.adapter.RecryCommonAdapter;
import cn.see.adapter.ViewHolder;
import cn.see.fragment.fragmentview.findview.FindWorldFragment;
import cn.see.fragment.fragmentview.findview.HotUserAct;
import cn.see.fragment.fragmentview.mineview.OtherMainAct;
import cn.see.model.FindWorldTextModel;
import cn.see.model.TxtModel;
import cn.see.util.ToastUtil;
import cn.see.util.UserUtils;
import cn.see.util.constant.HttpConstant;
import cn.see.util.constant.IntentConstant;
import cn.see.util.glide.GlideDownLoadImage;
import cn.see.util.http.Api;
import cn.see.util.http.OkHttpUtils;
import cn.see.util.widet.CircleImageView;
import cn.see.util.widet.CustomProgress;

/**
 * @日期：2018/6/20
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明：
 */

public class WorldPresenter extends XPresent<FindWorldFragment> {

    private List<FindWorldTextModel> results = new ArrayList<>();
    private int[] types = new int[]{0,1,2,3,1,2,0,1,2,3,1,2};
    public CustomProgress progress;

    /**
     * 获取文章
     * @param page
     */
    public void getWroldText(final int page){

        OkHttpUtils.ResultCallback<String> loadNewsCallback = new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                progress.dismiss();
                Log.i("FindWorldFragment","result："+response.toString());
                try {
                    JSONObject result = new JSONObject(response);
                    JSONObject jsonObject=result.getJSONObject("result");
                    String total = jsonObject.getString("total");
                    if(page>Integer.parseInt(total)){
                        ToastUtil.showToast("暂无更多数据");
                        return;
                    }
                    JSONArray jsonArray=jsonObject.getJSONArray("result");
                    Gson gson = new Gson();
                    //解析type外层为数组
                    Type type = new TypeToken<ArrayList<FindWorldTextModel.MoreItems>>() {
                    }.getType();
                    //每次获取4组 12条数据
                    results.clear();
                    for (int x = 0 ; x<12; x++){
                        ArrayList<FindWorldTextModel.MoreItems> marrayList = gson.fromJson(jsonArray.getJSONArray(x).toString(), type);
                        results.add(new FindWorldTextModel(marrayList.get(0),marrayList.get(1),marrayList.get(2),types[x]));
                    }
                    //回调V层
                    getV().getWroldTextResponse(results,page);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Exception e) {
                progress.dismiss();
                ToastUtil.showToast(HttpConstant.NET_ERROR_MSG);
            }
        };
        List<OkHttpUtils.Param> paramList = new ArrayList<>();
        paramList.add(new OkHttpUtils.Param("page",page+""));
        paramList.add(new OkHttpUtils.Param("pageSize","36"));
        Log.i("FindWorldFragment","page:"+page);
        OkHttpUtils.post(Api.BASE_PATH+"Text/homeLists.html",loadNewsCallback,paramList);
    }


    /**
     * 获取顶部热门用户
     * @param page
     * @param pageSize
     */
    public void getUserHot(final int page,String pageSize){
        progress = CustomProgress.show(getV().getActivity());
        Api.findService().getWorldHotUser(pageSize,page)
                .compose(XApi.<TxtModel>getApiTransformer())
                .compose(XApi.<TxtModel>getScheduler())
                .compose(getV().<TxtModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<TxtModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        Log.i("FindChildFragment","error:"+error.getMessage());
                    }

                    @Override
                    public void onNext(TxtModel txtModel) {
                        if(!txtModel.isError()){
                            if(page>txtModel.getResult().getTotalPage()){
                                ToastUtil.showToast("暂无更多热门用户");
                                return;
                            }
                            getV().hotUserResponse(txtModel.getResult().getResult(),page);
                        }else{
                            ToastUtil.showToast(txtModel.getErrorMsg());
                        }
                    }
                });
    }


    /**
     * 热门用户适配器
     * @param results
     * @return
     */
    public RecryCommonAdapter<TxtModel.TxtResult.Result> initTopAdapter(final List<TxtModel.TxtResult.Result> results){
         RecryCommonAdapter<TxtModel.TxtResult.Result> adapter = new RecryCommonAdapter<TxtModel.TxtResult.Result>(getV().getActivity(), R.layout.layout_find_world_top_item,results) {
             @Override
             protected void convert(ViewHolder holder, TxtModel.TxtResult.Result result, int position) {
                 CircleImageView userView = (CircleImageView) holder.getView(R.id.user_hot_img);
                 CircleImageView bac_image = (CircleImageView) holder.getView(R.id.bac_img);
                 if(position == results.size()-1){
                     userView.setImageResource(R.mipmap.options);
                     bac_image.setBorderColor(R.color.black);
                     bac_image.setBorderWidth(6);
                 }else{
                     holder.setText(R.id.user_hot_name,result.getNickname());
                     GlideDownLoadImage.getInstance().loadCircleImageToCust(result.getHead_img_url(),userView);
                 }
             }
         };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if(UserUtils.getLogin(getV().getActivity())){
                    if(position == results.size()-1){
                        getV().openActivity(HotUserAct.class);
                    }else{
                        Log.i("WorldPresenter","id:"+results.get(position).getUser_id());
                        Router.newIntent(getV().getActivity())
                                .putString(IntentConstant.OTHER_USER_ID,results.get(position).getId())
                                .to(OtherMainAct.class)
                                .launch();
                    }

                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

         return adapter;
    }



}
