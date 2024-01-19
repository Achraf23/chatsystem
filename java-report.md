# ChatSystem Report

## Tech Stack
I used the following technologies to develop my ChatSystem application.
* Use of UDP for the Discovery System library : 
  * Sending broadcast to alert connected users requires UDP protocol
  * Connection establishment is useless when sending only username.
  
  <br>
  
* Reasons for using TCP when establishing conversation sessions with connected users :
  * We have to make sure that the recipient receives the message (Quality of service)
  * Ensure that the message arrives uncorrupted to its destination unlike UDP
  
  <br>
* Reasons for using the swing library for the project : 
  * Easy to use : intuitive handling of the interactions between a frame and a panel.
  * Had a previous experience with java swing 

## Testing Policy
* Network tests aim to test that TCPClient and TCPServer classes function properly. In other words, we make sure that
we receive the right messages after sending them with TCPClient to TCPServer
* Database tests make sure that DatabaseManager functions work properly and that messages are correctly stored 
* Contact List Tests are very basic

## Highlights
I believe that the great part of the code is the loose coupling and the separation between the GUI, the discovery system and
the chat controller. <br>
We can see that MainController is never referenced in the classes that he uses. 
Moreover, the classes that are used by the View interact with the View via observers. 
The same can be said about ChatController (TCPServer interacts with ChatController via an observer). 