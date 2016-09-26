/*
package cn.aenox.plugins.hook;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;

import cn.aenox.plugins.exception.HookException;
import cn.aenox.plugins.handler.method.HandlerCallback;
import cn.aenox.plugins.loader.ReflectUtils;

*/
/**
 * Created by wangxi on 2016/8/8.
 *//*


public class ResourcesHook extends BaseHook {

    @Override
    public Class getHookClass() throws HookException {
        return Resources.class;
    }

    @Override
    public void install(Context context) throws HookException {
        super.install(context);
        try {
            Class clz = getHookClass();
            Resources resources = Resources.getSystem();
            resources

        } catch (Exception e) {
            throw new HookException(e);
        }
    }

    */
/**
     * 监听activity生命周期，将代理activity替换为真实的activity
     * ActivityThread的mH字段添加Callback
     *
     * @param activityThread
     *//*

    private void hookHCallback(Object activityThread) {
        Handler handler = (Handler) ReflectUtils.getFieldValue(activityThread, "mH");
        Object callback = ReflectUtils.getFieldValue(handler, "mCallback");
        if (callback == null) {
            callback = new HandlerCallback();
        }
        ReflectUtils.setFieldValue(handler, "mCallback", callback);
    }

}
*/
