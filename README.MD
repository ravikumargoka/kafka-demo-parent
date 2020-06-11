# Sample Kafka Project

This project has a Kafka producer and Kafka consumer. To make it clear and simple, I haven't used any Spring boot capabilities.
It has a stand alone consumer and producer, which can be run normal main method.

## Installation

Download and install (extract) the binaries from:
https://kafka.apache.org/downloads
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

Create a topic with name 'ravi-test'
```

## Contributing
Pull requests are welcome.
Please make sure to update tests as appropriate.