@echo off
chcp 65001 >nul
color 0A
echo.
echo ═══════════════════════════════════════════
echo   🎨 OPT 테스트 전시 시작
echo ═══════════════════════════════════════════
echo.

REM 프린터 확인
echo [1/3] 프린터 연결 확인 중...
timeout /t 2 /nobreak >nul
echo ✅ 준비 완료
echo.

REM 서버 시작
echo [2/3] 서버 시작 중...
echo.
start "" http://localhost:3000
node local-server.js

REM 종료 시
echo.
echo ═══════════════════════════════════════════
echo   전시가 종료되었습니다.
echo   창을 닫아도 됩니다.
echo ═══════════════════════════════════════════
pause