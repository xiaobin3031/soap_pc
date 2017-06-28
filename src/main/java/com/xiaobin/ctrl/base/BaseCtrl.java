package com.xiaobin.ctrl.base;

import com.jfinal.core.Controller;
import com.xiaobin.model.ReturnModel;
import com.xiaobin.util.Const;

/**
 * 基础控制块
 * Created by XWB on 2017-05-29.
 */
public class BaseCtrl extends Controller{

    protected String getUserId(){
        return getSessionAttr(Const.LOGIN_ID);
    }

    protected ReturnModel getReturnModel(){
        ReturnModel model = new ReturnModel();
        model.setSuccess(false);
        model.setMessage("");
        model.setCode(0);
        return model;
    }
}
