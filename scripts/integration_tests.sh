#!/bin/bash

PACKAGE='statsserver'
INTEGRATION_TEST_MVN='mvn failsafe:integration-test -Dit.test=servergame.integration.StatsServerTestIT#'
STATS_PORT=1335
DOCKER_TAG='7wonders:statsserver'
DOCKER_CONTAINER='serverstat_test'

cd ../7wonders
docker build $PACKAGE -t $DOCKER_TAG

cd gameserver
docker run -d --name $DOCKER_CONTAINER -p $STATS_PORT:$STATS_PORT $DOCKER_TAG

${INTEGRATION_TEST_MVN}sendStatsWorkTest
${INTEGRATION_TEST_MVN}finishStatsTest

# le serveur est stoper car le finish a mis fin (ce test et fait pour tester la non accessibilité du serveur)
${INTEGRATION_TEST_MVN}sendStatsServerDownTest

docker start $DOCKER_CONTAINER
${INTEGRATION_TEST_MVN}realUseTest