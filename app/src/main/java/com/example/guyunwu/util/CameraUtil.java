package com.example.guyunwu.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;
import androidx.fragment.app.FragmentActivity;
import com.permissionx.guolindev.PermissionX;


public class CameraUtil {
    public static class PhotoUriWrapper {
        public Uri photoUri;
    }

    public static void ifHaveCameraPermission(Activity activity, int code, PhotoUriWrapper wrapper) {
        PermissionX.init((FragmentActivity) activity)
                .permissions(Manifest.permission.CAMERA)
                .request((allGranted, grantedList, deniedList) -> {
                            if (allGranted) {
                                openCamera(activity, code, wrapper);
                            } else {
                                Toast.makeText(activity, "没有拍摄和录制权限！", Toast.LENGTH_LONG).show();
                            }
                        }
                );
    }

    // 打开相机
    @SuppressLint("QueryPermissionsNeeded")
    private static void openCamera(Activity activity, int code, PhotoUriWrapper wrapper) {
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断是否有相机
        if (activity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
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
