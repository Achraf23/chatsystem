package Controller;
import ContactDiscovery.ContactList;
import ContactDiscovery.User;

import java.io.IOException;

/** Handles the Users operations regarding the server
 *
 */
public class ConnectionController {

    ClientUDP c;
    EchoServer server;

    /** ConnectionController Constructor
     *
     * @throws IOException
     */
    ConnectionController() throws IOException{
        c = new ClientUDP();
        server = new EchoServer();

    }

    /** Tries to connect to the server with a given nickname, initializes the contactList table and verifies that our pseudo is unique.
     * Upon connection, sends our nickname to other users
     *
     * @param nickname The pseudo we chose
     * @throws IOException
     */
    public void tryConnection(String nickname) throws IOException {

        server.start(); ///launch server to listen to other users
        c.broadcastConnection("Hello"); //send broadcast to retrieve connected users nickname

        //waiting a bit for the contactList to be initialized
        try {
            Thread.sleep(100);
        }catch (InterruptedException e){
            System.out.println("sleep failed");
        }

        System.out.println(ContactList.getInstance().table.size());

        if(isUnique(nickname)){
            User.getInstance().nickname = nickname;
        }else{
            throw new IOException("Connection Failed");
        }

        System.out.println("nickname="+User.getInstance().nickname);
        // send my pseudo only if there are other users connected
        if(!ContactList.getInstance().table.isEmpty())
            c.sendMsgToOthers(User.getInstance().nickname);
        else System.out.println("I'm the only one");


    }

    /** Disconnecting from the server, we also notify the other users
     *
     * @throws Exception if sleep failed
     */
    public void logOut() throws Exception{
        //TODO: Do we interrupt the disconnect if sleep failed?
        c.sendMsgToOthers("disconnect");
        Thread.sleep(100);
        server.interrupt();
    }

    //TODO: Main segment
    public static void main(String[] args) throws Exception {
        ConnectionController c = new ConnectionController();
        c.tryConnection("a");
        c.logOut();

    }

    /** Verifies that our pseudo is unique by consulting the contactList table
     *
     * @param pseudo Our pseudo
     * @return false if already used, true if unique
     */
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
