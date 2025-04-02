package com.github.tvbox.osc.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

import androidx.annotation.NonNull;

import com.github.tvbox.osc.base.App;
import com.github.tvbox.osc.data.dao.CacheDao;
import com.github.tvbox.osc.data.dao.VodCollectDao;
import com.github.tvbox.osc.data.dao.VodRecordDao;
import com.github.tvbox.osc.util.FileUtils;

import java.io.File;
import java.io.IOException;


/**
 * 类描述:
 *
 * @author pj567
 * @since 2020/5/15
 */
public class AppDataManager {
    private static final int DB_FILE_VERSION = 3;
    private static final String DB_NAME = "tvbox";
    private static AppDataManager manager;
    private static SQLiteOpenHelper dbHelper;
    
    private CacheDao cacheDao;
    private VodCollectDao vodCollectDao;
    private VodRecordDao vodRecordDao;

    private AppDataManager() {
        cacheDao = new CacheDao();
        vodCollectDao = new VodCollectDao();
        vodRecordDao = new VodRecordDao();
    }

    public static void init() {
        if (manager == null) {
            synchronized (AppDataManager.class) {
                if (manager == null) {
                    manager = new AppDataManager();
                    dbHelper = new CustomSQLiteOpenHelper(App.getInstance());
                }
            }
        }
    }
    
    public static AppDataManager get() {
        if (manager == null) {
            init();
        }
        return manager;
    }

    public CacheDao getCacheDao() {
        return cacheDao;
    }

    public VodCollectDao getVodCollectDao() {
        return vodCollectDao;
    }

    public VodRecordDao getVodRecordDao() {
        return vodRecordDao;
    }

    public static String dbPath() {
        return DB_NAME;
    }
    
    private static class CustomSQLiteOpenHelper extends SQLiteOpenHelper {
        public CustomSQLiteOpenHelper(Context context) {
            super(context, dbPath(), null, DB_FILE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // 创建所需的表
            db.execSQL("CREATE TABLE IF NOT EXISTS cache (key TEXT PRIMARY KEY, data BLOB)");
            db.execSQL("CREATE TABLE IF NOT EXISTS vodRecord (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "vodId TEXT, updateTime INTEGER NOT NULL, sourceKey TEXT, dataJson TEXT)");
            db.execSQL("CREATE TABLE IF NOT EXISTS vodCollect (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "vodId TEXT, updateTime INTEGER NOT NULL, sourceKey TEXT, name TEXT, pic TEXT)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // 处理升级逻辑
            try {
                // 如果旧版本的数据库格式已经无法兼容，直接重建
                db.execSQL("DROP TABLE IF EXISTS cache");
                db.execSQL("DROP TABLE IF EXISTS vodRecord");
                db.execSQL("DROP TABLE IF EXISTS vodCollect");
                onCreate(db);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public static SQLiteDatabase getReadableDatabase() {
        if (dbHelper == null) {
            throw new RuntimeException("AppDataManager is not initialized");
        }
        return dbHelper.getReadableDatabase();
    }
    
    public static SQLiteDatabase getWritableDatabase() {
        if (dbHelper == null) {
            throw new RuntimeException("AppDataManager is not initialized");
        }
        return dbHelper.getWritableDatabase();
    }

    public static boolean backup(File path) throws IOException {
        if (dbHelper != null) {
            dbHelper.close();
        }
        File db = App.getInstance().getDatabasePath(dbPath());
        if (db.exists()) {
            FileUtils.copyFile(db, path);
            return true;
        } else {
            return false;
        }
    }

    public static boolean restore(File path) throws Exception {
        if (dbHelper != null) {
            dbHelper.close();
        }
        File db = App.getInstance().getDatabasePath(dbPath());
        if (path.exists()) {
            FileUtils.copyFile(path, db);
            dbHelper = new CustomSQLiteOpenHelper(App.getInstance());
            return true;
        } else {
            throw new Exception("备份文件不存在");
        }
    }

    public static String getDbFilePath() {
        return App.getInstance().getDatabasePath(dbPath()).getPath();
    }
}
