package cn.see.model;

import java.util.List;

import cn.see.base.BaseModel;

/**
 * @日期：2018/7/27
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明：
 */

public class SystemNoticModel extends BaseModel {

    private SystemModelResult result;

    public SystemModelResult getResult() {
        return result;
    }

    public void setResult(SystemModelResult result) {
        this.result = result;
    }

    public static class SystemModelResult{
        private SystemMessage SystemMsgs;
        private TdingsMessage tidings_lists;

        public SystemMessage getSystemMsgs() {
            return SystemMsgs;
        }

        public TdingsMessage getTidings_lists() {
            return tidings_lists;
        }

        public void setSystemMsgs(SystemMessage systemMsgs) {
            SystemMsgs = systemMsgs;
        }

        public void setTidings_lists(TdingsMessage tidings_lists) {
            this.tidings_lists = tidings_lists;
        }

        public static class SystemMessage{
            private List<SystemList> list;

            public List<SystemList> getList() {
                return list;
            }

            public void setList(List<SystemList> list) {
                this.list = list;
            }

            public static class SystemList{
                private String craete_time;
                private String id;
                private String msg;
                private String title;

                public String getCraete_time() {
                    return craete_time;
                }

                public String getId() {
                    return id;
                }

                public String getMsg() {
                    return msg;
                }

                public String getTitle() {
                    return title;
                }

                public void setCraete_time(String craete_time) {
                    this.craete_time = craete_time;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public void setMsg(String msg) {
                    this.msg = msg;
                }

                public void setTitle(String title) {
                    this.title = title;
                }
            }
        }

        public static class TdingsMessage{

        }
    }
}
