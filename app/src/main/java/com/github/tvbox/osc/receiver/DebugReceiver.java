package com.github.tvbox.osc.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.github.tvbox.osc.util.PlayerHelper;

/**
 * 调试模式广播接收器
 */
public class DebugReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String mode = intent.getStringExtra("mode");
        if ("enable".equals(mode)) {
            PlayerHelper.enableDebug(context);
        } else if ("disable".equals(mode)) {
            PlayerHelper.disableDebug(context);
        }
    }
} 