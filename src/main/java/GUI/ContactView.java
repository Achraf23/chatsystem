package GUI;

import ContactDiscovery.Contact;
import ContactDiscovery.ContactList;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ContactView extends JPanel implements ContactList.Observer, ActionListener {

    public interface Observer{
        void contactClicked(Contact contact);
        void changeConversationUsername(Contact contact);
        void endConversation(Contact contact);
    }

    ArrayList<ContactView.Observer> observers;

    public ContactView(){
        super();
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        this.setPreferredSize(new Dimension(View.width_frame/4,View.height_frame));
        this.setBorder(BorderFactory.createTitledBorder("Contacts"));

        ContactList.getInstance().addObserver(this);

        observers = new ArrayList<Observer>();


    }

    public synchronized void addObserver(ContactView.Observer obs){
        this.observers.add(obs);
    }


    void updateContactPanel(){
        removeAll();
        ArrayList<Contact> contactList = ContactList.getInstance().getTable();
        for(Contact contact : contactList){
            CustomizedButton btn = new CustomizedButton(contact);
            btn.addActionListener(this);
            add(btn);
        }

        this.revalidate();
        this.repaint();
    }


    @Override
    public void newContact(Contact contact) {
        updateContactPanel();
        for(Observer observer : observers)
            observer.changeConversationUsername(contact);
    } // Works also for changing pseudo because we remove the contact and add a new one

    @Override
    public void contactRemoved(Contact contact) {
        updateContactPanel();
        for(Observer observer : observers)
            observer.endConversation(contact);
    }

    /*
    */
    @Override
    public void actionPerformed(ActionEvent e) {
        CustomizedButton contactBtn = ((CustomizedButton) e.getSource());

        for(ContactView.Observer obs:observers ){
            obs.contactClicked(contactBtn.contact);
        }
    }
}
