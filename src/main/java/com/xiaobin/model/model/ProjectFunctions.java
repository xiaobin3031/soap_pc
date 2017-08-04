package com.xiaobin.model.model;

import com.jfinal.kit.Prop;
import com.xiaobin.model.base.BaseProjectFunctions;
import com.xiaobin.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class ProjectFunctions extends BaseProjectFunctions<ProjectFunctions> {
	public static final ProjectFunctions dao = new ProjectFunctions().dao();

	public List<ProjectFunctions> page(ProjectFunctions functions){
		Prop p = Util.getProp("dao/projectManage/projectFunctions");
		String sql = p.get("query");
		List<Object> values = new ArrayList<>();
		if(functions.getProjectId() != null){
			sql += " and f.project_id = ?";
			values.add(functions.getProjectId());
		}
		return find(sql,values.toArray());
	}
}