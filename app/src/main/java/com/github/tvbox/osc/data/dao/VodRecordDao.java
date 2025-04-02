package com.github.tvbox.osc.data.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.github.tvbox.osc.cache.VodRecord;
import com.github.tvbox.osc.data.AppDataManager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class VodRecordDao implements com.github.tvbox.osc.cache.VodRecordDao {
    
    private static final String TABLE_NAME = "vodRecord";
    
    @Override
    public VodRecord getVodRecord(String sourceKey, String vodId) {
        try {
            SQLiteDatabase database = AppDataManager.getReadableDatabase();
            Cursor cursor = database.query(TABLE_NAME, null, "sourceKey=? and vodId=?", new String[]{sourceKey, vodId}, null, null, null);
            if (cursor.moveToFirst()) {
                VodRecord record = new VodRecord();
                record.id = cursor.getInt(cursor.getColumnIndex("id"));
                record.vodId = cursor.getString(cursor.getColumnIndex("vodId"));
                record.sourceKey = cursor.getString(cursor.getColumnIndex("sourceKey"));
                record.updateTime = cursor.getLong(cursor.getColumnIndex("updateTime"));
                record.dataJson = cursor.getString(cursor.getColumnIndex("dataJson"));
                cursor.close();
                return record;
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public long insert(VodRecord record) {
        try {
            SQLiteDatabase database = AppDataManager.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("vodId", record.vodId);
            values.put("updateTime", record.updateTime);
            values.put("sourceKey", record.sourceKey);
            values.put("dataJson", record.dataJson);
            return database.replace(TABLE_NAME, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    @Override
    public int delete(VodRecord record) {
        try {
            SQLiteDatabase database = AppDataManager.getWritableDatabase();
            return database.delete(TABLE_NAME, "id=?", new String[]{String.valueOf(record.id)});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    @Override
    public List<VodRecord> getAll(int limit) {
        List<VodRecord> result = new ArrayList<>();
        try {
            SQLiteDatabase database = AppDataManager.getReadableDatabase();
            Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, "updateTime desc", limit > 0 ? String.valueOf(limit) : null);
            while (cursor.moveToNext()) {
                VodRecord record = new VodRecord();
                record.id = cursor.getInt(cursor.getColumnIndex("id"));
                record.vodId = cursor.getString(cursor.getColumnIndex("vodId"));
                record.sourceKey = cursor.getString(cursor.getColumnIndex("sourceKey"));
                record.updateTime = cursor.getLong(cursor.getColumnIndex("updateTime"));
                record.dataJson = cursor.getString(cursor.getColumnIndex("dataJson"));
                result.add(record);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    @Override
    public int getCount() {
        try {
            SQLiteDatabase database = AppDataManager.getReadableDatabase();
            Cursor cursor = database.rawQuery("select count(*) from " + TABLE_NAME, null);
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            cursor.close();
            return count;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    @Override
    public int reserver(int size) {
        try {
            SQLiteDatabase database = AppDataManager.getWritableDatabase();
            database.execSQL("delete from " + TABLE_NAME + " where id not in (select id from " + TABLE_NAME + " order by updateTime desc limit " + size + ")");
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
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