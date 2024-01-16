package ChatController;

import ContactDiscovery.Contact;
import ContactDiscovery.ContactList;
import GUI.View;
import TCP.TCPClient;
import TCP.TCPMessage;
import TCP.TCPServer;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

public class ChatSessionController {
    public TCPServer server;
    public TCPClient client;
    ArrayList<ChatSessionController.Observer> observers;


   public interface Observer {
       void receivedMessageFromServer(String msg,Contact origin);
   }

    public ChatSessionController() throws IOException{
        client = new TCPClient();
        server = new TCPServer();
        server.addObserver(msg -> {
            try{
                storeMessage(msg,ContactList.getInstance().getIpFromContact(msg.origin().getHostAddress()));
                for (ChatSessionController.Observer obs: observers)
                    obs.receivedMessageFromServer(msg.content(),ContactList.getInstance().getIpFromContact(msg.origin().getHostAddress()));
            }catch (Exception e){
                System.out.println("Observer error: "+e);
            }
        });

        observers = new ArrayList<Observer>();

        server.start(TCPServer.TCP_Server_Port);
    }

    public synchronized void addObserver(ChatSessionController.Observer obs){
        this.observers.add(obs);
    }


    public static void storeMessage(TCPMessage msg,Contact contact) throws Exception{
       DatabaseManager db = DatabaseManager.getInstance();
       if(msg.origin() == InetAddress.getLocalHost())
           db.addMessageToDatabase(msg.content(),contact,true);
       else db.addMessageToDatabase(msg.content(),contact,false);

    }


}
