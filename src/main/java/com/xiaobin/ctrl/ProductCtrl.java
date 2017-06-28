package com.xiaobin.ctrl;

import com.jfinal.aop.Duang;
import com.xiaobin.ctrl.base.BaseCtrl;
import com.xiaobin.model.model.Product;
import com.xiaobin.service.ProductService;

import java.util.List;

/**
 * 产品
 * Created by XWB on 2017-06-03.
 */
public class ProductCtrl extends BaseCtrl{

    public void list(){
        ProductService productService = Duang.duang(ProductService.class);
        List<Product> list = productService.list(getModel(Product.class));
        renderJson(list);
    }
}
