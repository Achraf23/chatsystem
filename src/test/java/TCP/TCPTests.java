package TCP;

import org.junit.jupiter.api.Test;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TCPTests {

    @Test
    void sendReceiveTest() throws Exception {

        TCPClient client = new TCPClient();
        TCPServer server = new TCPServer();
        int port = 6666;

        List<String> testMessages = Arrays.asList("alice", "bob", "chloe",  "éàç");

        List<String> receivedMessages = new ArrayList<>();
        server.start(port);

        server.addObserver(msg -> receivedMessages.add(msg.content()));



        client.startConnection("127.0.0.1",port);
        for (String msg : testMessages) {
            client.sendMessage(new TCPMessage(msg,InetAddress.getLocalHost()));
        }

        Thread.sleep(100);

        client.stopConnection();


        assertEquals(testMessages.size(), receivedMessages.size());
        assertEquals(testMessages, receivedMessages);
    }

    @Test
    void originIpTestMessage() throws Exception{
        TCPClient client = new TCPClient();
        TCPServer server = new TCPServer();
        int port = 8888;

        server.start(port);
        List<InetAddress> addressMsg = new ArrayList<>();


        server.addObserver(msg -> addressMsg.add(msg.origin()));

        client.startConnection("127.0.0.1",port);
        client.sendMessage(new TCPMessage("test",InetAddress.getLocalHost()));


        client.stopConnection();

        Thread.sleep(100);

        assertEquals(InetAddress.getLocalHost(),addressMsg.get(0));
    }




}
