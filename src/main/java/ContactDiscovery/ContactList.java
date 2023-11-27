package ContactDiscovery;

import java.net.UnknownHostException;
import java.util.ArrayList;

/** Class that keeps track of the others users that are connected
 *
 */
public class ContactList {
    private static ContactList contactList = null;
    public ArrayList<PseudoIP> table;

    public interface Observer{
        void newContactAdded(PseudoIP pseudoIP);
    }
    ArrayList<Observer> observers;

    /** ContactList Constructor
     *
     */
    public ContactList(){
        this.table = new ArrayList<PseudoIP>();
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
    public void addLine(String pseudo,String ip){
        PseudoIP contact = new PseudoIP(pseudo,ip);
        table.add(contact);
        for(Observer obs:observers ){
            obs.newContactAdded(contact);
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
            if(pseudo.equals(contactList.table.get(i).pseudo)){
                return false;
            }
        }
        return true;
    }


}