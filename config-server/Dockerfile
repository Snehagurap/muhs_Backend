FROM openjdk:17-alpine
RUN apk update
RUN apk add git
RUN mkdir /opt/config-server-repo
RUN git config --global user.email "test@test.com"
RUN git config --global user.name "test"
RUN git config --global init.defaultBranch master
RUN git init /opt/config-server-repo
COPY target/classes/common-config /opt/config-server-repo
RUN cd /opt/config-server-repo \
    && git add . \
    && git commit -m "Config file"
ADD ["target/config-server-0.0.1.jar", "app.jar"]
ENV JAVA_OPTS="-Dspring.profiles.active=uat -Dspring.cloud.config.server.git.uri=/opt/config-server-repo -Deureka.client.service-url.defaultZone=http://eureka-server:8002/eureka"
RUN sh -c 'touch /app.jar'
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /app.jar" ]