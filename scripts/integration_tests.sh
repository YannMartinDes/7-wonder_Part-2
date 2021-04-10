#!/bin/bash

# Tests d'integrations
main()
{
    pwd
    ls -al

    if [ "$1" = "client" ]
    then
        client
    elif [ "$1" = "statsserver" ]
    then
        statsserver
    fi
}

debug()
{
    echo "$@"
    $@
}

###################
# SETUP VARIABLES #
###################

STATS_PORT=1335
GAME_PORT=12345

# Fonction qui permet de lancer les tests d'integration pour la relation statistiques-moteur
statsserver ()
{
    ###################
    # SETUP VARIABLES #
    ###################

    PACKAGE='statsserver'
    DOCKER_TAG='7wonders:statsserver'
    DOCKER_CONTAINER='serverstat_test'
    INTEGRATION_TEST_MVN='mvn failsafe:integration-test -Dit.test=servergame.integration.StatsServerTestIT#'

    ################
    # SETUP DOCKER #
    ################

    docker build $PACKAGE -t $DOCKER_TAG

    cd gameserver
    docker run -d --name $DOCKER_CONTAINER -p $STATS_PORT:$STATS_PORT $DOCKER_TAG

    #####################################
    # LANCEMENT DES TESTS D'INTEGRATION #
    #####################################

    ${INTEGRATION_TEST_MVN}sendStatsWorkTest
    ${INTEGRATION_TEST_MVN}finishStatsTest

    # le serveur est stoper car le finish a mis fin (ce test et fait pour tester la non accessibilité du serveur)
    ${INTEGRATION_TEST_MVN}sendStatsServerDownTest

    docker start $DOCKER_CONTAINER
    ${INTEGRATION_TEST_MVN}realUseTest
} 


# Fonction qui permet de lancer les tests d'integration pour la relation client-moteur
client ()
{
    ###################
    # SETUP VARIABLES #
    ###################

    PACKAGE='client'
    DOCKER_TAG='7wonders:client'
    DOCKER_CONTAINER='client'
    INTEGRATION_TEST_MVN='mvn failsafe:integration-test -Dit.test=servergame.integration.RequestToPlayerIT#'
    NB_CLIENTS=5

    # CLIENTS=client1 client2 ... clientN
    CLIENTS=$(python3 -c "print(\" \".join([\"$DOCKER_CONTAINER{}\".format(i) for i in range(1, $NB_CLIENTS + 1)]), end=\"\")")

    echo $CLIENTS

    ################
    # SETUP DOCKER #
    ################

    docker build $PACKAGE -t $DOCKER_TAG

    cd gameserver
    for i in $(seq 1 $NB_CLIENTS)
    do
        CLIENT_PORT=$(($GAME_PORT + $i - 1))
        debug docker run -e PROFILE=IT -e PORT=$CLIENT_PORT -e IP=localhost -e GAME_IP=172.17.0.1 -d --name $DOCKER_CONTAINER$i -p $CLIENT_PORT:$GAME_PORT $DOCKER_TAG
    done

    #############################
    # SETUP TESTS D'INTEGRATION #
    #############################

    it()
    {
        echo "[>>>] Reset des containers clients..."
        debug docker stop $CLIENTS
        sleep 10s
        debug docker start $CLIENTS
        echo "[>>>] Reset termine"
        debug $@
    }

    #####################################
    # LANCEMENT DES TESTS D'INTEGRATION #
    #####################################

    it ${INTEGRATION_TEST_MVN}setNbPlayer
    it ${INTEGRATION_TEST_MVN}setPlayerPosition
    it ${INTEGRATION_TEST_MVN}chooseActionTest
    it ${INTEGRATION_TEST_MVN}choosePurchasePossibilityTest
    it ${INTEGRATION_TEST_MVN}ScientificsTest
    it ${INTEGRATION_TEST_MVN}ChooseCardTest
    docker stop $CLIENTS
}

main "$@"
exit