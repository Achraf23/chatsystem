package ContactDiscovery;
import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class EchoServer extends Thread {

    private DatagramSocket socket;
    private boolean running;
    private byte[] buf = new byte[256];
    static  final int Server_Port=8080;

    public static void main(String[] args) throws Exception {
        EchoServer server = new EchoServer();


    }

    public EchoServer() throws SocketException{

        socket = new DatagramSocket(Server_Port);
    }

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

    public void run() {
        running = true;

        while (running) {
            DatagramPacket packet
                    = new DatagramPacket(buf, buf.length);

            try {
                socket.receive(packet);
                System.out.println("after receiving");
            } catch (IOException e) {
                System.out.println("receive method error");

            }

            String received
                    = new String(packet.getData(), 0, packet.getLength());

            //make sure not to receive its own broadcast

            try {
                InetAddress i = InetAddress.getByName(User.getInstance().getIP());
                if (!packet.getAddress().equals(i)) {
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

            if (received.equals("end")) {
                running = false;
            }


        }
        socket.close();
    }
}
