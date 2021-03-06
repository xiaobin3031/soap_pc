package com.xiaobin.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseTask<M extends BaseTask<M>> extends Model<M> implements IBean {

	public void setTaskId(java.lang.String taskId) {
		set("task_id", taskId);
	}

	public java.lang.String getTaskId() {
		return get("task_id");
	}

	public void setFunctionId(java.lang.String functionId) {
		set("function_id", functionId);
	}

	public java.lang.String getFunctionId() {
		return get("function_id");
	}

	public void setParentId(java.lang.String parentId) {
		set("parent_id", parentId);
	}

	public java.lang.String getParentId() {
		return get("parent_id");
	}

	public void setStatus(java.lang.String status) {
		set("status", status);
	}

	public java.lang.String getStatus() {
		return get("status");
	}

	public void setTitle(java.lang.String title) {
		set("title", title);
	}

	public java.lang.String getTitle() {
		return get("title");
	}

	public void setTaskInfo(java.lang.String taskInfo) {
		set("task_info", taskInfo);
	}

	public java.lang.String getTaskInfo() {
		return get("task_info");
	}

	public void setUserId(java.lang.String userId) {
		set("user_id", userId);
	}

	public java.lang.String getUserId() {
		return get("user_id");
	}

	public void setCreateUser(java.lang.String createUser) {
		set("create_user", createUser);
	}

	public java.lang.String getCreateUser() {
		return get("create_user");
	}

	public void setCreateTime(java.sql.Timestamp createTime) {
		set("create_time", createTime);
	}

	public java.sql.Timestamp getCreateTime() {
		return get("create_time");
	}

	public void setUpdateUser(java.lang.String updateUser) {
		set("update_user", updateUser);
	}

	public java.lang.String getUpdateUser() {
		return get("update_user");
	}

	public void setUpdateTime(java.sql.Timestamp updateTime) {
		set("update_time", updateTime);
	}

	public java.sql.Timestamp getUpdateTime() {
		return get("update_time");
	}

	public void setCompleteUser(java.lang.String completeUser) {
		set("complete_user", completeUser);
	}

	public java.lang.String getCompleteUser() {
		return get("complete_user");
	}

	public void setCompleteTime(java.sql.Timestamp completeTime) {
		set("complete_time", completeTime);
	}

	public java.sql.Timestamp getCompleteTime() {
		return get("complete_time");
	}

	public void setTaskType(java.lang.String taskType) {
		set("task_type", taskType);
	}

	public java.lang.String getTaskType() {
		return get("task_type");
	}

	public void setIsNext(java.lang.String isNext) {
		set("is_next", isNext);
	}

	public java.lang.String getIsNext() {
		return get("is_next");
	}

}
