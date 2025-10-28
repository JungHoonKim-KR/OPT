@echo off
chcp 65001 >nul
color 0E
cls
echo.
echo ================================================
echo    Starting ALL Servers...
echo ================================================
echo.

REM Start Printer Server in new window
echo [1/2] Starting Printer Server...
start "Printer Server" cmd /k "color 0A && node printer-server.js"
timeout /t 2 /nobreak >nul
echo Printer Server started!
echo.

REM Start Frontend Server in new window
echo [2/2] Starting Frontend Server...
start "Frontend Server" cmd /k "color 0B && npm run dev"
timeout /t 2 /nobreak >nul
echo Frontend Server started!
echo.

echo ================================================
echo    ALL SERVERS STARTED!
echo ================================================
echo.
echo Two windows opened:
echo  - Green Window: Printer Server (port 3001)
echo  - Blue Window: Frontend Server (port 5173)
echo.
echo Press any key to exit this window
echo (Servers will keep running)
echo ================================================
pause
```

## 🚀 사용 방법:

### 개발할 때:
**`start-all.bat` 더블클릭 한 번!**
- 초록 창: 프린터 서버
- 파랑 창: 프론트 서버
- 자동으로 브라우저 열림

### 종료할 때:
각 창에서 `Ctrl + C` → `Y`

## 📁 폴더 구조:
```
your-project/
├── start-frontend.bat    ← 프론트만
├── start-printer.bat     ← 프린터만
├── start-all.bat         ← 둘 다! (추천)
├── printer-server.js
├── package.json
└── ...