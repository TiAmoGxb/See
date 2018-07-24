package cn.see.model;

import cn.see.base.BaseModel;

/**
 * Created by sks on 2018/7/20.
 */

public class NoticeModel extends BaseModel {

    private NoticeResult result;

    public NoticeResult getResult() {
        return result;
    }

    public void setResult(NoticeResult result) {
        this.result = result;
    }
    public static class NoticeResult{

        private String like;
        private String review;
        private String call;
        private String activity;
        private String  id;
        private String subscribe;//被关注消息的推送
        private String system;

        public String getLike() {
            return like;
        }

        public String getReview() {
            return review;
        }

        public String getCall() {
            return call;
        }

        public String getActivity() {
            return activity;
        }

        public String getId() {
            return id;
        }

        public String getSubscribe() {
            return subscribe;
        }

        public String getSystem() {
            return system;
        }

        public void setLike(String like) {
            this.like = like;
        }

        public void setReview(String review) {
            this.review = review;
        }

        public void setCall(String call) {
            this.call = call;
        }

        public void setActivity(String activity) {
            this.activity = activity;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setSubscribe(String subscribe) {
            this.subscribe = subscribe;
        }

        public void setSystem(String system) {
            this.system = system;
        }
    }
}
