package cn.aenox.plugins.hook;

/**
 * Created by wangxi on 2016/8/5.
 */

public class HookParam {
    public boolean invokeOrignal = true;
    private Object result;
    public Object[] args;
    public Object thisObject;

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Object getThisObject() {
        return thisObject;
    }

    public void setThisObject(Object thisObject) {
        this.thisObject = thisObject;
    }
}
