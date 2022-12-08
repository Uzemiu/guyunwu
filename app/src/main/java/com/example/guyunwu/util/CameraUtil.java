package com.example.guyunwu.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;


public class CameraUtil {
    public static class PhotoUriWrapper {
        public Uri photoUri;
    }

    public static void ifHaveCameraPermission(Activity activity, int code, PhotoUriWrapper wrapper) {
        if (!AndPermission.hasPermissions(activity, Permission.Group.CAMERA)) {
            // 动态申请权限
            AndPermission.with(activity).runtime().permission(Permission.Group.CAMERA)
                    .onGranted(permissions -> {
                        openCamera(activity, code, wrapper);
                    })
                    .onDenied(denieds -> {
                        if (denieds != null && denieds.size() > 0) {
                            for (String denied : denieds) {
                                if (!activity.shouldShowRequestPermissionRationale(denied)) {
                                    DialogUtil.permissionDialog(activity, "没有拍摄和录制权限！");
                                    break;
                                }
                            }
                        }
                    }).start();
        } else {
            // 有权限 打开相机
            openCamera(activity, code, wrapper);
        }
    }

    // 打开相机
    @SuppressLint("QueryPermissionsNeeded")
    private static void openCamera(Activity activity, int code, PhotoUriWrapper wrapper) {
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断是否有相机
        if (captureIntent.resolveActivity(activity.getPackageManager()) != null) {
            Uri photoUri = createImageUri(activity);
            wrapper.photoUri = photoUri;
            if (photoUri != null) {
                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                captureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                captureIntent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                activity.startActivityForResult(captureIntent, code);
            }
        }
    }

    private static Uri createImageUri(Activity activity) {
        String status = Environment.getExternalStorageState();
        // 判断是否有SD卡,优先使用SD卡存储,当没有SD卡时使用手机存储
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new ContentValues());
        } else {
            return activity.getContentResolver().insert(MediaStore.Images.Media.INTERNAL_CONTENT_URI, new ContentValues());
        }
    }
}
