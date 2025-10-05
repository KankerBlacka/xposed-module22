# LSPosed Module APK Builder

This is a complete LSPosed/Xposed module project ready for building into an APK. Compatible with modern LSPosed framework and traditional Xposed Framework.

## 🚀 Quick Start

### 🌟 **RECOMMENDED: GitHub Build (No Setup Required!)**

**Skip all local setup headaches - build in the cloud!**

1. **Create GitHub Repository** → Upload this project
2. **GitHub automatically builds your APK** 
3. **Download ready APK** from Actions tab

👉 **[Follow the GitHub Build Guide](GITHUB_BUILD_GUIDE.md)** ← **EASIEST METHOD**

### 💻 **Alternative: Local Build**

If you prefer building locally:

```cmd
setup_environment.bat
build_apk.bat
```

*Note: Requires Java JDK setup which can be complex*

## 📋 Prerequisites

The setup script will check for these automatically:

1. **Java Development Kit (JDK) 11 or higher**
   - Download from: https://adoptium.net/
   - Add Java to your system PATH

2. **Android SDK (Optional but recommended)**
   - Download Android Studio: https://developer.android.com/studio
   - Set ANDROID_HOME environment variable to SDK location

## Project Structure

```
XposedModule/
├── app/
│   ├── build.gradle              # App-level build configuration
│   ├── src/main/
│   │   ├── AndroidManifest.xml   # App manifest with Xposed metadata
│   │   ├── java/com/example/xposedmodule/
│   │   │   ├── MainActivity.java  # Main activity (optional UI)
│   │   │   └── MainHook.java      # Xposed hook implementation
│   │   ├── assets/
│   │   │   └── xposed_init       # Points to main hook class
│   │   └── res/                  # Resources (layouts, strings, etc.)
├── build.gradle                  # Project-level build configuration
├── settings.gradle               # Project settings
└── gradlew.bat                   # Gradle wrapper for Windows
```

## Building the APK

### Method 1: Using the batch script (Recommended)
```cmd
build_apk.bat
```

### Method 2: Using Gradle directly
```cmd
gradlew.bat assembleDebug
```

## Installing and Using the Module

1. **Install the APK on your Android device:**
   ```cmd
   adb install app\build\outputs\apk\debug\app-debug.apk
   ```

2. **Enable Xposed Framework:**
   - Install Xposed Framework on your device (requires root)
   - Use LSPosed or EdXposed for newer Android versions

3. **Activate the module:**
   - Open Xposed Installer/LSPosed Manager
   - Find "Xposed Module" in the modules list
   - Enable it
   - Reboot your device

## 🔧 Module Functionality

This example module includes:

- **SystemUI Hooks**: Hooks into Android SystemUI processes with multi-version compatibility
- **Activity Logging**: Logs when activities are created across all apps
- **LSPosed Optimized**: Configured specifically for LSPosed framework
- **Modern UI**: Material Design interface with status indicators
- **Smart Scope**: Pre-configured to hook only relevant system components

## 🎯 LSPosed Compatibility

This module is optimized for **LSPosed**, the modern successor to Xposed:

- ✅ **Android 8.1+** with LSPosed Manager
- ✅ **Android 5.0-8.0** with EdXposed  
- ✅ **Android 4.1-4.4** with traditional Xposed

## 🛠 Customizing the Module

To modify the module behavior, edit `MainHook.java`:

```java
public class MainHook implements IXposedHookLoadPackage {
    private static final String TAG = "YourModuleName";
    
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {
        // Hook specific apps
        if (lpparam.packageName.equals("com.target.app")) {
            hookTargetApp(lpparam);
        }
    }
    
    private void hookTargetApp(XC_LoadPackage.LoadPackageParam lpparam) {
        // Your hooks here
        XposedBridge.log(TAG + ": Hooking " + lpparam.packageName);
    }
}
```

### Adding New Hooks

1. **Hook Method Calls:**
```java
XposedHelpers.findAndHookMethod("ClassName", lpparam.classLoader, 
    "methodName", parameterTypes, new XC_MethodHook() {
    @Override
    protected void beforeHookedMethod(MethodHookParam param) {
        // Code before method execution
    }
    
    @Override
    protected void afterHookedMethod(MethodHookParam param) {
        // Code after method execution
    }
});
```

2. **Hook Constructors:**
```java
XposedHelpers.findAndHookConstructor("ClassName", lpparam.classLoader,
    parameterTypes, new XC_MethodHook() {
    // Hook logic
});
```

3. **Modify Return Values:**
```java
@Override
protected void afterHookedMethod(MethodHookParam param) {
    param.setResult(yourNewReturnValue);
}
```

## Troubleshooting

- **Build fails**: Ensure Java and Android SDK are properly installed
- **Module not detected**: Check that `xposed_init` file contains correct class name
- **Hooks not working**: Verify Xposed Framework is active and module is enabled

## Files Created

After successful build, the APK will be located at:
`app\build\outputs\apk\debug\app-debug.apk`
