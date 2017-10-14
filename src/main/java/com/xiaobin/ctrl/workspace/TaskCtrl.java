package com.xiaobin.ctrl.workspace;

import com.jfinal.aop.Duang;
import com.xiaobin.ctrl.base.BaseCtrl;
import com.xiaobin.model.model.Task;
import com.xiaobin.service.workspace.TaskService;

import java.util.List;

/**
 * 任务
 * Created by XWB on 2017-08-08.
 */
public class TaskCtrl extends BaseCtrl{

    public void list(){
        Task task = getModel(Task.class);
        TaskService taskService = Duang.duang(TaskService.class);
        List<Task> list = taskService.page(task);
        renderJson(list);
    }

    public void save(){
        Task task = getModel(Task.class);
        TaskService taskService = Duang.duang(TaskService.class);
        taskService.save(task,getUserId());
        renderJson(getSuccessModel());
    }

    public void unCompleteTask(){
        Task task = getModel(Task.class);
        TaskService taskService = Duang.duang(TaskService.class);
        List<Task> list = taskService.unCompleteTask(task);
        renderJson(list);
    }

    public void taskOnLine(){
        Task task = new Task();
        task.setUserId(getUserId());
        TaskService taskService = Duang.duang(TaskService.class);
        taskService.taskOnLine(task);
        renderJson(getSuccessModel());
    }
}
