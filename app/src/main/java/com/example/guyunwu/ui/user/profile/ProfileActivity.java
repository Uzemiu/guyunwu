package com.example.guyunwu.ui.user.profile;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.*;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.example.guyunwu.api.BaseResponse;
import com.example.guyunwu.api.FileUploadRequest;
import com.example.guyunwu.api.RequestModule;
import com.example.guyunwu.api.UserRequest;
import com.example.guyunwu.api.req.UserReq;
import com.example.guyunwu.api.resp.UserResp;
import com.example.guyunwu.util.CameraUtil;
import com.example.guyunwu.util.SharedPreferencesUtil;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.guyunwu.util.AlbumUtil.REQUEST_CODE_ALBUM;
import static com.example.guyunwu.util.AlbumUtil.ifHaveAlbumPermission;
import static com.example.guyunwu.util.CameraUtil.REQUEST_CODE_CAMERA;
import static com.example.guyunwu.util.CameraUtil.ifHaveCameraPermission;
import static com.example.guyunwu.util.FileUtil.uriToFileApiQ;

public class ProfileActivity extends AppCompatActivity {

    private UserReq userReq;
    private static final String TAG = "ProfileActivity";

    private final CameraUtil.PhotoUriWrapper photoUriWrapper = new CameraUtil.PhotoUriWrapper();// 记录图片地址

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initActionBar();
        initBinding();
        initUserReq();
        initView();
    }

    private void initView() {
        String avatar = userReq.getAvatar();
        if (avatar != null) {
            Glide.with(this).load(avatar).apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into((ImageView) findViewById(R.id.avatar));
        } else {
            Glide.with(this).load(R.drawable.ic_user_user_24dp).apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into((ImageView) findViewById(R.id.avatar));
        }
        TextView textGender = findViewById(R.id.gender);
        Integer gender = userReq.getGender();
        if (gender == 0) {
            textGender.setText("男");
        } else if (gender == 1) {
            textGender.setText("女");
        } else {
            textGender.setText("保密");
        }
        TextView textUserName = findViewById(R.id.nickname);
        textUserName.setText(userReq.getUsername() == null ? "未登录" : userReq.getUsername());
        Date birthDate = userReq.getBirthDate();
        TextView textBirthDate = findViewById(R.id.birthday);
        if (birthDate == null) {
            textBirthDate.setText("未填写");
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            textBirthDate.setText(formatter.format(birthDate));
        }
    }

    private void initUserReq() {
        userReq = new UserReq();
        userReq.setAvatar(SharedPreferencesUtil.getString("avatar", null));
        userReq.setGender(SharedPreferencesUtil.getInt("gender", 2));
        long birthDate = SharedPreferencesUtil.getLong("birthDate", 0);
        Date date = null;
        if (birthDate != 0) {
            date = new Date(birthDate);
        }
        userReq.setBirthDate(date);
        userReq.setUsername(SharedPreferencesUtil.getString("userName", null));
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
            if (imgUri != null) {
                photoUriWrapper.photoUri = Uri.parse(imgUri);
            }
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
                userReq.setUsername(nickName);
                updateRequest();
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
                    // Glide.with(this).load(data.getData()).apply(RequestOptions.bitmapTransform(new
                    // CircleCrop())).into((ImageView) findViewById(R.id.avatar));
                    uploadAvatar(uriToFileApiQ(data.getData(), this));
                    break;
                case REQUEST_CODE_CAMERA:
                    // Glide.with(this).load(photoUriWrapper.photoUri).apply(RequestOptions.bitmapTransform(new
                    // CircleCrop())).into((ImageView) findViewById(R.id.avatar));
                    uploadAvatar(uriToFileApiQ(photoUriWrapper.photoUri, this));
                    break;
            }
        }
    }

    private void uploadAvatar(File image) {
        FileUploadRequest fileUploadRequest = RequestModule.FILE_UPLOAD_REQUEST;

        RequestBody imageBody = RequestBody.create(MediaType.parse("image/*"), image);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", image.getName(), imageBody);

        fileUploadRequest.uploadImage(body, SharedPreferencesUtil.getString("phoneNumber", "1145141919810"))
                .enqueue(new Callback<BaseResponse<String>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                        if (response.body() == null || response.body().getCode() != 200) {
                            onFailure(call, new Throwable("上传失败"));
                            return;
                        }
                        Toast.makeText(ProfileActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                        String url = response.body().getData();
                        userReq.setAvatar(url);
                        updateRequest();
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<String>> call, Throwable t) {
                        Toast.makeText(ProfileActivity.this,
                                t.getMessage() == null ? "上传失败" : t.getMessage() == null ? "请求失败" : t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onFailure: ", t);
                    }
                });
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

    private void updateRequest() {
        UserRequest userRequest = RequestModule.USER_REQUEST;
        userRequest.update(userReq).enqueue(new Callback<BaseResponse<UserResp>>() {
            @Override
            public void onResponse(Call<BaseResponse<UserResp>> call, Response<BaseResponse<UserResp>> response) {
                BaseResponse<UserResp> body = response.body();
                if (body == null || body.getCode() != 200) {
                    onFailure(call, new Throwable("更新失败"));
                    return;
                }
                Toast.makeText(ProfileActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
                Log.e(TAG, body.getData().toString());
                SharedPreferencesUtil.putString("userName", body.getData().getUsername());
                SharedPreferencesUtil.putString("avatar", body.getData().getAvatar());
                SharedPreferencesUtil.putLong("birthDate",
                        body.getData().getBirthDate() == null ? 0 : body.getData().getBirthDate().getTime());
                SharedPreferencesUtil.putInt("gender", body.getData().getGender());
                initUserReq();
                initView();
            }

            @Override
            public void onFailure(Call<BaseResponse<UserResp>> call, Throwable t) {
                Toast.makeText(ProfileActivity.this,
                        t.getMessage() == null ? "更新失败" : t.getMessage() == null ? "请求失败" : t.getMessage(),
                        Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    private void initBinding() {
        findViewById(R.id.layout_birthday).setOnClickListener((v) -> {
            Calendar startTime = Calendar.getInstance();
            startTime.set(1970, 0, 1);
            Calendar endTime = Calendar.getInstance();
            TimePickerView pvTime = new TimePickerBuilder(ProfileActivity.this, new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    userReq.setBirthDate(date);
                    Log.e(TAG, userReq.toString());
                    updateRequest();
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
            OptionsPickerView<String> pvOptions = new OptionsPickerBuilder(ProfileActivity.this,
                    new OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int option2, int options3, View v) {
                            userReq.setGender(option2);
                            updateRequest();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) { // 返回键的id
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
}
