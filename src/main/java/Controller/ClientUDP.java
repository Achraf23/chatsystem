package Controller;
import ContactDiscovery.ContactList;
import ContactDiscovery.Contact;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

/**
 *
 */
class ClientUDP {

    private DatagramSocket socket = null;
    DatagramPacket outPacket = null;

    /** Sends a broadcast to all connected users upon connection
     *
     * @param broadcastMessage The broadcasted message
     * @throws IOException
     */
    public void broadcastConnection(String broadcastMessage) throws IOException{

        InetAddress addr=InetAddress.getByName("255.255.255.255");
        socket = new DatagramSocket();
        socket.setBroadcast(true);

        byte[] buffer = broadcastMessage.getBytes();

        outPacket = new DatagramPacket(buffer, buffer.length,addr, EchoServer.Server_Port);
        socket.send(outPacket);

        socket.close();
    }

    /** Sends a message to all connected users through their IP address
     *
     * @param msg the message to transmit
     */
    public void sendMsgToOthers(String msg) {
        ArrayList<Contact> table= ContactList.getInstance().getTable();
        try{
            socket = new DatagramSocket();

            for(int i=0;i<table.size();i++){
                try {
//                    System.out.println(table.get(i).ip);
                    InetAddress addr_dest = InetAddress.getByName(table.get(i).ip());

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

    public void sendMsg(String msg,InetAddress ip_dest,int port_dest) throws IOException{
        outPacket = new DatagramPacket(msg.getBytes(), msg.getBytes().length,
                ip_dest, port_dest);

        socket = new DatagramSocket();
        socket.send(outPacket);

    }



}