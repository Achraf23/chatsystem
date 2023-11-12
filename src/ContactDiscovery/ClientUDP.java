package ContactDiscovery;
import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class ClientUDP {

    private DatagramSocket socket = null;

    public void broadcastConnection(String broadcastMessage) throws IOException{

        InetAddress addr=InetAddress.getByName("255.255.255.255");
        socket = new DatagramSocket();
        socket.setBroadcast(true);

        byte[] buffer = broadcastMessage.getBytes();

        DatagramPacket packet
                = new DatagramPacket(buffer, buffer.length,addr,EchoServer.Server_Port);
        socket.send(packet);

        socket.close();
    }

    public void sendMsgToOthers(String msg) {
        ArrayList<PseudoIP> table=ContactList.getInstance().table;
        DatagramPacket outPacket;
        try{
            socket= new DatagramSocket();

            for(int i=0;i<table.size();i++){
                try {
                    System.out.println(table.get(i).ip);
                    InetAddress addr_dest = InetAddress.getByName(table.get(i).ip);

                    outPacket = new DatagramPacket(msg.getBytes(), msg.getBytes().length,
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

}