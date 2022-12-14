package com.example.guyunwu.exception.handler;

import android.content.Context;
import androidx.annotation.NonNull;

public class ExceptionHandler implements Thread.UncaughtExceptionHandler {

    private final Context mContext;

    public ExceptionHandler(Context context) {
        this.mContext = context;
    }

    @Override
    public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
        e.printStackTrace();
    }

    public void register() {
        Thread.setDefaultUncaughtExceptionHandler(this);
    }
}
