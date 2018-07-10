package cn.see.model;

import java.util.List;

import cn.see.base.BaseModel;

/**
 * @日期：2018/6/19
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 发现tabBanner
 */

public class TabBannerModel extends BaseModel {


    private BannerResult result;

    public void setResult(BannerResult result) {
        this.result = result;
    }

    public BannerResult getResult() {
        return result;
    }

    public static class BannerResult{
        private List<BannerList> result;

        public List<BannerList> getResult() {
            return result;
        }

        public void setResult(List<BannerList> result) {
            this.result = result;
        }

        public static class BannerList{
            private String banner_id;
            private String link;//连接
            private String type;
            private String type_id;
            private String url;//图片URL

            public String getBanner_id() {
                return banner_id;
            }

            public String getLink() {
                return link;
            }

            public String getType() {
                return type;
            }

            public String getType_id() {
                return type_id;
            }

            public String getUrl() {
                return url;
            }

            public void setBanner_id(String banner_id) {
                this.banner_id = banner_id;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public void setType(String type) {
                this.type = type;
            }

            public void setType_id(String type_id) {
                this.type_id = type_id;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }


}
