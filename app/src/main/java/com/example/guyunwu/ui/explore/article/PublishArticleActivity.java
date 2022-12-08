package com.example.guyunwu.ui.explore.article;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.guyunwu.R;
import com.example.guyunwu.databinding.ActivityPublishArticleBinding;
import com.example.guyunwu.repository.ArticleRepository;

import org.xutils.x;

import java.time.LocalDateTime;
import java.util.Arrays;

import io.github.mthli.knife.KnifeText;

public class PublishArticleActivity extends AppCompatActivity {

    private static final String TAG = "PublishArticleActivity";
    
    private ActivityPublishArticleBinding binding;

    private KnifeText knife;

    private ArticleRepository articleRepository;

    private String coverImageUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPublishArticleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        articleRepository = new ArticleRepository();
        knife = binding.editorKnife;
        setupKnife();
    }

    private void setupKnife(){
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

    private void showCoverImageDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
        builder.show();
    }

    private void showLinkDialog() {
        final int start = knife.getSelectionStart();
        final int end = knife.getSelectionEnd();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);

        View view = getLayoutInflater().inflate(R.layout.dialog_link, null, false);
        final EditText editText = (EditText) view.findViewById(R.id.edit);
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

    private void completeEdit(){
        String content = knife.toHtml();
        Article article = new Article();
        article.setTitle(binding.editorTitleInput.getText().toString());
        article.setContent(content);

        String text = knife.getText().toString();
        article.setSummary(text.substring(0, Math.min(text.length(), 100)));
        article.setPublishDate(LocalDateTime.now());
        article.setReads(1L);
        article.setLikes(0L);
        article.setCategory("未分类");
        article.setCoverImage(coverImageUrl);
        article.setTags(Arrays.asList("test", "test2"));
        article.setAuthor(new Author(
                1,
                "guyunwu",
                "https://bing.com/th?id=OHR.WistmansWood_ZH-CN4453301808_1920x1080.jpg&qlt=100"));
        articleRepository.save(article);

        Toast.makeText(this, "发送成功", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "completeEdit: " + articleRepository.findById(article.getId()));
        
        finish();
    }
}