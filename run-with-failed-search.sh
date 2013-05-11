#!/bin/bash

DIRNAME="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

echo "starting applications"

$DIRNAME/backends/search/target/start -Dhttp.port=9001 -Ddown=true -DresponseTime=30000 &
$DIRNAME/backends/payment/target/start -Dhttp.port=9002 &
$DIRNAME/async/scalastore/target/start -Dhttp.port=9000 &
$DIRNAME/server/apache-tomcat-7.0.40/bin/catalina.sh start


sleep 2
echo "all applications are running"
read -p "press key to stop"

echo "killing applications"
search_pid=`cat $DIRNAME/backends/search/RUNNING_PID`
kill -SIGTERM $search_pid

payment_pid=`cat $DIRNAME/backends/payment/RUNNING_PID`
kill -SIGTERM $payment_pid

scalastore_pid=`cat $DIRNAME/async/scalastore/RUNNING_PID`
kill -SIGTERM $scalastore_pid

$DIRNAME/server/apache-tomcat-7.0.40/bin/catalina.sh stop
