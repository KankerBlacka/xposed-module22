@echo off
echo ========================================
echo   Xposed Module - Environment Setup
echo ========================================
echo.

echo Checking your development environment...
echo.

REM Check if Java is installed
echo [1/4] Checking Java installation...
java -version >nul 2>&1
if %errorlevel% equ 0 (
    echo ✓ Java is installed and accessible
    java -version
) else (
    echo ✗ Java is NOT installed or not in PATH
    echo.
    echo SOLUTION:
    echo 1. Download Java JDK from: https://adoptium.net/
    echo 2. Install the JDK
    echo 3. Add Java to your system PATH
    echo.
    echo Or run this command to download Java automatically:
    echo winget install EclipseAdoptium.Temurin.11.JDK
    echo.
    goto java_missing
)
echo.

REM Check Android SDK
echo [2/4] Checking Android SDK...
if defined ANDROID_HOME (
    echo ✓ ANDROID_HOME is set to: %ANDROID_HOME%
    if exist "%ANDROID_HOME%\platform-tools\adb.exe" (
        echo ✓ Android platform tools found
    ) else (
        echo ⚠ Android platform tools not found in SDK
    )
) else (
    echo ✗ ANDROID_HOME is NOT set
    echo.
    echo SOLUTION:
    echo 1. Download Android Studio from: https://developer.android.com/studio
    echo 2. Install Android Studio
    echo 3. Set ANDROID_HOME environment variable to SDK location
    echo    Example: set ANDROID_HOME=C:\Users\%USERNAME%\AppData\Local\Android\Sdk
    echo.
)
echo.

REM Check Gradle
echo [3/4] Checking Gradle wrapper...
if exist "gradlew.bat" (
    echo ✓ Gradle wrapper found
) else (
    echo ✗ Gradle wrapper not found
    echo Make sure you're in the correct project directory
)
echo.

REM Check project structure
echo [4/4] Checking project structure...
if exist "app\build.gradle" (
    echo ✓ App build.gradle found
) else (
    echo ✗ App build.gradle not found
)

if exist "app\src\main\java\com\example\xposedmodule\MainHook.java" (
    echo ✓ MainHook.java found
) else (
    echo ✗ MainHook.java not found
)

if exist "app\src\main\assets\xposed_init" (
    echo ✓ xposed_init file found
) else (
    echo ✗ xposed_init file not found
)
echo.

REM Summary
echo ========================================
echo   ENVIRONMENT CHECK SUMMARY
echo ========================================
java -version >nul 2>&1
if %errorlevel% equ 0 (
    if defined ANDROID_HOME (
        if exist "gradlew.bat" (
            echo ✓ Your environment is ready for building!
            echo.
            echo To build your APK, run:
            echo   build_apk.bat
            echo.
            echo Or build manually with:
            echo   gradlew.bat assembleDebug
            goto end
        )
    )
)

echo ⚠ Your environment needs some setup before building.
echo Please follow the solutions above and run this script again.
echo.

:java_missing
echo ========================================
echo   QUICK JAVA INSTALLATION
echo ========================================
echo.
echo Would you like to install Java automatically? (Requires Windows 10/11 with winget)
echo.
set /p install_java="Install Java now? (y/n): "
if /i "%install_java%"=="y" (
    echo Installing Java JDK 11...
    winget install EclipseAdoptium.Temurin.11.JDK
    echo.
    echo Java installation completed. Please restart this script.
) else (
    echo Manual installation required. Please visit: https://adoptium.net/
)

:end
echo.
pause
