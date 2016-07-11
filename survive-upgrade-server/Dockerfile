FROM isuper/java-oracle:jdk_8

# Maven download and unzip
ENV MAVEN_VERSION 3.3.9

RUN mkdir -p /usr/share/maven \
  && curl -fsSL http://apache.osuosl.org/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz \
    | tar -xzC /usr/share/maven --strip-components=1 \
  && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

ENV MAVEN_HOME /usr/share/maven
ENV PATH $PATH:/usr/share/maven/bin

VOLUME /root/.m2

# Copy upgrade server under image
COPY target/deploy/survive-upgrade-server /usr/upgrade-server
VOLUME /usr/upgrade-server/repo
VOLUME /usr/upgrade-server/upgrades
WORKDIR /usr/upgrade-server

EXPOSE 11199
CMD ["sh", "run.sh"]