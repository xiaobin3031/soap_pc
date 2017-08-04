package com.xiaobin.ctrl.projectManage;

import com.jfinal.aop.Duang;
import com.xiaobin.ctrl.base.BaseCtrl;
import com.xiaobin.model.model.ProjectInfo;
import com.xiaobin.service.projectManage.ProjectInfoService;

import java.util.List;

/**
 * 项目信息
 * Created by XWB on 2017-07-16.
 */
public class ProjectInfoCtrl extends BaseCtrl{

    public void list(){
        ProjectInfo info = getModel(ProjectInfo.class);
        ProjectInfoService projectInfoService = Duang.duang(ProjectInfoService.class);
        List<ProjectInfo> list = projectInfoService.page(info);
        renderJson(list);
    }

    public void save(){
        ProjectInfo info = getModel(ProjectInfo.class);
        ProjectInfoService projectInfoService = Duang.duang(ProjectInfoService.class);
        projectInfoService.save(info,getUserId());
        renderJson(getSuccessModel());
    }
}
