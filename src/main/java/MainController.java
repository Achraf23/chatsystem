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
    public static void main(String[] args) throws Exception{
        MainController app = new MainController();
        ContactList.getInstance().addContact("achraf","0.0.0.0");
        ContactList.getInstance().addContact("rach","0.0.1.0");

//        app.discovery.tryConnection("test");

//        Scanner myObj = new Scanner(System.in);
//        String elt = "rien";
//
//        while (!elt.equals(".")){
//            elt = myObj.nextLine();
//            ContactList.getInstance().addContact(elt,elt);
//        }

    }

}
