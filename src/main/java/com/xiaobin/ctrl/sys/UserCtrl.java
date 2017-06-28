package com.xiaobin.ctrl.sys;

import com.jfinal.aop.Duang;
import com.xiaobin.ctrl.base.BaseCtrl;
import com.xiaobin.model.ReturnModel;
import com.xiaobin.model.model.Users;
import com.xiaobin.service.sys.UsersService;

import java.util.List;

/**
 * 用户管理
 * Created by XWB on 2017-06-18.
 */
public class UserCtrl extends BaseCtrl{

    public void list(){
        Users users = getModel(Users.class);
        UsersService usersService = Duang.duang(UsersService.class);
        List<Users> list = usersService.query(users);
        renderJson(list);
    }

    public void save(){
        Users users = getModel(Users.class);
        UsersService usersService = Duang.duang(UsersService.class);
        ReturnModel model = getReturnModel();
        usersService.save(users,getUserId(),model);
        renderJson(model.toJson());
    }

    public void delete(){
        Users users = getModel(Users.class);
        UsersService usersService = Duang.duang(UsersService.class);
        ReturnModel model = getReturnModel();
        usersService.delete(users,model);
        renderJson(model.toJson());
    }
}
