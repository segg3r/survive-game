rmdir /s /q ${release.upgrade-client-temp.path}
xcopy "${release.upgrade-client.path}" "${release.upgrade-client-temp.path}/*" /s /q
set rootPath=%~dp0

echo Root path is "%rootPath%"

cd ${release.upgrade-client-temp.path}
${deploy.upgrade-client.debugPath} %rootPath% (
	cd ../
) || (
	cd ../
)

