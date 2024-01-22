package ContactDiscovery;
import java.util.ArrayList;

/** Class that keeps track of the others users that are connected
 *
 */
public class ContactList {
    private static ContactList contactList = null;
    private ArrayList<Contact> table;

    public interface Observer{
        void newContact(Contact contact);
        void contactRemoved(Contact contact);
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
     * @param contact The associated pseudo of the user
     */
    public synchronized void addContact(Contact contact){
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
                Contact contact = new Contact(table.get(i).pseudo(),table.get(i).ip());
                table.remove(i);
                for(Observer obs:observers ){
                    obs.contactRemoved(contact);
                }

                res = true;
                break;
            }
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

    public void changeUsername(Contact contactParam){
        Contact foundContact = contactList.table.stream().filter(contact -> contact.ip().equals(contactParam.ip())).findFirst().orElse(null);
        if(foundContact != null){
            ContactList.getInstance().table.remove(foundContact);
            ContactList.getInstance().addContact(contactParam);
        }



    }


}