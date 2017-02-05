package com.taobao.weex.plus.permissions;

import com.taobao.weex.bridge.JSCallback;

/**
 * Created by zhuzhe on 2017/2/5.
 */

public interface ICamera {
    void requestCamera();

    void doneCamera(JSCallback jsCallback);
}
