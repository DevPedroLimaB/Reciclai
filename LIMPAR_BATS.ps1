# Script PowerShell para limpar arquivos .bat
$ErrorActionPreference = "SilentlyContinue"

Write-Host "==========================================" -ForegroundColor Cyan
Write-Host "  🧹 LIMPEZA DE ARQUIVOS .BAT" -ForegroundColor Cyan
Write-Host "==========================================" -ForegroundColor Cyan
Write-Host ""

# Criar pasta de backup
if (-not (Test-Path "scripts_backup")) {
    New-Item -ItemType Directory -Path "scripts_backup" | Out-Null
}

# Arquivos essenciais que devem ser mantidos
$essenciais = @(
    "gradlew.bat",
    "INICIAR_DOCKER.bat",
    "PARAR_DOCKER.bat",
    "LIMPAR_BATS_AGORA.bat"
)

Write-Host "Movendo arquivos .bat desnecessários para scripts_backup..." -ForegroundColor Yellow
Write-Host ""

$count = 0
Get-ChildItem -Path "." -Filter "*.bat" | ForEach-Object {
    if ($essenciais -notcontains $_.Name) {
        Write-Host "  Movendo: $($_.Name)" -ForegroundColor Gray
        Move-Item -Path $_.FullName -Destination "scripts_backup\" -Force
        $count++
    }
}

Write-Host ""
Write-Host "==========================================" -ForegroundColor Green
Write-Host "✅ Limpeza concluída!" -ForegroundColor Green
Write-Host "   $count arquivos movidos para scripts_backup\" -ForegroundColor Green
Write-Host ""
Write-Host "Arquivos mantidos:" -ForegroundColor White
Write-Host "  - gradlew.bat (build do projeto)" -ForegroundColor White
Write-Host "  - INICIAR_DOCKER.bat (iniciar containers)" -ForegroundColor White
Write-Host "  - PARAR_DOCKER.bat (parar containers)" -ForegroundColor White
Write-Host "==========================================" -ForegroundColor Green
Write-Host ""
Write-Host "Pressione qualquer tecla para continuar..."
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")

