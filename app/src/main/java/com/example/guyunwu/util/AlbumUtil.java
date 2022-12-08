package com.example.guyunwu.util;

import android.app.Activity;
import android.content.Intent;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

public class AlbumUtil {

    // 判断是否有文件存储权限
    public static void ifHaveAlbumPermission(Activity activity, int code) {
        //  Permission.Group.STORAGE：文件存储权限
        if (!AndPermission.hasPermissions(activity, Permission.Group.STORAGE)) {
            AndPermission.with(activity).runtime().permission(Permission.Group.STORAGE).onGranted(permissions -> {
                openAlbum(activity, code);
            }).onDenied(denieds -> {
                if (denieds != null && denieds.size() > 0) {
                    for (String denied : denieds) {
                        if (!activity.shouldShowRequestPermissionRationale(denied)) {
                            DialogUtil.permissionDialog(activity, "没有访问存储权限！");
                            break;
                        }
                    }
                }
            }).start();
        } else {
            openAlbum(activity, code);
        }
    }

    // 打开相册
    private static void openAlbum(Activity activity, int code) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory("android.intent.category.OPENABLE");
        activity.startActivityForResult(intent, code);
    }

}
