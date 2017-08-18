package com.xiaobin.ctrl.sys;

import com.jfinal.aop.Duang;
import com.xiaobin.ctrl.base.BaseCtrl;
import com.xiaobin.model.ReturnModel;
import com.xiaobin.model.model.UserAttr;
import com.xiaobin.model.model.UserOperate;
import com.xiaobin.model.model.Users;
import com.xiaobin.service.sys.UsersService;
import com.xiaobin.util.Util;

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

    public void queryCurrent(){
        Users users = getModel(Users.class);
        UsersService usersService = Duang.duang(UsersService.class);
        List<Users> list = usersService.queryCurrent(users);
        renderJson(list);
    }

    public void queryHis(){
        Users users = getModel(Users.class);
        UsersService usersService = Duang.duang(UsersService.class);
        List<Users> list = usersService.queryHis(users);
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

    public void userLeave(){
        if(Util.isEmpty(getPara("beginTime")) || Util.isEmpty(getPara("endTime"))){
            throw new RuntimeException("请假开始时间和结束时间不能为空");
        }
        UserAttr userAttr = new UserAttr();
        userAttr.setUserId(getPara("user_id"));
        userAttr.put("beginTime",getPara("beginTime"));
        userAttr.put("endTime",getPara("endTime"));
        UsersService usersService = Duang.duang(UsersService.class);
        ReturnModel model = getReturnModel();
        usersService.userLeave(userAttr,model);
        renderJson(model);
    }

    public void userStatus(){
        UserOperate operate = getModel(UserOperate.class);
        operate.setOperateId(Util.uuid());
        operate.setCreateUser(getUserId());
        operate.setCreateTime(Util.currentTimeStamp());
        UsersService usersService = Duang.duang(UsersService.class);
        usersService.userStatus(operate);
        renderJson(getSuccessModel());
    }

    public void modifyPass(){
        Users users = getModel(Users.class);
        users.put("orionPassword",getPara("orionPassword"));
        users.setUserId(getUserId());
        UsersService usersService = Duang.duang(UsersService.class);
        usersService.modifyPass(users);
        renderJson(getSuccessModel());
    }
}
