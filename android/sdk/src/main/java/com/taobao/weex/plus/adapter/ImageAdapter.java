package com.taobao.weex.plus.adapter;

import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.taobao.weex.plus.util.ResourceUtil;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.adapter.IWXImgLoaderAdapter;
import com.taobao.weex.common.WXImageStrategy;
import com.taobao.weex.dom.WXImageQuality;

/**
 * Created by zhuzhe on 2017/1/17.
 */

public class ImageAdapter implements IWXImgLoaderAdapter {
    @Override
    public void setImage(final String url, final ImageView view, final WXImageQuality quality, final WXImageStrategy strategy) {
        WXSDKManager.getInstance().postOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (view == null || view.getLayoutParams() == null) {
                    return;
                }
                if (TextUtils.isEmpty(url)) {
                    view.setImageBitmap(null);
                    return;
                }
                String temp = url;
                if (url.startsWith("//")) {
                    temp = "http:" + url;
                }
                if (view.getLayoutParams().width <= 0 || view.getLayoutParams().height <= 0) {
                    return;
                }
                DrawableTypeRequest request = getDrawableTypeRequest(temp);
                placeHolder(request, strategy);
                request.centerCrop()
                        .crossFade()
                        .listener(new RequestListener() {
                            @Override
                            public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
                                if (strategy.getImageListener() != null) {
                                    strategy.getImageListener().onImageFinish(url, view, false, null);
                                }
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
                                if (strategy.getImageListener() != null) {
                                    strategy.getImageListener().onImageFinish(url, view, true, null);
                                }
                                return false;
                            }
                        })
                        .into(view);
            }
        }, 0);
    }

    private void placeHolder(DrawableTypeRequest request, WXImageStrategy strategy) {
        if (!TextUtils.isEmpty(strategy.placeHolder)) {
            String placeHolder = strategy.placeHolder;
            if (placeHolder.startsWith("mipmap:") || placeHolder.startsWith("drawable:")) {
                Uri resourceUri = Uri.parse(getAndroidResource(placeHolder));
                try {
                    int resourceId = ResourceUtil.getResourceId(WXEnvironment.getApplication(), resourceUri);
                    request.placeholder(resourceId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private DrawableTypeRequest getDrawableTypeRequest(String temp) {
        DrawableTypeRequest request;
        if (temp.startsWith("file:") || temp.startsWith("http:") || temp.startsWith("https:"))
            request = Glide.with(WXEnvironment.getApplication()).load(Uri.parse(temp));
        else {
            if (temp.startsWith("mipmap:") || temp.startsWith("drawable:")) {
                temp = getAndroidResource(temp);
            }
            if (temp.startsWith("data:")) { // base64 image
                temp = temp.substring(temp.indexOf("base64,") + 7);
                byte[] decodedString = Base64.decode(temp, Base64.DEFAULT);
                request = Glide.with(WXEnvironment.getApplication()).load(decodedString);
            } else {
                request = Glide.with(WXEnvironment.getApplication()).load(temp);
            }
        }
        return request;
    }

    @NonNull
    private String getAndroidResource(String resource) {
        return ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + WXEnvironment.getApplication().getPackageName() + "/" + resource.replace("://", "/");
    }
}
