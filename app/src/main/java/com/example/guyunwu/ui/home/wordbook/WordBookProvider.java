package com.example.guyunwu.ui.home.wordbook;

import java.util.ArrayList;
import java.util.List;

public class WordBookProvider {

    public static List<WordBook> getWordBooks() {
        List<WordBook> wordBooks = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            wordBooks.add(new WordBook("何",
                    "其两膝相比者，各隐卷底衣褶",
                    "《核舟记》",
                    "他们互相靠近的两膝，都被遮蔽在手卷下边的衣褶里。",
                    "靠近"));
        }

        return wordBooks;
    }
}
