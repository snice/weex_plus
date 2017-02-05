package com.snicecool.weexdemo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.zxing.Result;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKEngine;

import cn.avcon.zxing.CustomViewFinderView;
import me.dm7.barcodescanner.core.IViewFinder;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

import static com.taobao.weex.plus.module.WXEventModule.WEEX_ACTION;
import static com.taobao.weex.plus.module.WXEventModule.WEEX_CATEGORY;

@RuntimePermissions
public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    ZXingScannerView mScannerView;
    FrameLayout flZxingContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScanActivityPermissionsDispatcher.permissionCameraWithCheck(this);
        setContentView(R.layout.activity_scan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        flZxingContainer = (FrameLayout) findViewById(R.id.container);
        mScannerView = new ZXingScannerView(this) {
            @Override
            protected IViewFinder createViewFinderView(Context context) {
                return new CustomViewFinderView(context);
            }
        };
        mScannerView.setZoom(5);
        flZxingContainer.addView(mScannerView);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        String code = rawResult.getText();
        if (!TextUtils.isEmpty(code)) {
            Uri uri = Uri.parse(code);
            if (uri.getPath().contains("dynamic/replace")) {
                Intent intent = new Intent("weex.intent.action.dynamic", uri);
                intent.addCategory("weex.intent.category.dynamic");
                startActivity(intent);
                finish();
            } else if (uri.getQueryParameterNames().contains("_wx_devtool")) {
                WXEnvironment.sRemoteDebugProxyUrl = uri.getQueryParameter("_wx_devtool");
                WXSDKEngine.reload();
                Toast.makeText(this, "devtool", Toast.LENGTH_SHORT).show();
                finish();
                return;
            } else if (code.contains("_wx_debug")) {
                uri = Uri.parse(code);
                String debug_url = uri.getQueryParameter("_wx_debug");
                WXSDKEngine.switchDebugModel(true, debug_url);
                finish();
            } else {
                Toast.makeText(this, rawResult.getText(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(WEEX_ACTION, uri);
                intent.addCategory(WEEX_CATEGORY);
                startActivity(intent);
                finish();
            }
        }
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    void permissionCamera() {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ScanActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
}
