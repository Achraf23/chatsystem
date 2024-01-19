import ChatController.ChatSessionController;
import ControllerDiscovery.DiscoverySystem;
import GUI.View;
import TCP.TCPClient;

import java.io.IOException;


public class MainController implements View.MainObserver {
    final DiscoverySystem discovery = new DiscoverySystem();
    final ChatSessionController chatController = new ChatSessionController();
    View view ;

    public MainController() throws IOException {
    }


    public static void main(String[] args) throws Exception{

        //Initialize instances
        MainController app = new MainController();
        TCPClient tcpSender = new TCPClient();
        app.view = new View();
        app.view.addMainObserver(app);

        //Add observers
        app.chatController.addObserver(app.view);
        app.view.addObserver(tcpSender);
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


}
