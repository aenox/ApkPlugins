package cn.aenox.plugins.handler;

import cn.aenox.plugins.handler.method.StartActivityHandler;

/**
 * Created by wangxi on 2016/8/5.
 */

public class ActivityManagerHandler extends BaseHandler {

    public ActivityManagerHandler(Object orignalObj) {
        super(orignalObj);
        init();
    }

    private void init() {
        addMethod("startActivity",new StartActivityHandler());
    }

}
