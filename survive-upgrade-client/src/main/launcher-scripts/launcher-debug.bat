@echo off
:runUpgrade
	rmdir /s /q ${release.upgrade-client-temp.path}
	xcopy "${release.upgrade-client.path}" "${release.upgrade-client-temp.path}/*" /s /q
	set rootPath=%~dp0
	set rootPath=%rootPath:~0,-1%

	echo --- Root path is "%rootPath%"

	cd ${release.upgrade-client-temp.path}
	call ${deploy.upgrade-client.debugPath} "%rootPath%"
	set errLevel=%ERRORLEVEL%
	cd %rootPath%

	if %errLevel% EQU 3 (
		echo --- Failed upgrade execution
	) else (
		rmdir /s /q ${release.upgrade-client-temp.path}
		if %errLevel% GTR 0 (
			echo --- Successfully upgraded. Re-running upgrade-client.
			goto runUpgrade
		) else (
			echo --- Successfully upgraded client to latest version.
			cd ${release.client.path}
			call ${deploy.client.runPath}
		)
	)
	

