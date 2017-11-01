package com.xiaobin.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.xiaobin.model.ReturnModel;
import com.xiaobin.util.Const;

import java.util.Arrays;
import java.util.List;

/**
 * 全局拦截器
 * Created by XWB on 2017-05-29.
 */
public class GlobalActionInterceptor implements Interceptor{
    private List<String> ignoreList = Arrays.asList("/","/login","/sync");
    public void intercept(Invocation invocation) {
        Controller ctrl = invocation.getController();
        System.out.println(invocation.getActionKey());
        try{
            if((ctrl.getSession() == null || ctrl.getSessionAttr(Const.LOGIN_ID) == null) && !ignoreList.contains(invocation.getActionKey())){
                //未登陆，以后处理
                ReturnModel model = new ReturnModel();
                model.setCode(-101);
                ctrl.renderJson(model);
            }else{
                invocation.invoke();
            }
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
