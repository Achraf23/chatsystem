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

    ChatSessionController(){
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
    }

    void startChatSession(Contact contact) throws IOException {
        conversations.add(new ChatSession(contact));
    }

    void addMessageReceived(TCPMessage msg) throws Exception{
        for(int i=0;i<conversations.size();i++){
            if(conversations.get(i).contact.ip.equals(msg.origin().getHostAddress())){
                conversations.get(i).addMessage(msg);
            }
        }
    }



}
