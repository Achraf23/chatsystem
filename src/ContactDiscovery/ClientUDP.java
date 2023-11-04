package ContactDiscovery;
import java.net.*;
import java.io.*;

public class ClientUDP {

    public void BroadcastConnection() throws IOException{

        InetAddress addr=null;
        try{
             addr = InetAddress.getByName("255.255.255.255");

        }catch (UnknownHostException e){
            System.out.println("error can't find broadcast addresses name");
            throw e;
        }
        //to request the passwords
        BroadcastingClient.broadcast("Hello", addr);

    }

    public void BroadcastNickname(String Pseudo){
        // To update other users' ContacList
    }

    public static void main(String[] args) throws IOException {
        System.out.println(InetAddress.getLocalHost().toString());

    }


    class BroadcastingClient {
        private static DatagramSocket socket = null;


        public static void broadcast(
                String broadcastMessage, InetAddress address) throws IOException {
            socket = new DatagramSocket();
            socket.setBroadcast(true);

            byte[] buffer = broadcastMessage.getBytes();

            DatagramPacket packet
                    = new DatagramPacket(buffer, buffer.length, address, EchoServer.Broadcast_Port);
            socket.send(packet);

            socket.close();
        }
    }



}