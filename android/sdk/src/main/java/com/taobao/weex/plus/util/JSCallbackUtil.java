package com.taobao.weex.plus.util;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by zhuzhe on 2017/2/5.
 */

public class JSCallbackUtil {
    public static final String CALLBACK_RESULT = "result";
    public static final String CALLBACK_MESSAGE = "message";

    public static final String MSG_SUCCESS = "WX_SUCCESS";
    public static final String MSG_FAILED = "WX_FAILED";
    public static final String MSG_PARAM_ERR = "WX_PARAM_ERR";

    public static JSONObject success(String message) {
        JSONObject result = new JSONObject();
        result.put(CALLBACK_RESULT, MSG_SUCCESS);
        result.put(CALLBACK_MESSAGE, message);
        return result;
    }

    public static JSONObject failed(String message) {
        JSONObject result = new JSONObject();
        result.put(CALLBACK_RESULT, MSG_FAILED);
        result.put(CALLBACK_MESSAGE, message);
        return result;
    }
}
