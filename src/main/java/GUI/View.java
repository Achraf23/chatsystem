package GUI;

import ChatController.ChatSessionController;
import ChatController.DatabaseManager;
import ContactDiscovery.Contact;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class View implements ContactView.Observer,LoginView.Observer,ChatSessionController.Observer,ChatSessionView.Observer {
    JFrame frame;
    ContactView contactView;
    LoginView loginView;
    ChatSessionView activeConversation;
    UsernamePanel usernamePanel;
    ArrayList<ChatSessionView> conversations;
    ArrayList<View.Observer> observers = new ArrayList<View.Observer>();
    MainObserver mainObserver;
    static final int width_frame = 500;
    static final int height_frame = 300;



    public interface Observer {
        void sendMessage(String msg, Contact recipient);
    }

    public interface MainObserver{
        void tryConnecting(String username);
        void disconnect();
        void changeUsername(String username);
    }

    public static void showWarningDialog() {
        JOptionPane.showMessageDialog(null, "Username exists already !", "Warning", JOptionPane.WARNING_MESSAGE);
    }

    public View() {
        //Frame settings
        frame = new JFrame("Chat System");
        frame.setSize(width_frame,height_frame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        WindowListener l = new WindowAdapter() {
            public void windowClosing(WindowEvent e){
                mainObserver.disconnect();
            }
        };
        frame.addWindowListener(l);
        frame.setLocationRelativeTo(null);

        //Init view components
        conversations = new ArrayList<ChatSessionView>();
        observers = new ArrayList<Observer>();
        contactView = new ContactView();

        //Observes ContactView
        contactView.addObserver(this);

        loginView = new LoginView();
        loginView.addObserver(this);
        frame.add(loginView);

        frame.setVisible(true);


    }

    /** shows the view with the right username when the connection
     * has succeeded. The conversation view on the left
     * remains empty as no conversation has been added
     */
    public void initChatSession(String username){
        //Remove loginView
        frame.remove(loginView);

        usernamePanel = new UsernamePanel(username);
        usernamePanel.createIconButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show a dialog to get user input (blocking instruction)
                String userInput = JOptionPane.showInputDialog("Change Username:");
                if(userInput != null){
                    mainObserver.changeUsername(userInput);

                }

            }
        });


        //Discovery Panel (Contact view and username) positioned at the left of the frame
        JPanel discovery = new JPanel(new BorderLayout());
        discovery.add(usernamePanel,BorderLayout.NORTH);
        discovery.add(contactView,BorderLayout.CENTER);

        frame.add(discovery,BorderLayout.WEST);

        frame.revalidate();
        frame.repaint();

    }


    public synchronized void addObserver(View.Observer obs){
        this.observers.add(obs);
    }
    public synchronized void addMainObserver(MainObserver obs){mainObserver = obs;}

    /** Adds a new conversation to the Conversation array
     */
    private void addConversation(ChatSessionView conversation){
        conversation.addObserver(this);
        conversations.add(conversation);
    }

    /** returns the conversation with the right contact in the conversations
     * array
     * returns null if conversation not found
     */
    private ChatSessionView getConversation(Contact contact){
        for(ChatSessionView conversation : conversations){
            if(conversation.contact.ip().equals(contact.ip()))
                return conversation;
        }

        return null;
    }

    /** returns a ChatSessionView containing the messages saved
     * in the jdbc database with contact
     */
    private ChatSessionView retrieveConversationFromDatabase(Contact contact) throws Exception{
        ArrayList<ChatSessionView.Message> messages = DatabaseManager.getInstance().retrieveAllMessages(contact);
        ChatSessionView conversation = new ChatSessionView(contact);

        for(ChatSessionView.Message msg : messages)
            conversation.addMessageToConversation(msg);
        return conversation;
    }

    /** Changes username on the screen
     */
    public void changeUsername(String username){usernamePanel.setUsername(username);}

    /** Shows the right stored conversation when contact is clicked
     */
    @Override
    public void contactClicked(Contact contact) {
        if(activeConversation!=null)
            frame.remove(activeConversation);

        //Attempt to get Conversation from Array of Conversations
        ChatSessionView conversation = getConversation(contact);

        if(conversation == null){
            //First time talking with a contact
            //Retrieve conversation from Database
            try {
                ChatSessionView newConversation = retrieveConversationFromDatabase(contact);
                addConversation(newConversation);
                activeConversation = newConversation;
                frame.add(activeConversation);
            }catch (Exception e){
                System.out.println("Error retrieving conversation From Database:\n"+e);
            }

        }else{
            activeConversation = conversation;
            frame.add(conversation);

        }

        frame.revalidate();
        frame.repaint();
    }

    /** When a contact changes username, updates the
     * contact in the right conversation
     */
    @Override
    public void changeConversationUsername(Contact contact) {
        ChatSessionView conversation = getConversation(contact);
        if(conversation!=null)
            conversation.contact = contact;
    }

    /** removes conversation with contact from the screen
     * when the contact disconnects
     */
    @Override
    public void endConversation(Contact contact) {
        ChatSessionView conversation = getConversation(contact);
        if(conversation !=null){
            if(conversation == activeConversation){
                frame.remove(activeConversation);
                frame.repaint();
                frame.revalidate();
            }

            conversation.remove(conversation);
        }


    }

    /** Adds message received from the server to the right
     * ChatSessionView and updates the panel
     */
    @Override
    public void receivedMessageFromServer(String msg,Contact origin){
        ChatSessionView conversation = getConversation(origin);
        if(conversation != null)
            conversation.addMessageToConversation(new ChatSessionView.Message(msg, false));

    }

    /** When button send is clicked,
     * transmits message to TCPClient to send it
     * through the network
     */
    @Override
    public void sentMessage(String msg, Contact recipient) {
        for (Observer obs : observers)
            obs.sendMessage(msg,recipient);

    }

    /** alert mainController to call
     * tryConnection method from the discovery system
     */
    @Override
    public void connectClicked(String username) {mainObserver.tryConnecting(username);}


}
