package com.example.guyunwu.columnconverter;

import android.database.Cursor;
import com.alibaba.fastjson2.JSON;
import org.xutils.db.converter.ColumnConverter;
import org.xutils.db.sqlite.ColumnDbType;

public class JsonColumnConverter<T> implements ColumnConverter<T> {

    private final Class<T> clazz;

    @SuppressWarnings("all")
    public JsonColumnConverter() {
        clazz = (Class<T>) ((java.lang.reflect.ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public JsonColumnConverter(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T getFieldValue(Cursor cursor, int index) {
        String json = cursor.getString(index);
        return JSON.parseObject(json, clazz);
    }

    @Override
    public Object fieldValue2DbValue(T fieldValue) {
        return JSON.toJSONString(fieldValue);
    }

    @Override
    public ColumnDbType getColumnDbType() {
        return ColumnDbType.TEXT;
    }
}
