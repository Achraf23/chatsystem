
import ContactDiscovery.Contact;
import ContactDiscovery.ContactList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestContactList {


    @Test
    void isUniqueTest(){
        String pseudo = "toto";
        ContactList.getInstance().addContact(new Contact(pseudo,"0.0.0.0"));
        ContactList.getInstance().addContact(new Contact(pseudo,"0.0.0.0"));
        assertFalse(ContactList.getInstance().isUnique(pseudo));
    }

    @Test
    void testAddContactList(){
        int sizeList=ContactList.getInstance().getTable().size();
        ContactList.getInstance().addContact(new Contact("pseudo","0.0.0.0"));
        assertEquals(sizeList+1,ContactList.getInstance().getTable().size());
        ContactList.getInstance().getTable().clear();
    }

    @Test
    void testRemoveContactList(){
        ContactList.getInstance().addContact(new Contact("pseudo","0.0.0.0"));
        ContactList.getInstance().removeContact("0.0.0.0");
        assertEquals(ContactList.getInstance().getTable().size(),0);
    }

    @Test
    void testChangeUsername(){
        String pseudo = "newUsername";
        ContactList.getInstance().addContact(new Contact("pseudo","0.0.0.0"));
        ContactList.getInstance().changeUsername(new Contact(pseudo,"0.0.0.0"));
        assertEquals(pseudo,ContactList.getInstance().getTable().get(0).pseudo());
        ContactList.getInstance().getTable().clear();

    }


}
