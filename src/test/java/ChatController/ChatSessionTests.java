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

}
