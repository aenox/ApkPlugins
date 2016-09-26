package cn.aenox.plugins.handler.method;

import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;

import cn.aenox.plugins.hook.HookParam;
import cn.aenox.plugins.handler.method.MethodHandler;

/**
 * Created by wangxi on 2016/8/5.
 */

public class StartActivityHandler extends MethodHandler{
    @Override
    public void beforeMethod(HookParam param) {
        super.beforeMethod(param);
        Intent intent = getIntent(param.getArgs());
        if (intent == null) {
            return;
        }
        ComponentName componentName = intent.getComponent();

        intent.putExtra("real_activity",componentName.getClassName());
        intent.setClassName(componentName.getPackageName(),"cn.aenox.plugins.ProxyActivity");

        Log.e("wangxi","StartActivity:"+param.thisObject);
    }

    private Intent getIntent(Object[] args) {
        for (int i=0; i<args.length; i++) {
            Object param = args[i];
            if (param instanceof Intent) {
                return (Intent) param;
            }
        }
        return null;
    }
}
