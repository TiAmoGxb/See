package cn.see.model;

import java.util.List;

import cn.see.base.BaseModel;

/**
 * @日期：2018/7/2
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明：
 */

public class NewsLikeReviewModel extends BaseModel {

    private NewsLikeResult result;
    private int totalPage;

    public NewsLikeResult getResult() {
        return result;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setResult(NewsLikeResult result) {
        this.result = result;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }


    public static class NewsLikeResult{

        private List<TxtModel.TxtResult.Result> lists;

        public List<TxtModel.TxtResult.Result> getLists() {
            return lists;
        }

        public void setLists(List<TxtModel.TxtResult.Result> lists) {
            this.lists = lists;
        }
    }


}
