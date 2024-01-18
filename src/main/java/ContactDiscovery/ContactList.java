package ContactDiscovery;

import java.net.UnknownHostException;
import java.util.ArrayList;

/** Class that keeps track of the others users that are connected
 *
 */
public class ContactList {

    private static ContactList contactList = null;
    private ArrayList<Contact> table;

    public interface Observer{
        void newContact(Contact contact);
        void contactRemoved();
    }
    ArrayList<Observer> observers;

    /** ContactList Constructor
     *
     */
    public ContactList(){
        this.table = new ArrayList<Contact>();
        this.observers = new ArrayList<Observer>();
    }


    /** Return or creates instance of the class contactlist
     *
     * @return contactList instance
     */
    public static synchronized ContactList getInstance () {
        if (contactList == null)
            contactList = new ContactList();

        return contactList;
    }

    public synchronized void addObserver(Observer obs){
        this.observers.add(obs);
    }

    /** Adds a User through their Pseudo and IP address in the Contact List
     *
     * @param pseudo The associated pseudo of the user
     * @param ip The associated IP address of the user
     */
    public synchronized void addContact(String pseudo,String ip){
        Contact contact = new Contact(pseudo,ip);
        table.add(contact);
        for(Observer obs:observers ){
            obs.newContact(contact);
        }
    }

    /** Verifies that our pseudo is unique by consulting the contactList table
     *
     * @param pseudo Our pseudo
     * @return false if already used, true if unique
     */
    public boolean isUnique(String pseudo){
        ContactList contactList = ContactList.getInstance();
        for(int i=0;i<contactList.table.size();i++){
            if(pseudo.equals(contactList.table.get(i).pseudo())){
                return false;
            }
        }
        return true;
    }

    public synchronized boolean removeContact(String ip){
        boolean res=false;
        ArrayList<Contact> table = ContactList.getInstance().table;
        for(int i=0;i<table.size();i++){
            if(table.get(i).ip().equals(ip)){
                table.remove(i);
                res = true;
                break;
            }
        }

        for(Observer obs:observers ){
            obs.contactRemoved();
        }

        return res;
    }


    public Contact getIpFromContact(String ip){
        ContactList contactList = ContactList.getInstance();
        for(int i=0;i<contactList.table.size();i++){
            if(ip.equals(contactList.table.get(i).ip())){
                return contactList.table.get(i);
            }
        }
        return null;
    }

    public ArrayList<Contact> getTable(){return table;}


}