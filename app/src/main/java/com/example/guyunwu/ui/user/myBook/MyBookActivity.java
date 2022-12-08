package com.example.guyunwu.ui.user.myBook;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.guyunwu.R;
import com.example.guyunwu.databinding.ActivityBookBinding;
import com.example.guyunwu.ui.user.BookDataProvider;
import com.example.guyunwu.ui.user.book.Book;
import com.example.guyunwu.ui.user.book.BookAdapter;
import com.example.guyunwu.ui.user.book.BookViewModel;

import java.util.List;

public class MyBookActivity  extends AppCompatActivity {

    private static final String TAG = "BookActivity";

    private ActivityBookBinding binding;

    private BookViewModel bookViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_books);

        initActionBar();
        initRecyclerView();
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
            bar.setTitle("我的图书");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
    private void initRecyclerView(){
        // 获取图书数据
        List<Book> books = BookDataProvider.getBooks();
        RecyclerView recyclerView = findViewById(R.id.my_book_recycler_view);
        StaggeredGridLayoutManager layoutManager = new
                StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        BookAdapter adapter = new BookAdapter(books);
        recyclerView.setAdapter(adapter);
    }
}