package cn.aenox.plugins.handler;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Message;

import java.net.URL;
import java.util.Set;

import cn.aenox.plugins.hook.HookParam;
import cn.aenox.plugins.handler.method.MethodHandler;
import cn.aenox.plugins.loader.ReflectUtils;

/**
 * Created by wangxi on 2016/8/5.
 * activity的生命周期回调
 *
 */

public class ActivityThreadHandler extends BaseHandler {

    public ActivityThreadHandler(Object orignalObj) {
        super(orignalObj);
        init();
    }

    private void init() {
        addMethod("handleMessage",new HandleMessage());
    }

    private static class HandleMessage extends MethodHandler {
        @Override
        public void beforeMethod(HookParam param) {
            super.beforeMethod(param);
            Message message = (Message) param.args[0];


            Object activityClientRecord = message.obj;
            Intent intent = (Intent) ReflectUtils.getFieldValue(activityClientRecord,"intent");
            ActivityInfo activityInfo = (ActivityInfo) ReflectUtils.getFieldValue(activityClientRecord,"activityInfo");

            if (intent != null) {
                ComponentName componentName = intent.getComponent();
                String packageName = componentName.getPackageName();
                String readlActivity = intent.getStringExtra("real_activity");
                intent.setClassName(packageName,readlActivity);
                activityInfo.targetActivity = readlActivity;
            }


        }
    }

}
