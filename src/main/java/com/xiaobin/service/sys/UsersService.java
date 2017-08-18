package com.xiaobin.service.sys;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.xiaobin.model.ReturnModel;
import com.xiaobin.model.model.UserAttr;
import com.xiaobin.model.model.UserOperate;
import com.xiaobin.model.model.Users;
import com.xiaobin.service.base.BaseService;
import com.xiaobin.util.Security;
import com.xiaobin.util.Util;

import java.util.List;

/**
 * 用户管理
 * Created by XWB on 2017-06-18.
 */
public class UsersService extends BaseService{

    public List<Users> query(Users users){
        return Users.dao.query(users);
    }

    public List<Users> queryCurrent(Users users){
        return Users.dao.queryCurrent(users);
    }
    public List<Users> queryHis(Users users){
        return Users.dao.queryHis(users);
    }

    public void save(Users users, String userId, ReturnModel model){
        if(users.getUserId() == null){
            Users user = Users.dao.findFirst("select * from soap_users where user_name = ?",users.getUserName());
            if(user != null){
                throw new RuntimeException("用户已存在");
            }
            users.setPassword(Security.security(users.getPassword()));
            users.setUserId(Util.uuid());
            users.setCreateUser(userId);
            users.setCreateTime(Util.currentTimeStamp());
            users.save();
        }else{
            users.remove("password");
            users.setUpdateUser(userId);
            users.setUpdateTime(Util.currentTimeStamp());
            users.update();
        }
        model.setSuccess(true);
    }

    public void delete(Users users,ReturnModel model){
        users.setStatus("99");
        users.setUpdateTime(Util.currentTimeStamp());
        users.update();
        model.setSuccess(true);
    }

    public void userLeave(UserAttr userAttr,ReturnModel model){
        userAttr.setAttrId("user.leave.date.begin");
        userAttr.setAttrType("user.leave");
        userAttr.setAttrValue(userAttr.get("beginTime"));
        userAttr.save();

        userAttr.setAttrId("user.leave.date.end");
        userAttr.setAttrValue(userAttr.get("endTime"));
        userAttr.save();

        model.setSuccess(true);
    }

    @Before(Tx.class)
    public void userStatus(UserOperate operate){
        String status = "";
        switch(Integer.parseInt(operate.getOperate())){
            case 1:
                status = "95";
                break;
            case 2:
                status = "90";
                break;
            case 3:
                status = "50";
                break;
        }
        boolean result = operate.save();
        if(!result){
            throw new RuntimeException("操作失败");
        }
        if(!"".equals(status)){
            Users user = new Users();
            user.setUserId(operate.getUserId());
            user.setStatus(status);
            user.setUpdateUser(operate.getCreateUser());
            user.setUpdateTime(Util.currentTimeStamp());
            result = user.update();
            if(!result){
                throw new RuntimeException("状态更新失败");
            }
        }

    }

    public void modifyPass(Users users){
        Users user = Users.dao.findById(users.getUserId());
        if(user == null){
            throw new RuntimeException("用户不存在");
        }
        if(!user.getPassword().equals(Security.security(users.get("orionPassword")))){
            throw new RuntimeException("原密码不正确");
        }
        users.setPassword(Security.security(users.getPassword()));
        boolean result = users.update();
        if(!result){
            throw new RuntimeException("密码修改失败");
        }
    }
}
