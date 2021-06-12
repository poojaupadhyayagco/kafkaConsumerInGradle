#!/bin/bash

set -e
set -o pipefail

echo "Starting service"
java -Djava.security.egd=file:/dev/./urandom -Xmx$JAVA_MAX_HEAP_SIZE -XshowSettings:vm -XX:MaxRAMPercentage=80.0 -jar /app.jar

exit