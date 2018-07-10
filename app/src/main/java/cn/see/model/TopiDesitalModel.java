package cn.see.model;

import java.util.List;

import cn.see.base.BaseModel;

/**
 * @日期：2018/7/3
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明：
 */

public class TopiDesitalModel extends BaseModel {

    private TopicDeresult result;

    public TopicDeresult getResult() {
        return result;
    }

    public void setResult(TopicDeresult result) {
        this.result = result;
    }

    public static class TopicDeresult{

        private List<TopicApply> apply;
        private TopicDe topic;
        private TopicUser user;

        public List<TopicApply> getApply() {
            return apply;
        }

        public TopicDe getTopic() {
            return topic;
        }

        public TopicUser getUser() {
            return user;
        }

        public void setApply(List<TopicApply> apply) {
            this.apply = apply;
        }

        public void setTopic(TopicDe topic) {
            this.topic = topic;
        }

        public void setUser(TopicUser user) {
            this.user = user;
        }

        public static class TopicApply{
            private String url;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
        public static class TopicDe{
            private String apply;
            private String create_time_info;
            private String description;
            private String id;
            private String see;
            private String tname;
            private List<String> url;

            public String getApply() {
                return apply;
            }

            public String getCreate_time_info() {
                return create_time_info;
            }

            public String getDescription() {
                return description;
            }

            public String getId() {
                return id;
            }

            public String getSee() {
                return see;
            }

            public String getTname() {
                return tname;
            }

            public List<String> getUrl() {
                return url;
            }

            public void setApply(String apply) {
                this.apply = apply;
            }

            public void setCreate_time_info(String create_time_info) {
                this.create_time_info = create_time_info;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setSee(String see) {
                this.see = see;
            }

            public void setTname(String tname) {
                this.tname = tname;
            }

            public void setUrl(List<String> url) {
                this.url = url;
            }
        }
        public static class TopicUser{
            private String fans;
            private String id;
            private String  attention_status;
            private String nickname;
            private String signature;
            private String url;

            public String getFans() {
                return fans;
            }

            public String getId() {
                return id;
            }

            public String getAttention_status() {
                return attention_status;
            }

            public String getNickname() {
                return nickname;
            }

            public String getSignature() {
                return signature;
            }

            public String getUrl() {
                return url;
            }

            public void setFans(String fans) {
                this.fans = fans;
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setAttention_status(String attention_status) {
                this.attention_status = attention_status;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public void setSignature(String signature) {
                this.signature = signature;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
