#!/bin/bash

source ./it_statsserver.sh
source ./it_client.sh

# Tests d'integration
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

main "$@"
exit