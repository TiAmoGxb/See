package cn.see.service;

import cn.see.model.AllTopicModel;
import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by sks on 2018/7/12.
 */

public interface ReleaseService {

    /**
     * 获取话题
     * @param pageSize
     * @param page
     * @return
     */
    @FormUrlEncoded
    @POST("Topic/hotList.html")
    Flowable<AllTopicModel> getTopicHot(@Field("pageSize") String pageSize , @Field("page") int page);
}
