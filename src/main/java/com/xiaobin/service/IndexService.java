package com.xiaobin.service;

import com.jfinal.plugin.activerecord.Record;
import com.xiaobin.model.model.Product;
import com.xiaobin.model.model.Users;
import com.xiaobin.service.base.BaseService;

import java.util.List;

/**
 * 首页
 * Created by XWB on 2017-05-29.
 */
public class IndexService extends BaseService{

    public List<Product> query(Product product){
        return Product.dao.query(product);
    }

    public Users login(Record record){
        Users users = new Users();
        users.setUserName(record.get("username"));
        List<Users> usersList = Users.dao.query(users);
        if(usersList == null || usersList.isEmpty()){
            throw new RuntimeException("操作员不存在");
        }
        users = usersList.get(0);
        if(record.get("md5Password").equals(users.getPassword())){
            return users;
        }else{
            throw new RuntimeException("用户名或密码错误");
        }
    }
}
