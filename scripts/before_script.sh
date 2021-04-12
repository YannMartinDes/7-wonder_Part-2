#!/bin/bash

docker-compose --version
docker ps -a -q
docker-compose build
docker images