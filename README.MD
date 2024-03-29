# Sample Kafka Project

This project has a Kafka producer and Kafka consumer. To make it clear and simple, I haven't used any Spring boot capabilities.
It has a stand alone consumer and producer, which can be run normal main method.

### Tool chain config
- This project uses maven tool chain. I have added the toolchains.xml to the project repo. Copy this file to your .m2 directory.
- Update the java versions and paths accordingly in the toolchains.xml.

## Installation

Download and install (extract) the binaries from:
<a href="https://kafka.apache.org/downloads" target="_blank" rel="noopener noreferrer">Kafka Downloads</a>

```
Instructions:
Start the Kafka by starting zookeeper and kafka server as follows:
1. On windows:
Run from <Kafka root directory>\bin\windows\
zookeeper-server-start.bat ../../config/zookeeper.properties
kafka-server-start.bat ../../config/server.properties

2. On mac:
Run from the <Kafka root directory>
bin/zookeeper-server-start.sh config/zookeeper.properties
bin/kafka-server-start.sh config/server.properties

Create a topic with name as configured in kafka.properties file of both producer and consumer
Run the main classes of producer and consumer.

To achieve the log compaction. Create a topic and run the producer and consumer on command line from the <kafka home diretory>\bin:

./kafka-topics.sh --create --zookeeper <zookeeper server ip>:<zookeeper port> --topic <topic name> --replication-factor 1 --partitions 1 --config "cleanup.policy=compact" --config "delete.retention.ms=100"  --config "segment.ms=100" --config "min.cleanable.dirty.ratio=0.01"

./kafka-console-producer.sh --broker-list <kafka server ip>:<kafka port> --topic <topic name> --property parse.key=true --property key.separator=:

./kafka-console-consumer.sh --bootstrap-server <kafka server ip>:<zookkafkaeeper port> --topic <topic name> --property  print.key=true --property key.separator=: --from-beginning

The producer and consumer can also be executed by the code provided.

Note: Log compaction will only work on mac or linux. Windows has an issue with file rename
```

## Contributing
Pull requests are welcome.
Please make sure to update tests as appropriate.
