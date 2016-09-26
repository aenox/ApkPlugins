package cn.aenox.test1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("wangxi","布局文件："+R.layout.activity_test);
        setContentView(R.layout.activity_test);
    }
}
