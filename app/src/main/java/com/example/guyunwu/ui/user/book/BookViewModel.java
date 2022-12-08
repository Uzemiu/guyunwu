package com.example.guyunwu.ui.user.book;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BookViewModel extends ViewModel {

    private final MutableLiveData<Book> mBook;

    public BookViewModel() {
        mBook = new MutableLiveData<>();
    }
}
