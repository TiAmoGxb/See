package cn.see.model;

import cn.see.base.BaseModel;

/**
 * @日期：2018/6/27
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 二维码Model
 */

public class QrModel extends BaseModel{

    private QrResult result;

    public QrResult getResult() {
        return result;
    }

    public void setResult(QrResult result) {
        this.result = result;
    }

    public static class QrResult{

        private String QR_url;
        private String head_img_url;
        private String nickname;
        private String user_id;

        public String getQR_url() {
            return QR_url;
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

        public void setQR_url(String QR_url) {
            this.QR_url = QR_url;
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
}
