#!/bin/bash

# Tests d'integration
main()
{
    pwd
    ls -al

    if [ "$1" = "gameserver" ]
    then
        it_gameserver.sh
    elif [ "$1" = "statsserver" ]
    then
        it_statsserver.sh
    elif [ "$1" = "client" ]
    then
        it_client.sh
    elif [ "$1" = "game" ]
    then
        it_game.sh
    fi
}

main "$@"
exit