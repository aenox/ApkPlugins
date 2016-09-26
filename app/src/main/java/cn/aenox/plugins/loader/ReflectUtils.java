package cn.aenox.plugins.loader;

import android.util.Log;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import cn.aenox.plugins.exception.HookException;

import static android.R.id.list;

/**
 * Created by wangxi on 2016/8/2.
 */
public final class ReflectUtils {
    public static Object getFieldValue(Object obj,String fieldName) {
        Field field = getField(obj.getClass(),fieldName);
        try {
            return field == null ? null : field.get(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static void setFieldValue(Object obj, String fieldName,Object value) {
        Field field = getField(obj.getClass(),fieldName);
        if (field != null) {
            try {
                field.setAccessible(true);
                field.set(obj,value);
            } catch (IllegalAccessException e) {
//                e.printStackTrace();
            }
        }
    }

    public static void setStaticFieldValue(Class clz, String fieldName,Object value) {
        Field field = getField(clz,fieldName);
        if (field != null) {
            try {
                field.setAccessible(true);
                field.set(null,value);
            } catch (IllegalAccessException e) {
//                e.printStackTrace();
            }
        }
    }

    public static Object getStaticFieldValue(Class clz,String fieldName) {
        Field field = getField(clz,fieldName);
        if (field != null) {
            try {
                return field.get(null);
            } catch (IllegalAccessException e) {
//                e.printStackTrace();
            }
        }
        return null;
    }

    public static Field getField(Class clz,String fieldName) {
        while (clz != Object.class){
            try {
                Field field = clz.getDeclaredField(fieldName);
                if (field != null) {
                    field.setAccessible(true);
                    return field;
                }
            } catch (Exception e){
//                e.printStackTrace();
            }
            clz = clz.getSuperclass();
        }
        return null;
    }

    public static Class[] getAllInterface(Class clz) {
        HashSet<Class> set = new HashSet<>();
        getAllInterface(set,clz);
        return set.toArray(new Class[set.size()]);
    }

    private static void getAllInterface(HashSet<Class> set,Class clz) {
        if (clz == null || clz == Object.class) {
            return;
        }
        Class[] classes;
        while (clz != null) {
            classes = clz.getInterfaces();
            for (Class c:classes) {
                if (set.add(c)) {
                    getAllInterface(set,c);
                }
            }
            clz = clz.getSuperclass();
        }

    }

    public static Class[] getInterface(Class clz,String interfaceName) {
        if (clz == null || clz == Object.class) {
            return null;
        }
        Class[] classes;
        while (clz != null) {
            classes = clz.getInterfaces();
            for (Class c:classes) {
                if (c.getName().equals(interfaceName)) {
                    return new Class[]{c};
                } else {
                    Class[] ret = getInterface(c,interfaceName);
                    if (ret != null) {
                        return ret;
                    }
                }

            }
            clz = clz.getSuperclass();
        }
        return null;
    }


    public static Method getMethod(Class clz,String methodName) {
        Method method;
        try {
            method = clz.getDeclaredMethod(methodName);
            method.setAccessible(true);
            return method;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static Object callMethod(Object obj, String methodName,Class[] paramsType,Object ...params) throws HookException {
        try {
            Method method = obj.getClass().getDeclaredMethod(methodName,paramsType);
            if (method != null) {
                method.setAccessible(true);
                return method.invoke(obj, params);
            }
        } catch (Exception e) {
            throw new HookException(e);
        }
        return null;
    }

    public static Object callMethod(Object obj, String methodName,Object ...params) throws HookException {
        try {
            Class[] paramClass = new Class[params == null ? 0:params.length];
            for (int i=0; i<paramClass.length; i++) {
                if (i == 3) {
                    paramClass[i] = int.class;
                } else
                paramClass[i] = params[i].getClass();
            }
            Method method = obj.getClass().getDeclaredMethod(methodName,paramClass);
            if (method != null) {
                method.setAccessible(true);
                return method.invoke(obj, params);
            }
        } catch (Exception e) {
            throw new HookException(e);
        }
        return null;
    }

    public static Object callStaticMethod(Class clz, String methodName,Object ...params) throws HookException {
        try {
            Method method = clz.getDeclaredMethod(methodName);
            if (method != null) {
                method.setAccessible(true);
                return method.invoke(null, params);
            }
        } catch (Exception e) {
            throw new HookException(e);
        }
        return null;
    }


    public static Object newInstance(String className,Object...params) throws HookException {
        try {
            Class clz = Class.forName(className);
            return clz.getDeclaredConstructor(String.class).newInstance(params);
        } catch (Exception e) {
            throw new HookException(e);
        }
    }
}
