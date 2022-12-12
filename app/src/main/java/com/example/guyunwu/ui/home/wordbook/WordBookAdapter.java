package com.example.guyunwu.ui.home.wordbook;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.guyunwu.R;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WordBookAdapter extends RecyclerView.Adapter<WordBookAdapter.ViewHolder> {

    private final List<WordBook> wordBookList;

    public WordBookAdapter(List<WordBook> wordBookList) {
        this.wordBookList = wordBookList;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        View wordBookView;
        TextView wordBookTitle;
        TextView wordBookContent;

        public ViewHolder(View view) {
            super(view);
            wordBookView = view;
            wordBookTitle = view.findViewById(R.id.word_book_title);
            wordBookContent = view.findViewById(R.id.word_book_content);
        }
    }

    @NotNull
    @Override
    public WordBookAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_book_preview, parent, false);
        final WordBookAdapter.ViewHolder holder = new WordBookAdapter.ViewHolder(view);

        view.setOnClickListener(v -> {
            int position = holder.getAdapterPosition();
            WordBook wordBook = wordBookList.get(position);
            Intent intent = new Intent(v.getContext(), WordBookDetailActivity.class);
            intent.putExtra("wordBook", wordBook);
            v.getContext().startActivity(intent);
        });
        // set margin in view
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        view.setLayoutParams(layoutParams);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull WordBookAdapter.ViewHolder holder, int position) {
        WordBook wordBook = wordBookList.get(position);
        holder.wordBookTitle.setText(wordBook.getKeyTitle());
        holder.wordBookContent.setText(wordBook.getContent());
    }

    @Override
    public int getItemCount() {
        return wordBookList.size();
    }
}
