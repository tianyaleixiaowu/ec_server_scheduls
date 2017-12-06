#!/usr/bin/env bash
set -e
set -x

COLLECTOR_IP=${COLLECTOR_IP:-127.0.0.1}
cp -f /assets/pinpoint.config /assets/pinpoint-agent/pinpoint.config
sed -i "s/profiler.collector.ip=127.0.0.1/profiler.collector.ip=${COLLECTOR_IP}/g" /assets/pinpoint-agent/pinpoint.config
java -javaagent:/assets/pinpoint-agent/pinpoint-bootstrap-1.6.2.jar -Dpinpoint.agentId=schedul-in-docker -Dpinpoint.applicationName=schedul -jar /app.jar