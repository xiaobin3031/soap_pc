package com.xiaobin.model.model;

import com.jfinal.kit.Prop;
import com.jfinal.plugin.activerecord.Db;
import com.xiaobin.model.base.BaseProjectUsers;
import com.xiaobin.util.Util;

import java.util.List;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class ProjectUsers extends BaseProjectUsers<ProjectUsers> {
	public static final ProjectUsers dao = new ProjectUsers().dao();

	public List<ProjectUsers> page(ProjectUsers projectUsers){
		Prop p = Util.getProp("dao/projectManage/projectUsers");
		return find(p.get("query"));
	}

	public void deleteProjectUserFunctions(ProjectUsers projectUsers){
		String sql = "delete from soap_project_users where project_id = ? and user_id = ?";
		Db.update(sql,projectUsers.getProjectId(),projectUsers.getUserId());
	}
}
