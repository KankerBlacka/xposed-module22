package com.example.xposedmodule;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        TextView statusText = findViewById(R.id.statusText);
        
        // Check if Xposed Framework is active
        if (isXposedActive()) {
            statusText.setText("✓ Module Status: Active in LSPosed");
            statusText.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
        } else {
            statusText.setText("⚠ Module Status: LSPosed Framework not detected");
            statusText.setTextColor(getResources().getColor(android.R.color.holo_orange_dark));
        }
        
        // Add test button for mod menu
        Button testButton = findViewById(R.id.testModMenuButton);
        if (testButton != null) {
            testButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Test if module is working
                    testModule();
                }
            });
        }
        
        // Add debug button
        Button debugButton = findViewById(R.id.debugButton);
        if (debugButton != null) {
            debugButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Show debug information
                    showDebugInfo();
                }
            });
        }
    }
    
    private boolean isXposedActive() {
        // This method will return false when running normally
        // It would return true only when the module is loaded by Xposed/LSPosed
        return false;
    }
    
    private void showDebugInfo() {
        StringBuilder debugInfo = new StringBuilder();
        debugInfo.append("=== DEBUG INFO ===\n");
        debugInfo.append("Module Package: ").append(getPackageName()).append("\n");
        debugInfo.append("Target: Sword Master Story\n");
        debugInfo.append("Overlay Permission: ").append(android.provider.Settings.canDrawOverlays(this) ? "GRANTED" : "NOT GRANTED").append("\n");
        debugInfo.append("Service Available: ").append(checkServiceAvailable() ? "YES" : "NO").append("\n\n");
        debugInfo.append("=== HOW TO FIND PACKAGE NAME ===\n");
        debugInfo.append("1. Install 'Package Name Viewer' app\n");
        debugInfo.append("2. Or use ADB: adb shell pm list packages | grep sword\n");
        debugInfo.append("3. Or check LSPosed logs for detected packages\n");
        debugInfo.append("4. Look for packages containing 'sword' or 'superplanet'\n");
        debugInfo.append("===============================");
        
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Debug Information")
               .setMessage(debugInfo.toString())
               .setPositiveButton("OK", null)
               .show();
    }
    
    private boolean checkServiceAvailable() {
        try {
            // Try to create the service
            Intent serviceIntent = new Intent(this, ModMenuService.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    private void testModule() {
        // Simple test to show module info
        StringBuilder info = new StringBuilder();
        info.append("Module Package: ").append(getPackageName()).append("\n");
        info.append("Target: com.superplanet.swordmaster\n");
        info.append("Architecture: ").append(android.os.Build.SUPPORTED_ABIS[0]).append("\n");
        info.append("Android Version: ").append(android.os.Build.VERSION.RELEASE).append("\n");
        info.append("SDK: ").append(android.os.Build.VERSION.SDK_INT).append("\n\n");
        info.append("If you see this, the module app is working.\n");
        info.append("Check LSPosed logs for module loading messages.");
        
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Module Test")
               .setMessage(info.toString())
               .setPositiveButton("OK", null)
               .show();
    }
}
