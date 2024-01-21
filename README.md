# Description of the project
This private repository is a final version of the Chat System app. 

After executing the app (see below), you will be presented a login page and asked for a username.
You must use a unique username or else you won't be able to connect to the ChatSystem.

Upon connection, you can :
* Change your username
* See who is connected on the network on the left side of the window
* Chat with other connected users (if any) by clicking on their names
* Disconnect from the ChatSystem at any time by clicking on the exit button.

<br>
N.B : <br>
If you are having a conversation with a connected user, and he suddenly disconnects,
you won't be able to chat with him anymore (the conversation view will disappear) until his next connection. 
<br>
If a username changes his name during a conversation, his username will change inside the conversation.
However, the previous messages will keep the former username.

## Instruction to compile the ChatSystem app
    mvn compile

## How to execute ChatSystem
    mvn exec:java -Dexec.mainClass="MainController" 

