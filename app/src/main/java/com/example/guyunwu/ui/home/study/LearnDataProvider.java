package com.example.guyunwu.ui.home.study;

import com.example.guyunwu.api.resp.Word;
import com.example.guyunwu.api.resp.WordResp;

import java.util.ArrayList;
import java.util.List;

public class LearnDataProvider {


    public static List<Word> getWords() {
        List<Word> words = new ArrayList<>();
        words.add(new Word(
                1L,1L,
                "比",
                0,
                "其两膝相比者，各隐卷底衣褶中。",
                "他们互相靠近的两膝,都被遮蔽在手卷下边的衣褶里。",
                "《核舟记》",
                "靠近",
                "比较",
                "等到",
                "联合",
                0,
                0));
        words.add(new Word(
                2L,1L,
                "比",
                0,
                "其两膝相比者，各隐卷底衣褶中。",
                "他们互相靠近的两膝,都被遮蔽在手卷下边的衣褶里。",
                "《核舟记》",
                "靠近",
                "比较",
                "等到",
                "联合",
                1,
                0));
        words.add(new Word(
                3L,1L,
                "比",
                0,
                "其两膝相比者，各隐卷底衣褶中。",
                "他们互相靠近的两膝,都被遮蔽在手卷下边的衣褶里。",
                "《核舟记》",
                "靠近",
                "比较",
                "等到",
                "联合",
                2,
                0));
        words.add(new Word(
                4L,1L,
                "比",
                0,
                "其两膝相比者，各隐卷底衣褶中。",
                "他们互相靠近的两膝,都被遮蔽在手卷下边的衣褶里。",
                "《核舟记》",
                "靠近",
                "比较",
                "等到",
                "联合",
                3,
                0));
        return words;
    }
}
