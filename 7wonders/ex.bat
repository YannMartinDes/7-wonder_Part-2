call mvn clean install -DskipTests=true -Dmaven.javadoc.skip=true -B -V
docker-compose build
docker build client -t 7wonders:client
docker build gameserver -t 7wonders:gameserver
cd client
call mvn failsafe:integration-test -DgameServer.uri=http://localhost:1336 -DIP=172.22.0.1 -Dit.test=client.integration.InscriptionIT#inscriptionEchecTimeout
docker run --network="itNetwork" --ip 172.22.0.250 -p 1336:1336 -d --name serverjeu 7wonders:gameserver
call mvn failsafe:integration-test -DgameServer.uri=http://localhost:1336 -DIP=host.docker.internal -Dit.test=client.integration.InscriptionIT#inscriptionSuccess
call mvn failsafe:integration-test -DgameServer.uri=http://localhost:1336 -DIP=host.docker.internal -Dit.test=client.integration.InscriptionIT#initPositionOk
call mvn failsafe:integration-test -DgameServer.uri=http://localhost:1336 -DIP=host.docker.internal -Dit.test=client.integration.InscriptionIT#initNbPlayerOK
cd ..