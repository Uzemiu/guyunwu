package com.example.guyunwu.ui.explore.daily;

import java.time.LocalDateTime;

public class DailySentence {

    private Integer id;

    private String sentence;

    private String from;

    private LocalDateTime date;

    private String imageUrl;

    public DailySentence() {
    }

    public DailySentence(Integer id, String sentence, String from, LocalDateTime date, String imageUrl) {
        this.id = id;
        this.sentence = sentence;
        this.from = from;
        this.date = date;
        this.imageUrl = imageUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
