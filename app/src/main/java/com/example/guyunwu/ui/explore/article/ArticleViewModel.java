package com.example.guyunwu.ui.explore.article;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import lombok.Getter;

@Getter
public class ArticleViewModel extends ViewModel {

    private final MutableLiveData<Article> mArticle;

    public ArticleViewModel() {
        mArticle = new MutableLiveData<>();
    }
}
