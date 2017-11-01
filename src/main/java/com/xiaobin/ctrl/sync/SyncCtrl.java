package com.xiaobin.ctrl.sync;

import com.xiaobin.ctrl.base.BaseCtrl;

/**
 * 同步数据
 * Created by XWB on 2017-10-31.
 */
public class SyncCtrl extends BaseCtrl{

    public void index(){
        renderJson(getSuccessModel());
    }
}
