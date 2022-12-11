package com.example.guyunwu.ui.explore.article;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.*;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.guyunwu.R;
import com.example.guyunwu.api.ArticleRequest;
import com.example.guyunwu.api.BaseResponse;
import com.example.guyunwu.api.FileUploadRequest;
import com.example.guyunwu.api.RequestModule;
import com.example.guyunwu.api.req.AddArticleReq;
import com.example.guyunwu.databinding.ActivityPublishArticleBinding;
import com.example.guyunwu.ui.user.profile.ProfileActivity;
import com.example.guyunwu.util.CameraUtil;
import com.example.guyunwu.util.SharedPreferencesUtil;
import io.github.mthli.knife.KnifeText;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.xutils.x;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.File;

import static com.example.guyunwu.util.AlbumUtil.REQUEST_CODE_ALBUM;
import static com.example.guyunwu.util.AlbumUtil.ifHaveAlbumPermission;
import static com.example.guyunwu.util.CameraUtil.REQUEST_CODE_CAMERA;
import static com.example.guyunwu.util.CameraUtil.ifHaveCameraPermission;
import static com.example.guyunwu.util.FileUtil.uriToFileApiQ;

public class PublishArticleActivity extends AppCompatActivity {

    private static final String TAG = "PublishArticleActivity";

    private final CameraUtil.PhotoUriWrapper photoUriWrapper = new CameraUtil.PhotoUriWrapper();//记录图片地址

    private ActivityPublishArticleBinding binding;

    private KnifeText knife;

