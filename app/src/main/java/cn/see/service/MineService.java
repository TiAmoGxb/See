package cn.see.service;

import cn.see.base.BaseModel;
import cn.see.model.AllTopicModel;
import cn.see.model.LoginModel;
import cn.see.model.MineAttModel;
import cn.see.model.MineTextModel;
import cn.see.model.QrModel;
import cn.see.model.TopiDesitalModel;
import cn.see.model.UserInfoModel;
import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @日期：2018/6/13
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 我的接口逻辑
 */

public interface MineService {

    /**
     * 登录
     * @param page
     * @param password
     * @param uniquely
     * @return
     */
    @FormUrlEncoded
    @POST("User/Login.html")
    Flowable<LoginModel> goLogin(@Field("account") String page, @Field("password") String password, @Field("uniquely") String uniquely) ;

    /**
     * 获取用户信息
     * @param uid
     * @return
     */
    @FormUrlEncoded
    @POST("User/userTextInfo.html")
    Flowable<UserInfoModel> getUserInfo(@Field("user_id") String uid,@Field("from_id") String from_id) ;

    /**
     * 获取我的发布文章列表
     * @param uid
     * @return
     */
    @FormUrlEncoded
    @POST("User/userTextList.html")
    Flowable<MineTextModel> getUserTextList(@Field("user_id") String uid,@Field("from_id") String from_id,@Field("topicSize") String topicSize,@Field("textSize") String textSize) ;


    /**
     * 获取我的关注
     * @param user_id
     * @return
     */
    @FormUrlEncoded
    @POST("User/getattentionlists.html")
    Flowable<MineAttModel> getUserAttention(@Field("user_id") String user_id,@Field("page") int page,@Field("pageSize") String pageSize) ;


    /**
     * 获取我的粉丝
     * @param user_id
     * @return
     */
    @FormUrlEncoded
    @POST("User/getfanslists.html")
    Flowable<MineAttModel> getUserFans(@Field("user_id") String user_id,@Field("page") int page,@Field("pageSize") String pageSize) ;


    /**
     * 获取我的喜欢
     * @param user_id
     * @return
     */
    @FormUrlEncoded
    @POST("Text/getUserLikeList.html")
    Flowable<MineTextModel> getUserLike(@Field("user_id") String user_id,@Field("page") int page) ;


    /**
     * 获取二维码
     * @param user_id
     * @return
     */
    @FormUrlEncoded
    @POST("User/QRcode.html")
    Flowable<QrModel> getUserQrCode(@Field("user_id") String user_id) ;


    /**
     * 点赞
     * @param text_id 文章ID
     * @param review_id 评论ID
     * @return
     */
    @FormUrlEncoded
    @POST("Text/like.html")
    Flowable<BaseModel> setLike(@Field("from_id") String from_id, @Field("text_id") String text_id, @Field("review_id") String review_id) ;

    /**
     * 关注
     * @param fid 关注者（我）
     * @param tid 被关注者
     * @return
     */
    @FormUrlEncoded
    @POST("User/setAttention.html")
    Flowable<BaseModel> setAtt(@Field("fid") String fid, @Field("tid") String tid) ;

    /**
     * 删除关注
     * @param fid 关注者（我）
     * @param tid 被关注者
     * @return
     */
    @FormUrlEncoded
    @POST("User/delAttention.html")
    Flowable<BaseModel> removeAtt(@Field("fid") String fid, @Field("tid") String tid) ;

    /**
     * 评论
     * @param from_id 评论者
     * @param text_id 文章ID
     * @param to_id   被回复人ID(评论文章可为0)
     * @return
     */
    @FormUrlEncoded
    @POST("Text/review.html")
    Flowable<BaseModel> setReview(@Field("from_id") String from_id, @Field("text_id") String text_id, @Field("to_id") String to_id,@Field("content") String content) ;


    /**
     * 获取话题头部信息
     * @param user_id
     * @param topic_id
     * @return
     */
    @FormUrlEncoded
    @POST("Topic/info.html")
    Flowable<TopiDesitalModel> getTopicInfo(@Field("user_id") String user_id, @Field("topic_id") String topic_id) ;

    /**
     * 获取话题参与文章
     * @param user_id
     * @param topic_id
     * @return
     */
    @FormUrlEncoded
    @POST("Topic/applyLists.html")
    Flowable<MineTextModel> getTopicText(@Field("user_id") String user_id, @Field("topic_id") String topic_id,@Field("page") int page,@Field("pageSize") String pageSize ) ;

    /**
     * 获取话题参与人
     * @param topic_id
     * @param page
     * @param pageSize
     * @return
     */
    @FormUrlEncoded
    @POST("Topic/applyUsers.html")
    Flowable<MineTextModel> getTopicApplyUser(@Field("topic_id") String topic_id,@Field("page") int page,@Field("pageSize") String pageSize ) ;


    /**
     * 我发起的话题
     * @param user_id
     * @param pageSize
     * @param page
     * @return
     */
    @FormUrlEncoded
    @POST("User/editTopicList.html")
    Flowable<MineTextModel> getUserEditTopic(@Field("user_id") String user_id,@Field("pageSize") String pageSize , @Field("page") int page);

}
