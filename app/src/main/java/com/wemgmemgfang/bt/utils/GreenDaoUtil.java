package com.wemgmemgfang.bt.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.wemgmemgfang.bt.database.CollectionInfoDao;
import com.wemgmemgfang.bt.database.DaoMaster;
import com.wemgmemgfang.bt.database.DaoSession;
import com.wemgmemgfang.bt.database.UserInfoDao;
import com.wemgmemgfang.bt.entity.CollectionInfo;

/**
 * Description:
 * author         xulei
 * Date           2017/7/5
 */

public class GreenDaoUtil {
    private static DaoSession daoSession;
    private static SQLiteDatabase database;

    /**
     * 初始化数据库
     * 建议放在Application中执行
     */
    public static void initDataBase(Context context) {
        //通过DaoMaster的内部类DevOpenHelper，可得到一个SQLiteOpenHelper对象。
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, "wengmf.db", null); //数据库名称
        database = devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }

    public static SQLiteDatabase getDatabase() {
        return database;
    }

    public static UserInfoDao getUserInfoDao() {
        return daoSession.getUserInfoDao();
    }

    public static CollectionInfoDao getCollectionInfoDao() {
        return daoSession.getCollectionInfoDao();
    }

}
