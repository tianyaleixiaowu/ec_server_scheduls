#!/usr/bin/env bash
set -e
set -x

COLLECTOR_IP=${COLLECTOR_IP:-127.0.0.1}
cp -f /assets/pinpoint.config /assets/pinpoint-agent/pinpoint.config
sed -i "s/profiler.collector.ip=127.0.0.1/profiler.collector.ip=${COLLECTOR_IP}/g" /assets/pinpoint-agent/pinpoint.config
java  -jar /app.jar