
import ContactDiscovery.ContactList;
import ContactDiscovery.PseudoIP;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

public class TestContactList {

    private int uniqueElement(String pseudo){
        int count=0;
        for(int i=0;i<ContactList.getInstance().table.size();i++){
            if(pseudo.equals(ContactList.getInstance().table.get(i).pseudo)){
                count++;
            }
        }
        return count;
    }

    @Test
    void testContactListValid(){
        for(int i=0;i<ContactList.getInstance().table.size();i++){
            assertEquals(1,uniqueElement(ContactList.getInstance().table.get(i).pseudo));
        }
    }


}
