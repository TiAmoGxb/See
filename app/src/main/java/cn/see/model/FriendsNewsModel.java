package cn.see.model;

import java.util.List;

import cn.see.base.BaseModel;

/**
 * @日期：2018/6/14
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 好友动态
 */

public class FriendsNewsModel extends BaseModel {

    private NewsResult result;

    public NewsResult getResult() {
        return result;
    }

    public void setResult(NewsResult result) {
        this.result = result;
    }

    public static class NewsResult{
        private String attentionSize;
        private String fansSize;
        private String likeSize;
        private String reviewSize;
        private List<NewsList> list;


        public String getAttentionSize() {
            return attentionSize;
        }

        public String getFansSize() {
            return fansSize;
        }

        public String getLikeSize() {
            return likeSize;
        }

        public String getReviewSize() {
            return reviewSize;
        }

        public void setAttentionSize(String attentionSize) {
            this.attentionSize = attentionSize;
        }

        public void setFansSize(String fansSize) {
            this.fansSize = fansSize;
        }

        public void setLikeSize(String likeSize) {
            this.likeSize = likeSize;
        }

        public void setReviewSize(String reviewSize) {
            this.reviewSize = reviewSize;
        }



        public List<NewsList> getList() {
            return list;
        }

        public void setList(List<NewsList> list) {
            this.list = list;
        }

        public static class NewsList{
            private String create_time_info;
            private String fnickname;
            private String nickname;
            private String tnickname;
            private String from_id;//点赞/评论用户ID
            private String text_id;//被点赞文章ID
            private String to_id;//被评论用户ID
            private String fid;//关注/粉丝用户ID
            private String tid;//被关注/粉丝用户ID
            private String head_img_url;
            private String text_nickname;//评论：文章所属用户昵称
            private String type;//like,review,attention,fans

            public String getTnickname() {
                return tnickname;
            }

            public void setTnickname(String tnickname) {
                this.tnickname = tnickname;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getText_id() {
                return text_id;
            }

            public String getTo_id() {
                return to_id;
            }

            public String getFid() {
                return fid;
            }

            public String getTid() {
                return tid;
            }

            public void setText_id(String text_id) {
                this.text_id = text_id;
            }

            public void setTo_id(String to_id) {
                this.to_id = to_id;
            }

            public void setFid(String fid) {
                this.fid = fid;
            }

            public void setTid(String tid) {
                this.tid = tid;
            }

            public String getCreate_time_info() {
                return create_time_info;
            }

            public String getFnickname() {
                return fnickname;
            }

            public String getFrom_id() {
                return from_id;
            }

            public String getHead_img_url() {
                return head_img_url;
            }

            public String getText_nickname() {
                return text_nickname;
            }

            public String getType() {
                return type;
            }

            public void setCreate_time_info(String create_time_info) {
                this.create_time_info = create_time_info;
            }

            public void setFnickname(String fnickname) {
                this.fnickname = fnickname;
            }

            public void setFrom_id(String from_id) {
                this.from_id = from_id;
            }

            public void setHead_img_url(String head_img_url) {
                this.head_img_url = head_img_url;
            }

            public void setText_nickname(String text_nickname) {
                this.text_nickname = text_nickname;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
