package cn.aenox.plugins;

import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import cn.aenox.plugins.exception.HookException;
import cn.aenox.plugins.loader.ReflectUtils;

/**
 * Created by wangxi on 2016/8/9.
 */

public class PackageParser {
    private HashMap<String,Object> packages = new HashMap<>();

    private static final PackageParser INSTANCE = new PackageParser();
    private Object mParser;
    private static final String CLASS_NAME = "android.content.pm.PackageParser";

    private PackageParser() {
        try {
            mParser = ReflectUtils.newInstance(CLASS_NAME,"");
        } catch (HookException e) {
            e.printStackTrace();
        }
    }

    public static PackageParser getInstance() {
        return INSTANCE;
    }

    public void parsePackage(String apkPath) throws HookException {
        try {
            File sourceFile = new File(apkPath);
            DisplayMetrics metrics = new DisplayMetrics();
            Class[] types = new Class[]{File.class, String.class, DisplayMetrics.class, int.class};
            Object obj = ReflectUtils.callMethod(mParser, "parsePackage", types, sourceFile, sourceFile.getPath(), metrics, 0);

            packages.put(ReflectUtils.getFieldValue(obj, "packageName").toString(), obj);
            Log.e("wangxi", "obj:" + obj);
        } catch (Exception e) {
            throw new HookException(e);
        }
    }

    public ActivityInfo getActivityInfo(ComponentName componentName) {
        Object pack = packages.get(componentName.getPackageName());
        ArrayList activities = (ArrayList) ReflectUtils.getFieldValue(pack,"activities");
        Log.e("wangxi","activities:"+activities.size());
        activities.get(0);
        for (Object obj:activities) {
            ComponentName component = (ComponentName) ReflectUtils.getFieldValue(obj,"componentName");
            Log.e("wangxi","activities:"+component+","+activities.get(0));
            if (component != null && component.equals(componentName)) {
                return (ActivityInfo) ReflectUtils.getFieldValue(obj,"info");
            }
        }
        return null;
    }

}
