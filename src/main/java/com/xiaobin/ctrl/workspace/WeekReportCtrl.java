package com.xiaobin.ctrl.workspace;

import com.jfinal.aop.Duang;
import com.xiaobin.ctrl.base.BaseCtrl;
import com.xiaobin.model.ReturnModel;
import com.xiaobin.model.model.Weekreport;
import com.xiaobin.service.workspace.WeekReportService;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

/**
 * 周报
 * Created by XWB on 2017-06-03.
 */
public class WeekReportCtrl extends BaseCtrl {

    public void list(){
        WeekReportService weekReportService = Duang.duang(WeekReportService.class);
        Weekreport weekreport = getModel(Weekreport.class);
        weekreport.put("beginDate",getPara("beginDate"));
        List<Weekreport> list = weekReportService.query(weekreport);
        renderJson(list);
    }

    public void save(){
        Weekreport weekreport = getModel(Weekreport.class);
        weekreport.setCreateUser(getUserId());
        String week = getPara("week");
        int weekIndex = Integer.parseInt(week);
        Calendar c = Calendar.getInstance();
        if(c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
            c.add(Calendar.DAY_OF_WEEK,-1);
        }
        c.set(Calendar.DAY_OF_WEEK,++weekIndex);
        weekreport.setWorkTime(new Timestamp(c.getTimeInMillis()));
        ReturnModel model = getReturnModel();
        WeekReportService weekReportService = Duang.duang(WeekReportService.class);
        weekReportService.save(weekreport,model);
        renderJson(model);
    }

    public void delete(){
        Weekreport weekreport = getModel(Weekreport.class);
        WeekReportService weekReportService = Duang.duang(WeekReportService.class);
        ReturnModel model = getReturnModel();
        weekReportService.delete(weekreport,model);
        renderJson(model);
    }

    public void overWork(){
        String type = getPara("type");
        WeekReportService weekReportService = Duang.duang(WeekReportService.class);
        ReturnModel model = getReturnModel();
        weekReportService.overWork(type,model);
        renderJson(model);
    }

    public void toExcel() {
        WeekReportService weekReportService = Duang.duang(WeekReportService.class);
        ReturnModel model = getReturnModel();
        Weekreport weekreport = getModel(Weekreport.class);
        weekreport.put("beginDate",getPara("beginDate"));
        weekReportService.toExcel(weekreport,model);
        renderJson(model);
    }
}
