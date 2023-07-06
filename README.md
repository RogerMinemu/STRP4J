# Simple TCP Reverse Proxy for Java (STRP4J)
STRP4J is a simple, light-weight Java application for implementing a reverse proxy over TCP.

## Description
The Simple TCP Reverse Proxy for Java (STRP4J) acts as an intermediary for requests from clients seeking resources from servers. It accepts all traffic coming into the server and directs it to the appropriate backend server. The communication between the client and the backend servers is managed via TCP protocol.

This program utilizes a configuration file (config.properties) to load the destination host and port. Once the proxy server is started, it listens for incoming connections, and upon successful connection, a proxy protocol header is sent to the destination server. The application also spins up new threads to handle incoming and outgoing data.

## Getting Started
### Prerequisites
Java 8 or newer

### Installation
1- Download the latest release JAR and make a directory.

2- Create a file named config.properties in the root directory. This file should contain the following keys:

host - The IP address or hostname of the destination server.
port - The port number of the destination server.

For example:
host=minemu.net
port=25565

3- Run the jar file using the command:
java -jar STRP4J.jar

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License
MIT applies.
