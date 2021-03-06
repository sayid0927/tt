package com.wemgmemgfang.bt.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.wemgmemgfang.bt.entity.UserInfo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "USER_INFO".
*/
public class UserInfoDao extends AbstractDao<UserInfo, Long> {

    public static final String TABLENAME = "USER_INFO";

    /**
     * Properties of entity UserInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property UserName = new Property(1, String.class, "userName", false, "USER_NAME");
        public final static Property UserStart = new Property(2, String.class, "userStart", false, "USER_START");
        public final static Property UserDate = new Property(3, String.class, "userDate", false, "USER_DATE");
        public final static Property UserSiz = new Property(4, String.class, "userSiz", false, "USER_SIZ");
        public final static Property UserWeChat = new Property(5, String.class, "userWeChat", false, "USER_WE_CHAT");
        public final static Property UserUuid = new Property(6, String.class, "userUuid", false, "USER_UUID");
    }


    public UserInfoDao(DaoConfig config) {
        super(config);
    }
    
    public UserInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"USER_INFO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"USER_NAME\" TEXT," + // 1: userName
                "\"USER_START\" TEXT," + // 2: userStart
                "\"USER_DATE\" TEXT," + // 3: userDate
                "\"USER_SIZ\" TEXT," + // 4: userSiz
                "\"USER_WE_CHAT\" TEXT," + // 5: userWeChat
                "\"USER_UUID\" TEXT);"); // 6: userUuid
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"USER_INFO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, UserInfo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String userName = entity.getUserName();
        if (userName != null) {
            stmt.bindString(2, userName);
        }
 
        String userStart = entity.getUserStart();
        if (userStart != null) {
            stmt.bindString(3, userStart);
        }
 
        String userDate = entity.getUserDate();
        if (userDate != null) {
            stmt.bindString(4, userDate);
        }
 
        String userSiz = entity.getUserSiz();
        if (userSiz != null) {
            stmt.bindString(5, userSiz);
        }
 
        String userWeChat = entity.getUserWeChat();
        if (userWeChat != null) {
            stmt.bindString(6, userWeChat);
        }
 
        String userUuid = entity.getUserUuid();
        if (userUuid != null) {
            stmt.bindString(7, userUuid);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, UserInfo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String userName = entity.getUserName();
        if (userName != null) {
            stmt.bindString(2, userName);
        }
 
        String userStart = entity.getUserStart();
        if (userStart != null) {
            stmt.bindString(3, userStart);
        }
 
        String userDate = entity.getUserDate();
        if (userDate != null) {
            stmt.bindString(4, userDate);
        }
 
        String userSiz = entity.getUserSiz();
        if (userSiz != null) {
            stmt.bindString(5, userSiz);
        }
 
        String userWeChat = entity.getUserWeChat();
        if (userWeChat != null) {
            stmt.bindString(6, userWeChat);
        }
 
        String userUuid = entity.getUserUuid();
        if (userUuid != null) {
            stmt.bindString(7, userUuid);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public UserInfo readEntity(Cursor cursor, int offset) {
        UserInfo entity = new UserInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // userName
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // userStart
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // userDate
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // userSiz
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // userWeChat
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6) // userUuid
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, UserInfo entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUserName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setUserStart(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setUserDate(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setUserSiz(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setUserWeChat(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setUserUuid(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(UserInfo entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(UserInfo entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(UserInfo entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
