package com.example.guyunwu.repository;

import android.util.Log;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

public class DBManager {

    private static final String TAG = "DBManager";

    // configure DbManager in singleton mode
    private static final DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
            .setDbName("guyunwu.db")
            .setDbVersion(1)
            .setDbOpenListener(db -> {
                // 开启WAL, 对写入加速提升巨大
                db.getDatabase().enableWriteAheadLogging();
            })
            .setDbUpgradeListener((db, oldVersion, newVersion) -> {

            });

    private static DbManager dbManager;

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
