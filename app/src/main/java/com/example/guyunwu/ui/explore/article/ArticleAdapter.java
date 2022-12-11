package com.example.guyunwu.ui.explore.article;

import static com.example.guyunwu.util.UiUtil.VIEW_HOLDER_TYPE_FOOTER;
import static com.example.guyunwu.util.UiUtil.VIEW_HOLDER_TYPE_NORMAL;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guyunwu.R;
import com.example.guyunwu.databinding.LoadingNoMoreBinding;
import com.example.guyunwu.ui.explore.FooterViewHolder;

import org.xutils.x;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Article> articleList;

    public ArticleAdapter(List<Article> articleList) {
        this.articleList = articleList;
    }

    static class ArticleHolder extends RecyclerView.ViewHolder {
        View articlePreviewView;

        TextView articlePreviewTitle;
        ImageView articlePreviewAuthorAvatar;
        TextView articlePreviewAuthorName;
        ImageView articlePreviewCover;
        TextView articlePreviewContent;
        TextView articlePreviewReads;
        CardView articlePreviewCoverCard;

        public ArticleHolder(View view) {
            super(view);
            articlePreviewView = view;
            articlePreviewTitle = view.findViewById(R.id.article_preview_title);
            articlePreviewAuthorAvatar = view.findViewById(R.id.article_preview_author_avatar);
            articlePreviewAuthorName = view.findViewById(R.id.article_preview_author_name);
            articlePreviewCover = view.findViewById(R.id.article_preview_cover);
            articlePreviewContent = view.findViewById(R.id.article_preview_content);
            articlePreviewReads = view.findViewById(R.id.article_preview_reads);
            articlePreviewCoverCard = view.findViewById(R.id.article_preview_cover_wrapper);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_HOLDER_TYPE_NORMAL:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_preview, parent, false);
                final ArticleHolder holder = new ArticleHolder(view);

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
            case VIEW_HOLDER_TYPE_FOOTER:
                LoadingNoMoreBinding footerBinding = LoadingNoMoreBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                footerBinding.exploreLoadMoreTextView.setText("没有更多文章了...");
                return new FooterViewHolder(footerBinding.getRoot());
            default:
                throw new RuntimeException("No match for " + viewType);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position >= articleList.size()) {
            return VIEW_HOLDER_TYPE_FOOTER;
        }
        return VIEW_HOLDER_TYPE_NORMAL;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ArticleHolder) {
            ArticleHolder holder = (ArticleHolder) viewHolder;
            Article article = articleList.get(position);
            holder.articlePreviewTitle.setText(article.getTitle());

            Author author = article.getAuthor();
            if (author != null) {
                String avatar = author.getAvatar();
                if (TextUtils.isEmpty(avatar)) {
                    holder.articlePreviewAuthorAvatar.setImageResource(R.drawable.ic_user_user_24dp);
                } else {
                    x.image().bind(holder.articlePreviewAuthorAvatar, avatar);
                }
                holder.articlePreviewAuthorName.setText(author.getUsername());
            }
            String cover = article.getCoverImage();
            if (cover != null && cover.length() > 0) {
                x.image().bind(holder.articlePreviewCover, article.getCoverImage());
            } else {
                holder.articlePreviewCoverCard.setVisibility(View.GONE);
            }
            holder.articlePreviewContent.setText(article.getSummary());
            holder.articlePreviewReads.setText(String.valueOf(article.getReads()));
        }
    }

    @Override
    public int getItemCount() {
        return articleList.size() + 1;
    }
}
