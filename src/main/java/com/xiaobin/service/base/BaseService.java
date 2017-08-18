package com.xiaobin.service.base;

import com.jfinal.plugin.activerecord.Model;
import com.xiaobin.util.Util;

/**
 * 基础服务
 * Created by XWB on 2017-05-29.
 */
public class BaseService {

    public void save(Model model,String modelId,String userId){
        boolean result;
        Object id = model.get(modelId);
        if(Util.isEmpty(id)){
            model.set(modelId,Util.uuid());
            model.set("create_user",userId);
            model.set("create_time",Util.currentTimeStamp());
            result = model.save();
        }else{
            model.set("update_user",userId);
            model.set("update_time",Util.currentTimeStamp());
            result = model.update();
        }
        if(!result){
            throw new RuntimeException("保存/更新失败");
        }
    }
}
