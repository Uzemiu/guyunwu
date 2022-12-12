package com.example.guyunwu.ui.user.myBook;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.example.guyunwu.R;
import com.example.guyunwu.api.BaseResponse;
import com.example.guyunwu.api.CollectionRequest;
import com.example.guyunwu.api.RequestModule;
import com.example.guyunwu.ui.user.book.Book;
import com.example.guyunwu.ui.user.book.BookAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class MyBookActivity extends AppCompatActivity {

    private static final String TAG = "MyBookActivity";
    private Menu menu;

    private final List<Book> books = new ArrayList<>();

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_books);

        initActionBar();
        initRecyclerView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_book, menu);
        this.menu = menu;
        onPrepareOptionsMenu(menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        initRecyclerView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {   //返回键的id
            this.finish();
            return false;
        } else if (item.getItemId() == R.id.book_more_add) {
            Intent toProfilePage = new Intent();
            toProfilePage.setClass(MyBookActivity.this, LibraryActivity.class);
            startActivity(toProfilePage);
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

    private void initRecyclerView() {
        // 获取图书数据
        CollectionRequest collectionRequest = RequestModule.COLLECTION_REQUEST;

        collectionRequest.myBook().enqueue(new Callback<BaseResponse<List<Book>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<Book>>> call, Response<BaseResponse<List<Book>>> response) {
                BaseResponse<List<Book>> body = response.body();
                if (body == null || body.getCode() != 200) {
                    onFailure(call, new Throwable("获取失败"));
                } else {
                    books.clear();
                    books.addAll(body.getData());
                    ((RecyclerView) findViewById(R.id.my_book_recycler_view)).getAdapter().notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<Book>>> call, Throwable t) {
                Toast.makeText(MyBookActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: ", t);
            }
        });

        recyclerView = findViewById(R.id.my_book_recycler_view);
        StaggeredGridLayoutManager layoutManager = new
                StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        BookAdapter adapter = new BookAdapter(books);
        adapter.setAdapterType(0);
        recyclerView.setAdapter(adapter);
    }
}
