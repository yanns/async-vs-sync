#!/bin/bash

DIRNAME="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

echo "preparing applications"

cd $DIRNAME/search
sbt stage

cd $DIRNAME/payment
sbt stage

cd $DIRNAME/scalastore
sbt stage


echo "starting applications"

$DIRNAME/search/target/start -Dhttp.port=9001 &
$DIRNAME/payment/target/start -Dhttp.port=9002 &
$DIRNAME/scalastore/target/start -Dhttp.port=9000 &


sleep 2
echo "all applications are running"
read -p "press key to stop"

echo "killing applications"
search_pid=`cat $DIRNAME/search/RUNNING_PID`
kill -SIGTERM $search_pid

payment_pid=`cat $DIRNAME/payment/RUNNING_PID`
kill -SIGTERM $payment_pid

scalastore_pid=`cat $DIRNAME/scalastore/RUNNING_PID`
kill -SIGTERM $scalastore_pid
