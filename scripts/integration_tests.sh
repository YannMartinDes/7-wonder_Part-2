#!/bin/bash

# Tests d'integration
main()
{
    pwd
    ls -al

    if [ "$1" = "client" ]
    then
        it_client.sh
    elif [ "$1" = "statsserver" ]
    then
        it_statsserver.sh
    fi
}

main "$@"
exit