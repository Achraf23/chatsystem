package ControllerDiscovery;
import ContactDiscovery.Contact;
import ContactDiscovery.ContactList;
import ContactDiscovery.User;

import java.net.*;
import java.io.*;

/** Thread that listens to the message received by the client from others clients
 *
 */
class EchoServer extends Thread {

    private DatagramSocket socket;
    private byte[] buf = new byte[256];
    static final int Server_Port=8080;


    /** EchoServer Constructor
     */
    public EchoServer() throws SocketException{
        socket = new DatagramSocket(Server_Port);
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
            try {
                socket.receive(packet);
                receive=true;
            }catch (SocketTimeoutException s){
                // Timeout normal
            }

        }

    }

    void sendMsg(String msg,InetAddress addrDest) throws IOException{

        DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.length(), addrDest, Server_Port);

        try {
            socket.send(packet);
        } catch (IOException e) {
            throw new IOException("socket.send exception");
        }
    }

    public void run() {

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

                        if (received.equals("Hello")) {
                            //sends pseudo after receiving broadcast
                            String pseudo = User.getInstance().nickname;
                            sendMsg(pseudo,packet.getAddress());

                        } else {
                            if (received.equals("disconnect")) {
                                //Disconnection request
                                System.out.println("received disconnect");
                                if (!ContactList.getInstance().removeContact(packet.getAddress().getHostAddress())) {
                                    System.out.println("ip not found in ContactList\n");
                                }
                            }else{
                                //User exists -> change username
                                if(ContactList.getInstance().getIpFromContact(packet.getAddress().getHostAddress()) != null){
                                    ContactList.getInstance().changeUsername(new Contact(received,packet.getAddress().getHostAddress()));
                                }else{
                                    //Create new user in ContactList
                                    ContactList.getInstance().addContact(new Contact(received, packet.getAddress().getHostAddress()));
                                }


                            }
                        }
                    }
                } catch (IOException i) {
                    System.out.println("stop thread");
                    this.interrupt();
                }
            }
        }
        socket.close();
    }
}