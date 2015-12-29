java -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=${deploy.debugPort} -jar "${release.finalName}" %1
