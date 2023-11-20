import Controller.ClientUDP;
import Controller.EchoServer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class NetworkTests {
    static final int server_port = 8080;
    static private DatagramSocket socket_server;
    static private byte[] buf = new byte[256];
    static private DatagramPacket packet;
    static ClientUDP client;

    @BeforeAll
    static void initTests() throws SocketException {
        client = new ClientUDP();
        socket_server = new DatagramSocket(server_port);
        socket_server.setSoTimeout(2000);

        packet = new DatagramPacket(buf, buf.length);
    }

    @Test
    void isMessageReceived() throws IOException{

        try {
            InetAddress addr_dest=InetAddress.getLocalHost();
            client.sendMsg("test", addr_dest,server_port);
            socket_server.receive(packet);
        }catch (UnknownHostException e){
            throw new IOException("Unknown Host");
        }

    }

    //EchoServerfunctions
    @Test
    void isMyBroadcast(){
//        client.broadcastConnection("test");
//        socket_server.receive(packet);
//        assertEquals();
    }
}
