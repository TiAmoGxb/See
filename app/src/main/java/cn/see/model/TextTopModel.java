package cn.see.model;

import cn.see.base.BaseModel;

/**
 * @日期：2018/6/21
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明：
 */

public class TextTopModel extends BaseModel {

    private TxtModel.TxtResult.Result result;

    public TxtModel.TxtResult.Result getResult() {
        return result;
    }

    public void setResult(TxtModel.TxtResult.Result result) {
        this.result = result;
    }
}
