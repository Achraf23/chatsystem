package TCP;

import ContactDiscovery.Contact;
import ContactDiscovery.ContactList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TCPTests {

    @Test
    void sendReceiveTest() throws Exception {

        TCPClient client = new TCPClient();
        TCPServer server = new TCPServer();

        List<String> testMessages = Arrays.asList("alice", "bob", "chloe",  "éàç");

        List<String> receivedMessages = new ArrayList<>();
        server.start(TCPServer.TCP_Server_Port);

        server.addObserver(new TCPServer.Observer() {
            @Override
            public void messageReceived(TCPMessage msg) {
                receivedMessages.add(msg.content());
            }


        });

        Thread.sleep(100);

        client.startConnection("127.0.0.1",TCPServer.TCP_Server_Port);
        for (String msg : testMessages) {
            client.sendMessage(new TCPMessage(msg,InetAddress.getLocalHost()));
        }

        server.stop();
        client.stopConnection();


        assertEquals(testMessages.size(), receivedMessages.size());
        assertEquals(testMessages, receivedMessages);
    }

    @Test
    void originIpTestMessage() throws Exception{
        TCPClient client = new TCPClient();
        TCPServer server = new TCPServer();

        server.start(TCPServer.TCP_Server_Port);
        List<InetAddress> addressMsg = new ArrayList<InetAddress>();
        server.addObserver(new TCPServer.Observer() {
            @Override
            public void messageReceived(TCPMessage msg) {
                addressMsg.add(msg.origin());
            }


        });
        client.startConnection("127.0.0.1",TCPServer.TCP_Server_Port);
        client.sendMessage(new TCPMessage("test",InetAddress.getLocalHost()));

        server.stop();
        client.stopConnection();

        assertEquals(InetAddress.getLocalHost(),addressMsg.get(0));
    }

    @Test
    void testNewConnection() throws Exception{

        TCPClient client = new TCPClient();
        TCPServer server = new TCPServer();

        server.start(TCPServer.TCP_Server_Port);
        List<InetAddress> ipConnection = new ArrayList<InetAddress>();
        server.addObserver(new TCPServer.Observer() {
            @Override
            public void messageReceived(TCPMessage msg) {}

        });

        client.startConnection("127.0.0.1",TCPServer.TCP_Server_Port);

        Thread.sleep(100);

        server.stop();
        client.stopConnection();

        assertEquals(1,ipConnection.size());
        assertEquals(InetAddress.getLocalHost(),ipConnection.get(0));
    }


}
