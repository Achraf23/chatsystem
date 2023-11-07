package ContactDiscovery;

import java.io.IOException;
import java.net.*;

public class User {

    ContactList contactList;
    //Récuperer l'adresse IP par un getter
    private String ip;
    //Pseudo a même à changer
    public String nickname = null;

    private static User user = null;


    public User() throws UnknownHostException {
            this.ip=InetAddress.getLocalHost().getHostAddress();
    }

    public static synchronized User getInstance () throws UnknownHostException
    {
        if (user == null)
            user = new User();

        return user;
    }

    public String getIP(){return this.ip;}



}
