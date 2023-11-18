package ContactDiscovery;

import java.net.UnknownHostException;
import java.util.ArrayList;

/** Class that keeps track of the others users that are connected
 *
 */
public class ContactList {

    private static ContactList contactList = null;
    public ArrayList<PseudoIP> table;

    /** ContactList Constructor
     *
     */
    public ContactList(){
        this.table = new ArrayList<PseudoIP>();
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

    /** Adds a User through their Pseudo and IP address in the Contact List
     *
     * @param pseudo The associated pseudo of the user
     * @param ip The associated IP address of the user
     */
    public void addLine(String pseudo,String ip){
        table.add(new PseudoIP(pseudo,ip));
    }



}