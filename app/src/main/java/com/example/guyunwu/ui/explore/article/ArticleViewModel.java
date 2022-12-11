package com.example.guyunwu.ui.explore.article;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import lombok.Getter;

@Getter
public class ArticleViewModel extends ViewModel {

    private final MutableLiveData<Article> mArticle;

    private final MutableLiveData<Boolean> mLike;

    public ArticleViewModel() {
        mArticle = new MutableLiveData<>();
        mLike = new MutableLiveData<>();
    }

    public Long getArticleId(){
        return mArticle.getValue() == null ? null : mArticle.getValue().getId();
    }
}
