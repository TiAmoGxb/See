package cn.see.base;

import cn.droidlover.xdroidmvp.net.IModel;
import cn.see.util.constant.HttpConstant;


/**
 * @日期：2018/6/4
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 基类Model 主要作为通用返回
 */
public class BaseModel implements IModel {
    public boolean error;
    public Integer code ;
    public String status ;
    private String info;

    public BaseModel() {
    }

    public Integer getRespCode() {
        return code;
    }

    public void setRespCode(Integer respCode) {
        this.code = respCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isError(){
        if(this.status .equals("success")){
            this.setError(false);
        }else{
            this.setError(true);
        }
        return error;
    }

    public void setError(boolean error){
        this.error = error;
    }

    @Override
    public boolean isNull() {
        return false;
    }

    @Override
    public boolean isAuthError() {

        return false;
    }


    @Override
    public boolean isBizError() {
        return error;
    }

    @Override
    public String getErrorMsg() {
        return info;
    }


}
