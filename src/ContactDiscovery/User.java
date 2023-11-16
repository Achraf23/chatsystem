package ContactDiscovery;

import Controller.ConnectionController;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;

public class User {

    ContactList contactList;
    //Récuperer l'adresse IP par un getter
    private final String ip;
    //Pseudo a même à changer
    public String nickname = null;

    private static User user = null;
    private static final boolean enableInsa = false;


    private User() throws IOException {
//            this.ip=InetAddress.getLocalHost().getHostAddress();
        if(enableInsa){
            Enumeration e = NetworkInterface.getNetworkInterfaces();
            if(e.hasMoreElements()){
                NetworkInterface n = (NetworkInterface) e.nextElement();
                Enumeration ee = n.getInetAddresses();
                InetAddress j=null;
                for(int i=0;i<2;i++){
                    if(ee.hasMoreElements()){
                        j= (InetAddress) ee.nextElement();
                    }
                }

                this.ip=j.getHostAddress();

            }
        }else this.ip=InetAddress.getLocalHost().getHostAddress();

    }


    public static synchronized User getInstance () throws IOException
    {
        if (user == null)
            user = new User();

        return user;
    }

    public String getIP(){return this.ip;}



}
