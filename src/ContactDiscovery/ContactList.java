package ContactDiscovery;

import java.util.ArrayList;

public class ContactList {

    private ArrayList<String> contactList;

    //Implementer InitContactList, a contructor for Model.ContactList, to initialize listOfContacts

    //Verify that the nickname is Unique
    //add context
    public boolean isUnique(String nickname){
        return !contactList.contains(nickname);
    }

    public ContactList(){
        contactList = new ArrayList<String> ();
        //Recoie les messages des autres utilisateurs
        //Ajoute les pseudo recu dans listOfContacts
    }


}