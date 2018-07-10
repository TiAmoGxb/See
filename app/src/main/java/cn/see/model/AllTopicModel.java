package cn.see.model;

import java.util.List;

import cn.see.base.BaseModel;

/**
 * @日期：2018/7/3
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明：
 */

public class AllTopicModel extends BaseModel {

    private AllTopResult result;

    public AllTopResult getResult() {
        return result;
    }

    public void setResult(AllTopResult result) {
        this.result = result;
    }

    public static class AllTopResult{
       private List<MineTextModel.MineTextResult.ResultList> list;

        public List<MineTextModel.MineTextResult.ResultList> getList() {
            return list;
        }

        public void setList(List<MineTextModel.MineTextResult.ResultList> list) {
            this.list = list;
        }
    }
}
