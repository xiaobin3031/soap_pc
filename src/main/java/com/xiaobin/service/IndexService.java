package com.xiaobin.service;

import com.xiaobin.model.model.Product;
import com.xiaobin.service.base.BaseService;

import java.util.List;
import java.util.Map;

/**
 * 首页
 * Created by XWB on 2017-05-29.
 */
public class IndexService extends BaseService{

    public List<Product> query(Product product){
        return Product.dao.query(product);
    }
}
