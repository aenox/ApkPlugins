package cn.aenox.plugins.file;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by wangxi on 2016/7/29.
 */
public class FileUtils {

    private FileUtils() {
    }

    public static void copyApkFromAsset(Context context) {
        String[] paths = null;
        try {
            paths = context.getAssets().list("plugins");
            for (String file : paths) {
                Log.e("wangxi",file);
                InputStream is = context.getAssets().open("plugins"+ File.separator+file);
                FileOutputStream fos = new FileOutputStream(new File(getPluginApkDir(context),file));
                byte[] buffer = new byte[1024];
                int len;
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }
                is.close();
                fos.close();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getPluginApkDir(Context context) {
        return makeDirIfNotExists(context, "src");
    }
    public static String getCacheApkDir(Context context) {
        return makeDirIfNotExists(context, "opt");
    }

    private static String makeDirIfNotExists(Context context, String subDir) {
        File sub = new File(context.getCacheDir().getParentFile()+File.separator+"plugins",subDir);
        if (!sub.exists()) {
            sub.mkdirs();
        }
        return sub.getAbsolutePath();

    }


}
