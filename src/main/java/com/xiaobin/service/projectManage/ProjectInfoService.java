package com.xiaobin.service.projectManage;

import com.xiaobin.model.model.ProjectInfo;
import com.xiaobin.service.base.BaseService;

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
        super.save(info,"project_id",userId);
    }
}
