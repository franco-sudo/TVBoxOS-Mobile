package com.github.tvbox.osc.ui.activity;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.tvbox.osc.data.AppDataManager;

import java.io.File;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            // 初始化数据库
            AppDataManager.init();
        } catch (Exception e) {
            // 显示数据库错误对话框
            Toast.makeText(this, "数据库初始化失败，正在尝试修复", Toast.LENGTH_LONG).show();
            // 清除旧数据库
            File dbFile = getDatabasePath(AppDataManager.dbPath());
            if (dbFile.exists()) {
                dbFile.delete();
                Toast.makeText(this, "已删除旧数据库，请重启应用", Toast.LENGTH_LONG).show();
            }
        }
    }
} 