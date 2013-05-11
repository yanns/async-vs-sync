#!/bin/bash

DIRNAME="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

echo "starting applications"

$DIRNAME/backends/search/target/start -Dhttp.port=9001 &
$DIRNAME/backends/payment/target/start -Dhttp.port=9002 &


sleep 2
echo "all applications are running"
read -p "press key to stop"

echo "killing applications"
search_pid=`cat $DIRNAME/backends/search/RUNNING_PID`
kill -SIGTERM $search_pid

payment_pid=`cat $DIRNAME/backends/payment/RUNNING_PID`
kill -SIGTERM $payment_pid

