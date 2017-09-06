#!/bin/sh

export PORT="-Dserver.port=8080"
export SPRING_PROFILE="-Dspring.profiles.active=ddl-create"
export DATABASE_URL="-Dspring.datasource.url=jdbc:postgresql://localhost:5432/catalog"

mvn clean install -P postgresql
java $PORT $JAVA_OPTS $SPRING_PROFILE $DATABASE_URL -jar target/chinook-2.0.0.jar