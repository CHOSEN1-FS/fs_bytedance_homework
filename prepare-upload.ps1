# 将 Day1/Day2/Day3 源码复制到本仓库，自动排除 build 等不应上传的内容
$ErrorActionPreference = "Stop"
$Root = Split-Path -Parent $MyInvocation.MyCommand.Path
$SourceRoot = Split-Path -Parent $Root

$Projects = @("Day1", "Day2", "Day3")
$ExcludeDirs = @("build", ".gradle", "captures", ".externalNativeBuild", ".cxx")

foreach ($name in $Projects) {
    $src = Join-Path $SourceRoot $name
    $dst = Join-Path $Root $name

    if (-not (Test-Path $src)) {
        Write-Warning "Skip: $src not found"
        continue
    }

    if (Test-Path $dst) {
        Remove-Item $dst -Recurse -Force
    }

    Write-Host "Copying $name ..."
    robocopy $src $dst /E /NFL /NDL /NJH /NJS /nc /ns /np `
        /XD $ExcludeDirs ".idea\caches" `
        /XF "local.properties" "*.iml" | Out-Null

    if ($LASTEXITCODE -ge 8) {
        throw "robocopy failed for $name (exit $LASTEXITCODE)"
    }
}

Write-Host ""
Write-Host "Done. Next steps:"
Write-Host "  1. Ensure Day3/local.properties exists locally (copy from local.properties.example)"
Write-Host "  2. cd $Root"
Write-Host "  3. git checkout --orphan clean-main"
Write-Host "  4. git add ."
Write-Host "  5. git commit -m `"chore: initial clean upload with Day1-3 source`""
Write-Host "  6. git branch -M main"
Write-Host "  7. git push -f origin main"
