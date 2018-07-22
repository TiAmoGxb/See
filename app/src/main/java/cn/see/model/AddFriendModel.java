package cn.see.model;

import java.util.List;

import cn.see.base.BaseModel;

/**
 * Created by sks on 2018/7/19.
 */

public class AddFriendModel extends BaseModel {

    private List<AddResult> result;

    public List<AddResult> getResult() {
        return result;
    }

    public void setResult(List<AddResult> result) {
        this.result = result;
    }

    public static class AddResult{
        private String head_img_url;
        private String nickname;
        private String user_id;
        private String signature;
        private List<AddTextList> lists;

        public String getSignature() {
            return signature;
        }

        public List<AddTextList> getLists() {
            return lists;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public void setLists(List<AddTextList> lists) {
            this.lists = lists;
        }

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

        public static class AddTextList{
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
