package ContactDiscovery;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class User {

    ContactList contactList;
    //Récuperer l'adresse IP par un getter
    private String ip;
    //Pseudo a même à changer
    public String nickname = null;

    private static User user = null;


    User() throws UnknownHostException {
            this.contactList = new ContactList();
            this.ip=InetAddress.getLocalHost().getHostAddress().toString();
    }

    public static synchronized User getInstance () throws UnknownHostException
    {
        if (user == null)
            user = new User();

        return user;
    }



}
