package com.xiaobin.model.model;

import com.jfinal.kit.Prop;
import com.xiaobin.model.base.BaseProduct;
import com.xiaobin.util.Util;

import java.util.List;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class Product extends BaseProduct<Product> {
	public static final Product dao = new Product().dao();

	public List<Product> query(Product product){
		Prop p = Util.getProp("dao/product");
		return find(p.get("query"));
	}
}