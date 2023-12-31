import ChatController.ChatSessionController;
import ContactDiscovery.ContactList;
import Controller.DiscoverySystem;
import GUI.ContactView;
import GUI.View;

import java.util.Scanner;

public class MainController {
    DiscoverySystem discovery;
    ChatSessionController chatController;
    View view;

    MainController() throws Exception{
        discovery = new DiscoverySystem();
        chatController = new ChatSessionController();
        view = new View();


    }


}
