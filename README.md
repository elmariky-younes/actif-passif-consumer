## best practises
begin to stop after this you must start
## start active consumer
java -jar target/consumerkafka-0.0.1-SNAPSHOT.jar --server.port=7766
## switch to passive
  ** stop active by http://localhost:7766/kafka/stop
  ** start passive just setup appli java -jar target/consumerkafka-0.0.1-SNAPSHOT.jar --server.port=8866
## swith to active to be restarted
  ** http://localhost:8866/kafka/stop   (stop passive)
  ** http://localhost:7766/kafka/start   (start active)

## test by command
bin/kafka-consumer-groups.sh --group test-group --bootstrap-server localhost:8094,localhost:8093,localhost:8092 --describe
