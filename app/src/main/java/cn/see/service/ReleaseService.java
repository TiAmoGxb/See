package cn.see.service;

import com.squareup.okhttp.RequestBody;

import java.util.Map;

import cn.see.base.BaseModel;
import cn.see.model.AllTopicModel;
import cn.see.model.ReleaseTopicModel;
import cn.see.model.TxtModel;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Created by sks on 2018/7/12.
 */

public interface ReleaseService {

    /**
     * 获取话题
     * @return
     */
    @POST("Topic/textSearchRecommend.html")
    Flowable<ReleaseTopicModel> getTopicHot();


    /**
     * 获取标签
     * @param page
     * @param pageSize
     * @return
     */
    @FormUrlEncoded
    @POST("Tab/gettRandTab.html")
    Flowable<TxtModel> getReleaseTab(@Field("page") int page, @Field("pageSize") String pageSize);


    /**
     * 发布
     * @param msg 内容
     * @param user_id 用户ID
     * @param tab 标签ID
     * @param type 类型
     * @param type_id 话题ID
     * @param show 隐私设置
     * @param parms 图片
     * @return
     */
    Observable<BaseModel> uploadRelease(@Part("msg") String msg, @Part("user_id") String user_id, @Part("tab") String tab
                                        , @Part("type") String type, @Part("type_id") String type_id, @Part("show") String show
                                        , @PartMap Map<String,RequestBody> parms);
}
