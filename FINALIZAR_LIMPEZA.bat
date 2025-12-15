@echo off
cd /d "C:\Users\Pedro Lima\Reciclai"

echo Removendo ultimo script temporario...
del LIMPAR_SCRIPT_TEMP.bat
git add -A
git commit -m "chore: remover último script temporário"
git push origin master

echo.
echo ====================================
echo REPOSITORIO LIMPO E ORGANIZADO!
echo ====================================
echo.
echo Commits totais criados: 4
echo.
echo Historico final:
git log --oneline -8
echo.
echo ====================================
echo Scripts .bat essenciais mantidos:
echo ====================================
dir *.bat /b
echo.
echo ====================================
echo CONCLUIDO COM SUCESSO!
echo ====================================
pause
del "%~f0"

