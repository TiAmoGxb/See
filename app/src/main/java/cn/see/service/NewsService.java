package cn.see.service;

import cn.see.model.MineTextModel;
import cn.see.model.NewsLikeReviewModel;
import cn.see.model.SystemNoticModel;
import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @日期：2018/7/2
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明：
 */

public interface NewsService {

    /**
     * 点赞列表
     * @param uid
     * @param page
     * @param pageSize
     * @return
     */
    @FormUrlEncoded
    @POST("Text/liketList.html")
    Flowable<NewsLikeReviewModel> getNewsLikeList(@Field("user_id") String uid, @Field("page") int page, @Field("pageSize") String pageSize) ;


    /**
     * 评论列表
     * @param uid
     * @param page
     * @param pageSize
     * @return
     */
    @FormUrlEncoded
    @POST("Text/reviewtList.html")
    Flowable<NewsLikeReviewModel> getNewsreviewList(@Field("user_id") String uid, @Field("page") int page, @Field("pageSize") String pageSize) ;


    /**
     * 获取官方通知
     * @param uid
     * @param page
     * @param pageSize
     * @return
     */
    @FormUrlEncoded
    @POST("User/tidings_list.html")
    Flowable<SystemNoticModel> getNotice(@Field("user_id") String uid, @Field("page") int page, @Field("pageSize") String pageSize) ;



}
