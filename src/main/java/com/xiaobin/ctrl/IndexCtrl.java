package com.xiaobin.ctrl;

import com.jfinal.aop.Duang;
import com.jfinal.plugin.activerecord.Record;
import com.xiaobin.ctrl.base.BaseCtrl;
import com.xiaobin.model.model.Users;
import com.xiaobin.service.IndexService;
import com.xiaobin.util.Const;
import com.xiaobin.util.Security;
import com.xiaobin.util.Util;

/**
 * 首页
 * Created by XWB on 2017-05-29.
 */
public class IndexCtrl extends BaseCtrl{

    public void index(){
        render("dashboard.html");
    }

    public void login(){
        Record record = new Record();
        record.set("username",getPara("login.username"));
        record.set("password",getPara("login.password"));
        if(Util.isEmpty(record.get("username"))){
            throw new RuntimeException("用户名不能太为空");
        }
        if(Util.isEmpty(record.get("password"))){
            throw new RuntimeException("密码不能为空");
        }
        String md5Pass = Security.security(record.get("password"));
        if(Util.isEmpty(md5Pass)){
            throw new RuntimeException("密码格式化失败");
        }
        record.set("md5Password",md5Pass);
        IndexService indexService = Duang.duang(IndexService.class);
        Users users = indexService.login(record);
        setSessionAttr(Const.LOGIN_ID,users.getUserId());
        setSessionAttr(Const.LOGIN_USER,users);
        users.put("success",true);
        renderJson(users);
    }
}
