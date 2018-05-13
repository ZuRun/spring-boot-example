FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD lottery-server-1.0-SNAPSHOT.jar app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS=""
# 指定环境为test
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar -Dspring.profiles.active=test /app.jar" ]