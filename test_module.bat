@echo off
echo ========================================
echo LSPosed Module Test Script
echo ========================================
echo.

echo 1. Building APK...
call gradlew.bat assembleDebug
if %errorlevel% neq 0 (
    echo ERROR: Build failed!
    pause
    exit /b 1
)

echo.
echo 2. Installing APK...
adb install -r app\build\outputs\apk\debug\app-debug.apk
if %errorlevel% neq 0 (
    echo ERROR: Installation failed!
    pause
    exit /b 1
)

echo.
echo 3. APK installed successfully!
echo.
echo 4. Next steps:
echo    - Open LSPosed Manager
echo    - Go to Modules
echo    - Enable "Xposed Module"
echo    - Add "com.superplanet.swordmaster" to scope
echo    - Add "com.android.systemui" to scope (for testing)
echo    - Reboot device
echo.
echo 5. Test steps:
echo    - Launch Sword Master Story
echo    - Look for toast messages
echo    - Check LSPosed logs for "SwordMasterMod" messages
echo.
echo 6. To check logs:
echo    adb logcat | findstr "SwordMasterMod"
echo.
echo 7. To check system logs:
echo    adb logcat | findstr "SWORD MASTER MOD IS ACTIVE"
echo.

pause
