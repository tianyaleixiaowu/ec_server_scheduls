FROM daocloud.io/brave8/maven-jdk8

ADD pom.xml /tmp/build/

ADD src /tmp/build/src
        #构建应用
RUN cd /tmp/build && mvn clean package \
        #拷贝编译结果到指定目录
        && mv target/*.jar /app.jar \
        #清理编译痕迹
        && cd / && rm -rf /tmp/build
        && mkdir -p /assets/pinpoint-agent

EXPOSE 8080
ENTRYPOINT ["java","-javaagent:/assets/pinpoint-agent/pinpoint-bootstrap-1.6.2.jar -Dpinpoint.agentId=app-in-docker -Dpinpoint.applicationName=ap -jar","/app.jar"]