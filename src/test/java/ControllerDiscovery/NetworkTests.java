package ControllerDiscovery;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;



public class NetworkTests {
    static private byte[] buf = new byte[256];
    static private DatagramPacket packet;
    static ClientUDP client;
    static EchoServer server;

    @BeforeAll
    static void initTests() throws SocketException {
        server = new EchoServer();
        client = new ClientUDP();
//        socket_server = new DatagramSocket(server_port);


        packet = new DatagramPacket(buf, buf.length);
    }

    @Test
    void testClientUDP() throws IOException{

        try {
            try {
                DatagramSocket socket = new DatagramSocket(2020);
                InetAddress addr_dest=InetAddress.getLocalHost();
                client.sendMsg("test", addr_dest,2020);
                socket.receive(packet);
                assertEquals("test",new String(packet.getData(),0,packet.getLength()));
            }catch (SocketException s){
                throw new IOException("Socket exception");
            }

        }catch (UnknownHostException e){
            throw new IOException("Unknown Host");
        }

    }

    //EchoServerfunctions
    @Test
    void testReceiveMessage() throws IOException{
        try {
            client.sendMsg("test",InetAddress.getLocalHost(),EchoServer.Server_Port);
        }catch (UnknownHostException s){
            throw new IOException("getLocalHost Exception");
        }
        server.receiveMsg(packet);
        assertEquals("test",new String(packet.getData(),0,packet.getLength()));

    }


}
