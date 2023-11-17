package ContactDiscovery;

import Controller.ConnectionController;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;

public class User {

    ContactList contactList;
    //Récuperer l'adresse IP par un getter
    private ArrayList<InetAddress> ipAdresses;
    //Pseudo a même à changer
    public String nickname = null;

    private static User user = null;
    private static final boolean enableInsa = true;


    private User() throws IOException {
        ipAdresses = new ArrayList<InetAddress>();
        retrieveIpAdresses();
    }

    public static void main(String[] args) throws Exception {

        System.out.println("test");
    }


    public static synchronized User getInstance () throws IOException
    {
        if (user == null)
            user = new User();

        return user;
    }

    private void retrieveIpAdresses() throws SocketException{
        Enumeration e = NetworkInterface.getNetworkInterfaces();
        while(e.hasMoreElements())
        {
            NetworkInterface n = (NetworkInterface) e.nextElement();
            Enumeration ee = n.getInetAddresses();
            while (ee.hasMoreElements())
            {
                InetAddress i = (InetAddress) ee.nextElement();
                if(!i.getHostAddress().contains(":"))
                    ipAdresses.add(i);
            }
        }

        for(InetAddress addr:ipAdresses){
            System.out.println(addr.getHostAddress());
        }
    }

    public ArrayList<InetAddress> getIpAdresses(){return ipAdresses;}








}
