package com.example.guyunwu.ui.user.myBook;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.example.guyunwu.R;
import com.example.guyunwu.ui.user.BookDataProvider;
import com.example.guyunwu.ui.user.LibraryDataProvider;
import com.example.guyunwu.ui.user.book.Book;
import com.example.guyunwu.ui.user.book.BookAdapter;

import java.util.List;

public class LibraryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
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
            bar.setTitle("图书馆");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initRecyclerView() {
        List<Book> books = LibraryDataProvider.getBooks();
        RecyclerView recyclerView = findViewById(R.id.my_book_recycler_view);
        StaggeredGridLayoutManager layoutManager = new
                StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        BookAdapter adapter = new BookAdapter(books);
        adapter.setAdapterType(1);
        recyclerView.setAdapter(adapter);
    }
}
