package com.github.tvbox.osc.data.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.github.tvbox.osc.cache.Cache;
import com.github.tvbox.osc.data.AppDataManager;

public class CacheDao implements com.github.tvbox.osc.cache.CacheDao {
    
    private static final String TABLE_NAME = "cache";
    
    /**
     * 保存缓存数据
     */
    @Override
    public long save(Cache cache) {
        try {
            SQLiteDatabase database = AppDataManager.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("key", cache.key);
            values.put("data", cache.data);
            return database.replace(TABLE_NAME, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    /**
     * 删除缓存数据
     */
    public void delete(Cache cache) {
        try {
            SQLiteDatabase database = AppDataManager.getWritableDatabase();
            database.delete(TABLE_NAME, "key=?", new String[]{cache.key});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 获取缓存对象
     */
    public Cache getCacheObj(String key) {
        try {
            SQLiteDatabase database = AppDataManager.getReadableDatabase();
            Cursor cursor = database.query(TABLE_NAME, null, "key=?", new String[]{key}, null, null, null);
            
            if (cursor.moveToFirst()) {
                Cache cache = new Cache();
                cache.key = cursor.getString(cursor.getColumnIndex("key"));
                cache.data = cursor.getBlob(cursor.getColumnIndex("data"));
                cursor.close();
                return cache;
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 获取缓存数据
     */
    @Override
    public byte[] getCache(String key) {
        Cache cache = getCacheObj(key);
        return cache != null ? cache.data : null;
    }
} 