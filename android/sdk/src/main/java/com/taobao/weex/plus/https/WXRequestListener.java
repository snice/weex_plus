package com.taobao.weex.plus.https;

public interface WXRequestListener {

  void onSuccess(WXHttpTask task);

  void onError(WXHttpTask task);
}
