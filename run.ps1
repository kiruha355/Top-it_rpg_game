$ErrorActionPreference = 'Stop'
Set-Location $PSScriptRoot
# если Java не установлена в систему, берём JDK из папки .tools или из программ JetBrains
if (!$env:JAVA_HOME -and !(Get-Command javac -ErrorAction SilentlyContinue)) {
    $jdk = Get-ChildItem -LiteralPath "$PSScriptRoot\.tools" -Directory -ErrorAction SilentlyContinue |
        Where-Object { Test-Path -LiteralPath "$($_.FullName)\bin\javac.exe" } |
        Select-Object -First 1
    if (!$jdk) {
        $jdk = Get-ChildItem -Path "$env:ProgramFiles\JetBrains\*\jbr" -Directory -ErrorAction SilentlyContinue |
            Where-Object { Test-Path -LiteralPath "$($_.FullName)\bin\javac.exe" } |
            Select-Object -First 1
    }
    if (!$jdk) { throw 'Install JDK 17 or newer, then run again.' }
    $env:JAVA_HOME = $jdk.FullName
}
& "$PSScriptRoot\gradlew.bat" lwjgl3:run
if ($LASTEXITCODE -ne 0) { throw 'Game failed to start.' }
