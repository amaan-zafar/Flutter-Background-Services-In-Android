package com.example.backgroundservices;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;


public class MainActivity extends FlutterActivity {
    private Intent forService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        forService = new Intent(MainActivity.this,MyService.class);
        new MethodChannel(getFlutterEngine().getDartExecutor().getBinaryMessenger(), "com.example.backgroundservices.messages")
                .setMethodCallHandler(new MethodChannel.MethodCallHandler() {
                    @Override
                    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
                        if (call.method.equals("startService")){
                            startService();
                            result.success("Service started");
                        }
                    }
                });
    }

    private void startService () {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            startForegroundService(forService);
        else
            startService(forService);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(forService);
    }
}
