package com.xiaobin.ctrl.baseInfo;

import com.jfinal.aop.Duang;
import com.xiaobin.ctrl.base.BaseCtrl;
import com.xiaobin.model.model.Company;
import com.xiaobin.service.baseInfo.CompanyService;

import java.util.List;

/**
 * 公司列表
 * Created by XWB on 2017-07-16.
 */
public class CompanyCtrl extends BaseCtrl{

    public void list(){
        Company company = getModel(Company.class);
        CompanyService companyService = Duang.duang(CompanyService.class);
        List<Company> list = companyService.page(company);
        renderJson(list);
    }

    public void save(){
        Company company = getModel(Company.class);
        CompanyService companyService = Duang.duang(CompanyService.class);
        companyService.save(company,getUserId());
        renderJson(getSuccessModel());
    }
}
