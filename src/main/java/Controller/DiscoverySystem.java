package Controller;
import ContactDiscovery.ContactList;
import ContactDiscovery.User;

import java.io.IOException;
import java.util.Scanner;  // Import the Scanner class

/** Handles the Users operations regarding the server
 *
 */
public class DiscoverySystem {

    ClientUDP client;
    EchoServer server;

    /** ConnectionController Constructor
     *
     * @throws IOException
     */
    DiscoverySystem() throws IOException{
        client = new ClientUDP();
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
        client.broadcastConnection("Hello"); //send broadcast to retrieve connected users nickname

        //waiting a bit for the contactList to be initialized
        try {
            Thread.sleep(100);
        }catch (InterruptedException e){
            System.out.println("sleep failed");
        }


        if(ContactList.getInstance().isUnique(nickname)){
            User.getInstance().nickname = nickname;
        }else{
            server.interrupt();
            throw new IOException("Connection Failed");
        }

        System.out.println("nickname="+User.getInstance().nickname+"\n");
        // send my pseudo only if there are other users connected
        if(!ContactList.getInstance().table.isEmpty())
            client.sendMsgToOthers(User.getInstance().nickname);
        else System.out.println("I'm the only one\n");


    }

    /** Disconnecting from the server, we also notify the other users
     *
     * @throws Exception if sleep failed
     */
    public void logOut() throws Exception{
        //TODO: Do we interrupt the disconnect if sleep failed?
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

    //TODO: Main segment
    public static void main(String[] args) throws Exception {

        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter username");
        String input = myObj.nextLine();

        DiscoverySystem c = new DiscoverySystem();
        c.tryConnection(input);

        do{
            System.out.println("1.Change Pseudo");
            System.out.println("2.Log Out and Quit");

            do{
                input=myObj.nextLine();
                switch (input){
                    case "1":
                        System.out.println("Enter username");
                        input = myObj.nextLine();
                        try {
                            c.changePseudo(input);
                        }catch (IOException e){
                            System.out.println("User alreay taken");
                            c.logOut();
                        }
                        break;

                    case "2":
                        c.logOut();
                        break;

                    default:
                        System.out.println("Pas compris");
                        break;
                }
            }while (input.equals("3"));


        }while (!input.equals("2"));







    }



}
