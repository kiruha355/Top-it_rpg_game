$ErrorActionPreference = 'Stop'
Set-Location $PSScriptRoot
$jdk = Get-ChildItem -LiteralPath "$PSScriptRoot\.tools" -Directory -ErrorAction SilentlyContinue |
    Where-Object { Test-Path -LiteralPath "$($_.FullName)\bin\javac.exe" } |
    Select-Object -First 1
if (!$jdk) {
    $jdk = Get-ChildItem -Path "$env:ProgramFiles\JetBrains\*\jbr" -Directory -ErrorAction SilentlyContinue |
        Where-Object { Test-Path -LiteralPath "$($_.FullName)\bin\javac.exe" } |
        Select-Object -First 1
}
if ($jdk) {
    $compiler = "$($jdk.FullName)\bin\javac.exe"
    $runtime = "$($jdk.FullName)\bin\java.exe"
} else {
    $compiler = (Get-Command javac -ErrorAction SilentlyContinue).Source
    $runtime = (Get-Command java -ErrorAction SilentlyContinue).Source
    if (!$compiler -or !$runtime) { throw 'Install JDK 17 or newer, then run again.' }
}
New-Item -ItemType Directory -Force build | Out-Null
$sources = Get-ChildItem -LiteralPath "$PSScriptRoot\src" -Filter '*.java' | ForEach-Object { $_.FullName }
& $compiler -encoding UTF-8 -d build $sources
if ($LASTEXITCODE -ne 0) { throw 'Compilation failed.' }
& $runtime -cp build Main
