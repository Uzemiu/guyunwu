package com.example.guyunwu.repository;

import com.example.guyunwu.exception.DBException;
import lombok.Data;
import org.xutils.DbManager;
import org.xutils.db.Selector;
import org.xutils.ex.DbException;

@Data
public class BaseQuery<ENTITY> {

    protected Selector<ENTITY> between(Selector<ENTITY> selector, Object[] iterable, String columnName) {
        if (iterable == null) {
            return selector;
        }
        if (iterable.length > 0 && iterable[0] != null) {
            selector.and(columnName, ">", iterable[0]);
        }
        if (iterable.length > 1 && iterable[1] != null) {
            selector.and(columnName, "<", iterable[1]);
        }
        return selector;
    }

    public Selector<ENTITY> toSelector(DbManager dbManager, Class<ENTITY> c) {
        try {
            return dbManager.selector(c);
        } catch (DbException e) {
            throw new DBException(e.getMessage());
        }
    }
}
