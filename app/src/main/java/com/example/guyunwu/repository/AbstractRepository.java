package com.example.guyunwu.repository;

import android.database.sqlite.SQLiteDatabase;

import com.example.guyunwu.exception.DBException;
import com.example.guyunwu.util.Assert;

import org.xutils.DbManager;
import org.xutils.db.Selector;
import org.xutils.ex.DbException;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public abstract class AbstractRepository<ENTITY, ID> implements BaseRepository<ENTITY, ID>{

    private final DbManager manager;

    private final Class<ENTITY> actualClass;

    private final String entityName;

    @SuppressWarnings("all")
    public AbstractRepository() {
        actualClass = (Class<ENTITY>) ((ParameterizedType) this.getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
        entityName = actualClass.getSimpleName();
        manager = DBManager.getManager();
    }

    @Override
    public ENTITY save(ENTITY entity){
        Assert.notNull(entity, entityName + " must not be null");

        SQLiteDatabase database = manager.getDatabase();
        try {
            database.beginTransaction();

            manager.saveBindingId(entity);
            postPersist(entity);

            database.setTransactionSuccessful();

            return entity;
        } catch (DbException e) {
            throw new DBException(e.getMessage());
        } finally {
            database.endTransaction();
        }
    }

    @Override
    public List<ENTITY> save(Collection<ENTITY> entities) {
        Assert.notNull(entities, "Entities must not be null");
        List<ENTITY> entityList = new ArrayList<>(entities);

        SQLiteDatabase database = manager.getDatabase();
        try {
            database.beginTransaction();

            manager.saveBindingId(entityList);
            postPersist(entityList);

            database.setTransactionSuccessful();
            return entityList;
        } catch (DbException e) {
            throw new DBException(e.getMessage());
        } finally {
            database.endTransaction();
        }
    }

    @Override
    public void update(ENTITY entity){
        Assert.notNull(entity, entityName + " must not be null");

        SQLiteDatabase database = manager.getDatabase();
        try {
            database.beginTransaction();

            manager.saveOrUpdate(entity);
            postUpdate(entity);

            database.setTransactionSuccessful();
        } catch (DbException e) {
            throw new DBException(e.getMessage());
        } finally {
            database.endTransaction();
        }
    }

    @Override
    public void update(Collection<ENTITY> entities) {
        Assert.notNull(entities, "Entities must not be null");
        List<ENTITY> entityList = new ArrayList<>(entities);

        SQLiteDatabase database = manager.getDatabase();
        try {
            database.beginTransaction();

            manager.saveOrUpdate(entityList);
            postUpdate(entityList);

            database.setTransactionSuccessful();
        } catch (DbException e) {
            throw new DBException(e.getMessage());
        }finally {
            database.endTransaction();
        }
    }

    @Override
    public List<ENTITY> findAll(){
        try {
            List<ENTITY> list = manager.findAll(actualClass);
            if(list == null){
                list = new ArrayList<>();
            }
            return postQuery(list);
        } catch (DbException e) {
            throw new DBException(e.getMessage());
        }
    }

    @Override
    public List<ENTITY> findByIds(Collection<ID> ids) {
        Assert.notNull(ids, "Ids must not be null");
        try {
            Selector<ENTITY> selector = manager.selector(actualClass);
            selector.where(selector.getTable().getId().getName(), "in", ids);
            List<ENTITY> entityList = selector.findAll();
            if(entityList == null){
                entityList = new ArrayList<>();
            }
            return postQuery(entityList);
        } catch (DbException e) {
            throw new DBException(e.getMessage());
        }
    }

    @Override
    public ENTITY findById(ID id){
        Assert.notNull(id, "Id must be null");
        try {
            return postQuery(manager.findById(actualClass, id));
        } catch (DbException e) {
            throw new DBException(e.getMessage());
        }
    }

    @Override
    public List<ENTITY> query(BaseQuery<ENTITY> query) {
        return postQuery(query(query, new Pageable()));
    }

    @Override
    public List<ENTITY> query(BaseQuery<ENTITY> query, Pageable pageable) {
        Assert.notNull(query, "Query must not be null");
        if(pageable == null){
            pageable = new Pageable();
        }
        Selector<ENTITY> selector = query.toSelector(manager, actualClass);

        selector.orderBy("");
        selector.getOrderByList().clear();
        selector.getOrderByList().addAll(pageable.getOrderByList());

        selector.offset(pageable.getOffset()).limit(pageable.getSize());
        try {
            List<ENTITY> entityList = selector.findAll();
            if(entityList == null){
                entityList = new ArrayList<>();
            }
            return postQuery(entityList);
        } catch (DbException e) {
            throw new DBException(e.getMessage());
        }
    }

    @Override
    public void delete(ENTITY entity) {
        Assert.notNull(entity, entityName + " must not be null");
        SQLiteDatabase database = manager.getDatabase();
        try {
            database.beginTransaction();

            manager.delete(entity);
            postDelete(entity);

            database.setTransactionSuccessful();
        } catch (DbException e) {
            throw new DBException(e.getMessage());
        }finally {
            database.endTransaction();
        }
    }

    @Override
    public void delete(Collection<ENTITY> entities) {
        Assert.notNull(entities, entityName + " must not be null");
        List<ENTITY> entityList = new ArrayList<>(entities);
        SQLiteDatabase database = manager.getDatabase();
        try {
            database.beginTransaction();

            manager.delete(entityList);
            postDelete(entityList);

            database.setTransactionSuccessful();
        } catch (DbException e) {
            throw new DBException(e.getMessage());
        }finally {
            database.endTransaction();
        }
    }

    @Override
    public ENTITY deleteById(ID id){
        Assert.notNull(id, "Id must not be nul");
        ENTITY entity = findById(id);
        if(entity != null){
            SQLiteDatabase database = manager.getDatabase();
            try {
                database.beginTransaction();

                manager.deleteById(actualClass, id);
                postDelete(entity);

                database.setTransactionSuccessful();
            } catch (DbException e) {
                throw new DBException(e.getMessage());
            } finally {
                database.endTransaction();
            }
        }
        return entity;
    }

    @Override
    public void deleteByIds(Collection<ID> ids) {
        Assert.notNull(ids, "Ids must not be null");
        SQLiteDatabase database = manager.getDatabase();
        try {
            database.beginTransaction();

            List<ENTITY> entityList = findByIds(ids);
            manager.delete(entityList);
            postDelete(entityList);

            database.setTransactionSuccessful();
        } catch (DbException e) {
            throw new DBException(e.getMessage());
        }finally {
            database.endTransaction();
        }
    }

    @Override
    public long count() {
        try {
            return manager.selector(actualClass).count();
        } catch (DbException e) {
            throw new DBException(e.getMessage());
        }
    }

    private List<ENTITY> postQuery(List<ENTITY> entities){
        for (ENTITY entity : entities) {
            postQuery(entity);
        }
        return entities;
    }

    protected ENTITY postQuery(ENTITY entity){
        return entity;
    }

    private List<ENTITY> postPersist(List<ENTITY> entities){
        for (ENTITY entity : entities) {
            postPersist(entity);
        }
        return entities;
    }
    protected ENTITY postPersist(ENTITY entity){
        return entity;
    }

    private List<ENTITY> postUpdate(List<ENTITY> entities){
        for (ENTITY entity : entities) {
            postUpdate(entity);
        }
        return entities;
    }
    protected ENTITY postUpdate(ENTITY entity){
        return entity;
    }

    private List<ENTITY> postDelete(List<ENTITY> entities){
        for (ENTITY entity : entities) {
            postDelete(entity);
        }
        return entities;
    }
    protected ENTITY postDelete(ENTITY entity){
        return entity;
    }

    protected void createTableIfNotExists(){
        if(manager != null){
            try {
                manager.getTable(actualClass).createTableIfNotExists();
            } catch (DbException e) {
                e.printStackTrace();
            }
        }
    }
}
