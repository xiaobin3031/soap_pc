package com.xiaobin.service.workspace;

import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Record;
import com.xiaobin.model.ReturnModel;
import com.xiaobin.model.model.Weekreport;
import com.xiaobin.model.model.WeekreportOvertime;
import com.xiaobin.service.base.BaseService;
import com.xiaobin.util.ExcelUtil;
import com.xiaobin.util.Util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 周报
 * Created by XWB on 2017-06-03.
 */
public class WeekReportService extends BaseService{

    //测试用
    private static int index = 0;

    public List<Weekreport> query(Weekreport weekreport){
        return Weekreport.dao.query(weekreport);
    }

    public void save(Weekreport weekreport,ReturnModel model){
        if(weekreport.getReportId() == null){
            weekreport.setReportId(Util.uuid());
            weekreport.setCreateTime(Util.currentTimeStamp());
            boolean save = weekreport.save();
            model.setSuccess(save);
            if(!save){
                model.setMessage("保存失败");
            }
        }else{
            weekreport.setUpdateUser(weekreport.getCreateUser());
            weekreport.remove("create_user");
            weekreport.setUpdateTime(Util.currentTimeStamp());
            boolean update = weekreport.update();
            model.setSuccess(update);
            if(!update){
                model.setMessage("更新失败");
            }
        }
    }

    public void delete(Weekreport weekreport,ReturnModel model){
        if(weekreport.delete()){
            model.setSuccess(true);
        }else{
            model.setMessage("删除失败");
        }
    }

    public void overWork(String type,ReturnModel model){
        WeekreportOvertime weekreportOvertime = new WeekreportOvertime();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        weekreportOvertime.setWorkTime(dateFormat.format(c.getTime()));
        List<WeekreportOvertime> list = WeekreportOvertime.dao.query(weekreportOvertime);
        String field = "over_time_" + type;
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        weekreportOvertime.set(field,timeFormat.format(c.getTime()));
        if(list == null || list.isEmpty()){
            model.setSuccess(weekreportOvertime.save());
        }else{
            WeekreportOvertime queryData = list.get(0);
            if(queryData.get(field) != null){
                model.setMessage("每天只允许执行一次加班开始和加班结束");
            }else{
                model.setSuccess(weekreportOvertime.update());
            }
        }
    }

    public void toExcel(Weekreport weekreport, ReturnModel model) {
        String path = PathKit.getWebRootPath() + File.separator + "files";
        File file = new File(path + File.separator + "testForWeekreport.xlsx");
        String fileName = "鄞州企业网银_徐卫斌_周报_20170619.xlsx";
        File file2 = new File(path + File.separator + fileName);
        if(!file.exists()){
            throw new RuntimeException("文件不存在 : " + file.getPath());
        }
        List<Weekreport> weekreportList = Weekreport.dao.query(weekreport);
        List<Record> weekreportRecords = new ArrayList<>();
        Record record = new Record();
        record.set("work_suggest","");
        record.set("remain_problems","");
        record.set("notes","");
        record.set("pmo_email","");
        Map<String,Record> weekreportMap = new HashMap<>();
        for(Weekreport weekreport1 : weekreportList){
            if(!Util.isEmpty(weekreport1.getWorkSuggest())){
                String value = record.get("work_suggest");
                if(!Util.isEmpty(value) && !value.endsWith(";")){
                    value += ";";
                }
                value += weekreport1.getWorkSuggest();
                record.set("work_suggest",value);
            }else if(!Util.isEmpty(weekreport1.getRemainProblems())){
                String value = record.get("remain_problems");
                if(!Util.isEmpty(value) && !value.endsWith(";")){
                    value += ";";
                }
                value += weekreport1.getRemainProblems();
                record.set("remain_problems",value);
            }else if(!Util.isEmpty(weekreport1.getNotes())){
                String value = record.get("notes");
                if(!Util.isEmpty(value) && !value.endsWith(";")){
                    value += ";";
                }
                value += weekreport1.getNotes();
                record.set("notes",value);
            }else if(!Util.isEmpty(weekreport1.getPmoEmail())){
                String value = record.get("pmo_email");
                if(!Util.isEmpty(value) && !value.endsWith(";")){
                    value += ";";
                }
                value += weekreport1.getPmoEmail();
                record.set("pmo_email",value);
            }
            weekreport1.remove("work_suggest");
            weekreport1.remove("remain_problems");
            weekreport1.remove("notes");
            weekreport1.remove("pmo_email");

            Record record1 = weekreport1.toRecord();
            String date = record1.getStr("work_time");
            weekreportMap.putIfAbsent(date,new Record());
            String content = weekreportMap.get(date).getStr("content");
            if(Util.isEmpty(content)) {
                content = "";
            }else{
                content += "\n";
            }
            content += weekreport1.getContent();
            weekreportMap.get(date).set("content",content);
        }
        weekreportMap.forEach((k,v) -> weekreportRecords.add(v));
        ExcelUtil excelUtil = new ExcelUtil(weekreportRecords,record);
        excelUtil.copyFromExcel(file,file2);
        Calendar c = Calendar.getInstance();
        if(c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
            c.add(Calendar.WEEK_OF_YEAR,-1);
        }
        c.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
        fileName = fileName.replaceAll("\\d{4}-?\\d{1,2}-?\\d{1,2}",new SimpleDateFormat("yyyyMMdd").format(c.getTime()));
        File file3 = new File(path + File.separator + fileName);
        file2.renameTo(file3);
    }


}
