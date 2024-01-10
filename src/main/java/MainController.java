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
    final View view = new View();

    public MainController() throws IOException {
    }


    public static void main(String[] args) throws Exception{
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter username");
        String username = myObj.nextLine();

        MainController app = new MainController();
        app.view.addObserver(app.chatController.client);
        app.chatController.addObserver(app.view);
        app.discovery.tryConnection(username);



    }

}
