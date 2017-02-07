package com.taobao.weex.plus.permissions.service;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.plus.util.JSCallbackUtil;
import com.taobao.weex.plus.util.UriUtil;

import java.io.File;
import java.util.UUID;

/**
 * Created by zhuzhe on 2017/2/5.
 */

public class CameraService {
    public final static int TAKE_BIG_PICTURE = 102;
    public final static int CHOOSE_SMALL_PICTURE = 103;
    public final static int CROP_SMALL_PICTURE = 104;


    Activity activity;
    final static int HEAD_SIZE = 500;
    String imageFilePath;
    JSCallback jsCallback;

    public CameraService(Activity activity) {
        this.activity = activity;
    }

    public void done(JSCallback jsCallback) {
        this.jsCallback = jsCallback;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("修改用户头像").setItems(new String[]{"拍照上传", "相册选择"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // 拍照
                //设置图片的保存路径,作为全局变量
                imageFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + UUID.randomUUID() + ".jpg";
                if (i == 0) {
                    Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//跳转到相机Activity
                    it.putExtra(MediaStore.EXTRA_OUTPUT, getExtraOutput());//告诉相机拍摄完毕输出图片到指定的Uri
                    activity.startActivityForResult(it, TAKE_BIG_PICTURE);
                } else if (i == 1) {
                    // 相册选取
                    Intent intent1 = new Intent(Intent.ACTION_GET_CONTENT);
                    intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    intent1.putExtra("return-data", true);
                    intent1.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
                    intent1.putExtra("noFaceDetection", true);
                    activity.startActivityForResult(intent1, CHOOSE_SMALL_PICTURE);
                }
            }
        });
        builder.show();
    }

    private Uri getExtraOutput() {
        return UriUtil.compNVersion(activity, new File(imageFilePath));
    }

    private void cropImageUri(Uri sourceUri, Uri extra, int outputX, int outputY, int requestCode) {
//        activity.grantUriPermission("com.google.android.apps.photos", sourceUri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setDataAndType(sourceUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, extra);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        activity.startActivityForResult(intent, requestCode);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_BIG_PICTURE:
                if (resultCode == Activity.RESULT_OK) {
                    Uri source = getExtraOutput();
                    cropImageUri(source, source, HEAD_SIZE, HEAD_SIZE, CROP_SMALL_PICTURE);
                } else {
                    jsCallback.invoke(JSCallbackUtil.failed("用户取消"));
                    break;
                }
                break;
            case CHOOSE_SMALL_PICTURE:
                Uri result = data == null || resultCode != Activity.RESULT_OK ? null
                        : data.getData();
                if (result != null) {
                    String sourcePath = UriUtil.getImageAbsolutePath(activity, result);
                    if (!TextUtils.isEmpty(sourcePath)) {
                        result = UriUtil.compNVersion(activity, new File(sourcePath));
                    }
                }
                if (result == null) {
                    jsCallback.invoke(JSCallbackUtil.failed("用户取消"));
                    break;
                }
                cropImageUri(result, Uri.fromFile(new File(imageFilePath)), HEAD_SIZE, HEAD_SIZE, CROP_SMALL_PICTURE);
                break;
            case CROP_SMALL_PICTURE:
//                upload(imageFilePath);
                jsCallback.invoke(JSCallbackUtil.success(imageFilePath));
                break;
            default:
                break;
        }
    }
}
