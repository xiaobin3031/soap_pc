package com.xiaobin.model.model;

import com.jfinal.kit.Prop;
import com.xiaobin.model.base.BaseWeekreport;
import com.xiaobin.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class Weekreport extends BaseWeekreport<Weekreport> {
	public static final Weekreport dao = new Weekreport().dao();

	public List<Weekreport> query(Weekreport weekreport){
		Prop p = Util.getProp("dao/workspace/weekreport");
		String query = p.get("query");
		String beginDate = weekreport.get("beginDate");
		List<Object> values = new ArrayList<>();
		if(beginDate != null){
			query += " and work_time >= to_date(?,'yyyy-mm-dd')";
			values.add(beginDate);
		}
		query += " order by work_time asc";
		return find(query,values.toArray());
	}

}
