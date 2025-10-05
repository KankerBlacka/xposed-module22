@echo off
echo ========================================
echo   LSPosed Module APK Builder
echo ========================================
echo.

echo Preparing to build LSPosed/Xposed Module APK...
echo.

REM Check environment first
echo [1/3] Checking build environment...
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ✗ ERROR: Java is not installed or not in PATH
    echo.
    echo SOLUTION:
    echo 1. Run setup_environment.bat to check and fix your environment
    echo 2. Or manually install Java from: https://adoptium.net/
    echo 3. Add Java to your system PATH
    echo.
    echo Would you like to run the environment setup now?
    set /p run_setup="Run setup_environment.bat? (y/n): "
    if /i "%run_setup%"=="y" (
        call setup_environment.bat
        pause
        exit /b 1
    ) else (
        pause
        exit /b 1
    )
)

echo ✓ Java is available
java -version | head -1
echo.

REM Check if Android SDK is available
echo [2/3] Checking Android SDK...
if not defined ANDROID_HOME (
    echo ⚠ WARNING: ANDROID_HOME is not set
    echo This might cause build issues, but we'll try anyway...
    echo.
    echo For best results, set ANDROID_HOME to your Android SDK location
    echo Example: set ANDROID_HOME=C:\Users\%USERNAME%\AppData\Local\Android\Sdk
    echo.
    timeout /t 3 /nobreak >nul
) else (
    echo ✓ ANDROID_HOME is set to: %ANDROID_HOME%
)
echo.

REM Clean and build
echo [3/3] Building APK...
echo Cleaning previous build...
call gradlew.bat clean
echo.

echo Building debug APK...
call gradlew.bat assembleDebug

if %errorlevel% equ 0 (
    echo.
    echo ========================================
    echo   BUILD SUCCESSFUL! 🎉
    echo ========================================
    echo.
    echo ✓ APK built successfully!
    echo 📁 APK location: app\build\outputs\apk\debug\app-debug.apk
    echo.
    echo File size:
    if exist "app\build\outputs\apk\debug\app-debug.apk" (
        for %%A in ("app\build\outputs\apk\debug\app-debug.apk") do echo   %%~zA bytes
    )
    echo.
    echo ========================================
    echo   INSTALLATION INSTRUCTIONS
    echo ========================================
    echo.
    echo METHOD 1 - ADB Installation:
    echo 1. Enable USB Debugging on your Android device
    echo 2. Connect your device via USB
    echo 3. Run: adb install app\build\outputs\apk\debug\app-debug.apk
    echo.
    echo METHOD 2 - Manual Installation:
    echo 1. Copy app-debug.apk to your device
    echo 2. Enable "Install from Unknown Sources" in Settings
    echo 3. Tap the APK file to install
    echo.
    echo ========================================
    echo   LSPOSED/XPOSED SETUP
    echo ========================================
    echo.
    echo After installing the APK:
    echo 1. Install LSPosed Manager (for Android 8.1+)
    echo    - Download from: https://github.com/LSPosed/LSPosed
    echo 2. Or install EdXposed/Xposed Framework (older devices)
    echo 3. Open LSPosed Manager / Xposed Installer
    echo 4. Find "LSPosed Example Module" in the modules list
    echo 5. Enable it and select target applications
    echo 6. Reboot your device
    echo 7. Check logs in LSPosed Manager to verify it's working
    echo.
    echo ========================================
    echo   TESTING THE MODULE
    echo ========================================
    echo.
    echo To verify the module is working:
    echo 1. Open the installed app on your device
    echo 2. Check LSPosed logs for hook messages
    echo 3. Look for log entries tagged "LSPosedExampleModule"
    echo.
) else (
    echo.
    echo ========================================
    echo   BUILD FAILED ❌
    echo ========================================
    echo.
    echo ✗ Build failed with error code: %errorlevel%
    echo.
    echo COMMON SOLUTIONS:
    echo 1. Run setup_environment.bat to check your setup
    echo 2. Make sure Java JDK 11+ is installed
    echo 3. Set ANDROID_HOME environment variable
    echo 4. Try building again after fixing issues
    echo.
    echo Need help? Check the error messages above for specific issues.
)

echo.
echo Press any key to exit...
pause >nul
