FROM isuper/java-oracle:jdk_8

# Copy upgrade server under image
COPY target/deploy/survive-server /usr/server
WORKDIR /usr/server

EXPOSE 11099
CMD ["sh", "run.sh"]