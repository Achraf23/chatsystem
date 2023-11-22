package Controller;
import ContactDiscovery.ContactList;
import ContactDiscovery.PseudoIP;
import ContactDiscovery.User;

import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/** Thread that listens to the message received by the client from others clients
 *
 */
class EchoServer extends Thread {

    private DatagramSocket socket;
    private boolean running;
    private byte[] buf = new byte[256];
    static final int Server_Port=8080;

    //TODO:
    /*public static void main(String[] args) throws Exception {
        EchoServer server = new EchoServer();


    }*/

    /** EchoServer Constructor
     */
    public EchoServer() throws SocketException{
        socket = new DatagramSocket(Server_Port);
    }

    /** Remove the given IP address from the Contact list
     * @param ip Wanted IP address as a String
     * @return true if correctly removed, false otherwise
     */
    boolean findAndRemove(String ip){
        ArrayList<PseudoIP> table = ContactList.getInstance().table;
        for(int i=0;i<table.size();i++){
            if(table.get(i).ip.equals(ip)){
                table.remove(i);
                return true;
            }
        }
        return false;
    }

    /** Check if the datagram received has been sent by me
     *
     * @param addrSrc
     * @return true if so, false otherwise
     * @throws IOException
     */
    boolean isMyMessage(InetAddress addrSrc) throws IOException{
        return User.getInstance().getIpAdresses().contains(addrSrc);
    }

    void receiveMsg(DatagramPacket packet) throws IOException{
        boolean receive=false;

        try {
            socket.setSoTimeout(100);

        }catch (SocketException s){
            throw new IOException("setSoTimeout error");
        }

        //je receive si jai rien recu et si mon thread na pas ete interrompu
        while (!receive && !this.isInterrupted()){
            try{
                try {
                    socket.receive(packet);
                    receive=true;
                }catch (SocketTimeoutException s){
                    // Timeout normal
                }
            }catch (IOException e){
                throw new IOException("Socket.receive Error");
            }
        }

    }

    void sendMsg(String msg,InetAddress addrDest) throws IOException{

        DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.length(), addrDest, Server_Port);

        try {
            System.out.println("sending pseudo: "+new String(packet.getData(), StandardCharsets.UTF_8));
            socket.send(packet);
        } catch (IOException e) {
            throw new IOException("socket.send exception");
        }
    }

    public void run() {
        //TODO: "Updating conctactList": Send credentials back

        while (!this.isInterrupted()) {
            DatagramPacket packet
                    = new DatagramPacket(buf, buf.length);

            try {
                receiveMsg(packet);
            }catch (IOException e){
                this.interrupt();
            }

            String received = new String(packet.getData(), 0, packet.getLength());

            if(!this.isInterrupted()){
                try {
                    //make sure not to receive its own broadcast
                    InetAddress addressSrc = packet.getAddress();

                    if (!isMyMessage(addressSrc)) {
//                        System.out.println("packet from different host");

                        if (received.equals("Hello")) {
//                            System.out.println("broadcast received");

                            //sends pseudo after receiving broadcast
                            String pseudo = User.getInstance().nickname;
                            sendMsg(pseudo,packet.getAddress());

                        } else {

                            switch (received) {
                                case "disconnect":
                                    //Disconnection request
                                    System.out.println("received disconnect");
                                    if (!findAndRemove(packet.getAddress().getHostAddress())){
//                                        System.out.println("ip not found in ContactList");
                                    }else {
                                        sendMsg("OK",packet.getAddress());
                                    }
                                    break;
                                case "OK":
                                    //Confirm disconnection request
                                    if (!findAndRemove(packet.getAddress().getHostAddress())){
//                                        System.out.println("ip not found in ContactList");
                                    }else System.out.println("oui");
                                    break;

                                default:
                                    //Save new pseudo user
                                    String elt = received + "/" + packet.getAddress().toString();
//                                    System.out.println("elt=" + elt);
                                    if(findAndRemove(packet.getAddress().getHostAddress())){
//                                        System.out.println("pseudo has changed: "+elt);
                                    }

                                    ContactList.getInstance().addLine(received, packet.getAddress().getHostAddress());
                            }
                        }
                    } //else {System.out.println("received same address "+packet.getAddress().getHostAddress());}
                } catch (IOException i) {
                    System.out.println("stop thread");
                    this.interrupt();
                }
            }
        }
        socket.close();
    }
}