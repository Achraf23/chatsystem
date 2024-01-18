package Controller;
import ChatController.ChatSessionController;
import ContactDiscovery.Contact;
import ContactDiscovery.ContactList;
import ContactDiscovery.User;
import GUI.View;

import java.io.IOException;
import java.util.Scanner;  // Import the Scanner class

/** Handles the Users operations regarding the server
 *
 */
public class DiscoverySystem implements View.Observer {

    ClientUDP client;
    EchoServer server;
    public boolean connected = false;

    /** ConnectionController Constructor
     *
     * @throws IOException
     */
    public DiscoverySystem() throws IOException{
        client = new ClientUDP();

    }

    /** Tries to connect to the server with a given nickname, initializes the contactList table and verifies that our pseudo is unique.
     * Upon connection, sends our nickname to other users
     *
     * @param nickname The pseudo we chose
     * @throws IOException
     */
    public void tryConnection(String nickname) throws IOException {

        server = new EchoServer();
        server.start(); ///launch server to listen to other users
        client.broadcastConnection("Hello"); //send broadcast to retrieve connected users nickname


        //waiting a bit for the contactList to be initialized
        try {
            Thread.sleep(100);
        }catch (InterruptedException e){
            System.out.println("sleep failed");
        }


        if(ContactList.getInstance().isUnique(nickname)){
            User.getInstance().nickname = nickname;
            connected = true;
        }else{
            server.interrupt();
            throw new IOException("Connection Failed");
        }

        System.out.println("nickname="+User.getInstance().nickname+"\n");
        // send my pseudo only if there are other users connected
        if(!ContactList.getInstance().getTable().isEmpty())
            client.sendMsgToOthers(User.getInstance().nickname);
        else System.out.println("I'm the only one\n");


    }

    /** Disconnecting from the server, we also notify the other users
     *
     * @throws Exception if sleep failed
     */
    public void logOut() throws Exception{
        client.sendMsgToOthers("disconnect");
        Thread.sleep(100);
        server.interrupt();
    }

    public void changePseudo(String pseudo) throws IOException{
        if(ContactList.getInstance().isUnique(pseudo)){
            User.getInstance().nickname = pseudo;
            System.out.println("nickname= "+pseudo+"\n");
            client.sendMsgToOthers(pseudo);
        }else throw new IOException("Pseudo already taken");
    }


    @Override
    public void sendMessage(String msg, Contact recipient) {

    }

    @Override
    public void changeUsername(String username) {
        try {
            System.out.println("observer discovery method");
            changePseudo(username);
        }catch (IOException e){
            System.out.println("Change pseudo error: " + e);
        }
    }

    @Override
    public void tryConnecting(String username) {

    }

    @Override
    public void disconnect() {

    }

}
