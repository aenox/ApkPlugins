package cn.aenox.plugins.hook;

import android.content.Context;

import cn.aenox.plugins.exception.HookException;

/**
 * Created by wangxi on 2016/8/5.
 */

public interface IHook {
    void install(Context context) throws HookException;
    void uninstall(Context context);
    Class getHookClass() throws HookException;

    boolean isEnabled();


}
