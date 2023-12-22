package ChatController;

import ContactDiscovery.Contact;
import TCP.TCPMessage;
import TCP.TCPServer;

import java.net.InetAddress;
import java.util.ArrayList;

public class ChatSessionController {
    ArrayList<ChatSession> conversations;
    TCPServer server;

    ChatSessionController(){
        conversations = new ArrayList<ChatSession>();
        server = new TCPServer();
        server.addObserver(new TCPServer.Observer() {
            @Override
            public void messageReceived(TCPMessage msg) {
            }

            @Override
            public void newConnection(InetAddress addr){

            }

        });
    }


}
