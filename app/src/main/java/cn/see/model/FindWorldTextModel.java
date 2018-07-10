package cn.see.model;


import cn.see.base.BaseModel;

/**
 * @日期：2018/6/20
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明：
 */

public class FindWorldTextModel extends BaseModel {

    private MoreItems item1,item2,item3;
    private int type;
    public FindWorldTextModel(MoreItems item1,MoreItems item2,MoreItems item3,int type){
        this.item1=item1;
        this.item2=item2;
        this.item3=item3;
        this.type=type;
    }
    public void setItem1(MoreItems item1) {
        this.item1 = item1;
    }

    public void setItem2(MoreItems item2) {
        this.item2 = item2;
    }

    public void setItem3(MoreItems item3) {
        this.item3 = item3;
    }

    public void setType(int type) {
        this.type = type;
    }

    public MoreItems getItem1() {
        return item1;
    }

    public MoreItems getItem2() {
        return item2;
    }

    public MoreItems getItem3() {
        return item3;
    }

    public int getType() {
        return type;
    }
    public static class MoreItems{
        private String text_id;
        private String url;

        public String getText_id() {
            return text_id;
        }

        public String getUrl() {
            return url;
        }

        public void setText_id(String text_id) {
            this.text_id = text_id;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
