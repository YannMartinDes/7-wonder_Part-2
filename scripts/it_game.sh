#!/bin/bash

game ()
{
    ###################
    # SETUP VARIABLES #
    ###################
    NB_CLIENTS='2'
    GAME_IP='172.17.0.2'

    ################
    # SETUP DOCKER #
    ################
    docker build client -t 7wonders:client
    docker build gameserver -t 7wonders:gameserver

    #Lancement des doker
    docker run -p 1336:1336 -d --name serverjeu 7wonders:gameserver
    for i in $(seq 1 $NB_CLIENTS)
    do
        docker run -e GAME_IP=$GAME_IP -d --name client$i 7wonders:client
    done

    cd client
    #on lance notre ia qui vas pouvoir faire les test au cours de la partie
    mvn failsafe:integration-test -DgameServer.uri=http://localhost:1336 -DIP=172.17.0.1 -Dit.test=client.integration.PlayGameIT#canCheckAllPlayerBoard
}

game
