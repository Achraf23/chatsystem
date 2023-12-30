package GUI;

import ContactDiscovery.Contact;
import ContactDiscovery.ContactList;

import java.awt.event.MouseEvent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class ContactView extends JPanel implements MouseListener, ContactList.Observer {

    public ArrayList<CustomizedButton> contacts;
    JPanel contactPanel;

    public ContactView(){
        super();
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        this.setPreferredSize(new Dimension(View.width_frame/4,View.height_frame));
        this.setBorder(BorderFactory.createTitledBorder("Contacts"));

        JPanel titlePanel = new JPanel();
//        JLabel title = new JLabel("Contact List");
//        titlePanel.add(title);
//        titlePanel.setBackground(Color.RED);
//        this.add(title);

        contacts = new ArrayList<CustomizedButton>();
        contactPanel = new JPanel();
        contactPanel.setLayout(new BoxLayout(contactPanel, BoxLayout.Y_AXIS));
        this.add(contactPanel,BorderLayout.CENTER);
        ContactList.getInstance().addObserver(this);

    }



    void updateContactPanel(){
        contactPanel.removeAll();
        for(Contact contact : ContactList.getInstance().table){
            contactPanel.add(new CustomizedButton(contact.pseudo()));
        }

        this.revalidate();
        this.repaint();
    }

    public void highlightButtons(Point cursor) {
        for (JButton contact : contacts) {
            Point buttonLocation = contact.getLocationOnScreen();
            double west = buttonLocation.getX();
            double east = buttonLocation.getX() + contact.getWidth();
            double north = buttonLocation.getY();
            double south = buttonLocation.getY() + contact.getHeight();
            boolean inRow = cursor.getX() > west && cursor.getX() < east;
            boolean inCol = cursor.getY() > north && cursor.getY() < south;
            boolean inBounds = inRow || inCol;
            contact.setBackground(inBounds ? new Color(0xFFFF00) : null);
        }
    }

    @Override
    public void mouseEntered(MouseEvent event) {
        highlightButtons(event.getLocationOnScreen());
    }
    @Override
    public void mouseClicked(MouseEvent e) { }

    @Override
    public void mousePressed(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

    @Override
    public void newContact(Contact contact) {
        updateContactPanel();
    }

    @Override
    public void contactRemoved() {
        updateContactPanel();
    }
}
