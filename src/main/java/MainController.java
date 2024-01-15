import ChatController.ChatSessionController;
import ContactDiscovery.ContactList;
import Controller.DiscoverySystem;
import GUI.ChatSessionView;
import GUI.ContactView;
import GUI.View;
import TCP.TCPMessage;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

public class MainController {
    final DiscoverySystem discovery = new DiscoverySystem();
    final ChatSessionController chatController = new ChatSessionController();
    View view ;

    public MainController() throws IOException {
    }


    public static void main(String[] args) throws Exception{
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter username");
        String username = myObj.nextLine();

        //Initialize instances
        MainController app = new MainController();
        app.view = new View(username);

        //Add observers
        app.chatController.addObserver(app.view);
        app.view.addObserver(app.chatController.client);
        app.view.addObserver(app.discovery);

        //Try connection
        app.discovery.tryConnection(username);



    }

}
