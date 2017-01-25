package com.snicecool.weexdemo;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.taobao.weex.plus.WXPageFragment;

public class FragmentActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.activity_fragment, WXPageFragment.newInstance("https://www.zhuzhe.wang/examples/mobile/index.js"), "test");
        transaction.commit();
    }
}
