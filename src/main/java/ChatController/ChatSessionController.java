package ChatController;

import ContactDiscovery.Contact;
import ContactDiscovery.ContactList;
import TCP.TCPMessage;
import TCP.TCPServer;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

public class ChatSessionController {
    ArrayList<ChatSession> conversations;
    TCPServer server;

    ChatSessionController() throws IOException{
        conversations = new ArrayList<ChatSession>();
        server = new TCPServer();
        server.addObserver(new TCPServer.Observer() {
            @Override
            public void messageReceived(TCPMessage msg) {
            }

            @Override
            public void newConnection(InetAddress addr) {
                Contact contact = ContactList.getInstance().getIpFromContact(addr.getHostAddress());
                try {
                    startChatSession(contact);
                }catch (IOException e){
                    System.out.println(e);
                }
            }

        });

        server.start(TCPServer.TCP_Server_Port);
    }

    void launchServer () throws IOException {server.start(TCPServer.TCP_Server_Port);}

    void startChatSession(Contact contact) throws IOException {
        conversations.add(new ChatSession(contact));
    }

    void addMessageReceived(TCPMessage msg) throws Exception{
        for(ChatSession conversation : conversations){
            if(conversation.contact.ip.equals(msg.origin().getHostAddress())){
                conversation.addMessage(msg);
            }
        }
    }



}
