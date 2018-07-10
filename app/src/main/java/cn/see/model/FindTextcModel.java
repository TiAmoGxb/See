package cn.see.model;

import java.util.List;

import cn.see.base.BaseModel;

/**
 * @日期：2018/6/19
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明：
 */

public class FindTextcModel extends BaseModel {

    private FindTextResult result;

    public FindTextResult getResult() {
        return result;
    }

    public void setResult(FindTextResult result) {
        this.result = result;
    }

    public static class FindTextResult{
        private int page;
        private int totalPage;
        private List<TxtModel.TxtResult.Result> list;

        public int getPage() {
            return page;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public List<TxtModel.TxtResult.Result> getList() {
            return list;
        }

        public void setList(List<TxtModel.TxtResult.Result> list) {
            this.list = list;
        }
    }
}
