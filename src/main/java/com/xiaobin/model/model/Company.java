package com.xiaobin.model.model;

import com.jfinal.kit.Prop;
import com.xiaobin.model.base.BaseCompany;
import com.xiaobin.util.Util;

import java.util.List;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class Company extends BaseCompany<Company> {
	public static final Company dao = new Company().dao();

	public List<Company> page(Company company){
		Prop p = Util.getProp("dao/baseInfo/company");
		return find(p.get("query"));
	}
}