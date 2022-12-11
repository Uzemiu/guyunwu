package com.example.guyunwu.ui.user.book;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.guyunwu.R;
import lombok.Setter;
import org.xutils.x;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    private final List<Book> bookList;

    @Setter
    private int adapterType;

    public BookAdapter(List<Book> bookList) {
        this.bookList = bookList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View bookPreviewView;
        TextView bookPreviewTitle;
        TextView bookPreviewAuthorName;
        ImageView bookPreviewCover;
        TextView bookPreviewContent;
        TextView bookPreviewReads;
        CardView bookPreviewCoverCard;


        public ViewHolder(View view) {
            super(view);
            bookPreviewView = view;
            bookPreviewTitle = view.findViewById(R.id.book_preview_title);
            bookPreviewAuthorName = view.findViewById(R.id.book_preview_author_name);
            bookPreviewCover = view.findViewById(R.id.book_preview_cover);
            bookPreviewContent = view.findViewById(R.id.book_preview_content);
            bookPreviewReads = view.findViewById(R.id.book_preview_reads);
            bookPreviewCoverCard = view.findViewById(R.id.book_preview_cover_wrapper);
        }
    }

    @NonNull
    @Override
    public BookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_preview, parent, false);
        final BookAdapter.ViewHolder holder = new BookAdapter.ViewHolder(view);

        view.setOnClickListener(v -> {
            int position = holder.getAdapterPosition();
            Book book = bookList.get(position);
            Intent intent = null;
            if (adapterType == 0) {
                intent = new Intent(v.getContext(), BookActivity.class);
            } else if (adapterType == 1) {
                intent = new Intent(v.getContext(), LibraryBookActivity.class);
            }
            intent.putExtra("book", book);
            v.getContext().startActivity(intent);
        });
        // set margin in view
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        layoutParams.bottomMargin = 16;
        layoutParams.topMargin = 16;
        view.setLayoutParams(layoutParams);

        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BookAdapter.ViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.bookPreviewTitle.setText(book.getTitle());

        Author author = book.getAuthor();
        if (author != null) {
            holder.bookPreviewAuthorName.setText('[' + author.getDynasty() + ']' + author.getName());
        }
        String cover = book.getCoverImage();
        if (cover != null && cover.length() > 0) {
            x.image().bind(holder.bookPreviewCover, book.getCoverImage());
        } else {
            holder.bookPreviewCoverCard.setVisibility(View.GONE);
        }
        holder.bookPreviewContent.setText(book.getIntroduce());
        holder.bookPreviewReads.setText(String.valueOf(book.getReads()));
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }
}
