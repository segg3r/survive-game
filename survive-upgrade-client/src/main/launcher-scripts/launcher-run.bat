:runUpgrade
	rmdir /s /q ${release.upgrade-client-temp.path}
	xcopy "${release.upgrade-client.path}" "${release.upgrade-client-temp.path}/*" /s /q
	set rootPath=%~dp0

	echo Root path is "%rootPath%"

	cd ${release.upgrade-client-temp.path}
	call ${deploy.upgrade-client.runPath} %rootPath%
	echo %ERRORLEVEL%
	set errLevel=%ERRORLEVEL%
	echo Error level is "%errLevel%"
	cd %rootPath%
	rmdir /s /q ${release.upgrade-client-temp.path}

	if %errLevel% EQU 3 (
		echo Failed upgrade execution
	) else (
		if %errLevel% GTR 0 (
			goto runUpgrade
		) else (
			cd ${release.client.path}
			call ${deploy.client.runPath}
		)
	)
	

