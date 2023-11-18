package Controller;

import ContactDiscovery.ContactList;
import ContactDiscovery.PseudoIP;

import java.util.ArrayList;

public class ContactListController {

    ContactList contactList;
    ContactListController(){
        contactList = ContactList.getInstance();
    }

    /** Remove the given IP address from the Contact list
     * @param ip Wanted IP address as a String
     * @return true if correctly removed, false otherwise
     */
    boolean findAndRemove(String ip){
        ArrayList<PseudoIP> table = contactList.table;
        for(int i=0;i<table.size();i++){
            if(table.get(i).ip.equals(ip)){
                table.remove(i);
                return true;
            }
        }
        return false;
    }

    void addLine(String pseudo,String ip){
        contactList.table.add(new PseudoIP(pseudo,ip));
    }


}
