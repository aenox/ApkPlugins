package cn.aenox.plugins.hook;

import android.content.Context;
import android.os.Handler;

import cn.aenox.plugins.exception.HookException;
import cn.aenox.plugins.handler.method.HandlerCallback;
import cn.aenox.plugins.loader.ReflectUtils;

/**
 * Created by wangxi on 2016/8/8.
 */

public class PackageParserHook extends BaseHook {
    @Override
    public Class getHookClass() throws HookException {
        try {
            return Class.forName("android.app.ActivityThread");
        } catch (ClassNotFoundException e) {
            throw new HookException("android.app.ActivityThread",e);
        }
    }

    @Override
    public void install(Context context) throws HookException {
        super.install(context);
        try {
            Class clz = getHookClass();
            Object activityThread = ReflectUtils.callStaticMethod(clz,"currentActivityThread");
            Handler handler = (Handler) ReflectUtils.getFieldValue(activityThread, "mH");
            Object callback = ReflectUtils.getFieldValue(handler,"mCallback");
            if (callback == null) {
                callback = new HandlerCallback();
            }
            ReflectUtils.setFieldValue(handler,"mCallback",callback);

        } catch (Exception e) {
            throw new HookException(e);
        }
    }
}
