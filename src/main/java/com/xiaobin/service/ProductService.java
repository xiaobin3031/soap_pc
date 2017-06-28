package com.xiaobin.service;

import com.xiaobin.model.model.Product;
import com.xiaobin.service.base.BaseService;

import java.util.List;

/**
 * 产品服务
 * Created by XWB on 2017-06-03.
 */
public class ProductService extends BaseService{

    public List<Product> list(Product product){
        return Product.dao.query(product);
    }
}
