package cn.see.model;

import java.util.List;

import cn.see.base.BaseModel;

/**
 * @日期：2018/6/14
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 我的关注Model
 */

public class MineAttModel extends BaseModel{

    private List<AttResult> result;

    public List<AttResult> getResult() {
        return result;
    }

    public void setResult(List<AttResult> result) {
        this.result = result;
    }

    public static class AttResult{

        private String head_url;
        private String nickname;
        private String signature;
        private String aStatus;//是否是相互关注 1：是 0：否
        private String uid;//对方ID

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getaStatus() {
            return aStatus;
        }

        public void setaStatus(String aStatus) {
            this.aStatus = aStatus;
        }

        public String getHead_url() {
            return head_url;
        }

        public String getNickname() {
            return nickname;
        }

        public String getSignature() {
            if(signature == null || signature.trim().toString().equals("")){
                return "这家伙很懒，什么都没有留下...";
            }
            return signature;
        }

        public void setHead_url(String head_url) {
            this.head_url = head_url;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }
    }
}
