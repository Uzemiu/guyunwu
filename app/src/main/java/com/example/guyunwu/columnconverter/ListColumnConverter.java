package com.example.guyunwu.columnconverter;

import android.database.Cursor;

import com.alibaba.fastjson2.JSON;

import org.xutils.db.converter.ColumnConverter;
import org.xutils.db.sqlite.ColumnDbType;

import java.util.List;

public class ListColumnConverter implements ColumnConverter<List> {

    @Override
    public List getFieldValue(Cursor cursor, int index) {
        String json = cursor.getString(index);
        return JSON.parseArray(json);
    }

    @Override
    public Object fieldValue2DbValue(List fieldValue) {
        return JSON.toJSONString(fieldValue);
    }

    @Override
    public ColumnDbType getColumnDbType() {
        return ColumnDbType.TEXT;
    }
}
