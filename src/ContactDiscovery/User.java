package ContactDiscovery;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class User {

    ContactList contactList;
    //Récuperer l'adresse IP par un getter
    private String ip;
    //Pseudo a même à changer
    public String nickname;


    User() throws UnknownHostException {
        contactList = new ContactList();
        this.ip=InetAddress.getLocalHost().getHostAddress().toString();
    }

}
