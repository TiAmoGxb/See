package cn.see.model;

import java.io.Serializable;

import cn.see.base.BaseModel;

/**
 * @日期：2018/6/13
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 用户信息
 *
 */

public class UserInfoModel extends BaseModel implements Serializable{

    private UserInfoResult result;

    public UserInfoResult getResult() {
        return result;
    }

    public void setResult(UserInfoResult result) {
        this.result = result;
    }

    public static class UserInfoResult implements Serializable{
        private String like_count;//喜欢数量
        private String account;//手机号
        private String area;//所在地址
        private String args_val;//?
        private String attention_count;//关注数量
        private String attention_status;
        private String background;//背景
        private String background_url;//背景地址
        private String birthday_info;//生日
        private String constellation;//星座
        private String fans_count;//粉丝
        private String greade;//性别
        private String sex;
        private String head_img_url;//头像
        private String id;//id
        private String nickname;//昵称
        private String school_id;//学校ID
        private String school_name;//学校
        private String signature;//签名
        private String tag_count;//?
        private String status;//?
        private String head_url;

        public void setImgUri(String url){
            this.head_url = url;
        }
        public String getUserUrl(){
            return head_url;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getSex() {
            return sex;
        }

        public String getAttention_status() {
            return attention_status;
        }

        public String getHead_img_url() {
            return head_img_url;
        }

        public void setAttention_status(String attention_status) {
            this.attention_status = attention_status;
        }

        public void setHead_img_url(String head_img_url) {
            this.head_img_url = head_img_url;
        }

        public String getLike_count() {
            return like_count;
        }

        public String getAccount() {
            return account;
        }

        public String getArea() {
            return area;
        }

        public String getArgs_val() {
            return args_val;
        }

        public String getAttention_count() {
            return attention_count;
        }

        public String getBackground() {
            return background;
        }

        public String getBackground_url() {
            return background_url;
        }

        public String getBirthday_info() {
            return birthday_info;
        }

        public String getConstellation() {
            return constellation;
        }

        public String getFans_count() {
            return fans_count;
        }

        public String getGreade() {
            return greade;
        }

        public String getHead_url() {
            return head_img_url;
        }

        public String getId() {
            return id;
        }

        public String getNickname() {
            return nickname;
        }

        public String getSchool_id() {
            return school_id;
        }

        public String getSchool_name() {
            return school_name;
        }

        public String getSignature() {
            return signature;
        }

        public String getTag_count() {
            return tag_count;
        }

        public String getStatus() {
            return status;
        }

        public void setLike_count(String like_count) {
            like_count = like_count;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public void setArgs_val(String args_val) {
            this.args_val = args_val;
        }

        public void setAttention_count(String attention_count) {
            this.attention_count = attention_count;
        }

        public void setBackground(String background) {
            this.background = background;
        }

        public void setBackground_url(String background_url) {
            this.background_url = background_url;
        }

        public void setBirthday_info(String birthday_info) {
            this.birthday_info = birthday_info;
        }

        public void setConstellation(String constellation) {
            this.constellation = constellation;
        }

        public void setFans_count(String fans_count) {
            this.fans_count = fans_count;
        }

        public void setGreade(String greade) {
            this.greade = greade;
        }

        public void setHead_url(String head_url) {
            this.head_img_url = head_url;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public void setSchool_id(String school_id) {
            this.school_id = school_id;
        }

        public void setSchool_name(String school_name) {
            this.school_name = school_name;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public void setTag_count(String tag_count) {
            this.tag_count = tag_count;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
