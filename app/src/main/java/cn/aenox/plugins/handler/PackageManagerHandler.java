package cn.aenox.plugins.handler;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Message;

import cn.aenox.plugins.PackageParser;
import cn.aenox.plugins.handler.method.MethodHandler;
import cn.aenox.plugins.hook.HookParam;
import cn.aenox.plugins.loader.ReflectUtils;

/**
 * Created by wangxi on 2016/8/5.
 * packageManger方法拦截
 *
 */

public class PackageManagerHandler extends BaseHandler {

    public PackageManagerHandler(Object orignalObj) {
        super(orignalObj);
        init();
    }

    private void init() {
        addMethod("getActivityInfo",new getActivityInfo());
    }

    private static class getActivityInfo extends MethodHandler {
        @Override
        public void beforeMethod(HookParam param) {
            super.beforeMethod(param);
            ComponentName component = (ComponentName) param.args[0];
            ActivityInfo activityInfo = PackageParser.getInstance().getActivityInfo(component);
            if (activityInfo != null) {
                param.setResult(activityInfo);
                param.invokeOrignal = false;
            }
        }
    }

}
