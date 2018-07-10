package cn.see.util.version;

/**
 * Created by Administrator on 2017/10/18.
 */

public class VersionInfo {

    private int status_code;
    private VersionErrors errors;

    public int getStatus_code() {
        return status_code;
    }

    public VersionErrors getErrors() {
        return errors;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public void setErrors(VersionErrors errors) {
        this.errors = errors;
    }

    public static class VersionErrors{

        private VersionMessAge message;

        public VersionMessAge getMessage() {
            return message;
        }

        public void setMessage(VersionMessAge message) {
            this.message = message;
        }

        public static class VersionMessAge{

            private String name;
            private int code;
            private String content;
            private String url;
            private String size;
            private String update;

            public String getUpdate() {
                return update;
            }

            public void setUpdate(String update) {
                this.update = update;
            }

            public String getSize() {
                return size;
            }

            public void setSize(String size) {
                this.size = size;
            }

            public String getName() {
                return name;
            }

            public int getCode() {
                return code;
            }

            public String getContent() {
                return content;
            }

            public String getUrl() {
                return url;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setCode(int code) {
                this.code = code;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
