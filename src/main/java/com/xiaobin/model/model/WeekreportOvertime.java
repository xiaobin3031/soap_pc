package com.xiaobin.model.model;

import com.jfinal.kit.Prop;
import com.xiaobin.model.base.BaseWeekreportOvertime;
import com.xiaobin.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class WeekreportOvertime extends BaseWeekreportOvertime<WeekreportOvertime> {
	public static final WeekreportOvertime dao = new WeekreportOvertime().dao();

	public List<WeekreportOvertime> query(WeekreportOvertime weekreportOvertime){
		weekreportOvertime.removeNullValueAttrs();
		Map<String,Object> attrs = weekreportOvertime.getAttrs();
		Prop p = Util.getProp("dao/workspace/weekreportOvertime");
		String query = p.get("query");
		List<Object> values = new ArrayList<>();
		for(String key : attrs.keySet()){
			query += "and " + key + " = ?";
			values.add(attrs.get(key));
		}
		return find(query,values.toArray());
	}
}
