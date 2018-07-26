package cn.see.service;

import cn.see.base.BaseModel;
import cn.see.model.FindActModel;
import cn.see.model.LoginModel;
import cn.see.model.MineAttModel;
import cn.see.model.MineSchoolModel;
import cn.see.model.MineTextModel;
import cn.see.model.MsgContModel;
import cn.see.model.NoticeModel;
import cn.see.model.PrivateModel;
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
     * 获取用户信息 编辑用户信息使用
     * @param uid
     * @return
     */
    @FormUrlEncoded
    @POST("User/getInfo.html")
    Flowable<UserInfoModel> getUserInfoSetDate(@Field("uid") String uid) ;


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


    /**
     * 修改昵称
     * @param user_id
     * @param nickname
     * @return
     */
    @FormUrlEncoded
    @POST("User/updateNickname.html")
    Flowable<BaseModel> setUserNickName(@Field("uid") String user_id,@Field("nickname") String nickname );


    /**
     * 修改生日
     * @param user_id
     * @param brithdy
     * @return
     */
    @FormUrlEncoded
    @POST("User/birthday.html")
    Flowable<BaseModel> setUserBrithday(@Field("uid") String user_id,@Field("birthday") String brithdy );


    /**
     * 修改性别
     * @param user_id
     * @param sex
     * @return
     */
    @FormUrlEncoded
    @POST("User/updateSex.html")
    Flowable<BaseModel> setUserSex(@Field("uid") String user_id,@Field("sex") String sex );


    /**
     * 修改签名
     * @param user_id
     * @param signature
     * @return
     */
    @FormUrlEncoded
    @POST("User/updateSignature.html")
    Flowable<BaseModel> setUserSin(@Field("uid") String user_id,@Field("signature") String signature );


    /**
     * 修改常驻地址
     */
    @FormUrlEncoded
    @POST("User/setArea.html")
    Flowable<BaseModel> setUserArea(@Field("user_id") String user_id,@Field("area") String area );

    /**
     * 搜索学校
     * @param page
     * @param pageSize
     * @param sname
     * @return
     */
    @FormUrlEncoded
    @POST("User/school_lists.html")
    Flowable<MineSchoolModel> seaSchool(@Field("page") int page, @Field("pageSize") String pageSize , @Field("sname") String sname );


    /**
     * 修改学校
     * @param user_id
     * @param school_id
     * @return
     */
    @FormUrlEncoded
    @POST("User/saveUschool.html")
    Flowable<BaseModel> setSchool(@Field("user_id") String user_id, @Field("school_id") String school_id );


    /**
     * 修改密码
     * @param uid
     * @param pd 新密码
     * @param rpd 重复新密码
     * @param opd 旧密码
     * @return
     */
    @FormUrlEncoded
    @POST("User/updataPD.html")
    Flowable<BaseModel> setUserPwd(@Field("uid") String uid, @Field("pd") String pd, @Field("rpd") String rpd,@Field("opd") String opd);


    /***
     * 获取通知设置
     * @param uid
     * @return
     */
    @FormUrlEncoded
    @POST("User/notice.html")
    Flowable<NoticeModel> getUserNotice(@Field("uid") String uid);


    /**
     * 设置活动消息
     * @param uid
     * @param activity
     * @return
     */
    @FormUrlEncoded
    @POST("User/activity.html")
    Flowable<BaseModel> setUserNoticeAct(@Field("uid") String uid,@Field("activity") String activity);



    /**
     * 设置被关注消息
     * @param uid
     * @param subscribe
     * @return
     */
    @FormUrlEncoded
    @POST("User/subscribe.html")
    Flowable<BaseModel> setUserNoticeSubscribe(@Field("uid") String uid,@Field("subscribe") String subscribe);



    /**
     * 设置系统消息
     * @param uid
     * @param ssystem
     * @return
     */
    @FormUrlEncoded
    @POST("User/ssystem.html")
    Flowable<BaseModel> setUserNoticeSystem(@Field("uid") String uid,@Field("ssystem") String ssystem);


    /**
     * 设置评论消息
     * @param uid
     * @param review
     * @return
     */
    @FormUrlEncoded
    @POST("User/review.html")
    Flowable<BaseModel> setUserReview(@Field("uid") String uid,@Field("review") String review);


    /**
     * 设置点赞消息
     * @param uid
     * @param like
     * @return
     */
    @FormUrlEncoded
    @POST("User/like.html")
    Flowable<BaseModel> setUserLike(@Field("uid") String uid,@Field("like") String like);


    /**
     * 设置点赞消息
     * @param uid
     * @param call
     * @return
     */
    @FormUrlEncoded
    @POST("User/call.html")
    Flowable<BaseModel> setUserCall(@Field("uid") String uid,@Field("call") String call);


    /**
     * 获取允许手机号找到我
     * @param user_id
     * @return
     */
    @FormUrlEncoded
    @POST("User/pboolinfo.html")
    Flowable<PrivateModel> getUserPhoneSet(@Field("user_id") String user_id);


    /**
     * 设置允许手机号找到我
     * @param user_id
     * @param pbool
     * @return
     */
    @FormUrlEncoded
    @POST("User/pbool.html")
    Flowable<BaseModel> setUserPhoneSet(@Field("user_id") String user_id,@Field("pbool") String pbool);


    /**
     * 签到
     * @param user_id
     * @return
     */
    @FormUrlEncoded
    @POST("User/sign.html")
    Flowable<BaseModel> setUserSin(@Field("user_id") String user_id);


    /**
     * 绑定设备唯一标示符
     * @param user_id
     * @return
     */
    @FormUrlEncoded
    @POST("User/relation_getui.html")
    Flowable<BaseModel> setCid(@Field("user_id") String user_id,@Field("cid") String cid);

    /**
     * 获取未读消息数量
     * @param user_id
     * @return
     */
    @FormUrlEncoded
    @POST("User/notice_list.html")
    Flowable<MsgContModel> msgCont(@Field("user_id") String user_id);

    /**
     * 根据type清空消息数量
     * @param user_id
     * @param type
     * @return
     */
    @FormUrlEncoded
    @POST("User/del_notice.html")
    Flowable<BaseModel> delCont(@Field("user_id") String user_id,@Field("type") String type);


    /**
     * 我的活动
     * @param user_id
     * @return
     */
    @FormUrlEncoded
    @POST("User/activityLists.html")
    Flowable<FindActModel> getMineAct(@Field("user_id") String user_id, @Field("pageSize") String pageSize, @Field("page") int page);


    /**
     * 删除文章
     * @param user_id
     * @param text_id
     * @return
     */
    @FormUrlEncoded
    @POST("Text/del.html")
    Flowable<BaseModel> delText(@Field("user_id") String user_id, @Field("text_id") String text_id );


    /**
     * 删除话题
     * @param user_id
     * @param topic_id
     * @return
     */
    @FormUrlEncoded
    @POST("Topic/del.html")
    Flowable<BaseModel> delTopic(@Field("user_id") String user_id, @Field("topic_id") String topic_id );


    /**
     * 搜索文章(通用)
     * @param user_id
     * @return
     */
    @FormUrlEncoded
    @POST("Index/searchText.html")
    Flowable<MineTextModel> seText(@Field("user_id") String user_id, @Field("page") int page , @Field("pageSize") String pageSize, @Field("str") String str);


    /**
     * 搜索用户(通用)
     * @param user_id
     * @return
     */
    @FormUrlEncoded
    @POST("Index/searchUser.html")
    Flowable<MineAttModel> seUser(@Field("user_id") String user_id, @Field("str") String str);

}



