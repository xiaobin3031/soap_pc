package com.xiaobin.service.projectManage;

import com.xiaobin.model.model.ProjectInfo;
import com.xiaobin.service.base.BaseService;
import com.xiaobin.util.Util;

import java.util.List;

/**
 * 项目信息
 * Created by XWB on 2017-07-16.
 */
public class ProjectInfoService extends BaseService{

    public List<ProjectInfo> page(ProjectInfo info){
        return ProjectInfo.dao.page(info);
    }

    public void save(ProjectInfo info,String userId){
        boolean result;
        if(info.getProjectId() == null){
            info.setProjectId(Util.uuid());
            info.setCreateUser(userId);
            info.setCreateTime(Util.currentTimeStamp());
            result = info.save();
        }else{
            info.setUpdateUser(userId);
            info.setUpdateTime(Util.currentTimeStamp());
            result = info.update();
        }
        if(!result){
            throw new RuntimeException("保存/更新失败");
        }
    }
}
