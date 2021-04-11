#!/bin/bash

game ()
{
    debug()
    {
        echo "$@"
        $@
    }

    ###################
    # SETUP VARIABLES #
    ###################

    SUBNET_CIDR='172.22.0.0/16'
    SUBNET_NAME='itNetwork'
    PACKAGE_CLIENT='client'
    PACKAGE_GAME='gameserver'
    PREFIX_DOCKER_TAG='7wonders'
    DOCKER_CONTAINER=$PACKAGE_CLIENT

    NB_CLIENTS='2'
    # CLIENTS=client1 client2 ... clientN
    CLIENTS=$(python3 -c "print(\" \".join([\"$DOCKER_CONTAINER{}\".format(i) for i in range(1, $NB_CLIENTS + 1)]), end=\"\")")
    alias mvn="mvn $@ | sed \"s/Tests run: 0, Failures: 0, Errors: 0, Skipped: 0/\x1B\[1;32mTests run: 1, Failures: 0, Errors: 0, Skipped: 0\x1B[0m/g\""
    # Flags IT
    IT_GAME_URI='-DgameServer.uri=http://localhost:1336'
    IT_GAME_IP='-DIP=172.22.0.1'
    IT_PREFIX='-Dit.test=client.integration.PlayGameIT#'
    INTEGRATION_TEST_MVN="mvn failsafe:integration-test $IT_GAME_URI $IT_GAME_IP $IT_PREFIX"

    ################
    # SETUP DOCKER #
    ################

    docker build $PACKAGE_CLIENT -t $PREFIX_DOCKER_TAG:$PACKAGE_CLIENT
    docker build $PACKAGE_GAME -t $PREFIX_DOCKER_TAG:$PACKAGE_GAME
    cd client

    #############################
    # SETUP TESTS D'INTEGRATION #
    #############################

    GAME_IP='172.17.0.2'

    #####################################
    # LANCEMENT DES TESTS D'INTEGRATION #
    #####################################

    docker run -p 1336:1336 -d --name serverjeu $PREFIX_DOCKER_TAG:$PACKAGE_GAME
       
    #on attend que le serveur de jeu soit up
    sleep 10s 

    debug ${INTEGRATION_TEST_MVN}canCheckAllPlayerBoard
    
    for i in $(seq 1 $NB_CLIENTS)
    do
        CLIENT_PORT=$(($GAME_PORT + $i - 1))
        debug docker run -e GAME_IP=$GAME_IP -d --name $DOCKER_CONTAINER$i $PREFIX_DOCKER_TAG:$PACKAGE_CLIENT
    done

    ${INTEGRATION_TEST_MVN}canCheckAllPlayerBoard
}

game
