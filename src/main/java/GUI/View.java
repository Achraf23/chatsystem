package GUI;

import ContactDiscovery.Contact;
import ContactDiscovery.ContactList;

import javax.swing.*;
import java.awt.*;

public class View {
    JFrame frame;
    ContactView contactView;
    static final int width_frame = 500;
    static final int height_frame = 300;

    View(){
        frame = new JFrame("Chat System");
        frame.setSize(width_frame,height_frame);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        contactView = new ContactView();
        frame.add(contactView, BorderLayout.WEST);
        frame.setVisible(true);

        JPanel conversation = new ChatSessionView("achraf");
        frame.add(conversation);
    }

    public static void main(String[] args)  {

        View v = new View();
        ContactList.getInstance().addContact("test1","0.0.0.0");
        ContactList.getInstance().addContact("test2","0.0.0.0");
    }
}
