package Model;
import javax.xml.crypto.Data;
import java.net.*;
import java.io.*;

public class ClientHandler {

    public void BroadcastConnection() {
        //to request the passwords
    }

    public void BroadcastNickname(String Pseudo){
        // To update other users' ContacList
    }

    public static void main(String[] args) throws IOException {
        BroadcastingClient.broadcast("Hello", InetAddress.getByName("255.255.255.255"));

    }


    class BroadcastingClient {
        public static DatagramSocket socket = null;


        public static void broadcast(
                String broadcastMessage, InetAddress address) throws IOException {
            socket = new DatagramSocket();
            socket.setBroadcast(true);

            byte[] buffer = broadcastMessage.getBytes();

            DatagramPacket packet
                    = new DatagramPacket(buffer, buffer.length, address, 6666);
            socket.send(packet);
            socket.close();
        }
    }



}