package cn.aenox.plugins.handler;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

import cn.aenox.plugins.hook.HookParam;
import cn.aenox.plugins.handler.method.MethodHandler;

/**
 * Created by wangxi on 2016/8/5.
 */

public abstract class BaseHandler implements InvocationHandler {

    private Object mOriginalObj;

    private HashMap<String, MethodHandler> methods = new HashMap<>();

    public BaseHandler(Object obj) {
        mOriginalObj = obj;
    }

    public void addMethod(String methodName,MethodHandler handler) {
        methods.put(methodName,handler);
    }

    private MethodHandler getMethodHandler(Method method) {
        return methods.get(method.getName());

    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.e("wangxi","method:"+method.getName());

        MethodHandler methodHandler = getMethodHandler(method);
        if (methodHandler == null) {
            return method.invoke(mOriginalObj, args);
        }

        HookParam param = new HookParam();
        param.setArgs(args);
        param.thisObject = mOriginalObj;
        param.setResult(null);
        methodHandler.beforeMethod(param);
        if (param.invokeOrignal) {
            Object result = method.invoke(mOriginalObj, param.getArgs());
            param.setResult(result);
        }

        methodHandler.afterMethod(param);
        return param.getResult();
    }

}
