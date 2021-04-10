#!/bin/bash

# Tests d'integration
main()
{
    pwd
    ls -al

    if [ "$1" = "client" ]
    then
        ./it_client
    elif [ "$1" = "statsserver" ]
    then
        ./it_statsserver
    fi
}

main "$@"
exit