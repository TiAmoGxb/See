package cn.see.service;
import cn.see.model.FriendsNewsModel;
import cn.see.model.MineTextModel;
import cn.see.model.TxtModel;
import cn.see.model.UserInfoModel;
import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @日期：2018/6/13
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 首页接口
 */

public interface HomeService {

    /**
     * 获取品质生活文章列表
     * @return
     */
    @FormUrlEncoded
    @POST("Text/qualityTextLists.html")
    Flowable<TxtModel> getQuality(@Field("user_id") String user_id,@Field("page") int page);


    /**
     * 获取好友推荐
     * @param user_id
     * @return
     */
    @FormUrlEncoded
    @POST("User/recommendUsers.html")
    Flowable<TxtModel> getFriends(@Field("user_id") String user_id,@Field("page") int page,@Field("pageSize") String pageSize);

    /**
     * 好友动态
     */
    @FormUrlEncoded
    @POST("User/attentionNews.html")
    Flowable<FriendsNewsModel> getFriendsNews(@Field("user_id") String user_id, @Field("pageSize") int pageSize, @Field("likeSize") String likeSize, @Field("reviewSize") String reviewSize, @Field("attentionSize") String attentionSize, @Field("fansSize") String fansSize);


    /**
     * 我关注的话题
     * @param user_id
     * @return
     */
    @FormUrlEncoded
    @POST("User/applyTopicList.html")
    Flowable<MineTextModel> getAttTopic(@Field("user_id") String user_id,@Field("page") int page,@Field("pageSize") String pageSize);


    /**
     * 获取关注的文章列表
     * @param user_id
     * @return
     */
    @FormUrlEncoded
    @POST("Text/getAttentionTextList.html")
    Flowable<TxtModel> getAttTxtList(@Field("user_id") String user_id,@Field("page") int page);


    /**
     * 附近好友
     * @param user_id
     * @param page
     * @param pageSize
     * @return
     */
    @FormUrlEncoded
    @POST("User/nearbyUsers.html")
    Flowable<TxtModel> getNearbyUser(@Field("user_id") String user_id,@Field("page") int page,@Field("pageSize") String pageSize);


}
