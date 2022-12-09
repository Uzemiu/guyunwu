package com.example.guyunwu.util;

import androidx.core.view.ScrollingView;

import java.time.format.DateTimeFormatter;

public class UiUtil {

    public static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static boolean isScrollToBottom(ScrollingView recyclerView, int offset) {
        if (recyclerView == null) return false;
        return recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() + offset
                >= recyclerView.computeVerticalScrollRange();
    }


}
