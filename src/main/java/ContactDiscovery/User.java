package ContactDiscovery;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;

/** User class. Stores contactList, pseudo, Ip address
 *
 */
public class User {
    private ArrayList<InetAddress> ipAdresses;
    //Pseudo a même à changer
    public String nickname = null;
    private static User user = null;


    /** User Constructor
     *
     * @throws IOException
     */
    private User() throws IOException {
        ipAdresses = new ArrayList<InetAddress>();
        retrieveIpAdresses();
    }

    /** Get User
     *
     * @return Static instance of User
     * @throws IOException
     */
    public static synchronized User getInstance () throws IOException {
        if (user == null)
            user = new User();
        return user;
    }


    /** Retieve the IP addresses associated to my machine
     *
     * @throws SocketException
     */
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

    }

    /** Get the different Ip addresses of our user
     *
     * @return Ip addresses as an ArrayList
     */
    public ArrayList<InetAddress> getIpAdresses(){return ipAdresses;}


}
