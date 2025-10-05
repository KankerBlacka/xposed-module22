@echo off
echo ========================================
echo   GitHub Actions Setup for Xposed Module
echo ========================================
echo.

echo This script will help you set up GitHub Actions to build your APK automatically.
echo.

echo Step 1: Create GitHub repository
echo 1. Go to https://github.com/new
echo 2. Create a new repository named "xposed-module"
echo 3. Make it public (for free Actions)
echo.

echo Step 2: Upload your code
echo 1. Initialize git in this folder
echo 2. Add all files
echo 3. Commit and push to GitHub
echo.

set /p continue="Ready to initialize git? (y/n): "
if /i "%continue%"=="y" goto init_git
goto end

:init_git
echo.
echo Initializing git repository...
git init
git add .
git commit -m "Initial Xposed module project"

echo.
echo Now you need to:
echo 1. Go to https://github.com/new
echo 2. Create repository named "xposed-module"
echo 3. Copy the repository URL
echo 4. Run: git remote add origin YOUR_REPO_URL
echo 5. Run: git push -u origin main
echo.
echo After pushing, GitHub Actions will automatically build your APK!
echo You can download it from the Actions tab in your repository.
echo.

:end
pause
