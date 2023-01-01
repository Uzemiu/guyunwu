package com.example.guyunwu.ui.user.myBook;

import android.os.Bundle;
import android.util.Log;
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

public class LibraryActivity extends AppCompatActivity {

    private static final String TAG = "LibraryActivity";
    private final List<Book> books = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        initActionBar();
        initRecyclerView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) { // 返回键的id
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
        CollectionRequest collectionRequest = RequestModule.COLLECTION_REQUEST;

        collectionRequest.allBook().enqueue(new Callback<BaseResponse<List<Book>>>() {
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
                Toast.makeText(LibraryActivity.this, t.getMessage() == null ? "请求失败" : t.getMessage(),
                        Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: ", t);
            }
        });
        RecyclerView recyclerView = findViewById(R.id.my_book_recycler_view);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        BookAdapter adapter = new BookAdapter(books);
        adapter.setAdapterType(1);
        recyclerView.setAdapter(adapter);
    }
}
