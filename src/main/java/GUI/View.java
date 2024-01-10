package GUI;

import ChatController.ChatSessionController;
import ContactDiscovery.Contact;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class View implements ChatSessionController.Observer,ChatSessionView.Observer{
    JFrame frame;
    public ContactView contactView;
    ChatSessionView activeConversation;
    ArrayList<ChatSessionView> conversations;
    static final int width_frame = 500;
    static final int height_frame = 300;

    final ArrayList<View.Observer> observers = new ArrayList<View.Observer>();

    public interface Observer {
        void sendMessage(String msg, Contact recipient);
    }

    public View() {
        frame = new JFrame("Chat System");
        frame.setSize(width_frame,height_frame);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        contactView = new ContactView();
        frame.add(contactView, BorderLayout.WEST);

        frame.setVisible(true);

        conversations = new ArrayList<ChatSessionView>();


        //Observes ContactView
        contactView.addObserver(contact -> {
            if(activeConversation!=null)
                frame.remove(activeConversation);

            //Attempt to get Conversation
            ChatSessionView conversation = getConversation(contact);


            if(conversation == null){
                //First time talking with a contact
                ChatSessionView newConversation = new ChatSessionView(contact);
                newConversation.addObserver(this);
                activeConversation = newConversation;
                conversations.add(activeConversation);
                frame.add(activeConversation);

            }else{
                //Retrieve stored conversation
                activeConversation = conversation;
                frame.add(conversation);

            }



            frame.revalidate();
            frame.repaint();
        });


    }

    public synchronized void addObserver(View.Observer obs){
        this.observers.add(obs);
    }


    private ChatSessionView getConversation(Contact contact){
        for(ChatSessionView conversation : conversations){
            if(conversation.contact.ip().equals(contact.ip()))
                return conversation;
        }

        return null;
    }

    @Override
    public void receivedMessageFromServer(String msg,Contact origin){
        ChatSessionView conversation = getConversation(origin);
        if(conversation != null){
            conversation.addMessageToConversation(msg,origin.pseudo());

        }
        else System.out.println("Add message conversation error");

    }

    @Override
    public void sentMessage(String msg, Contact recipient) {
        for (Observer obs : observers)
            obs.sendMessage(msg,recipient);

    }


}
