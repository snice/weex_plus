package com.snicecool.weexdemo;

import android.app.Application;

import com.taobao.weex.InitConfig;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.common.WXException;
import com.taobao.weex.plus.adapter.ImageAdapter;
import com.taobao.weex.plus.module.WXEventModule;

public class WXApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        InitConfig config = new InitConfig.Builder().setImgAdapter(new ImageAdapter()).build();
        WXSDKEngine.initialize(this, config);

        try {
            WXSDKEngine.registerModule("event", WXEventModule.class);
        } catch (WXException e) {
            e.printStackTrace();
        }
//        initDebugEnvironment(true, "13.13.13.4");
    }

    private void initDebugEnvironment(boolean enable, String host) {
        WXEnvironment.sRemoteDebugMode = enable;
        WXEnvironment.sRemoteDebugProxyUrl = "ws://" + host + ":8088/debugProxy/native";
    }
}
