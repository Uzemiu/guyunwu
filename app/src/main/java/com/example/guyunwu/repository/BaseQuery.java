package com.example.guyunwu.repository;

import com.example.guyunwu.exception.DBException;

import org.xutils.DbManager;
import org.xutils.db.Selector;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;

import java.util.Date;

import lombok.Data;

@Data
public class BaseQuery<ENTITY> {

    private Date[] createTime;

    private Date[] updateTime;

    protected Selector<ENTITY> between(Selector<ENTITY> selector, Object[] iterable, String columnName){
        if(iterable == null){
            return selector;
        }
        if(iterable.length > 0 && iterable[0] != null){
            selector.and(columnName, ">", iterable[0]);
        }
        if(iterable.length > 1 && iterable[1] != null){
            selector.and(columnName, "<", iterable[1]);
        }
        return selector;
    }

    public Selector<ENTITY> toSelector(DbManager dbManager, Class<ENTITY> c){
        try {
            Selector<ENTITY> selector = dbManager.selector(c);
            selector.where(WhereBuilder.b());
            between(selector, createTime, "create_time");
            between(selector, updateTime, "update_time");
            return selector;
        } catch (DbException e) {
            throw new DBException(e.getMessage());
        }
    }
}
