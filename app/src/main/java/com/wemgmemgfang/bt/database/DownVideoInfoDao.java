package com.wemgmemgfang.bt.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.wemgmemgfang.bt.entity.DownVideoInfo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "DOWN_VIDEO_INFO".
*/
public class DownVideoInfoDao extends AbstractDao<DownVideoInfo, Long> {

    public static final String TABLENAME = "DOWN_VIDEO_INFO";

    /**
     * Properties of entity DownVideoInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property PlayPath = new Property(1, String.class, "playPath", false, "PLAY_PATH");
        public final static Property PlayTitle = new Property(2, String.class, "playTitle", false, "PLAY_TITLE");
        public final static Property PlayimgUrl = new Property(3, String.class, "PlayimgUrl", false, "PLAYIMG_URL");
        public final static Property HrefUrl = new Property(4, String.class, "hrefUrl", false, "HREF_URL");
        public final static Property HrefTitle = new Property(5, String.class, "hrefTitle", false, "HREF_TITLE");
        public final static Property Type = new Property(6, String.class, "type", false, "TYPE");
        public final static Property State = new Property(7, String.class, "state", false, "STATE");
        public final static Property MTaskStatus = new Property(8, int.class, "mTaskStatus", false, "M_TASK_STATUS");
        public final static Property TaskId = new Property(9, long.class, "taskId", false, "TASK_ID");
        public final static Property MFileSize = new Property(10, long.class, "mFileSize", false, "M_FILE_SIZE");
        public final static Property SaveVideoPath = new Property(11, String.class, "saveVideoPath", false, "SAVE_VIDEO_PATH");
        public final static Property MDownloadSize = new Property(12, long.class, "mDownloadSize", false, "M_DOWNLOAD_SIZE");
    }


    public DownVideoInfoDao(DaoConfig config) {
        super(config);
    }
    
    public DownVideoInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"DOWN_VIDEO_INFO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"PLAY_PATH\" TEXT," + // 1: playPath
                "\"PLAY_TITLE\" TEXT," + // 2: playTitle
                "\"PLAYIMG_URL\" TEXT," + // 3: PlayimgUrl
                "\"HREF_URL\" TEXT," + // 4: hrefUrl
                "\"HREF_TITLE\" TEXT," + // 5: hrefTitle
                "\"TYPE\" TEXT," + // 6: type
                "\"STATE\" TEXT," + // 7: state
                "\"M_TASK_STATUS\" INTEGER NOT NULL ," + // 8: mTaskStatus
                "\"TASK_ID\" INTEGER NOT NULL ," + // 9: taskId
                "\"M_FILE_SIZE\" INTEGER NOT NULL ," + // 10: mFileSize
                "\"SAVE_VIDEO_PATH\" TEXT," + // 11: saveVideoPath
                "\"M_DOWNLOAD_SIZE\" INTEGER NOT NULL );"); // 12: mDownloadSize
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"DOWN_VIDEO_INFO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, DownVideoInfo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String playPath = entity.getPlayPath();
        if (playPath != null) {
            stmt.bindString(2, playPath);
        }
 
        String playTitle = entity.getPlayTitle();
        if (playTitle != null) {
            stmt.bindString(3, playTitle);
        }
 
        String PlayimgUrl = entity.getPlayimgUrl();
        if (PlayimgUrl != null) {
            stmt.bindString(4, PlayimgUrl);
        }
 
        String hrefUrl = entity.getHrefUrl();
        if (hrefUrl != null) {
            stmt.bindString(5, hrefUrl);
        }
 
        String hrefTitle = entity.getHrefTitle();
        if (hrefTitle != null) {
            stmt.bindString(6, hrefTitle);
        }
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(7, type);
        }
 
        String state = entity.getState();
        if (state != null) {
            stmt.bindString(8, state);
        }
        stmt.bindLong(9, entity.getMTaskStatus());
        stmt.bindLong(10, entity.getTaskId());
        stmt.bindLong(11, entity.getMFileSize());
 
        String saveVideoPath = entity.getSaveVideoPath();
        if (saveVideoPath != null) {
            stmt.bindString(12, saveVideoPath);
        }
        stmt.bindLong(13, entity.getMDownloadSize());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, DownVideoInfo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String playPath = entity.getPlayPath();
        if (playPath != null) {
            stmt.bindString(2, playPath);
        }
 
        String playTitle = entity.getPlayTitle();
        if (playTitle != null) {
            stmt.bindString(3, playTitle);
        }
 
        String PlayimgUrl = entity.getPlayimgUrl();
        if (PlayimgUrl != null) {
            stmt.bindString(4, PlayimgUrl);
        }
 
        String hrefUrl = entity.getHrefUrl();
        if (hrefUrl != null) {
            stmt.bindString(5, hrefUrl);
        }
 
        String hrefTitle = entity.getHrefTitle();
        if (hrefTitle != null) {
            stmt.bindString(6, hrefTitle);
        }
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(7, type);
        }
 
        String state = entity.getState();
        if (state != null) {
            stmt.bindString(8, state);
        }
        stmt.bindLong(9, entity.getMTaskStatus());
        stmt.bindLong(10, entity.getTaskId());
        stmt.bindLong(11, entity.getMFileSize());
 
        String saveVideoPath = entity.getSaveVideoPath();
        if (saveVideoPath != null) {
            stmt.bindString(12, saveVideoPath);
        }
        stmt.bindLong(13, entity.getMDownloadSize());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public DownVideoInfo readEntity(Cursor cursor, int offset) {
        DownVideoInfo entity = new DownVideoInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // playPath
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // playTitle
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // PlayimgUrl
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // hrefUrl
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // hrefTitle
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // type
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // state
            cursor.getInt(offset + 8), // mTaskStatus
            cursor.getLong(offset + 9), // taskId
            cursor.getLong(offset + 10), // mFileSize
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // saveVideoPath
            cursor.getLong(offset + 12) // mDownloadSize
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, DownVideoInfo entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setPlayPath(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setPlayTitle(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setPlayimgUrl(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setHrefUrl(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setHrefTitle(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setType(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setState(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setMTaskStatus(cursor.getInt(offset + 8));
        entity.setTaskId(cursor.getLong(offset + 9));
        entity.setMFileSize(cursor.getLong(offset + 10));
        entity.setSaveVideoPath(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setMDownloadSize(cursor.getLong(offset + 12));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(DownVideoInfo entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(DownVideoInfo entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(DownVideoInfo entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
