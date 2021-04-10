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
    fi
}

main "$@"
exit