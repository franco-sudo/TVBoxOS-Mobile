package com.github.tvbox.osc.data.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.github.tvbox.osc.cache.VodCollect;
import com.github.tvbox.osc.data.AppDataManager;

import java.util.ArrayList;
import java.util.List;

public class VodCollectDao implements com.github.tvbox.osc.cache.VodCollectDao {
    
    private static final String TABLE_NAME = "vodCollect";
    
    @Override
    public VodCollect getVodCollect(String sourceKey, String vodId) {
        try {
            SQLiteDatabase database = AppDataManager.getReadableDatabase();
            Cursor cursor = database.query(TABLE_NAME, null, "sourceKey=? and vodId=?", new String[]{sourceKey, vodId}, null, null, null);
            if (cursor.moveToFirst()) {
                VodCollect vodCollect = new VodCollect();
                vodCollect.id = cursor.getInt(cursor.getColumnIndex("id"));
                vodCollect.vodId = cursor.getString(cursor.getColumnIndex("vodId"));
                vodCollect.sourceKey = cursor.getString(cursor.getColumnIndex("sourceKey"));
                vodCollect.name = cursor.getString(cursor.getColumnIndex("name"));
                vodCollect.pic = cursor.getString(cursor.getColumnIndex("pic"));
                vodCollect.updateTime = cursor.getLong(cursor.getColumnIndex("updateTime"));
                cursor.close();
                return vodCollect;
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public VodCollect getVodCollect(int id) {
        try {
            SQLiteDatabase database = AppDataManager.getReadableDatabase();
            Cursor cursor = database.query(TABLE_NAME, null, "id=?", new String[]{String.valueOf(id)}, null, null, null);
            if (cursor.moveToFirst()) {
                VodCollect vodCollect = new VodCollect();
                vodCollect.id = cursor.getInt(cursor.getColumnIndex("id"));
                vodCollect.vodId = cursor.getString(cursor.getColumnIndex("vodId"));
                vodCollect.sourceKey = cursor.getString(cursor.getColumnIndex("sourceKey"));
                vodCollect.name = cursor.getString(cursor.getColumnIndex("name"));
                vodCollect.pic = cursor.getString(cursor.getColumnIndex("pic"));
                vodCollect.updateTime = cursor.getLong(cursor.getColumnIndex("updateTime"));
                cursor.close();
                return vodCollect;
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public long insert(VodCollect vodCollect) {
        try {
            SQLiteDatabase database = AppDataManager.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("vodId", vodCollect.vodId);
            values.put("sourceKey", vodCollect.sourceKey);
            values.put("name", vodCollect.name);
            values.put("pic", vodCollect.pic);
            values.put("updateTime", vodCollect.updateTime);
            return database.replace(TABLE_NAME, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    @Override
    public void delete(int id) {
        try {
            SQLiteDatabase database = AppDataManager.getWritableDatabase();
            database.delete(TABLE_NAME, "id=?", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public int delete(VodCollect vodCollect) {
        try {
            SQLiteDatabase database = AppDataManager.getWritableDatabase();
            return database.delete(TABLE_NAME, "id=?", new String[]{String.valueOf(vodCollect.id)});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    @Override
    public List<VodCollect> getAll() {
        List<VodCollect> result = new ArrayList<>();
        try {
            SQLiteDatabase database = AppDataManager.getReadableDatabase();
            Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, "updateTime desc");
            while (cursor.moveToNext()) {
                VodCollect vodCollect = new VodCollect();
                vodCollect.id = cursor.getInt(cursor.getColumnIndex("id"));
                vodCollect.vodId = cursor.getString(cursor.getColumnIndex("vodId"));
                vodCollect.sourceKey = cursor.getString(cursor.getColumnIndex("sourceKey"));
                vodCollect.name = cursor.getString(cursor.getColumnIndex("name"));
                vodCollect.pic = cursor.getString(cursor.getColumnIndex("pic"));
                vodCollect.updateTime = cursor.getLong(cursor.getColumnIndex("updateTime"));
                result.add(vodCollect);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    @Override
    public void deleteAll() {
        try {
            SQLiteDatabase database = AppDataManager.getWritableDatabase();
            database.delete(TABLE_NAME, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 