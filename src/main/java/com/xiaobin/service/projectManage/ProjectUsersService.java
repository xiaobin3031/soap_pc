package com.xiaobin.service.projectManage;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.xiaobin.model.model.ProjectUsers;
import com.xiaobin.service.base.BaseService;
import com.xiaobin.util.Const;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目人员
 * Created by XWB on 2017-07-30.
 */
public class ProjectUsersService extends BaseService{

    public List<ProjectUsers> page(ProjectUsers projectUsers){
        return ProjectUsers.dao.page(projectUsers);
    }

    @Before(Tx.class)
    public void save(ProjectUsers projectUsers){
        String functionIds = projectUsers.get("functionIds");
        String[] functionArrs = functionIds.split(",");
        projectUsers.remove("functionIds");
        List<ProjectUsers> projectUsersList = new ArrayList<>();
        for(String functionId : functionArrs){
            projectUsers.setFunctionId(functionId);
            projectUsers.delete();
            ProjectUsers user = new ProjectUsers();
            user.setUserId(projectUsers.getUserId());
            user.setProjectId(projectUsers.getProjectId());
            user.setFunctionId(functionId);
            user.setStatus(projectUsers.getStatus());
            user.setDuty(projectUsers.getDuty());
            user.setCreateUser(projectUsers.getCreateUser());
            user.setCreateTime(projectUsers.getCreateTime());
            projectUsersList.add(user);
        }
        int[] result = Db.batchSave(projectUsersList, Const.batchSize);
        if(result.length == 0){
            throw new RuntimeException("保存失败");
        }
    }
}
