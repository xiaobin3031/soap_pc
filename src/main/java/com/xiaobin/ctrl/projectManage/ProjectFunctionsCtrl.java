package com.xiaobin.ctrl.projectManage;

import com.jfinal.aop.Duang;
import com.xiaobin.ctrl.base.BaseCtrl;
import com.xiaobin.model.model.ProjectFunctions;
import com.xiaobin.service.projectManage.ProjectFunctionsService;

import java.util.List;

/**
 * 项目功能
 * Created by XWB on 2017-07-21.
 */
public class ProjectFunctionsCtrl extends BaseCtrl{

    public void list(){
        ProjectFunctions functions = getModel(ProjectFunctions.class);
        ProjectFunctionsService projectFunctionsService = Duang.duang(ProjectFunctionsService.class);
        List<ProjectFunctions> list = projectFunctionsService.page(functions);
        renderJson(list);
    }

    public void save(){
        ProjectFunctions functions = getModel(ProjectFunctions.class);
        ProjectFunctionsService projectFunctionsService = Duang.duang(ProjectFunctionsService.class);
        projectFunctionsService.save(functions,getUserId());
        renderJson(getSuccessModel());
    }
}
