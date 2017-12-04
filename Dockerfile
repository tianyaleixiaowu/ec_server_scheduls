FROM daocloud.io/brave8/maven-jdk8

ENV PINPOINT_VERSION=1.6.2

ADD configure.sh /usr/local/bin/

ADD pom.xml /tmp/build/

ADD src /tmp/build/src
        #构建应用
RUN cd /tmp/build && mvn clean package \
        #拷贝编译结果到指定目录
        && mv target/*.jar /app.jar \
        #清理编译痕迹
        && cd / && rm -rf /tmp/build \
        && chmod a+x /usr/local/bin/configure.sh \
        && mkdir -p /assets/pinpoint-agent \
        && curl -SL https://raw.githubusercontent.com/naver/pinpoint/$PINPOINT_VERSION/agent/src/main/resources-release/pinpoint.config -o /assets/pinpoint.config \
        && curl -SL https://github.com/naver/pinpoint/releases/download/$PINPOINT_VERSION/pinpoint-agent-$PINPOINT_VERSION.tar.gz -o pinpoint-agent-$PINPOINT_VERSION.tar.gz \
        && gunzip pinpoint-agent-$PINPOINT_VERSION.tar.gz \
        && tar -xf pinpoint-agent-$PINPOINT_VERSION.tar -C /assets/pinpoint-agent \
        && curl -SL https://raw.githubusercontent.com/naver/pinpoint/$PINPOINT_VERSION/agent/src/main/resources-release/lib/log4j.xml -o /assets/pinpoint-agent/lib/log4j.xml \
        && sed -i 's/DEBUG/INFO/' /assets/pinpoint-agent/lib/log4j.xml \
        && rm pinpoint-agent-$PINPOINT_VERSION.tar 

EXPOSE 8080
ENTRYPOINT ["/usr/local/bin/configure.sh"]