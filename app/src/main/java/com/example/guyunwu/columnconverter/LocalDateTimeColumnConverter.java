package com.example.guyunwu.columnconverter;

import android.database.Cursor;

import org.xutils.db.converter.ColumnConverter;
import org.xutils.db.sqlite.ColumnDbType;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class LocalDateTimeColumnConverter implements ColumnConverter<LocalDateTime> {

    public static final ZoneOffset ZONE_OFFSET = ZoneOffset.ofHours(8);

    @Override
    public LocalDateTime getFieldValue(Cursor cursor, int index) {
        long millis = cursor.getLong(index);
        return LocalDateTime.ofEpochSecond(millis / 1000, (int) (millis % 1000) * 1000000, ZONE_OFFSET);
    }

    @Override
    public Object fieldValue2DbValue(LocalDateTime fieldValue) {
        return fieldValue.toEpochSecond(ZONE_OFFSET) * 1000L;
    }

    @Override
    public ColumnDbType getColumnDbType() {
        return ColumnDbType.INTEGER;
    }
}
