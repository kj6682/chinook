#!/bin/sh

export PORT="-Dserver.port=8080"
export SPRING_PROFILE="-Dspring.profiles.active=h2"

mvn clean install -P h2
java $PORT $JAVA_OPTS $SPRING_PROFILE -jar target/chinook-2.0.0.jar