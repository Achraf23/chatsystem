
import ContactDiscovery.ContactList;
import ContactDiscovery.PseudoIP;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

public class TestContactList {

    ArrayList<PseudoIP> contactList;
    @BeforeAll
    void initTest(){
        contactList=ContactList.getInstance().table;

    }

    private int uniqueElement(String pseudo){
        int count=0;
        for(int i=0;i<contactList.size();i++){
            if(pseudo.equals(contactList.get(i).pseudo)){
                count++;
            }
        }
        return count;
    }

    @Test
    void isContactListValid(){
        for(int i=0;i<contactList.size();i++){
            assertEquals(1,uniqueElement(contactList.get(i).pseudo));
        }
    }


}
