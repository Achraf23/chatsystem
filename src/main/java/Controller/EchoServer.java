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
public class EchoServer extends Thread {

    private DatagramSocket socket;
    private boolean running;
    private byte[] buf = new byte[256];
    static  final int Server_Port=8080;

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

    public void run() {
        running = true;
        //TODO: "Updating conctactList": Send credentials back

        while (running) {
            DatagramPacket packet
                    = new DatagramPacket(buf, buf.length);

            boolean receive=false;
            try {
                socket.setSoTimeout(100);

            }catch (SocketException s){
                System.out.println("setSoTimeout error");
                this.interrupt();
            }

            //je receive si jai rien recu et si mon thread na pas ete interrompu
            while (!receive && !this.isInterrupted()){
                try{
                    try {
                        socket.receive(packet);
                        receive=true;
                    }catch (SocketTimeoutException s){
                        System.out.println("timeout");
                    }
                }catch (IOException e){
                    System.out.println("receive ");
                }
            }


            String received = new String(packet.getData(), 0, packet.getLength());

            if(!this.isInterrupted()){
                try {
                    //make sure not to receive its own broadcast
                    InetAddress addressSrc = packet.getAddress();

                    if (!isMyMessage(addressSrc)) {
                        System.out.println("packet from different host");

                        if (received.equals("Hello")) {
                            System.out.println("broadcast received");

                            //sends pseudo after receiving broadcast
                            InetAddress address = packet.getAddress();
                            int port = packet.getPort();
                            String pseudo;
                            try {
                                pseudo = User.getInstance().nickname;
                                packet = new DatagramPacket(pseudo.getBytes(), pseudo.length(), address, Server_Port);

                                try {
                                    System.out.println("sending pseudo: "+new String(packet.getData(), StandardCharsets.UTF_8));
                                    socket.send(packet);
                                } catch (IOException e) {
                                    System.out.println("send method error");
                                }
                            } catch (IOException e) {
                                System.out.println("exception ip address echo server");
                            }
                        } else {
                            String string = packet.getAddress().toString();
                            System.out.println("ip="+string);
                            String[] parts = string.split("/");
                            String part2 = parts[1];

                            switch (received) {
                                case "disconnect":
                                    System.out.println("received disconnect");
                                    if (!findAndRemove(part2)){
                                        System.out.println("ip not found in ContactList");
                                    }else {
                                        packet = new DatagramPacket("OK".getBytes(), "OK".length(), packet.getAddress(), Server_Port);
                                        socket.send(packet);
                                    }
                                    break;
                                case "OK":
                                    if (!findAndRemove(part2)){
                                        System.out.println("ip not found in ContactList");
                                    }else System.out.println("oui");
                                    break;
                                default:
                                    //Not a broadcast ==> Save new pseudo user
                                    String elt = received + "/" + packet.getAddress().toString();
                                    System.out.println("elt=" + elt);

                                    ContactList.getInstance().addLine(received, part2);
                            }
                        }
                    } else System.out.println("received same address");
                } catch (IOException i) {
                    System.out.println("stop thread");
                    this.interrupt();
                }
            }else running = false;
        }
        socket.close();
    }
}