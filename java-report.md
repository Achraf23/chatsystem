# ChatSystem Report

## Tech Stack
I used the following technologies to develop my ChatSystem application.
* Use of UDP for the Discovery System library : 
  * Sending broadcast to alert connected users requires UDP protocol
  * Connection establishment is useless when sending only username.
  
  <br>
  
* Use of TCP when establishing conversation sessions with connected users.
  * We have to make sure that the recipient receives the message (Quality of service)

## Testing Policy
* Network tests aim to test that TCPClient and TCPServer classes function properly. In other words, we make sure that
we receive the right messages after sending them with TCPClient to TCPServer
* Database tests make sure that DatabaseManager functions work properly and that messages are correctly stored 
* Contact List Tests are very basic

## Highlights
I believe that the great part of the code is the loose coupling and the separation between the GUI, the discovery system and
the chat controller. 
We can see that MainController is never referenced in the classes that he uses. 
Moreover, the classes that are used by the View interact with the View via observers. *
TCPServer also interacts with the chatController via an observer. 