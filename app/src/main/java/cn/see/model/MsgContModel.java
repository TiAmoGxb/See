package cn.see.model;

import cn.see.base.BaseModel;

/**
 * Created by sks on 2018/7/24.
 */

public class MsgContModel extends BaseModel {

    private ContResult result;

    public ContResult getResult() {
        return result;
    }

    public void setResult(ContResult result) {
        this.result = result;
    }

    public static class ContResult{
        private String fans_count;
        private String call_count;
        private String likes_count;
        private String review_count;
        private String tidings_count;
        private String treview_count;
        private String total_count;

        @Override
        public String toString() {
            return "ContResult{" +
                    "fans_count='" + fans_count + '\'' +
                    ", call_count='" + call_count + '\'' +
                    ", likes_count='" + likes_count + '\'' +
                    ", review_count='" + review_count + '\'' +
                    ", tidings_count='" + tidings_count + '\'' +
                    ", treview_count='" + treview_count + '\'' +
                    ", total_count='" + total_count + '\'' +
                    '}';
        }

        public String getTotal_count() {
            return total_count;
        }

        public void setTotal_count(String total_count) {
            this.total_count = total_count;
        }

        public String getFans_count() {
            return fans_count;
        }

        public String getCall_count() {
            return call_count;
        }

        public String getLikes_count() {
            return likes_count;
        }

        public String getReview_count() {
            return review_count;
        }

        public String getTidings_count() {
            return tidings_count;
        }

        public String getTreview_count() {
            return treview_count;
        }

        public void setFans_count(String fans_count) {
            this.fans_count = fans_count;
        }

        public void setCall_count(String call_count) {
            this.call_count = call_count;
        }

        public void setLikes_count(String likes_count) {
            this.likes_count = likes_count;
        }

        public void setReview_count(String review_count) {
            this.review_count = review_count;
        }

        public void setTidings_count(String tidings_count) {
            this.tidings_count = tidings_count;
        }

        public void setTreview_count(String treview_count) {
            this.treview_count = treview_count;
        }
    }
}
