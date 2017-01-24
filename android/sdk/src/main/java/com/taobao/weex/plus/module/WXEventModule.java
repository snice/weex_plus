package com.taobao.weex.plus.module;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.WXModule;


public class WXEventModule extends WXModule {

  private static final String WEEX_CATEGORY = "com.taobao.android.intent.category.WEEX";
  private static final String WEEX_ACTION = "com.taobao.android.intent.action.WEEX";


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
}
