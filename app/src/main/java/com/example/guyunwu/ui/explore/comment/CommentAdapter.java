package com.example.guyunwu.ui.explore.comment;

import static com.example.guyunwu.util.UiUtil.VIEW_HOLDER_TYPE_FOOTER;
import static com.example.guyunwu.util.UiUtil.VIEW_HOLDER_TYPE_NORMAL;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guyunwu.R;
import com.example.guyunwu.databinding.CommentBinding;
import com.example.guyunwu.databinding.LoadingNoMoreBinding;
import com.example.guyunwu.ui.explore.FooterViewHolder;
import com.example.guyunwu.ui.explore.article.Author;
import com.example.guyunwu.util.UiUtil;

import org.xutils.x;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Comment> commentList;

    public CommentAdapter(List<Comment> commentList) {
        this.commentList = commentList;
    }

    static class CommentHolder extends RecyclerView.ViewHolder {

        CommentBinding binding;

        public CommentHolder(CommentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_HOLDER_TYPE_NORMAL:
                CommentBinding binding = CommentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new CommentHolder(binding);
            case VIEW_HOLDER_TYPE_FOOTER:
                LoadingNoMoreBinding footerBinding = LoadingNoMoreBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                footerBinding.exploreLoadMoreTextView.setText("没有更多评论了...");
                return new FooterViewHolder(footerBinding.getRoot());
            default:
                throw new RuntimeException("No match for " + viewType);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position >= commentList.size()) {
            return VIEW_HOLDER_TYPE_FOOTER;
        }
        return VIEW_HOLDER_TYPE_NORMAL;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof CommentHolder) {
            CommentHolder holder  = (CommentHolder) viewHolder;

            Comment article = commentList.get(position);
            Author author = article.getAuthor();
            if(author != null){
                String avatar = author.getAvatar();
                if(TextUtils.isEmpty(avatar)) {
                    holder.binding.commentAuthorAvatar.setImageResource(R.drawable.ic_user_user_24dp);
                } else {
                    x.image().bind(holder.binding.commentAuthorAvatar, avatar);
                }
                holder.binding.commentAuthorName.setText(author.getName());
            }

            holder.binding.commentContent.setText(article.getContent());
            holder.binding.commentTime.setText(article.getPublishDate().format(UiUtil.DATE_TIME_FORMATTER));
        }
    }

    @Override
    public int getItemCount() {
        return commentList.size() + 1;
    }
}
