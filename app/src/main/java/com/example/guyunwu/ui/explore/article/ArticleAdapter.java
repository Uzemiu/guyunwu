package com.example.guyunwu.ui.explore.article;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guyunwu.R;

import org.xutils.x;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {

    private final List<Article> articleList;

    public ArticleAdapter(List<Article> articleList) {
        this.articleList = articleList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View articlePreviewView;

        TextView articlePreviewTitle;
        ImageView articlePreviewAuthorAvatar;
        TextView articlePreviewAuthorName;
        ImageView articlePreviewCover;
        TextView articlePreviewContent;
        TextView articlePreviewReads;

        public ViewHolder(View view) {
            super(view);
            articlePreviewView = view;
            articlePreviewTitle = view.findViewById(R.id.article_preview_title);
            articlePreviewAuthorAvatar = view.findViewById(R.id.article_preview_author_avatar);
            articlePreviewAuthorName = view.findViewById(R.id.article_preview_author_name);
            articlePreviewCover = view.findViewById(R.id.article_preview_cover);
            articlePreviewContent = view.findViewById(R.id.article_preview_content);
            articlePreviewReads = view.findViewById(R.id.article_preview_reads);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_preview, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        view.setOnClickListener(v -> {
            int position = holder.getAdapterPosition();
            Article article = articleList.get(position);
            Intent intent = new Intent(v.getContext(), ArticleActivity.class);
            intent.putExtra("article", article);
            v.getContext().startActivity(intent);
        });
        // set margin in view
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        layoutParams.bottomMargin = 16;
        layoutParams.topMargin = 16;
        view.setLayoutParams(layoutParams);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Article article = articleList.get(position);
        holder.articlePreviewTitle.setText(article.getTitle());
        x.image().bind(holder.articlePreviewAuthorAvatar, article.getAuthor().getAvatar());
        holder.articlePreviewAuthorName.setText(article.getAuthor().getName());
        String cover = article.getCoverImage();
        if(cover != null && cover.length() > 0){
            x.image().bind(holder.articlePreviewCover, article.getCoverImage());
        } else {
            holder.articlePreviewCover.setVisibility(View.GONE);
        }
        holder.articlePreviewContent.setText(article.getContent());
        holder.articlePreviewReads.setText(String.valueOf(article.getReads()));
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }
}
