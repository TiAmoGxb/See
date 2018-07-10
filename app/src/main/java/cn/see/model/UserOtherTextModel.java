package cn.see.model;

import java.util.List;

import cn.see.base.BaseModel;

/**
 * @日期：2018/6/22
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 用户关联的其他文章
 */

public class UserOtherTextModel extends BaseModel{

    private List<TxtModel.TxtResult.Result> result;

    public List<TxtModel.TxtResult.Result> getResult() {
        return result;
    }

    public void setResult(List<TxtModel.TxtResult.Result> result) {
        this.result = result;
    }


}
