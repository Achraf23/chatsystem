
import ContactDiscovery.ContactList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestContactList {


    @Test
    void isUniqueTest(){
        String pseudo = "toto";
        ContactList.getInstance().addContact(pseudo,"0.0.0.0");
        ContactList.getInstance().addContact(pseudo,"0.0.0.0");
        assertFalse(ContactList.getInstance().isUnique(pseudo));
    }

    @Test
    void testAddContactList(){
        int sizeList=ContactList.getInstance().getTable().size();
        ContactList.getInstance().addContact("pseudo","test");
        assertEquals(sizeList+1,ContactList.getInstance().getTable().size());
    }


}
