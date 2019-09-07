@echo off
title CreateFileAlias
setlocal enableDelayedExpansion
cd .\src\main\resources\assets\irobot\lua

(echo.) > FileAlias.txt

for /r %%f in (*) do (
	set v=%%f
	
	(echo !v:~72,300!) >> FileAlias.txt
)

cd ../../../../../..