package com.xiaobin.service.workspace;

import com.xiaobin.model.model.Task;
import com.xiaobin.service.base.BaseService;

import java.util.List;

/**
 * 任务
 * Created by XWB on 2017-08-08.
 */
public class TaskService extends BaseService{

    public List<Task> page(Task task){
        return Task.dao.page(task);
    }

    public void save(Task task,String userId){
        super.save(task,"task_id",userId);
    }

    public List<Task> unCompleteTask(Task task){
        return Task.dao.unCompleteTask(task);
    }
}
