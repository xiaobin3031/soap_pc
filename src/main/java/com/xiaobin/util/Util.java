package com.xiaobin.util;

import com.jfinal.core.JFinal;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * 工具类
 * Created by XWB on 2017-06-04.
 */
public class Util {

    public static String uuid(){
        return UUID.randomUUID().toString().replace("-","");
    }

    public static Date currentDate(){
        Calendar c = Calendar.getInstance();
        return c.getTime();
    }

    public static Timestamp currentTimeStamp(){
        Calendar c = Calendar.getInstance();
        return new Timestamp(c.getTimeInMillis());
    }

    public static Prop getProp(String fileName){
        boolean devMode = JFinal.me().getConstants().getDevMode();
        if(devMode){
            return new Prop(fileName + ".properties");
        }else{
           return PropKit.getProp(fileName + ".properties");
        }
    }

    public static boolean isEmpty(Object value){
        return isEmpty(value,true);
    }
    public static boolean isEmpty(Object value,boolean ignoreSpace ){
        if(value == null) return true;
        if(value instanceof String){
            if(ignoreSpace){
                value = ((String) value).trim();
            }
            return "".equals(value);
        }
        return true;
    }
}