    private String coverImageUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPublishArticleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        knife = binding.editorKnife;
        setupKnife();
    }

    private void setupKnife() {
        binding.editorBold.setOnClickListener(v -> knife.bold(!knife.contains(KnifeText.FORMAT_BOLD)));
        binding.editorItalic.setOnClickListener(v -> knife.italic(!knife.contains(KnifeText.FORMAT_ITALIC)));
        binding.editorUnderline.setOnClickListener(v -> knife.underline(!knife.contains(KnifeText.FORMAT_UNDERLINED)));
        binding.editorStrikethrough.setOnClickListener(v -> knife.strikethrough(!knife.contains(KnifeText.FORMAT_STRIKETHROUGH)));
        binding.editorBullet.setOnClickListener(v -> knife.bullet(!knife.contains(KnifeText.FORMAT_BULLET)));
        binding.editorQuote.setOnClickListener(v -> knife.quote(!knife.contains(KnifeText.FORMAT_QUOTE)));
        binding.editorLink.setOnClickListener(v -> showLinkDialog());
        binding.editorClear.setOnClickListener(v -> knife.clearFormats());
        binding.editorCoverImage.setOnClickListener(v -> showCoverImageDialog());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_ALBUM:
                    uploadCover(uriToFileApiQ(data.getData(), this));
                    break;
                case REQUEST_CODE_CAMERA:
                    uploadCover(uriToFileApiQ(photoUriWrapper.photoUri, this));
                    break;
            }
        }
    }

    private void uploadCover(File image) {
        FileUploadRequest fileUploadRequest = RequestModule.FILE_UPLOAD_REQUEST;

        RequestBody imageBody = RequestBody.create(MediaType.parse("image/*"), image);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", image.getName(), imageBody);

        fileUploadRequest.uploadImage(body, SharedPreferencesUtil.getString("phoneNumber", "1145141919810")).enqueue(new Callback<BaseResponse<String>>() {
            @Override
            public void onResponse(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                if (response.body() == null || response.body().getCode() != 200) {
                    onFailure(call, new Throwable("上传失败"));
                    return;
                }
                Toast.makeText(PublishArticleActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                coverImageUrl = response.body().getData();
                x.image().bind(binding.editorCoverImage, coverImageUrl);
            }

            @Override
            public void onFailure(Call<BaseResponse<String>> call, Throwable t) {
                Toast.makeText(PublishArticleActivity.this, t.getMessage() == null ? "上传失败" : t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    private void showCoverImageDialog() {
        /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cover Image");
        builder.setMessage("Please enter the image url");
        View view = getLayoutInflater().inflate(R.layout.dialog_link, null);
        builder.setView(view);
        EditText editText = view.findViewById(R.id.edit);
        editText.setText(coverImageUrl);
        builder.setPositiveButton(R.string.dialog_button_ok, (dialog, which) -> {
            String url = editText.getText().toString();
            if(TextUtils.isEmpty(url)){
                Toast.makeText(this, "Please enter the image url", Toast.LENGTH_SHORT).show();
                return;
            }

            coverImageUrl = url;
            x.image().bind(binding.editorCoverImage, url);
        });
        builder.setNegativeButton(R.string.dialog_button_cancel, (dialog, which) -> dialog.dismiss());
        builder.show();*/
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
            ifHaveCameraPermission(PublishArticleActivity.this, REQUEST_CODE_CAMERA, photoUriWrapper);
            dialog.dismiss();
        });

        dialog.findViewById(R.id.tv_take_pic).setOnClickListener(v -> {
            ifHaveAlbumPermission(PublishArticleActivity.this, REQUEST_CODE_ALBUM);
            dialog.dismiss();
        });

        dialog.findViewById(R.id.tv_cancel).setOnClickListener(v -> dialog.dismiss());
    }

    private void showLinkDialog() {
        final int start = knife.getSelectionStart();
        final int end = knife.getSelectionEnd();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);

        View view = getLayoutInflater().inflate(R.layout.dialog_link, null, false);
        final EditText editText = view.findViewById(R.id.edit);
        builder.setView(view);
        builder.setTitle(R.string.dialog_title);

        builder.setPositiveButton(R.string.dialog_button_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String link = editText.getText().toString().trim();
                if (TextUtils.isEmpty(link)) {
                    return;
                }

                // When KnifeText lose focus, use this method
                knife.link(link, start, end);
            }
        });

        builder.setNegativeButton(R.string.dialog_button_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // DO NOTHING HERE
            }
        });

        builder.create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.editor_undo:
                knife.undo();
                break;
            case R.id.editor_redo:
                knife.redo();
                break;
            case R.id.editor_complete:
                try {
                    completeEdit();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "操作失败", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }

        return true;
    }

    private volatile boolean loading = false;

    private void completeEdit() {
        if (loading) {
            Toast.makeText(this, "发送中，请稍后", Toast.LENGTH_SHORT).show();
            return;
        }
        loading = true;

        ArticleRequest articleRequest = RequestModule.ARTICLE_REQUEST;

        AddArticleReq req = new AddArticleReq();
        req.setTitle(binding.editorTitleInput.getText().toString());
        req.setContent(knife.toHtml());
        req.setCoverImage(coverImageUrl);
        String text = knife.getText().toString();
        req.setSummary(text.substring(0, Math.min(text.length(), 100)));

        if (TextUtils.isEmpty(req.getTitle())) {
            Toast.makeText(this, "标题不能为空", Toast.LENGTH_SHORT).show();
            loading = false;
            return;
        }
        if (TextUtils.isEmpty(req.getContent()) || TextUtils.isEmpty(req.getSummary())) {
            Toast.makeText(this, "内容不能为空", Toast.LENGTH_SHORT).show();
            loading = false;
            return;
        }

        articleRequest.addArticle(req).enqueue(new Callback<BaseResponse<Object>>() {
            @Override
            public void onResponse(Call<BaseResponse<Object>> call, Response<BaseResponse<Object>> response) {
                Toast.makeText(PublishArticleActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                loading = false;
                finish();
            }

            @Override
            public void onFailure(Call<BaseResponse<Object>> call, Throwable t) {
                Toast.makeText(PublishArticleActivity.this, "发送失败" + t.getMessage(), Toast.LENGTH_SHORT).show();
                loading = false;
            }
        });
    }
}
