package cn.see.model;

import cn.see.base.BaseModel;

/**
 * @日期：2018/6/13
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 登录Model
 */

public class LoginModel extends BaseModel {

    private LoginResult result;

    public LoginResult getResult() {
        return result;
    }

    public void setResult(LoginResult result) {
        this.result = result;
    }

    public static class LoginResult {
        private String uid;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }
}
