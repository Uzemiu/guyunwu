package com.example.guyunwu.api.resp;

import com.example.guyunwu.ui.user.book.Book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "word")
public class Word {

    @Column(name = "id", isId = true)
    private Long id;

    @Column(name = "book")
    private Book book;

    @Column(name = "key_word")
    private String keyWord;

    @Column(name = "key_index")
    private Integer keyIndex;

    @Column(name = "content")
    private String content;

    @Column(name = "translate")
    private String translate;

    @Column(name = "book_name")
    private String bookName;

    @Column(name = "answer_a")
    private String answerA;

    @Column(name = "answer_b")
    private String answerB;

    @Column(name = "answer_c")
    private String answerC;

    @Column(name = "answer_d")
    private String answerD;

    @Column(name = "correct_answer")
    private Integer correctAnswer;

    @Column(name = "status")
    private Integer status;
}
