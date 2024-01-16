import ChatController.ChatSessionController;
import ContactDiscovery.Contact;
import ContactDiscovery.ContactList;
import Controller.DiscoverySystem;
import GUI.ChatSessionView;
import GUI.ContactView;
import GUI.View;
import TCP.TCPMessage;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;
import java.util.Set;

public class MainController implements View.Observer {
    final DiscoverySystem discovery = new DiscoverySystem();
    final ChatSessionController chatController = new ChatSessionController();
    View view ;

    public MainController() throws IOException {
    }


    public static void main(String[] args) throws Exception{

        //Initialize instances
        MainController app = new MainController();
        app.view = new View();
        app.view.addObserver(app);

        //Add observers
        app.chatController.addObserver(app.view);
        app.view.addObserver(app.chatController.client);
        app.view.addObserver(app.discovery);

    }


    @Override
    public void tryConnecting(String username) {

        //Try connection
        try {
            discovery.tryConnection(username);
            view.initChatSession(username);

        }catch (IOException e){
            //Failed to Connect because username not unique
            View.showWarningDialog();
        }
    }

    @Override
    public void disconnect() {
        try {
            if(discovery.connected)
                discovery.logOut();
        }catch (Exception e){
            System.out.println("Disconnect error MainController: " + e);
        }
    }

    @Override
    public void sendMessage(String msg, Contact recipient) {

    }

    @Override
    public void changeUsername(String username) {

    }

}
