package com.example.guyunwu.ui.home.wordbook;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.guyunwu.api.resp.WordWithBook;
import lombok.Getter;

@Getter
public class WordBookViewModel extends ViewModel {

    private final MutableLiveData<WordWithBook> mWordBook;

    public WordBookViewModel() {
        mWordBook = new MutableLiveData<>();
    }
}
