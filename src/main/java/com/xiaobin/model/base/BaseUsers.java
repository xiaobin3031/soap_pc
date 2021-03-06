package com.xiaobin.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseUsers<M extends BaseUsers<M>> extends Model<M> implements IBean {

	public void setUserId(java.lang.String userId) {
		set("user_id", userId);
	}

	public java.lang.String getUserId() {
		return get("user_id");
	}

	public void setUserName(java.lang.String userName) {
		set("user_name", userName);
	}

	public java.lang.String getUserName() {
		return get("user_name");
	}

	public void setStatus(java.lang.String status) {
		set("status", status);
	}

	public java.lang.String getStatus() {
		return get("status");
	}

	public void setName(java.lang.String name) {
		set("name", name);
	}

	public java.lang.String getName() {
		return get("name");
	}

	public void setMobile(java.lang.String mobile) {
		set("mobile", mobile);
	}

	public java.lang.String getMobile() {
		return get("mobile");
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

	public void setOfficeTime(java.sql.Timestamp officeTime) {
		set("office_time", officeTime);
	}

	public java.sql.Timestamp getOfficeTime() {
		return get("office_time");
	}

	public void setLeaveTime(java.sql.Timestamp leaveTime) {
		set("leave_time", leaveTime);
	}

	public java.sql.Timestamp getLeaveTime() {
		return get("leave_time");
	}

	public void setPracticeOverTime(java.sql.Timestamp practiceOverTime) {
		set("practice_over_time", practiceOverTime);
	}

	public java.sql.Timestamp getPracticeOverTime() {
		return get("practice_over_time");
	}

	public void setDuty(java.lang.String duty) {
		set("duty", duty);
	}

	public java.lang.String getDuty() {
		return get("duty");
	}

	public void setIsTrip(java.lang.String isTrip) {
		set("is_trip", isTrip);
	}

	public java.lang.String getIsTrip() {
		return get("is_trip");
	}

	public void setPassword(java.lang.String password) {
		set("password", password);
	}

	public java.lang.String getPassword() {
		return get("password");
	}

}
