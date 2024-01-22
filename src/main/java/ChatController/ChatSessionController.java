package ChatController;

import ContactDiscovery.Contact;
import ContactDiscovery.ContactList;
import TCP.TCPMessage;
import TCP.TCPServer;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

public class ChatSessionController {
    public TCPServer server;
    ArrayList<ChatSessionController.Observer> observers;


   public interface Observer {
       void receivedMessageFromServer(String msg,Contact origin);
   }

    /** launches the server and defines the observer
     */
    public ChatSessionController() throws IOException{
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

    /** store msg in the database
     */
    public static void storeMessage(TCPMessage msg,Contact contact) throws Exception{
       DatabaseManager db = DatabaseManager.getInstance();
       db.addMessageToDatabase(msg.content(),contact, msg.origin() == InetAddress.getLocalHost());

    }


}
