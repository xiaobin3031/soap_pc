package com.xiaobin.service.projectManage;

import com.xiaobin.model.model.ProjectFunctions;
import com.xiaobin.service.base.BaseService;
import com.xiaobin.util.Util;

import java.util.List;

/**
 * 项目功能
 * Created by XWB on 2017-07-21.
 */
public class ProjectFunctionsService extends BaseService{

    public List<ProjectFunctions> page(ProjectFunctions functions){
        return ProjectFunctions.dao.page(functions);
    }

    public void save(ProjectFunctions functions,String userId){
        boolean result;
        if(functions.getFunctionId() == null){
            functions.setFunctionId(Util.uuid());
            functions.setCreateUser(userId);
            functions.setCreateTime(Util.currentTimeStamp());

            result = functions.save();
        }else{
            functions.setUpdateUser(userId);
            functions.setUpdateTime(Util.currentTimeStamp());

            result = functions.update();
        }
        if(!result){
            throw new RuntimeException("保存/更新失败");
        }
    }
}
