#!/bin/sh

#
# resolve symlinks
#

PRG=$0

while [ -h "$PRG" ]; do
    ls=`ls -ld "$PRG"`
    link=`expr "$ls" : '^.*-> \(.*\)$' 2>/dev/null`
    if expr "$link" : '^/' 2> /dev/null >/dev/null; then
	PRG="$link"
    else
	PRG="`dirname "$PRG"`/$link"
    fi
done

progdir=`dirname "$PRG"`
cd $progdir

java -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=${deploy.debugPort} -jar -Djava.library.path="${release.natives.path}" "${release.finalName}" &