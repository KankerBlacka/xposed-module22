package com.example.xposedmodule;

import android.app.Activity;
import android.widget.Toast;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class MainHook implements IXposedHookLoadPackage {
    
    private static final String TAG = "LSPosedModMenu";
    
    // Change this to your target game package name, or leave empty to hook all apps
    private static final String TARGET_PACKAGE = ""; // Example: "com.fungames.sniper3d"
    
    // Static initializer
    static {
        XposedBridge.log(TAG + ": ===== MODULE LOADED =====");
        android.util.Log.i(TAG, "===== MODULE LOADED =====");
    }
    
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        // Skip system packages to avoid issues
        if (lpparam.packageName.startsWith("android") || 
            lpparam.packageName.startsWith("com.android") ||
            lpparam.packageName.equals("system")) {
            return;
        }
        
        // Check if we should hook this package
        boolean shouldHook = TARGET_PACKAGE.isEmpty() || lpparam.packageName.equals(TARGET_PACKAGE);
        
        if (!shouldHook) {
            return;
        }
        
        XposedBridge.log(TAG + ": Hooking package: " + lpparam.packageName);
        android.util.Log.i(TAG, "Hooking package: " + lpparam.packageName);
        
        try {
            // Hook Activity.onCreate to initialize our native menu
            XposedHelpers.findAndHookMethod(Activity.class, "onCreate", android.os.Bundle.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    Activity activity = (Activity) param.thisObject;
                    String packageName = activity.getPackageName();
                    
                    XposedBridge.log(TAG + ": Activity created in: " + packageName);
                    android.util.Log.i(TAG, "Activity created in: " + packageName);
                    
                    // Initialize native mod menu
                    try {
                        if (NativeLib.isLibraryLoaded()) {
                            XposedBridge.log(TAG + ": Initializing native mod menu...");
                            NativeLib.safeInit();
                            
                            Toast.makeText(activity, 
                                "LSPosed Mod Menu Active\nPackage: " + packageName, 
                                Toast.LENGTH_LONG).show();
                        } else {
                            XposedBridge.log(TAG + ": Native library not loaded!");
                            Toast.makeText(activity, 
                                "Mod Menu Error: Native lib not loaded", 
                                Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        XposedBridge.log(TAG + ": Error initializing menu: " + e.getMessage());
                        android.util.Log.e(TAG, "Error initializing menu", e);
                    }
                }
            });
            
            XposedBridge.log(TAG + ": Successfully hooked " + lpparam.packageName);
            
        } catch (Exception e) {
            XposedBridge.log(TAG + ": Hook failed: " + e.getMessage());
            android.util.Log.e(TAG, "Hook failed", e);
            e.printStackTrace();
        }
    }
}