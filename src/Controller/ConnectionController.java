package Controller;
import ContactDiscovery.ClientUDP;
import ContactDiscovery.ContactList;
import ContactDiscovery.EchoServer;

import java.io.IOException;

public class ConnectionController {
    public void tryConnection(String nickname) throws IOException {
        //Dès réception d'une demande de connexion du Model.User
        //Renvoie erreur ?
        //Call isUnique()
        ClientUDP c =new ClientUDP();
        c.BroadcastConnection();

        EchoServer server = new EchoServer();





    }
    public static void main(String[] args) throws IOException {
        ConnectionController c = new ConnectionController();
        c.tryConnection("a");
    }

    boolean isUnique(String pseudo){
        ContactList contactList = ContactList.getInstance();
        for(int i=0;i<contactList.table.size();i++){
            if(pseudo.equals(contactList.table.get(i).pseudo)){
                return false;
            }
        }
        return true;
    }

}
