package com.taobao.weex.plus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.WXRenderStrategy;

/**
 * Created by zhuzhe on 2017/1/25.
 */

public class WXPageFragment extends Fragment implements IWXRenderListener {
    public static WXPageFragment newInstance(String url) {
        Bundle args = new Bundle();
        args.putString("url", url);
        WXPageFragment fragment = new WXPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    WXSDKInstance mWXSDKInstance;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWXSDKInstance = new WXSDKInstance(getActivity());
        mWXSDKInstance.registerRenderListener(this);
        mWXSDKInstance.renderByUrl("Vue首页", getArguments().getString("url"), null, null, WXRenderStrategy.APPEND_ASYNC);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return new FrameLayout(getActivity());
    }

    @Override
    public void onViewCreated(WXSDKInstance instance, View view) {
        ((FrameLayout) getView()).addView(view);
    }

    @Override
    public void onRenderSuccess(WXSDKInstance instance, int width, int height) {

    }

    @Override
    public void onRefreshSuccess(WXSDKInstance instance, int width, int height) {

    }

    @Override
    public void onException(WXSDKInstance instance, String errCode, String msg) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityStop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityDestroy();
        }
    }
}
