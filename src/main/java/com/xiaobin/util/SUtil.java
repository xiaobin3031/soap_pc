package com.xiaobin.util;

import com.jfinal.plugin.activerecord.Record;

import java.util.ArrayList;
import java.util.List;

/**
 * 拼接数据库条件
 * Created by XWB on 2017-07-23.
 */
public class SUtil {

    private StringBuilder sql = new StringBuilder();
    private List<Object> values = new ArrayList<>();

    public String getSql() {
        return sql.toString();
    }

    public List<Object> getValues() {
        return values;
    }

    public void addValues(Object value){
        values.add(value);
    }

    public SUtil(){

    }

    public void condition(Record record,String alias){
        record = record.removeNullValueColumns();
        String[] columns = record.getColumnNames();
        if(Util.isEmpty(alias)){
            for(String column : columns){
                sql.append(" and ").append(column).append(" = ?");
                values.add(record.get(column));
            }
        }else{
            for(String column : columns){
                sql.append(" and ").append(alias).append(".").append(column).append(" = ?");
                values.add(record.get(column));
            }
        }

    }


}
