package com.xiaobin.model.model;

import com.jfinal.kit.Prop;
import com.jfinal.plugin.activerecord.Db;
import com.xiaobin.model.base.BaseTask;
import com.xiaobin.util.SUtil;
import com.xiaobin.util.Util;

import java.util.List;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class Task extends BaseTask<Task> {
	public static final Task dao = new Task().dao();

	public List<Task> page(Task task){
		Prop p = Util.getProp("dao/workspace/task");
		String sql = p.get("query");
		SUtil sUtil = new SUtil(task.toRecord());
		sUtil.allLike("t.task_info","t.title");
		sUtil.condition("t");
		sql += sUtil.getSql();
		sql += " order by nvl(t.update_time,t.create_time) desc";
		return find(sql,sUtil.getValues().toArray());
	}

	public List<Task> unCompleteTask(Task task){
		Prop p = Util.getProp("dao/workspace/task");
		String sql = p.get("query");
		sql += " and t.status < '99'";
		SUtil sUtil = new SUtil(task.toRecord());
		sUtil.allLike("t.task_info","t.title");
		sUtil.condition("t");
		return find(sql + sUtil.getSql() + " order by t.create_time desc",sUtil.getValues().toArray());
	}

	public void taskOnLine(Task task){
		String sql = "update soap_task set status = '99',update_user = ?,update_time = sysdate,complete_user = ?,complete_time = sysdate where is_next = '1' and status <> '99'";
		Db.update(sql,task.getUserId(),task.getUserId());
	}
}
