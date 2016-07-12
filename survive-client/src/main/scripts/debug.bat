start "" javaw -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=${deploy.debugPort} -jar -Djava.library.path="${release.natives.path}" "${release.finalName}"
exit