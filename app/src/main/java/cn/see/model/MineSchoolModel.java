package cn.see.model;

import java.util.List;

import cn.see.base.BaseModel;

/**
 * Created by sks on 2018/7/17.
 */

public class MineSchoolModel extends BaseModel {

    private SchoolResult result;

    public SchoolResult getResult() {
        return result;
    }

    public void setResult(SchoolResult result) {
        this.result = result;
    }

    public static class SchoolResult{
        private List<SchoolList> list;

        public List<SchoolList> getList() {
            return list;
        }

        public void setList(List<SchoolList> list) {
            this.list = list;
        }

        public static class SchoolList{
            private String school_id;
            private String school_name;

            public String getSchool_id() {
                return school_id;
            }

            public String getSchool_name() {
                return school_name;
            }

            public void setSchool_id(String school_id) {
                this.school_id = school_id;
            }

            public void setSchool_name(String school_name) {
                this.school_name = school_name;
            }
        }
    }
}
