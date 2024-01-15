package GUI;

import ChatController.ChatSessionController;
import ChatController.DatabaseManager;
import ContactDiscovery.Contact;
import ContactDiscovery.ContactList;
import Controller.DiscoverySystem;
import TCP.TCPMessage;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.util.ArrayList;

public class View implements ChatSessionController.Observer,ChatSessionView.Observer {
    JFrame frame;
    ContactView contactView;
    ChatSessionView activeConversation;
    ArrayList<ChatSessionView> conversations;
    static final int width_frame = 500;
    static final int height_frame = 300;

    ArrayList<View.Observer> observers = new ArrayList<View.Observer>();


    public interface Observer {
        void sendMessage(String msg, Contact recipient);
        void changeUsername(String username);
    }

    public View(String username) {
        frame = new JFrame("Chat System");
        frame.setSize(width_frame,height_frame);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);



        UsernamePanel usernamePanel = new UsernamePanel(username);
        usernamePanel.createIconButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show a dialog to get user input (blocking instruction)
                String userInput = JOptionPane.showInputDialog("Change Username:");
                if(userInput != null){
                    usernamePanel.setUsername(userInput);
                    for(Observer observer : observers)
                        observer.changeUsername(userInput);

                }

            }
        });

        contactView = new ContactView();

        JPanel discovery = new JPanel(new BorderLayout());
        discovery.add(usernamePanel,BorderLayout.NORTH);
//        pseudo.add(Box.createRigidArea(new Dimension(0, 10)));
        discovery.add(contactView,BorderLayout.CENTER);

        frame.add(discovery,BorderLayout.WEST);

        frame.setVisible(true);

        conversations = new ArrayList<ChatSessionView>();
        observers = new ArrayList<Observer>();


        //Observes ContactView
        contactView.addObserver(contact -> {
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


        });




    }

    public static void main(String[] args) throws Exception {
        View v = new View("test");
    }

        public synchronized void addObserver(View.Observer obs){
        this.observers.add(obs);
    }


    void addConversation(ChatSessionView conversation){
        conversation.addObserver(this);
        conversations.add(conversation);
    }


    private ChatSessionView getConversation(Contact contact){
        for(ChatSessionView conversation : conversations){
            if(conversation.contact.ip().equals(contact.ip()))
                return conversation;
        }

        return null;
    }

    ChatSessionView retrieveConversationFromDatabase(Contact contact) throws Exception{
        ArrayList<ChatSessionView.Message> messages = DatabaseManager.getInstance().retrieveAllMessages(contact);
        ChatSessionView conversation = new ChatSessionView(contact);

        for(ChatSessionView.Message msg : messages)
            conversation.addMessageToConversation(msg);
        return conversation;
    }

    @Override
    public void receivedMessageFromServer(String msg,Contact origin){
        ChatSessionView conversation = getConversation(origin);
        if(conversation != null) {
            conversation.addMessageToConversation(new ChatSessionView.Message(msg, false));
        }
    }

    @Override
    public void sentMessage(String msg, Contact recipient) {
        for (Observer obs : observers)
            obs.sendMessage(msg,recipient);

    }


}
