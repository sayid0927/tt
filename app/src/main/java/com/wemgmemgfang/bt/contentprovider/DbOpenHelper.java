package com.wemgmemgfang.bt.contentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DbOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "playstart_provider.db";
    public static final String PLAY_TABLE_NAME = "start";

    private static final int DB_VERSION = 1;

    private String CREATE_PLAYCONTROLLER= "CREATE TABLE IF NOT EXISTS "
            + PLAY_TABLE_NAME + "(_id INTEGER PRIMARY KEY, type TEXT, isPlay TEXT)";

    public DbOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PLAYCONTROLLER);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
