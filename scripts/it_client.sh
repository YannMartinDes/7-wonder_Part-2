#!/bin/bash

client ()
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

    NB_CLIENTS='7'
    # CLIENTS=client1 client2 ... clientN
    CLIENTS=$(python3 -c "print(\" \".join([\"$DOCKER_CONTAINER{}\".format(i) for i in range(1, $NB_CLIENTS + 1)]), end=\"\")")
    # Flags IT
    IT_GAME_URI='-DgameServer.uri=http://localhost:1336'
    IT_GAME_IP='-DIP=172.17.0.1'
    IT_PREFIX='-Dit.test=client.integration.InscriptionIT#'
    INTEGRATION_TEST_MVN="mvn failsafe:integration-test $IT_GAME_URI $IT_GAME_IP $IT_PREFIX"

    ################
    # SETUP DOCKER #
    ################
    
    docker network create --subnet=$SUBNET_CIDR $SUBNET_NAME
    docker build $PACKAGE_CLIENT -t $PREFIX_DOCKER_TAG:$PACKAGE_CLIENT
    docker build $PACKAGE_GAME -t $PREFIX_DOCKER_TAG:$PACKAGE_GAME

    cd client
    
    #############################
    # SETUP TESTS D'INTEGRATION #
    #############################

    reset()
    {
        echo "[>>>] Reset des containers clients..."
        debug docker stop $CLIENTS
        sleep 5s
        echo "[>>>] Reset termine"
    }

    GAME_IP='172.22.0.250'

    #####################################
    # CREATION DES CONTAINER DOCKER     #
    #####################################
    docker create --network="$SUBNET_NAME" --ip $GAME_IP -p 1336:1336 --name serverjeu $PREFIX_DOCKER_TAG:$PACKAGE_GAME
    for i in $(seq 1 $NB_CLIENTS)
    do
        CLIENT_PORT=$(($GAME_PORT + $i - 1))
        debug docker create --network="$SUBNET_NAME" -e GAME_IP=$GAME_IP --name $DOCKER_CONTAINER$i $PREFIX_DOCKER_TAG:$PACKAGE_CLIENT
    done


    #####################################
    # LANCEMENT DES TESTS D'INTEGRATION #
    #####################################

    #on a pas de serveur lancer -> echec
    debug ${INTEGRATION_TEST_MVN}inscriptionEchecTimeout
    
    #on lance le serveur, il y a de la place -> success
    docker start serverjeu
    debug ${INTEGRATION_TEST_MVN}inscriptionSuccess
    
    #on verifie que le server envois bien la position et le nombre des joueurs au client
    debug ${INTEGRATION_TEST_MVN}initPositionOk
    reset
    #on lance le serveur de jeu et 2 client
    docker start serverjeu ${DOCKER_CONTAINER}1 ${DOCKER_CONTAINER}2
    #on commence la parti et on a bien le set du nb de player qui est envoyer au client
    debug ${INTEGRATION_TEST_MVN}initNbPlayerOK
    reset
    
    for i in $(seq 1 $NB_CLIENTS)
    do
        debug docker start $DOCKER_CONTAINER$i
    done

    ${INTEGRATION_TEST_MVN}inscriptionToManyPlayerOrClose
}

client