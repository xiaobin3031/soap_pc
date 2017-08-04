package com.xiaobin.ctrl.projectManage;

import com.jfinal.aop.Duang;
import com.xiaobin.ctrl.base.BaseCtrl;
import com.xiaobin.model.model.ProjectUsers;
import com.xiaobin.service.projectManage.ProjectUsersService;
import com.xiaobin.util.Util;

import java.util.List;

/**
 * 项目人员
 * Created by XWB on 2017-07-30.
 */
public class ProjectUsersCtrl extends BaseCtrl{

    public void list(){
        ProjectUsers projectUsers = getModel(ProjectUsers.class);
        ProjectUsersService projectUsersService = Duang.duang(ProjectUsersService.class);
        List<ProjectUsers> list = projectUsersService.page(projectUsers);
        renderJson(list);
    }

    public void save(){
        ProjectUsers projectUsers = getModel(ProjectUsers.class);
        projectUsers.setCreateUser(getUserId());
        projectUsers.setCreateTime(Util.currentTimeStamp());
        String functionIds = getPara("functionIds[]");
        projectUsers.put("functionIds",functionIds);
        ProjectUsersService projectUsersService = Duang.duang(ProjectUsersService.class);
        projectUsersService.save(projectUsers);
        renderJson(getSuccessModel());
    }
}
