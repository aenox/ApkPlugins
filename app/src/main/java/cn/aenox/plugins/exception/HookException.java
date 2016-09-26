package cn.aenox.plugins.exception;

/**
 * Created by wangxi on 2016/8/5.
 */

public class HookException extends Exception {
    public HookException(String className,Exception e) {
        super(className,e);
    }
    public HookException(Exception e) {
        super(e);
    }
}
