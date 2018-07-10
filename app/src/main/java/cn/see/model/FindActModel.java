package cn.see.model;

import java.util.List;

import cn.see.base.BaseModel;

/**
 * @日期：2018/6/21
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明：
 */

public class FindActModel extends BaseModel {

    private ActResult result;

    public ActResult getResult() {
        return result;
    }

    public void setResult(ActResult result) {
        this.result = result;
    }

    public static class ActResult{

        private List<ActList> lists;
        private int totalPage;

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public List<ActList> getLists() {
            return lists;
        }

        public void setLists(List<ActList> lists) {
            this.lists = lists;
        }

        public static class ActList{

            private String activity_id,start_time_info,end_time_info,url,user_count,start_status,name;

            public String getActivity_id() {
                return activity_id;
            }

            public String getStart_time_info() {
                return start_time_info;
            }

            public String getEnd_time_info() {
                return end_time_info;
            }

            public String getUrl() {
                return url;
            }

            public String getUser_count() {
                return user_count;
            }

            public String getStart_status() {
                return start_status;
            }

            public void setActivity_id(String activity_id) {
                this.activity_id = activity_id;
            }

            public void setStart_time_info(String start_time_info) {
                this.start_time_info = start_time_info;
            }

            public void setEnd_time_info(String end_time_info) {
                this.end_time_info = end_time_info;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public void setUser_count(String user_count) {
                this.user_count = user_count;
            }

            public void setStart_status(String start_status) {
                this.start_status = start_status;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
