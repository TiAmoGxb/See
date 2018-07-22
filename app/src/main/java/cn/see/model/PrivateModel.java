package cn.see.model;

import java.util.List;

import cn.see.base.BaseModel;

/**
 * Created by sks on 2018/7/21.
 */

public class PrivateModel extends BaseModel {

    private List<PrivateResult> result;

    public List<PrivateResult> getResult() {
        return result;
    }

    public void setResult(List<PrivateResult> result) {
        this.result = result;
    }

    public static class PrivateResult{
        private String pbool;

        public String getPbool() {
            return pbool;
        }

        public void setPbool(String pbool) {
            this.pbool = pbool;
        }
    }
}
