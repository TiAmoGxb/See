package cn.see.model;

import java.util.List;

import cn.see.base.BaseModel;

/**
 * @日期：2018/7/12
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明：
 */

public class ReleaseTopicModel extends BaseModel {
    private TopicResult result;

    public TopicResult getResult() {
        return result;
    }

    public void setResult(TopicResult result) {
        this.result = result;
    }

    public static class TopicResult{

        private List<TopicList> topic;
        private List<TopicList> activity;

        public List<TopicList> getTopic() {
            return topic;
        }

        public List<TopicList> getActivity() {
            return activity;
        }

        public void setTopic(List<TopicList> topic) {
            this.topic = topic;
        }

        public void setActivity(List<TopicList> activity) {
            this.activity = activity;
        }

        public static class TopicList{
            private String apply_count;
            private String topic_id;
            private String tname;
            private String name;
            private String activity_id;

            public String getName() {
                return name;
            }

            public String getActivity_id() {
                return activity_id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setActivity_id(String activity_id) {
                this.activity_id = activity_id;
            }

            public String getApply_count() {
                return apply_count;
            }

            public String getTopic_id() {
                return topic_id;
            }

            public String getTname() {
                return tname;
            }

            public void setApply_count(String apply_count) {
                this.apply_count = apply_count;
            }

            public void setTopic_id(String topic_id) {
                this.topic_id = topic_id;
            }

            public void setTname(String tname) {
                this.tname = tname;
            }
        }
    }
}
