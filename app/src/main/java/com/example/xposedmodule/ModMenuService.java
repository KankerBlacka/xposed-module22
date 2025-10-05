package com.example.xposedmodule;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class ModMenuService extends Service {
    
    private static final String TAG = "SwordMasterMod";
    private WindowManager windowManager;
    private View floatingView;
    private boolean isMenuVisible = false;
    
    @Override
    public void onCreate() {
        super.onCreate();
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getAction() != null) {
            switch (intent.getAction()) {
                case "SHOW_MENU":
                    showFloatingMenu();
                    break;
                case "HIDE_MENU":
                    hideFloatingMenu();
                    break;
                case "TOGGLE_MENU":
                    toggleFloatingMenu();
                    break;
            }
        }
        return START_STICKY;
    }
    
    private void showFloatingMenu() {
        if (isMenuVisible) return;
        
        try {
            XposedBridge.log(TAG + ": Creating floating menu...");
            
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            floatingView = inflater.inflate(R.layout.floating_mod_menu, null);
            
            // Simple window parameters
            WindowManager.LayoutParams params = new WindowManager.LayoutParams();
            
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            } else {
                params.type = WindowManager.LayoutParams.TYPE_PHONE;
            }
            
            params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
            params.format = PixelFormat.TRANSLUCENT;
            params.width = WindowManager.LayoutParams.WRAP_CONTENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.gravity = Gravity.TOP | Gravity.START;
            params.x = 20;
            params.y = 100;
            
            setupClickListeners();
            
            windowManager.addView(floatingView, params);
            isMenuVisible = true;
            
            XposedBridge.log(TAG + ": Floating menu created successfully!");
            Toast.makeText(this, "Sword Master Mod Menu Active", Toast.LENGTH_SHORT).show();
            
        } catch (Exception e) {
            XposedBridge.log(TAG + ": Error creating menu: " + e.getMessage());
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    
    private void hideFloatingMenu() {
        if (!isMenuVisible || floatingView == null) return;
        
        try {
            windowManager.removeView(floatingView);
            isMenuVisible = false;
            floatingView = null;
        } catch (Exception e) {
            // Handle error silently
        }
    }
    
    private void toggleFloatingMenu() {
        if (isMenuVisible) {
            hideFloatingMenu();
        } else {
            showFloatingMenu();
        }
    }
    
    private void setupClickListeners() {
        if (floatingView == null) return;
        
        // Make the menu draggable
        floatingView.setOnTouchListener(new FloatingViewTouchListener(windowManager, floatingView));
        
        // Close button
        View closeButton = floatingView.findViewById(R.id.closeButton);
        if (closeButton != null) {
            closeButton.setOnClickListener(v -> hideFloatingMenu());
        }
        
        // Test button
        View testButton = floatingView.findViewById(R.id.testButton);
        if (testButton != null) {
            testButton.setOnClickListener(v -> {
                Toast.makeText(this, "Mod Menu Test Button Clicked!", Toast.LENGTH_SHORT).show();
            });
        }
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        hideFloatingMenu();
    }
    
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    
    // Static methods to control the service from other parts of the app
    public static void showMenu(Context context) {
        Intent intent = new Intent(context, ModMenuService.class);
        intent.setAction("SHOW_MENU");
        context.startService(intent);
    }
    
    public static void hideMenu(Context context) {
        Intent intent = new Intent(context, ModMenuService.class);
        intent.setAction("HIDE_MENU");
        context.startService(intent);
    }
    
    public static void toggleMenu(Context context) {
        Intent intent = new Intent(context, ModMenuService.class);
        intent.setAction("TOGGLE_MENU");
        context.startService(intent);
    }
}
