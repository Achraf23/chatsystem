package GUI;

import ContactDiscovery.Contact;
import ContactDiscovery.ContactList;

import javax.swing.*;
import java.awt.*;

public class View {
    JFrame frame;
    public ContactView contactView;
    static final int width_frame = 500;
    static final int height_frame = 300;

    public View(){
        frame = new JFrame("Chat System");
        frame.setSize(width_frame,height_frame);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        contactView = new ContactView();
        frame.add(contactView, BorderLayout.WEST);

        frame.setVisible(true);

//        frame.add(new ChatSessionView("achraf"));

        //Observes ContactView
        contactView.addObserver(contact -> {
            frame.add(new ChatSessionView(contact));
            frame.revalidate();
            frame.repaint();
        });


    }

}
