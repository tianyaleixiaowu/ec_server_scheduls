FROM daocloud.io/brave8/maven-jdk8

ENV PINPOINT_VERSION=1.6.2

ADD configure.sh /usr/local/bin/

ADD schedels-0.0.1-SNAPSHOT.jar /app.jar


EXPOSE 8080
#ENTRYPOINT ["/usr/local/bin/configure.sh"]
ENTRYPOINT ["java","-jar","/app.jar"]