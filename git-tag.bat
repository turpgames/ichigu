@echo off
set /p version="version: v"
set /p msg="message: "
git tag -a v%version% -m "%msg%"
git push origin --tags
pause