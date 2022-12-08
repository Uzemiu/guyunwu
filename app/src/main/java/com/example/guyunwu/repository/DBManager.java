package com.example.guyunwu.repository;

import android.util.Log;

import com.example.guyunwu.columnconverter.JsonColumnConverter;
import com.example.guyunwu.columnconverter.ListColumnConverter;
import com.example.guyunwu.columnconverter.LocalDateTimeColumnConverter;
import com.example.guyunwu.ui.explore.article.Author;

import org.apache.tools.ant.util.ReflectUtil;
import org.xutils.DbManager;
import org.xutils.db.converter.ColumnConverter;
import org.xutils.db.converter.ColumnConverterFactory;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.AbstractList;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import kotlin.jvm.internal.Reflection;

public class DBManager {

    private static final String TAG = "DBManager";

    // configure DbManager in singleton mode
    private static final DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
            .setDbName("guyunwu.db")
            .setDbVersion(13)
            .setAllowTransaction(false)
            .setDbOpenListener(db -> {
                // 开启WAL, 对写入加速提升巨大
                db.getDatabase().enableWriteAheadLogging();
            })
            .setDbUpgradeListener((db, oldVersion, newVersion) -> {
                db.dropDb();
            });

    private static DbManager dbManager;

    static {
        ColumnConverterFactory.registerColumnConverter(Author.class, new JsonColumnConverter<>(Author.class));
        ColumnConverterFactory.registerColumnConverter(LocalDateTime.class, new LocalDateTimeColumnConverter());


        List<Class<?>> listClasses = new ArrayList<>();
        listClasses.add(List.class);
        listClasses.add(AbstractList.class);
        listClasses.add(ArrayList.class);
        listClasses.add(LinkedList.class);
        listClasses.add(CopyOnWriteArrayList.class);
        listClasses.add(Stack.class);
        listClasses.add(ArrayDeque.class);
        try {
            listClasses.add(Class.forName("java.util.Arrays$ArrayList"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 注意 只支持简单类型的列表
        ColumnConverter<?> listColumnConverter = new ListColumnConverter();
        listClasses.forEach(clazz -> {
            ColumnConverterFactory.registerColumnConverter(clazz, listColumnConverter);
        });

    }

    public synchronized static DbManager getManager() {
        if (dbManager == null) {
            try {
                dbManager = x.getDb(daoConfig);
            } catch (DbException e) {
                Log.w(TAG, "获取DbManager失败: ", e);
            }
        }
        return dbManager;
    }
}
