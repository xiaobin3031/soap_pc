package com.xiaobin.service.baseInfo;

import com.xiaobin.model.model.Company;
import com.xiaobin.service.base.BaseService;
import com.xiaobin.util.Util;

import java.util.List;

/**
 * 公司
 * Created by XWB on 2017-07-16.
 */
public class CompanyService extends BaseService{

    public List<Company> page(Company company){
        return Company.dao.page(company);
    }

    public void save(Company company,String userId){
        boolean result;
        if(company.getCompanyId() == null){
            //新增
            company.setCompanyId(Util.uuid());
            company.setCreateUser(userId);
            company.setCreateTime(Util.currentTimeStamp());
            result = company.save();
        }else{
            company.setUpdateUser(userId);
            company.setUpdateTime(Util.currentTimeStamp());
            result = company.update();
        }
        if(!result){
            throw new RuntimeException("保存/更新失败");
        }
    }
}
