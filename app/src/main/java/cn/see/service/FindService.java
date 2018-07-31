package cn.see.service;

import cn.see.base.BaseModel;
import cn.see.model.AllTabModel;
import cn.see.model.AllTopicModel;
import cn.see.model.FindActModel;
import cn.see.model.FindTextcModel;
import cn.see.model.FindWorldTextModel;
import cn.see.model.MineTextModel;
import cn.see.model.TabBannerModel;
import cn.see.model.TabModel;
import cn.see.model.TextReviewModel;
import cn.see.model.TextTopModel;
import cn.see.model.TopicModel;
import cn.see.model.TxtModel;
import cn.see.model.UserOtherTextModel;
import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @日期：2018/6/14
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 发现接口逻辑
 */

public interface FindService {

    /**
     * 获取用户标签
     * @param user_id
     * @return
     */
    @FormUrlEncoded
    @POST("Tab/getUserTab")
    Flowable<TabModel> getUserTable(@Field("user_id") String user_id);


    /**
     * 基于标签的Banner
     * @param tab_id
     * @return
     */
    @FormUrlEncoded
    @POST("Banner/tabBanner.html")
    Flowable<TabBannerModel> getTabBanner(@Field("tab_id") String tab_id);

    /**
     * 基于标签话题
     * @param tab_id
     * @return
     */
    @FormUrlEncoded
    @POST("Topic/tabTopic.html")
    Flowable<TopicModel> getTabTopic(@Field("tab_id") String tab_id);

    /**
     * 基于标签文章
     * @param tab
     * @return
     */
    @FormUrlEncoded
    @POST("Text/recommend.html")
    Flowable<FindTextcModel> getTabText(@Field("tab") String tab,@Field("page") int page,@Field("user_id") String user_id);


    /**
     * 获取世界下文章(未用)
     * @param pageSize
     * @param page
     * @return
     */
    @FormUrlEncoded
    @POST("Text/homeLists.html")
    Flowable<FindTextcModel> getWorldText(@Field("pageSize") String pageSize, @Field("page") int page);


    /**
     * 获取顶部热门用户
     * @param pageSize
     * @param page
     * @return
     */
    @FormUrlEncoded
    @POST("User/hotUsers.html")
    Flowable<TxtModel> getWorldHotUser(@Field("pageSize") String pageSize, @Field("page") int page);


    /**
     * 获取文章详情顶部数据
     * @param user_id
     * @param text_id
     * @return
     */
    @FormUrlEncoded
    @POST("Text/info.html")
    Flowable<TextTopModel> getTextTopData(@Field("user_id") String user_id, @Field("text_id") String text_id);


    /**
     * 获取文章详情评论列表
     */
    @FormUrlEncoded
    @POST("Text/getReviewList.html")
    Flowable<TextReviewModel> getTextReviewList(@Field("user_id") String user_id, @Field("text_id") String text_id,@Field("page") int page,@Field("pageSize") String pageSize );

    /**
     * 获取活动列表
     * @param user_id
     * @return
     */
    @FormUrlEncoded
    @POST("Activity/msglists.html")
    Flowable<FindActModel> getFindAct(@Field("user_id") String user_id, @Field("page") int page, @Field("pageSize") String pageSize );


    /**
     * 文章详情 获取其他两篇相关文章
     * @param user_id
     * @param text_it
     * @param pageSize
     * @param page
     * @return
     */
    @FormUrlEncoded
    @POST("Text/ntextList.html")
    Flowable<UserOtherTextModel> getWithTwoTxt(@Field("user_id") String user_id, @Field("text_id") String text_it, @Field("pageSize") String pageSize , @Field("page") int page);


    /**
     * 获取热门话题
     * @param pageSize
     * @param page
     * @return
     */
    @FormUrlEncoded
    @POST("Topic/hotList.html")
    Flowable<AllTopicModel> getTopicHot(@Field("pageSize") String pageSize , @Field("page") int page);
    /**
     * 获取最新话题
     * @param pageSize
     * @param page
     * @return
     */
    @FormUrlEncoded
    @POST("Topic/newList.html")
    Flowable<AllTopicModel> getTopicNews(  @Field("pageSize") String pageSize , @Field("page") int page);


    /**
     * 获取全部标签
     * @param user_id
     * @return
     */
    @FormUrlEncoded
    @POST("Tab/tLists.html")
    Flowable<AllTabModel> getAllTab(@Field("user_id") String user_id );


    /**
     * 保存标签
     * @param user_id
     * @param tab_id_arr
     * @return
     */
    @FormUrlEncoded
    @POST("User/save_user_tab.html")
    Flowable<BaseModel> saveTab(@Field("user_id") String user_id , @Field("tab_id_arr") String tab_id_arr);

    /**
     * 活动参与人
     * @param activity_id
     * @param page
     * @param pageSize
     * @return
     */

    @FormUrlEncoded
    @POST("Activity/apply_users.html")
    Flowable<MineTextModel> actApply(@Field("activity_id") String activity_id , @Field("page") int page,@Field("pageSize") String pageSize );




}
