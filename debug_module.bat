@echo off
echo ========================================
echo LSPosed Module Debug Script
echo ========================================
echo.

echo 1. Building APK with x86_64 support...
call gradlew.bat assembleDebug
if %errorlevel% neq 0 (
    echo ERROR: Build failed!
    pause
    exit /b 1
)

echo.
echo 2. APK built successfully!
echo Location: app\build\outputs\apk\debug\app-debug.apk
echo.

echo 3. Installing APK...
adb install -r app\build\outputs\apk\debug\app-debug.apk
if %errorlevel% neq 0 (
    echo ERROR: Installation failed!
    echo Make sure device is connected and USB debugging is enabled
    pause
    exit /b 1
)

echo.
echo 4. APK installed successfully!
echo.
echo 5. Next steps:
echo    - Open LSPosed Manager
echo    - Go to Modules
echo    - Enable "Xposed Module"
echo    - Add "com.superplanet.swordmaster" to scope
echo    - Reboot device
echo    - Launch Sword Master Story
echo    - Check LSPosed logs for "TestModule" messages
echo.

echo 6. To check logs:
echo    adb logcat | findstr "TestModule"
echo.

pause
