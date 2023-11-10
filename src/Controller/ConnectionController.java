package Controller;
import ContactDiscovery.ClientUDP;
import ContactDiscovery.ContactList;
import ContactDiscovery.EchoServer;
import ContactDiscovery.User;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ConnectionController {

    User u;

    ConnectionController() throws IOException{
        u = User.getInstance(); // if exception, will be thrown in User
    }
    public void tryConnection(String nickname) throws IOException {
        //send broadcast to retrieve connected users nickname

        // Launch server to get their answers
        EchoServer server = new EchoServer();
        ClientUDP c =new ClientUDP();
        c.BroadcastConnection();

        //waiting a bit for the contactList to be initialized
        try {
            Thread.sleep(100);
        }catch (InterruptedException e){
            System.out.println("sleep failed");
        }

        System.out.println(ContactList.getInstance().table.size());

        if(isUnique(nickname)){
            u.nickname = nickname;
        }else{
            throw new IOException("Connection Failed");
        }

        System.out.println(User.getInstance().nickname);
        // send my pseudo only if there are other users connected
        if(!ContactList.getInstance().table.isEmpty())
            c.sendMsgToOthers(u.nickname);
        else System.out.println("I'm the only one");


    }
    public static void main(String[] args) throws IOException {
        ConnectionController c = new ConnectionController();
        c.tryConnection("b");
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
