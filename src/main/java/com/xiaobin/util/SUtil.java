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
    private Record record;

    public void setRecord(Record record) {
        this.record = record;
    }

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
    public SUtil(Record record){
        this.record = record;
    }

    public void condition(String alias){
        record = record.removeNullValueColumns();
        String[] columns = record.getColumnNames();
        if(Util.isEmpty(alias)){
            for(String column : columns){
                if(Util.isEmpty(column) || Util.isEmpty(record.get(column))){
                    continue;
                }
                sql.append(" and ").append(column).append(" = ?");
                values.add(record.get(column));
            }
        }else{
            for(String column : columns){
                if(Util.isEmpty(column) || Util.isEmpty(record.get(column))){
                    continue;
                }
                sql.append(" and ").append(alias).append(".").append(column).append(" = ?");
                values.add(record.get(column));
            }
        }

    }

    public void leftLike(String... columns){
        if(columns != null){
            for(String column : columns){
                String column__ = getColumn(column);
                if(!Util.isEmpty(column) && !Util.isEmpty(record.get(column__))){
                    sql.append(" and ").append(column).append(" like ?");
                    values.add("%" + record.get(column__));
                    record.remove(column__);
                }
            }
        }
    }

    public void rightLike(String... columns){
        if(columns != null){
            for(String column : columns){
                String column__ = getColumn(column);
                if(!Util.isEmpty(column) && !Util.isEmpty(record.get(column__))){
                    sql.append(" and ").append(column).append(" like ?");
                    values.add(record.get(column__) + "%");
                    record.remove(column__);
                }
            }
        }
    }

    public void allLike(String... columns){
        if(columns != null){
            for(String column : columns){
                String column__ = getColumn(column);
                if(!Util.isEmpty(column) && !Util.isEmpty(record.get(column__))){
                    sql.append(" and ").append(column).append(" like ?");
                    values.add("%" + record.get(column__) + "%");
                    record.remove(column__);
                }
            }
        }
    }

    private String getColumn(String column){
        return column.substring(column.indexOf(".") + 1);
    }

}
