package TCP;

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
    static TCPServer server;
    static TCPClient client;

    @BeforeAll
    static void initTests(){
        client = new TCPClient();
        server = new TCPServer();
    }

    @Test
    void sendReceiveTest() throws Exception {
        List<String> testMessages = Arrays.asList("alice", "bob", "chloe",  "éàç");

        List<String> receivedMessages = new ArrayList<>();
        server.start(TCPServer.TCP_Server_Port);
        server.addObserver(msg -> {
            receivedMessages.add(msg.content());

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
        server.start(TCPServer.TCP_Server_Port);
        List<InetAddress> address = new ArrayList<InetAddress>();
        server.addObserver(msg -> {
            address.add(msg.origin());
        });
        client.startConnection("127.0.0.1",TCPServer.TCP_Server_Port);
        client.sendMessage(new TCPMessage("test",InetAddress.getLocalHost()));
        assertEquals(InetAddress.getLocalHost(),address.get(0));
    }

}
