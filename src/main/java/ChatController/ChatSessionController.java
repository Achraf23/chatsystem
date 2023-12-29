package ChatController;

import ContactDiscovery.Contact;
import ContactDiscovery.ContactList;
import TCP.TCPClient;
import TCP.TCPMessage;
import TCP.TCPServer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class ChatSessionController {
    TCPServer server;
    TCPClient client;

    public ChatSessionController() throws IOException{
        client = new TCPClient();
        server = new TCPServer();
        server.addObserver(msg -> {
            try{
                storeMessage(msg);
            }catch (Exception e){
                System.out.println("Observer error: "+e);
            }
        });

        server.start(TCPServer.TCP_Server_Port);
    }


    public void sendMessage(Contact contact,TCPMessage msg) throws Exception{
        client.startConnection(contact.ip(),TCPServer.TCP_Server_Port);
        client.sendMessage(msg);
        client.stopConnection();
        storeMessage(msg);
    }

    private void storeMessage(TCPMessage msg) throws Exception{
//        for(ChatSession conversation : conversations){
//            if(conversation.contact.ip().equals(msg.origin().getHostAddress())){
//                conversation.addMessage(msg);
//            }
//        }
    }




}
