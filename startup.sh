#!/usr/bin/env bash

mvn clean package

docker build -t ancina/users .

docker run -d -p 8081:8081 -t ancina/users