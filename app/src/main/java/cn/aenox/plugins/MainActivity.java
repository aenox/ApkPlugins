package cn.aenox.plugins;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import cn.aenox.plugins.file.FileUtils;
import cn.aenox.plugins.hook.ActivityManagerHook;
import cn.aenox.plugins.hook.ActivityThreadHook;
import cn.aenox.plugins.hook.PackageManagerHook;
import cn.aenox.plugins.loader.PluginLoader;
import dalvik.system.PathClassLoader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void proxy(View view) {
        Test test = new Test();
        Object obj = Proxy.newProxyInstance(test.getClass().getClassLoader(), test.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Log.e("wangxi","invoke:"+method.getName());
                return null;
            }
        });
    }

    public void loadDex(View view) {
        FileUtils.copyApkFromAsset(this);
        PluginLoader.loadAllPlugins(this);
        try {
            hook();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void hook () throws Exception {
        new ActivityManagerHook().install(this);
        new ActivityThreadHook().install(this);
        new PackageManagerHook().install(this);

    }




    public void launch(View view) {
        try {
            Class clz = getClassLoader().loadClass("cn.aenox.test1.Test");
            Object obj = clz.newInstance();
            Method method = clz.getMethod("say");
            method.invoke(obj);

            Log.e("wangxi",getPackageManager()+"");

            Intent intent = new Intent(this,clz);
            intent.setComponent(new ComponentName(getPackageName(),"cn.aenox.test1.MainActivity"));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
