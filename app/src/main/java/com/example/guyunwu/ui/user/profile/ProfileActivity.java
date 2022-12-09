package com.example.guyunwu.ui.user.profile;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.*;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.guyunwu.R;
import com.example.guyunwu.util.CameraUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.guyunwu.util.AlbumUtil.ifHaveAlbumPermission;
import static com.example.guyunwu.util.CameraUtil.ifHaveCameraPermission;

public class ProfileActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_CAMERA = 103; //相机
    public static final int REQUEST_CODE_ALBUM = 102; //相册
    private final CameraUtil.PhotoUriWrapper photoUriWrapper = new CameraUtil.PhotoUriWrapper();//记录图片地址

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initActionBar();
        findViewById(R.id.layout_birthday).setOnClickListener((v) -> {
            Calendar startTime = Calendar.getInstance();
            startTime.set(1970, 0, 1);
            Calendar endTime = Calendar.getInstance();
            TimePickerView pvTime = new TimePickerBuilder(ProfileActivity.this, new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH) + 1;
                    int day = calendar.get(Calendar.DATE);
                    Toast.makeText(ProfileActivity.this, year + "-" + month + "-" + day, Toast.LENGTH_SHORT).show();
                }
            }).isAlphaGradient(true)
                    .setRangDate(startTime, endTime)
                    .setDate(endTime)
                    .build();
            pvTime.show();
        });
        findViewById(R.id.layout_gender).setOnClickListener((v) -> {
            List<String> gender = new ArrayList<>();
            gender.add("男");
            gender.add("女");
            gender.add("保密");
            List<String> dummy = new ArrayList<>();
            dummy.add(" ");
            OptionsPickerView<String> pvOptions = new OptionsPickerBuilder(ProfileActivity.this, new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3, View v) {
                    Toast.makeText(ProfileActivity.this, gender.get(option2), Toast.LENGTH_SHORT).show();
                }
            }).isAlphaGradient(true)
                    .build();
            pvOptions.setNPicker(dummy, gender, dummy);
            pvOptions.show();
        });
        findViewById(R.id.layout_nickname).setOnClickListener(v -> {
            showInputDialog();
        });
        findViewById(R.id.layout_avatar).setOnClickListener(v -> {
            showBottomDialog();
        });
    }


    @Override
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (photoUriWrapper.photoUri != null) {
            String imgUri = photoUriWrapper.photoUri.toString();
            outState.putString("photoUri", imgUri);
        }

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            String imgUri = savedInstanceState.getString("photoUri");
            photoUriWrapper.photoUri = Uri.parse(imgUri);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {   //返回键的id
            this.finish();
            return false;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initActionBar() {
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setTitle("个人资料");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("昵称");
        View view = getLayoutInflater().inflate(R.layout.dialog_link, null);
        builder.setView(view);
        EditText editText = view.findViewById(R.id.edit);
        editText.setHint("请输入昵称");
        builder.setPositiveButton(R.string.dialog_button_ok, (dialog, which) -> {
            String nickName = editText.getText().toString();
            if (!TextUtils.isEmpty(nickName)) {
                Toast.makeText(this, "你输入的昵称是" + nickName, Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton(R.string.dialog_button_cancel, (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_ALBUM:
                    Glide.with(this).load(data.getData()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into((ImageView) findViewById(R.id.avatar));
                    break;
                case REQUEST_CODE_CAMERA:
                    Glide.with(this).load(photoUriWrapper.photoUri).apply(RequestOptions.bitmapTransform(new CircleCrop())).into((ImageView) findViewById(R.id.avatar));
                    break;
            }
        }
    }

    private void showBottomDialog() {
        final Dialog dialog = new Dialog(this, R.style.DialogTheme);
        View view = View.inflate(this, R.layout.dialog_bottom_menu, null);
        dialog.setContentView(view);

        Window window = dialog.getWindow();
        // 设置弹出位置
        window.setGravity(Gravity.BOTTOM);
        // 设置弹出动画
        window.setWindowAnimations(R.style.main_menu_animStyle);
        // 设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

        dialog.findViewById(R.id.tv_take_photo).setOnClickListener(v -> {
            ifHaveCameraPermission(ProfileActivity.this, REQUEST_CODE_CAMERA, photoUriWrapper);
            dialog.dismiss();
        });

        dialog.findViewById(R.id.tv_take_pic).setOnClickListener(v -> {
            ifHaveAlbumPermission(ProfileActivity.this, REQUEST_CODE_ALBUM);
            dialog.dismiss();
        });

        dialog.findViewById(R.id.tv_cancel).setOnClickListener(v -> dialog.dismiss());

    }
}

