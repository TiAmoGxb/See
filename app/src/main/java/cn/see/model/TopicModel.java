package cn.see.model;

import java.util.List;

import cn.see.base.BaseModel;

/**
 * @日期：2018/6/19
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明：
 */

public class TopicModel extends BaseModel {

    private Topicresult result;

    public Topicresult getResult() {
        return result;
    }

    public void setResult(Topicresult result) {
        this.result = result;
    }

    public static class Topicresult{

        private List<TopicList> result;

        public List<TopicList> getResult() {
            return result;
        }

        public void setResult(List<TopicList> result) {
            this.result = result;
        }
        public static class TopicList{

            private String tname;
            private String see;
            private String topic_id;
            private String description;
            private String url;
            private String create_time_info;

            public String getTname() {
                return tname;
            }

            public String getSee() {
                return see;
            }

            public String getTopic_id() {
                return topic_id;
            }

            public String getDescription() {
                return description;
            }

            public String getUrl() {
                return url;
            }

            public String getCreate_time_info() {
                return create_time_info;
            }

            public void setTname(String tname) {
                this.tname = tname;
            }

            public void setSee(String see) {
                this.see = see;
            }

            public void setTopic_id(String topic_id) {
                this.topic_id = topic_id;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public void setCreate_time_info(String create_time_info) {
                this.create_time_info = create_time_info;
            }
        }
    }
}
