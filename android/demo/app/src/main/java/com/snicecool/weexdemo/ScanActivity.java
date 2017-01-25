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
import android.widget.FrameLayout;

import com.google.zxing.Result;

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
        String result = rawResult.getText();
        Uri uri = Uri.parse(result);
        Intent intent = new Intent(WEEX_ACTION, uri);
        intent.addCategory(WEEX_CATEGORY);
        startActivity(intent);
        finish();
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
