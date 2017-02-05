package com.taobao.weex.plus.module;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;
import com.taobao.weex.plus.permissions.ICamera;
import com.taobao.weex.plus.permissions.INeedPermission;
import com.taobao.weex.plus.permissions.Model;


public class WXEventModule extends WXModule {

    public static final String WEEX_CATEGORY = "com.taobao.android.intent.category.WEEX";
    public static final String WEEX_ACTION = "com.taobao.android.intent.action.WEEX";


    @JSMethod(uiThread = true)
    public void openURL(String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        String scheme = Uri.parse(url).getScheme();
        StringBuilder builder = new StringBuilder();
        if (TextUtils.equals("http", scheme) || TextUtils.equals("https", scheme) || TextUtils.equals("file", scheme)) {
            builder.append(url);
        } else {
            builder.append("http:");
            builder.append(url);
        }

        Uri uri = Uri.parse(builder.toString());
        Intent intent = new Intent(WEEX_ACTION, uri);
        intent.addCategory(WEEX_CATEGORY);
        mWXSDKInstance.getContext().startActivity(intent);

        if (mWXSDKInstance.checkModuleEventRegistered("event", this)) {
            mWXSDKInstance.fireModuleEvent("event", this, null);
        }
    }

    @JSMethod(uiThread = true)
    public void requestModel(String model, JSCallback success) {
        if (TextUtils.isEmpty(model))
            return;
        if (mWXSDKInstance.getContext() instanceof INeedPermission) {
            INeedPermission needPermission = (INeedPermission) mWXSDKInstance.getContext();
            if (needPermission.isAuth(model, success))
                done(model, success);
            else
                request(model);
        }
    }

    private void request(String model) {
        switch (model) {
            case Model.CAMERA:
                ((ICamera) mWXSDKInstance.getContext()).requestCamera();
                break;
        }
    }

    private void done(String model, JSCallback jsCallback) {
        switch (model) {
            case Model.CAMERA:
                ((ICamera) mWXSDKInstance.getContext()).doneCamera(jsCallback);
                break;
        }
    }
}
