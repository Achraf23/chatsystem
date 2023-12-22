package ChatController;

import ContactDiscovery.Contact;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChatSessionTests {
    static ChatSessionController chatSessionController;

    @BeforeAll
    static void initTests() throws IOException{
        chatSessionController = new ChatSessionController();
    }

    @Test
    void startSessionTest() throws IOException {
        Contact contact = new Contact("bob","127.0.0.1");
        chatSessionController.startChatSession(contact);
        assertEquals(contact,chatSessionController.conversations.get(0).contact);

    }
}
