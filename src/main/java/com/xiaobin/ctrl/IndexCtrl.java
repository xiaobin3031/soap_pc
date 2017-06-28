package com.xiaobin.ctrl;

import com.xiaobin.ctrl.base.BaseCtrl;

/**
 * 首页
 * Created by XWB on 2017-05-29.
 */
public class IndexCtrl extends BaseCtrl{

    public void index(){
        render("dashboard.html");
    }
}
