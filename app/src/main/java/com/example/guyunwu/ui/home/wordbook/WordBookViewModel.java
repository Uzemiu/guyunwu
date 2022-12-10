package com.example.guyunwu.ui.home.wordbook;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import lombok.Getter;

@Getter
public class WordBookViewModel extends ViewModel {

    private final MutableLiveData<WordBook> mWordBook;

    public WordBookViewModel() {
        mWordBook = new MutableLiveData<>();
    }
}
