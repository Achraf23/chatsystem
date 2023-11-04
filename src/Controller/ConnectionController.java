package Controller;
import ContactDiscovery.ClientUDP;
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

}
