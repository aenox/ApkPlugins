package cn.aenox.plugins.hook;

import android.content.Context;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

import cn.aenox.plugins.exception.HookException;
import cn.aenox.plugins.handler.ActivityManagerHandler;
import cn.aenox.plugins.handler.PackageManagerHandler;
import cn.aenox.plugins.loader.ReflectUtils;

/**
 * Created by wangxi on 2016/8/5.
 */

public class PackageManagerHook extends BaseHook {

    public static final String TAG = PackageManagerHook.class.getName();


    @Override
    public Class getHookClass() throws HookException {
        try {
            return Class.forName("android.app.ActivityThread");
        } catch (ClassNotFoundException e) {
            throw new HookException(TAG,e);
        }
    }

    @Override
    public void install(Context context) throws HookException {
        try {
            Class clz = getHookClass();
            Object packageManager = ReflectUtils.callStaticMethod(clz,"getPackageManager");

            Class[] classes = ReflectUtils.getAllInterface(packageManager.getClass());
            Object proxy = Proxy.newProxyInstance(getClass().getClassLoader(), classes, new PackageManagerHandler(packageManager));
            ReflectUtils.setStaticFieldValue(clz,"sPackageManager",proxy);
        } catch (Exception e) {
            throw new HookException(TAG,e);
        }
    }

    @Override
    public void uninstall(Context context) {

    }


}
