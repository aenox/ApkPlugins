package cn.aenox.plugins.loader;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import cn.aenox.plugins.PackageParser;
import cn.aenox.plugins.file.FileUtils;
import dalvik.system.DexFile;

/**
 * Created by wangxi on 2016/8/2.
 */
public class PluginLoader {
    public static final void loadAllPlugins(Context context) {
        File srcDir = new File(FileUtils.getPluginApkDir(context));
        File destDir = new File(FileUtils.getCacheApkDir(context));
        ClassLoader loader = context.getClassLoader();
        try {
            Object pathList = ReflectUtils.getFieldValue(loader, "pathList");// DexPathList
            Method loadDexFile = pathList.getClass().getDeclaredMethod("loadDexFile", File.class, File.class); // DexFile.loadDex()
            loadDexFile.setAccessible(true);

            Field dexElementsField = ReflectUtils.getField(pathList.getClass(),"dexElements"); // Element[] field
            dexElementsField.setAccessible(true);
            Object[] dexElements = (Object[]) dexElementsField.get(pathList);// Element[] value
            Class<?> elementType = dexElementsField.getType().getComponentType();// Element
            File[] files = srcDir.listFiles();
            Object[] destElements = (Object[]) Array.newInstance(elementType, files.length + dexElements.length);
            System.arraycopy(dexElements, 0, destElements, 0, dexElements.length);

            for (int i=0; i<files.length; i++) {
                File file = files[i];
                Object dexFile = loadDexFile.invoke(null, file, destDir);
                Constructor elementConstructor = elementType.getDeclaredConstructor(File.class, boolean.class, File.class, DexFile.class);
                elementConstructor.setAccessible(true);
                Object element = elementConstructor.newInstance(file, false, file, dexFile);
                destElements[dexElements.length+i] = element;

                PackageParser.getInstance().parsePackage(file.getAbsolutePath());
//                AssetManager assetManager = context.getAssets();
//                ReflectUtils.callMethod(assetManager,"addAssetPath",file.getAbsolutePath());
            }
            dexElementsField.set(pathList, destElements);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
