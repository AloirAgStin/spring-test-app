#!/usr/bin/env sh

set -e

ARGS=""

if [ -n "$JVM_OPTIONS" ]; then
  ARGS="${ARGS} ${JVM_OPTIONS}"
fi

if [ -n "$JVM_SHENANDOAH_ENABLED" ] && [ "$JVM_SHENANDOAH_ENABLED" = "true" ]; then
  ARGS="${ARGS} -XX:+UseShenandoahGC -XX:ShenandoahGCHeuristics=${JVM_SHENANDOAH_HEURISTICS}"
fi

if [ -n "$JVM_DEBUG_ENABLED" ] && [ "$JVM_DEBUG_ENABLED" = "true" ]; then
  ARGS="${ARGS} -Xdebug -Xrunjdwp:transport=dt_socket,server=y,address=*:${JVM_DEBUG_PORT},suspend=n"
fi

if [ -n "$JVM_JMX_ENABLED" ] && [ "$JVM_JMX_ENABLED" = "true" ]; then
  ARGS="${ARGS} \
-Djava.rmi.server.hostname=${JVM_JMX_HOST} \
-Dcom.sun.management.jmxremote.port=${JVM_JMX_PORT} \
-Dcom.sun.management.jmxremote.rmi.port=${JVM_JMX_PORT} \
-Dcom.sun.management.jmxremote.ssl=false \
-Dcom.sun.management.jmxremote.authenticate=false \
-Dcom.sun.management.jmxremote.local.only=false"
fi

if [ -n "$JVM_RAM_ENABLED" ] && [ "$JVM_RAM_ENABLED" = "true" ]; then
  ARGS="${ARGS} \
-XX:+UseContainerSupport \
-XX:+AlwaysActAsServerClassMachine \
-Xms${JVM_RAM_INIT} -Xmx${JVM_RAM_MAX}"
fi

if [ -n "$JVM_TIMEZONE" ]; then
  ARGS="${ARGS} -Duser.timezone=${JVM_TIMEZONE}"
fi

if [ -n "$PROFILES" ]; then
  ARGS="${ARGS} -Dspring.profiles.active=${PROFILES}"
fi

ARGS="$(echo "${ARGS}" | sed -e 's/^[[:space:]]*//')"

if [ -z "$ARGS" ]; then
    java -jar /service.jar
else
    java $ARGS -jar /service.jar
fi
