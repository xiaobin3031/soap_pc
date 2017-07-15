package com.xiaobin.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.xiaobin.model.ReturnModel;
import com.xiaobin.util.Const;

/**
 * 全局拦截器
 * Created by XWB on 2017-05-29.
 */
public class GlobalActionInterceptor implements Interceptor{
    public void intercept(Invocation invocation) {
        Controller ctrl = invocation.getController();
        try{
            String userId = "f0adbc906eb74d08816ce34a424625c3";
            String userName = "xwb";
            if(ctrl.getSession() == null || ctrl.getSessionAttr(Const.LOGIN_ID) == null){
                //未登陆，以后处理
                ctrl.setSessionAttr(Const.LOGIN_ID,userId);
                ctrl.setSessionAttr(Const.LOGIN_NAME,userName);
            }
            invocation.invoke();
        } catch(Exception e){
            e.printStackTrace();
            ReturnModel model = new ReturnModel();
            model.setCode(-1);
            model.setSuccess(false);
            model.setMessage(e.getMessage());
            ctrl.renderJson(model);
        } finally{
            System.out.println("11111");
        }
    }
}
