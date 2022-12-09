package com.example.guyunwu.util;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;
import androidx.fragment.app.FragmentActivity;
import com.permissionx.guolindev.PermissionX;

public class AlbumUtil {

    // 判断是否有文件存储权限
    public static void ifHaveAlbumPermission(Activity activity, int code) {

        PermissionX.init((FragmentActivity) activity)
                .permissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .request((allGranted, grantedList, deniedList) -> {
                            if (allGranted) {
                                openAlbum(activity, code);
                            } else {
                                Toast.makeText(activity, "没有访问存储权限！", Toast.LENGTH_LONG).show();
                            }
                        }
                );
    }

    // 打开相册
    private static void openAlbum(Activity activity, int code) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory("android.intent.category.OPENABLE");
        activity.startActivityForResult(intent, code);
    }

}
