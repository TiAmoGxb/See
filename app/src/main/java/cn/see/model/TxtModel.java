package cn.see.model;


import java.io.Serializable;
import java.util.List;

import cn.see.base.BaseModel;
import cn.see.util.TextUtils;

/**
 * @日期：2018/6/13
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 文章通用Model
 *        head_img_url通用
 *        用于首页关注顶部 好友推荐取一个头像
 */

public class TxtModel extends BaseModel implements Serializable {

    private TxtResult result;

    public TxtResult getResult() {
        return result;
    }

    public void setResult(TxtResult result) {
        this.result = result;
    }

    public static class TxtResult implements Serializable{

        private int page;
        private int totalPage;
        private List<Result> result;
        private String total;//总数量

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
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

        public List<Result> getResult() {
            return result;
        }

        public void setResult(List<Result> result) {
            this.result = result;
        }

        public static class Result implements Serializable {

            private String attention_status;//是否关注
            private String commment_count; //评论数量
            private String review_count;
            private String create_time_info;//发布时间
            private String head_img_id;//发布者ID
            private String head_img_url;//发布者头像
            private String legalize;//?
            private String like_count;//喜欢数量
            private String like_status;//是否喜欢
            private String msg;//文字描述
            private String nickname;//发布者名称
            private String see;//阅读人数
            private List<TableList> tab_lists; //标签
            private List<MineTextModel.MineTextResult.ResultList.ImageList> img_lists;//图片数据
            private List<TextLikeList> like_lists;//喜欢的列表
            private String text_id;//文章ID
            private String url;//文章封面
            private String user_id;//用户ID
            private String wh;//?
            private String signature;
            private String tab_area;//地址
            private String topic_id;//话题ID
            private String topic_name;//话题名称
            private String area;
            private String id;//热门用户ID
            private List<TextLists> text_lists;
            private String text_url;
            private String text_nickname;
            private String create_time;
            private String content;
            private String to_id;
            private String to_nickname;
            private String from_id;
            private String text;
            private boolean isCheck;

            public boolean isCheck() {
                return isCheck;
            }

            public void setCheck(boolean check) {
                isCheck = check;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getFrom_id() {
                return from_id;
            }

            public void setFrom_id(String from_id) {
                this.from_id = from_id;
            }

            public String getTo_id() {
                return to_id;
            }

            public String getTo_nickname() {
                return to_nickname;
            }

            public void setTo_id(String to_id) {
                this.to_id = to_id;
            }

            public void setTo_nickname(String to_nickname) {
                this.to_nickname = to_nickname;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getText_nickname() {
                return text_nickname;
            }

            public void setText_nickname(String text_nickname) {
                this.text_nickname = text_nickname;
            }

            public String getText_url() {
                return text_url;
            }

            public void setText_url(String text_url) {
                this.text_url = text_url;
            }

            public List<TextLists> getText_lists() {
                return text_lists;
            }

            public void setText_lists(List<TextLists> text_lists) {
                this.text_lists = text_lists;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public List<TextLikeList> getLike_lists() {
                return like_lists;
            }

            public String getTopic_id() {
                return topic_id;
            }

            public String getTopic_name() {
                return topic_name;
            }

            public String getArea() {
                if(area == null||area.equals("")){
                    return TextUtils.getRomArea();
                }
                return area;
            }

            public void setLike_lists(List<TextLikeList> like_lists) {
                this.like_lists = like_lists;
            }

            public void setTopic_id(String topic_id) {
                this.topic_id = topic_id;
            }

            public void setTopic_name(String topic_name) {
                this.topic_name = topic_name;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public String getSignature() {
                //如果没有签名 不显示
                if(signature == null || signature.trim().toString().equals("")){
                    return "";
                }
                //如果长度超过10 做处理
                if(signature.length()>10){
                    return signature.substring(0,9)+"...| ";
                }
                //返回签名
                return signature +" | ";
            }


            public List<MineTextModel.MineTextResult.ResultList.ImageList> getImg_lists() {
                return img_lists;
            }

            public void setImg_lists(List<MineTextModel.MineTextResult.ResultList.ImageList> img_lists) {
                this.img_lists = img_lists;
            }

            public void setSignature(String signature) {
                this.signature = signature;
            }

            public void setTab_area(String tab_area) {
                this.tab_area = tab_area;
            }
            public String getTab_area() {
                if(tab_area == null||tab_area.equals("")){
                    return TextUtils.getRomArea();
                }
                return tab_area;
            }

            public String getReview_count() {
                return review_count;
            }

            public void setReview_count(String review_count) {
                this.review_count = review_count;
            }

            public String getAttention_status() {
                return attention_status;
            }

            public String getCommment_count() {
                return commment_count;
            }

            public String getCreate_time_info() {
                return create_time_info;
            }

            public String getHead_img_id() {
                return head_img_id;
            }

            public String getHead_img_url() {
                return head_img_url;
            }

            public String getLegalize() {
                return legalize;
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

            public String getSee() {
                return see;
            }

            public List<TableList> getTab_lists() {
                return tab_lists;
            }

            public String getText_id() {
                return text_id;
            }

            public String getUrl() {
                return url;
            }

            public String getUser_id() {
                return user_id;
            }

            public String getWh() {
                return wh;
            }

            public void setAttention_status(String attention_status) {
                this.attention_status = attention_status;
            }

            public void setCommment_count(String commment_count) {
                this.commment_count = commment_count;
            }

            public void setCreate_time_info(String create_time_info) {
                this.create_time_info = create_time_info;
            }

            public void setHead_img_id(String head_img_id) {
                this.head_img_id = head_img_id;
            }

            public void setHead_img_url(String head_img_url) {
                this.head_img_url = head_img_url;
            }

            public void setLegalize(String legalize) {
                this.legalize = legalize;
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

            public void setSee(String see) {
                this.see = see;
            }

            public void setTab_lists(List<TableList> tab_lists) {
                this.tab_lists = tab_lists;
            }

            public void setText_id(String text_id) {
                this.text_id = text_id;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public void setWh(String wh) {
                this.wh = wh;
            }

            public static class TableList{
                private String id;//标签ID
                private String text;//标签名字

                public String getId() {
                    return id;
                }

                public String getText() {
                    return text;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public void setText(String text) {
                    this.text = text;
                }
            }

            public static class TextLikeList{

                private String head_img_url;
                private String nickname;
                private String user_id;

                public String getHead_img_url() {
                    return head_img_url;
                }

                public String getNickname() {
                    return nickname;
                }

                public String getUser_id() {
                    return user_id;
                }

                public void setHead_img_url(String head_img_url) {
                    this.head_img_url = head_img_url;
                }

                public void setNickname(String nickname) {
                    this.nickname = nickname;
                }

                public void setUser_id(String user_id) {
                    this.user_id = user_id;
                }


            }

            public static class TextLists{
                private String text_id;
                private String url;

                public String getText_id() {
                    return text_id;
                }

                public String getUrl() {
                    return url;
                }

                public void setText_id(String text_id) {
                    this.text_id = text_id;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }


        }
    }
}
