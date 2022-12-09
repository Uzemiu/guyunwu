package com.example.guyunwu.ui.explore.comment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guyunwu.R;
import com.example.guyunwu.databinding.CommentBinding;
import com.example.guyunwu.ui.explore.article.Author;
import com.example.guyunwu.util.UiUtil;

import org.xutils.x;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private final List<Comment> commentList;

    public CommentAdapter(List<Comment> commentList) {
        this.commentList = commentList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        CommentBinding binding;

        public ViewHolder(CommentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CommentBinding binding = CommentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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

    @Override
    public int getItemCount() {
        return commentList.size();
    }
}
