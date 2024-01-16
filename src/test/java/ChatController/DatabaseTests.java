package ChatController;

import ContactDiscovery.Contact;
import GUI.ChatSessionView;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DatabaseTests {

    static Contact contact1;
    static Contact contact2;
    static DatabaseManager db;
    @BeforeAll
    static void initTests() {
        try {
            db = DatabaseManager.getInstance();
            contact1 = new Contact("pseudo1", "0.0.0.0");
            contact2 = new Contact("pseudo2", "1.1.1.1");

            db.deleteMessagesByIp(contact1);
            db.deleteMessagesByIp(contact2);
        }catch (Exception e){
            System.out.println("Test errors : " + e);
        }

    }

    @Test
    void addMessageDatabaseTest() {
        List<String> testMessages = Arrays.asList("alice", "bob", "chloe", "éàç");

        try {

            db.addMessageToDatabase(testMessages.get(0), contact1,true);
            db.addMessageToDatabase(testMessages.get(1), contact1,true);
            db.addMessageToDatabase(testMessages.get(2), contact2,true);
            db.addMessageToDatabase(testMessages.get(3), contact2,true);

            ArrayList<ChatSessionView.Message> messages1 = db.retrieveAllMessages(contact1);
            ArrayList<ChatSessionView.Message> messages2 = db.retrieveAllMessages(contact2);

            assertEquals(testMessages.get(0),messages1.get(0).content());
            assertEquals(testMessages.get(1),messages1.get(1).content());
            assertEquals(testMessages.get(0),messages2.get(3).content());
            assertEquals(testMessages.get(1),messages2.get(4).content());

        }catch(Exception e){
            System.out.println("Exception database test : " + e);
        }

    }

    @AfterAll
    static void cleanTests(){
        db.deleteMessagesByIp(contact1);
        db.deleteMessagesByIp(contact2);
    }
}
