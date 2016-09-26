package cn.aenox.plugins.hook;

import android.content.Context;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

import cn.aenox.plugins.exception.HookException;
import cn.aenox.plugins.handler.ActivityManagerHandler;
import cn.aenox.plugins.loader.ReflectUtils;

/**
 * Created by wangxi on 2016/8/5.
 */

public class ActivityManagerHook extends BaseHook {

    public static final String TAG = ActivityManagerHook.class.getName();

    @Override
    public void install(Context context) throws HookException {
        try {
            Class clz = getHookClass();
            Object singleton = ReflectUtils.getStaticFieldValue(clz, "gDefault");
            Field amProxyField = ReflectUtils.getField(singleton.getClass(), "mInstance");
            final Object amProxy = amProxyField.get(singleton);

            Class[] classes = ReflectUtils.getAllInterface(amProxy.getClass());
            Object proxy = Proxy.newProxyInstance(amProxy.getClass().getClassLoader(), classes, new ActivityManagerHandler(amProxy));
            amProxyField.set(singleton, proxy);
        } catch (Exception e) {
            throw new HookException(TAG,e);
        }
    }

    @Override
    public void uninstall(Context context) {

    }

    @Override
    public Class getHookClass() throws HookException {
        try {
            return Class.forName("android.app.ActivityManagerNative");
        } catch (ClassNotFoundException e) {
            throw new HookException("android.app.ActivityManagerNative",e);
        }
    }
}
