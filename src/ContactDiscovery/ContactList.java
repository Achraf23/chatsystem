package ContactDiscovery;

import java.net.UnknownHostException;
import java.util.ArrayList;

public class ContactList {

    private static ContactList contactList = null;
    public ArrayList<PseudoIP> table;

    //Implementer InitContactList, a contructor for Model.ContactList, to initialize listOfContacts

    public ContactList(){
        this.table = new ArrayList<PseudoIP>();
    }

    public static synchronized ContactList getInstance () {
        if (contactList == null)
            contactList = new ContactList();

        return contactList;
    }

    public void addLine(String pseudo,String ip){
        table.add(new PseudoIP(pseudo,ip));
    }



}