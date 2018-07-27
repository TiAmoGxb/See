package cn.see.model;

import java.util.List;

import cn.see.base.BaseModel;

/**
 * @日期：2018/7/27
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明：
 */

public class AllTabModel extends BaseModel {

    private AllTabResult result;

    public AllTabResult getResult() {
        return result;
    }

    public void setResult(AllTabResult result) {
        this.result = result;
    }

    public static class AllTabResult{

        private List<TabModel.TabList> list;

        public List<TabModel.TabList> getList() {
            return list;
        }

        public void setList(List<TabModel.TabList> list) {
            this.list = list;
        }
    }
}
