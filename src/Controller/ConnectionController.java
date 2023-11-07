package Controller;
import ContactDiscovery.ClientUDP;
import ContactDiscovery.ContactList;
import ContactDiscovery.EchoServer;
import ContactDiscovery.User;

import java.io.IOException;
import java.net.UnknownHostException;

public class ConnectionController {

    User u;

    ConnectionController() throws UnknownHostException{
        u = new User(); // if exception, will be thrown in User
    }
    public void tryConnection(String nickname) throws IOException {
        //Dès réception d'une demande de connexion du Model.User
        //Renvoie erreur ?
        //Call isUnique()
        ClientUDP c =new ClientUDP();
        c.BroadcastConnection();

        EchoServer server = new EchoServer();
        if(isUnique(nickname)){
            ContactList.getInstance().addLine(nickname,u.getIP());
            u.nickname = nickname;
        }else{
            throw new IOException("Connection Failed");
        }


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
