package com.snicecool.weexdemo;

import android.app.Application;

import com.taobao.weex.InitConfig;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.common.WXException;
import com.taobao.weex.plus.adapter.ImageAdapter;
import com.taobao.weex.plus.module.WXEventModule;

public class WXApplication extends Application {
    final static String DEBUG_SERVER_HOST = "13.13.13.4";
    final static boolean isDebug = false;

    @Override
    public void onCreate() {
        super.onCreate();
        InitConfig.Builder builder = new InitConfig.Builder().setImgAdapter(new ImageAdapter());
        if (isDebug) {
            initDebugEnvironment(true, true, DEBUG_SERVER_HOST);
//            builder.setDebugAdapter(new com.taobao.weex.PlayDebugAdapter());
//            builder.setWebSocketAdapterFactory(new com.taobao.weex.DefaultWebSocketAdapterFactory());
        }
        WXSDKEngine.initialize(this, builder.build());


        try {
            WXSDKEngine.registerModule("event", WXEventModule.class);
        } catch (WXException e) {
            e.printStackTrace();
        }
    }

    private void initDebugEnvironment(boolean connectable, boolean debuggable, String host) {
        WXEnvironment.sDebugServerConnectable = connectable;
        WXEnvironment.sRemoteDebugMode = debuggable;
        WXEnvironment.sRemoteDebugProxyUrl = "ws://" + host + ":8088/debugProxy/native";
    }
}
