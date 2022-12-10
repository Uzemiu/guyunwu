package com.example.guyunwu.util;

import android.content.SharedPreferences;

public class SharedPreferencesUtil {

    private static SharedPreferences sharedPreferences;

    public static void setSharedPreferences(SharedPreferences sharedPreferences){
        SharedPreferencesUtil.sharedPreferences = sharedPreferences;
    }

    public static SharedPreferences getSharedPreferences(){
        return sharedPreferences;
    }

    public static void putString(String key, String value){
        sharedPreferences.edit().putString(key, value).commit();
    }

    public static String getString(String key, String defValue){
        return sharedPreferences.getString(key, defValue);
    }

    public static void putLong(String key, long value){
        sharedPreferences.edit().putLong(key, value).commit();
    }

    public static long getLong(String key, long defValue){
        return sharedPreferences.getLong(key, defValue);
    }

}
