package com.xiaobin.model;

import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

/**
 * 返回类
 * Created by XWB on 2017-06-04.
 */
public class ReturnModel<M extends ReturnModel<M>> extends Model<M> implements IBean {

    public boolean getSuccess(){
        return get("success");
    }
    public void setSuccess(boolean success){
        set("success",success);
    }

    public String getMessage(){
        return get("message");
    }
    public void setMessage(String message){
        set("message",message);
    }

    public int getCode(){
        return get("code");
    }
    public void setCode(int code){
        set("code",code);
    }
}
