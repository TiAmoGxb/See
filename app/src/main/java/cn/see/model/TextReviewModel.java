package cn.see.model;

import java.util.List;

import cn.see.base.BaseModel;

/**
 * @日期：2018/6/21
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 文章评论Model
 */

public class TextReviewModel extends BaseModel{

    private ReviewResult result;

    public ReviewResult getResult() {
        return result;
    }

    public void setResult(ReviewResult result) {
        this.result = result;
    }

    public static class ReviewResult{

        private List<ReviewList> lists;
        private int totalPage;

        public List<ReviewList> getLists() {
            return lists;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setLists(List<ReviewList> lists) {
            this.lists = lists;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public static class ReviewList{
            private String content;
            private String create_time_info;
            private String like_count;
            private String like_status;
            private String nickname;
            private String review_id;
            private String to_id;
            private String to_nickname;
            private String url;
            private String user_id;

            public String getContent() {
                return content;
            }

            public String getCreate_time_info() {
                return create_time_info;
            }

            public String getLike_count() {
                return like_count;
            }

            public String getLike_status() {
                return like_status;
            }

            public String getNickname() {
                return nickname;
            }

            public String getReview_id() {
                return review_id;
            }

            public String getTo_id() {
                return to_id;
            }

            public String getTo_nickname() {
                return to_nickname;
            }

            public String getUrl() {
                return url;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public void setCreate_time_info(String create_time_info) {
                this.create_time_info = create_time_info;
            }

            public void setLike_count(String like_count) {
                this.like_count = like_count;
            }

            public void setLike_status(String like_status) {
                this.like_status = like_status;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public void setReview_id(String review_id) {
                this.review_id = review_id;
            }

            public void setTo_id(String to_id) {
                this.to_id = to_id;
            }

            public void setTo_nickname(String to_nickname) {
                this.to_nickname = to_nickname;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }
        }
    }
}
