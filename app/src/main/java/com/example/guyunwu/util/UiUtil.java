package com.example.guyunwu.util;

import androidx.core.view.ScrollingView;

import java.time.format.DateTimeFormatter;

public class UiUtil {

    public static final int VIEW_HOLDER_TYPE_HEADER = 0;

    public static final int VIEW_HOLDER_TYPE_NORMAL = 1;

    public static final int VIEW_HOLDER_TYPE_FOOTER = 2;

    public static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static boolean isScrollToBottom(ScrollingView recyclerView, int offset) {
        if (recyclerView == null) return false;
        return recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() + offset
                >= recyclerView.computeVerticalScrollRange();
    }


}
