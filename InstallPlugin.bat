@echo off
pushd "%~dp0"

set installCommand=adb install -r ./app/build/outputs/apk/debug/app-debug.apk
echo ^>%installCommand%
adb install -r ./app/build/outputs/apk/debug/app-debug.apk
echo %ERRORLEVEL%

popd
exit /b %ERRORLEVEL%
