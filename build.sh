#!/bin/bash

DIRNAME="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

echo "preparing applications"

pushd $DIRNAME/sync/javastore
mvn package
popd
rm -f $DIRNAME/server/apache-tomcat-7.0.40/webapps/*.war
rm -rf $DIRNAME/server/apache-tomcat-7.0.40/webapps/*
cp $DIRNAME/sync/javastore/target/javastore-0.1-SNAPSHOT.war $DIRNAME/server/apache-tomcat-7.0.40/webapps/ROOT.war

pushd $DIRNAME/backends/search
sbt stage &
popd

pushd $DIRNAME/backends/payment
sbt stage &
popd

pushd $DIRNAME/async/scalastore
sbt stage &
popd

