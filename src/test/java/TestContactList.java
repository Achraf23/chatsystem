
import ContactDiscovery.ContactList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestContactList {

    private int uniqueElement(String pseudo){
        int count=0;
        for(int i=0;i<ContactList.getInstance().table.size();i++){
            if(pseudo.equals(ContactList.getInstance().table.get(i).pseudo())){
                count++;
            }
        }
        return count;
    }

    @Test
    void testContactListValid(){
        for(int i=0;i<ContactList.getInstance().table.size();i++){
            assertEquals(1,uniqueElement(ContactList.getInstance().table.get(i).pseudo()));
        }
    }

    @Test
    void testAddContactList(){
        int sizeList=ContactList.getInstance().table.size();
        ContactList.getInstance().addLine("pseudo","test");
        assertEquals(sizeList+1,ContactList.getInstance().table.size());
    }


}
