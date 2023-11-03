package Controller;
import ContactDiscovery.ClientUDP;
import ContactDiscovery.ContactList;
import ContactDiscovery.ServerUDP;

import java.io.IOException;

public class ConnectionController {
    public void tryConnection(String nickname) throws IOException {
        //Dès réception d'une demande de connexion du Model.User
        //Renvoie erreur ?
        //Call isUnique()
        ClientUDP c =new ClientUDP();
        c.BroadcastConnection();

        ServerUDP server = new ServerUDP();
        server.listenOtherUsers();




    }
    public static void main(String[] args) throws IOException {
        ConnectionController c = new ConnectionController();
        c.tryConnection("a");
    }

}
