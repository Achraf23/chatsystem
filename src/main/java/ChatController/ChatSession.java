package ChatController;

import ContactDiscovery.Contact;
import TCP.TCPClient;
import TCP.TCPMessage;
import TCP.TCPServer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ChatSession {
    TCPClient client;
    Contact contact;

    ChatSession(Contact contact) throws IOException {
        this.contact = contact;
        this.client = new TCPClient();
        client.startConnection(this.contact.ip(), TCPServer.TCP_Server_Port);
    }

    void addMessage(TCPMessage msg) throws Exception {
        if(msg.origin().equals(InetAddress.getLocalHost())){
            client.sendMessage(msg);
            //TODO : Store inside Database
        }else{
            //TODO : Store inside Database
        }

    }
}
