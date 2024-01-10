package ChatController;

import ContactDiscovery.Contact;
import ContactDiscovery.ContactList;
import TCP.TCPClient;
import TCP.TCPMessage;
import TCP.TCPServer;

import java.io.IOException;
import java.util.ArrayList;

public class ChatSessionController {
    public TCPServer server;
    public TCPClient client;
    ArrayList<ChatSessionController.Observer> observers;

//    @Override
//    public void sendMessage(String msg, Contact recipient) {
//        try {
//            client.startConnection(recipient.ip(),TCPServer.TCP_Server_Port);
//            client.sendMessage(new TCPMessage(msg,InetAddress.getLocalHost()));
//            client.stopConnection();
//            System.out.println("msg: "+msg);
//        }catch (IOException e){
//            System.out.println("sent Message Observer Method error: " + e);
//        }
//
//    }



   public interface Observer {
       void receivedMessageFromServer(String msg,Contact origin);
   }

    public ChatSessionController() throws IOException{
        client = new TCPClient();
        server = new TCPServer();
        server.addObserver(msg -> {
            try{
                storeMessage(msg);
                for (ChatSessionController.Observer obs: observers)
                    obs.receivedMessageFromServer(msg.content(),ContactList.getInstance().getIpFromContact(msg.origin().getHostAddress()));
            }catch (Exception e){
                System.out.println("Observer error: "+e);
            }
        });

        observers = new ArrayList<Observer>();

        server.start();
    }

    public synchronized void addObserver(ChatSessionController.Observer obs){
        this.observers.add(obs);
    }


    private void storeMessage(TCPMessage msg) throws Exception{
//        for(ChatSession conversation : conversations){
//            if(conversation.contact.ip().equals(msg.origin().getHostAddress())){
//                conversation.addMessage(msg);
//            }
//        }
    }


}
