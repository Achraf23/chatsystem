package ContactDiscovery;
import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class ClientUDP {

    public void BroadcastConnection() throws IOException{

        InetAddress addr=null;
        try{
            addr = InetAddress.getByName("255.255.255.255");
            BroadcastingClient.broadcast("Hello", addr);

        }catch (UnknownHostException e){
            System.out.println("error can't find broadcast addresses name");
            throw e;
        }

    }

    public void sendPseudoConnection(String pseudo) {
        ArrayList<PseudoIP> table=ContactList.getInstance().table;
        DatagramPacket outPacket;
        try{
            DatagramSocket socket= new DatagramSocket();

            for(int i=0;i<table.size();i++){
                try {
                    System.out.println(table.get(i).ip);
                    InetAddress addr_dest = InetAddress.getByName(table.get(i).ip);

                    outPacket = new DatagramPacket(pseudo.getBytes(), pseudo.getBytes().length,
                            addr_dest, EchoServer.Server_Port);
                    try {
                        socket.send(outPacket);

                    }catch (IOException e){
                        System.out.println("Send packet exception send pseudo method");
                    }

                }catch (UnknownHostException e){
                    System.out.println("Unknown host except send pseudo method");
                }

            }
            socket.close();

        }catch (SocketException s){
            System.out.println("Socket exception send pseudo method");
        }



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
                    = new DatagramPacket(buffer, buffer.length, address, EchoServer.Server_Port);
            socket.send(packet);

            socket.close();
        }
    }



}