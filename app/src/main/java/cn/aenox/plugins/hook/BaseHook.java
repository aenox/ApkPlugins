package cn.aenox.plugins.hook;

import android.content.Context;

import cn.aenox.plugins.exception.HookException;

/**
 * Created by wangxi on 2016/8/5.
 */

public abstract class BaseHook implements IHook {
    private boolean isEnable;
    @Override
    public void install(Context context) throws HookException {

    }

    @Override
    public void uninstall(Context context) {

    }

    @Override
    public boolean isEnabled() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }


}
