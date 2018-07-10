package cn.see.model;

import java.util.List;

import cn.see.base.BaseModel;

/**
 * @日期：2018/6/19
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 标签Model
 */

public class TabModel extends BaseModel {

    private List<TabList> result;

    public List<TabList> getResult() {
        return result;
    }

    public void setResult(List<TabList> result) {
        this.result = result;
    }

    public static class TabList{

        private String tab_id;
        private String text;
        private String url;
        private boolean flag;//自定义参数 标记是否被选中




        public boolean isFlag() {
            return flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        public String getTab_id() {
            return tab_id;
        }

        public String getText() {
            return text;
        }

        public String getUrl() {
            return url;
        }

        public void setTab_id(String tab_id) {
            this.tab_id = tab_id;
        }

        public void setText(String text) {
            this.text = text;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
