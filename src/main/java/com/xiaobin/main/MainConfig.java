package com.xiaobin.main;

import com.jfinal.config.*;
import com.jfinal.core.Const;
import com.jfinal.core.JFinal;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.activerecord.dialect.OracleDialect;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.template.Engine;
import com.xiaobin.ctrl.IndexCtrl;
import com.xiaobin.ctrl.ProductCtrl;
import com.xiaobin.ctrl.baseInfo.CompanyCtrl;
import com.xiaobin.ctrl.projectManage.ProjectFunctionsCtrl;
import com.xiaobin.ctrl.projectManage.ProjectInfoCtrl;
import com.xiaobin.ctrl.projectManage.ProjectUsersCtrl;
import com.xiaobin.ctrl.projectManage.WorkloadCtrl;
import com.xiaobin.ctrl.sys.UserCtrl;
import com.xiaobin.ctrl.workspace.TaskCtrl;
import com.xiaobin.ctrl.workspace.WeekReportCtrl;
import com.xiaobin.interceptor.GlobalActionInterceptor;
import com.xiaobin.model.model._MappingKit;

/**
 *  配置
 * Created by XWB on 2017-05-29.
 */
public class MainConfig extends JFinalConfig{
    public void configConstant(Constants constants) {
        Prop p = PropKit.use("config.properties");
        constants.setDevMode(p.getBoolean("devMode"));
        constants.setEncoding(Const.DEFAULT_ENCODING);
    }

    public void configRoute(Routes routes) {
        //首页
        routes.add("/", IndexCtrl.class);
        //产品
        routes.add("/product", ProductCtrl.class);
        //周报
        routes.add("/weekreport", WeekReportCtrl.class);
        //人员
        routes.add("/users", UserCtrl.class);
        //公司
        routes.add("/company", CompanyCtrl.class);
        //项目信息
        routes.add("/projectInfo", ProjectInfoCtrl.class);
        //项目功能
        routes.add("/projectFunctions", ProjectFunctionsCtrl.class);
        //项目人员
        routes.add("/projectUsers", ProjectUsersCtrl.class);
        //任务
        routes.add("/task", TaskCtrl.class);
        //工作量评估
        routes.add("/workload", WorkloadCtrl.class);
    }

    public void configEngine(Engine engine) {

    }

    public void configPlugin(Plugins plugins) {
        //加载c3p0插件
        Prop p = PropKit.use("c3p0.properties");
        C3p0Plugin c3p0Plugin = new C3p0Plugin(p.get("jdbcUrl"), p.get("user"), p.get("password"));
        c3p0Plugin.setDriverClass(p.get("driverClass"));
        plugins.add(c3p0Plugin);

        ActiveRecordPlugin activeRecordPlugin = new ActiveRecordPlugin(c3p0Plugin);
        activeRecordPlugin.setDialect(new OracleDialect());
        activeRecordPlugin.setShowSql(true);
        activeRecordPlugin.setDevMode(JFinal.me().getConstants().getDevMode());
        activeRecordPlugin.setContainerFactory(new CaseInsensitiveContainerFactory(true));
        _MappingKit.mapping(activeRecordPlugin);
        plugins.add(activeRecordPlugin);
    }

    public void configInterceptor(Interceptors interceptors) {
        interceptors.addGlobalActionInterceptor(new GlobalActionInterceptor());
    }

    public void configHandler(Handlers handlers) {

    }
}
