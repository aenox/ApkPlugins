package cn.aenox.plugins.handler.method;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.os.Message;

import cn.aenox.plugins.loader.ReflectUtils;

/**
 * Created by wangxi on 2016/8/8.
 */

public class HandlerCallback implements Handler.Callback {
    @Override
    public boolean handleMessage(Message msg) {
        if (msg.what == 100) {
            Object activityClientRecord = msg.obj;
            Intent intent = (Intent) ReflectUtils.getFieldValue(activityClientRecord,"intent");
            ActivityInfo activityInfo = (ActivityInfo) ReflectUtils.getFieldValue(activityClientRecord,"activityInfo");

            if (intent != null) {
                ComponentName componentName = intent.getComponent();
                String packageName = componentName.getPackageName();
                String readlActivity = intent.getStringExtra("real_activity");
                String readlPackage = "cn.aenox.test1";
                intent.setClassName(readlPackage,readlActivity);
                activityInfo.targetActivity = readlActivity;
            }
        }
        return false;
    }
}
