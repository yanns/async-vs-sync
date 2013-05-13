#!/bin/bash

rm -rf server
mkdir server
pushd server
wget http://mirror.softaculous.com/apache/tomcat/tomcat-7/v7.0.40/bin/apache-tomcat-7.0.40.tar.gz
tar -xzvf apache-tomcat-7.0.40.tar.gz
rm apache-tomcat-7.0.40.tar.gz
popd

pushd server/apache-tomcat-7.0.40
rm -rf webapps
mkdir webapps
popd