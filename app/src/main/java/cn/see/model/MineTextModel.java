package cn.see.model;

import java.util.List;

import cn.see.base.BaseModel;
import cn.see.util.TextUtils;

/**
 * @日期：2018/6/14
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明：
 */

public class MineTextModel extends BaseModel {

    private MineTextResult result;

    public MineTextResult getResult() {
        return result;
    }

    public void setResult(MineTextResult result) {
        this.result = result;
    }

    public static class MineTextResult{

        private int page;
        private int totalPage;
        private int total;
        private String topicSize;
        private String textSize;
        private List<ResultList> lists;

        public String getTopicSize() {
            return topicSize;
        }

        public String getTextSize() {
            return textSize;
        }

        public void setTopicSize(String topicSize) {
            this.topicSize = topicSize;
        }

        public void setTextSize(String textSize) {
            this.textSize = textSize;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getTotal() {
            return total;
        }

        public int getPage() {
            return page;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public List<ResultList> getLists() {
            return lists;
        }

        public void setLists(List<ResultList> lists) {
            this.lists = lists;
        }

        public static class ResultList{

            private String attention_status;//是否关注
            private String create_time_info;
            private String text_id;
            private String head_img_url;
            private String id;
            private String like_count;//喜欢数量
            private String like_status;//是否喜欢
            private String msg;
            private String nickname;
            private String review_count;
            private String tab_area;//地址
            private String area;
            private String see;
            private String url;
            private List<ImageList> img_lists;//图片数据
            private List<ReviewList> reviewList;//评论数据
            private List<TextTableList>  tab_lists;
            private String ltype;//文章类型
            private String tname;//话题名称
            private String description;//话题描述
            private String apply_count;//参与人数
            private String topic_name;//如果是文章类型 返回的话题名称
            private String topic_id;
            private String user_id;
            private String apply;

            public String getApply() {
                return apply;
            }

            public void setApply(String apply) {
                this.apply = apply;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getTopic_id() {
                return topic_id;
            }

            public void setTopic_id(String topic_id) {
                this.topic_id = topic_id;
            }

            public String getTopic_name() {
                return topic_name;
            }

            public void setTopic_name(String topic_name) {
                this.topic_name = topic_name;
            }

            public String getTname() {
                return tname;
            }

            public String getDescription() {
                return description;
            }

            public String getApply_count() {
                return apply_count;
            }

            public void setTname(String tname) {
                this.tname = tname;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public void setApply_count(String apply_count) {
                this.apply_count = apply_count;
            }

            public String getLtype() {
                return ltype;
            }

            public void setLtype(String ltype) {
                this.ltype = ltype;
            }

            public String getArea() {
                if(area == null||area.equals("")){
                    return TextUtils.getRomArea();
                }
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public String getText_id() {
                return text_id;
            }

            public void setText_id(String text_id) {
                this.text_id = text_id;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public List<TextTableList> getTab_lists() {
                return tab_lists;
            }

            public void setTab_lists(List<TextTableList> tab_lists) {
                this.tab_lists = tab_lists;
            }

            public String getAttention_status() {
                return attention_status;
            }

            public void setAttention_status(String attention_status) {
                this.attention_status = attention_status;
            }

            public String getCreate_time_info() {
                return create_time_info;
            }

            public void setCreate_time_info(String create_time_info) {
                this.create_time_info = create_time_info;
            }

            public String getHead_img_url() {
                return head_img_url;
            }

            public String getId() {
                return id;
            }

            public String getLike_count() {
                return like_count;
            }

            public String getLike_status() {
                return like_status;
            }

            public String getMsg() {
                return msg;
            }

            public String getNickname() {
                return nickname;
            }

            public String getReview_count() {
                return review_count;
            }
            public String getTab_area() {
                if(tab_area == null||tab_area.equals("")){
                    return TextUtils.getRomArea();
                }
                return tab_area;
            }

            public String getSee() {
                return see;
            }

            public List<ImageList> getImg_lists() {
                return img_lists;
            }

            public List<ReviewList> getReviewList() {
                return reviewList;
            }

            public void setHead_img_url(String head_img_url) {
                this.head_img_url = head_img_url;
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setLike_count(String like_count) {
                this.like_count = like_count;
            }

            public void setLike_status(String like_status) {
                this.like_status = like_status;
            }

            public void setMsg(String msg) {
                this.msg = msg;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public void setReview_count(String review_count) {
                this.review_count = review_count;
            }

            public void setTab_area(String tab_area) {
                this.tab_area = tab_area;
            }

            public void setSee(String see) {
                this.see = see;
            }

            public void setImg_lists(List<ImageList> img_lists) {
                this.img_lists = img_lists;
            }

            public void setReviewList(List<ReviewList> reviewList) {
                this.reviewList = reviewList;
            }


            public static class TextTableList{
                private String tab_id;
                private String text;

                public String getTab_id() {
                    return tab_id;
                }

                public String getText() {
                    return text;
                }

                public void setTab_id(String tab_id) {
                    this.tab_id = tab_id;
                }

                public void setText(String text) {
                    this.text = text;
                }
            }

            public static  class ImageList{
                private String msg;
                private String url;

                public String getMsg() {
                    return msg;
                }

                public String getUrl() {
                    return url;
                }

                public void setMsg(String msg) {
                    this.msg = msg;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }

            public static  class ReviewList{
                private String content;
                private String from_id;//?
                private String head_img_url;
                private String nickname;
                private String id;
                private String text_id;

                public String getContent() {
                    return content;
                }

                public String getFrom_id() {
                    return from_id;
                }

                public String getHead_img_url() {
                    return head_img_url;
                }

                public String getNickname() {
                    return nickname;
                }

                public String getId() {
                    return id;
                }

                public String getText_id() {
                    return text_id;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public void setFrom_id(String from_id) {
                    this.from_id = from_id;
                }

                public void setHead_img_url(String head_img_url) {
                    this.head_img_url = head_img_url;
                }

                public void setNickname(String nickname) {
                    this.nickname = nickname;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public void setText_id(String text_id) {
                    this.text_id = text_id;
                }
            }
        }
    }
}
