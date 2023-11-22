# Description of the project
This private repository is a first version of the Chat System project. This first version is a software library that handles the discovery of contacts
in the local network. 
Three functionalities are offered to the user in the Discovery System Class :
1. Log in the local network with a pseudonym
2. Log out from the local network with a pseudonym
3. Change pseudonym while the user is connected to the network

Upon connection, the user has real-time information on who is connected to the network.

# Instructions to compile Contact Discovery
mvn compile
mvn package

# How to execute ContactDiscovery
java -cp target/chatsystem-bensebaa-bakri-1.0-SNAPSHOT.jar Controller.DiscoverySystem
