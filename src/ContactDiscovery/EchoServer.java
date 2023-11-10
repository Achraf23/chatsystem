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

            //make sure not to receive its own broadcast

            try {
                InetAddress i = InetAddress.getByName(User.getInstance().getIP());
                if (!packet.getAddress().equals(i)) {
                    System.out.println("ici");
                    String received
                            = new String(packet.getData(), 0, packet.getLength());

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
                        //Not a broadcast ==> Save new pseudo user
                        String elt = received + "/" + packet.getAddress().toString();
                        System.out.println("elt=" + elt);
                        String string = packet.getAddress().toString();
                        String[] parts = string.split("/");
                        String part2 = parts[1];
                        ContactList.getInstance().addLine(received, part2);


                    }


                    if (received.equals("end")) {
                        running = false;
                    }
                } else System.out.println("received same address");


            } catch (IOException i) {
                System.out.println("stop thread");
                this.interrupt();

            }





        }
        socket.close();
    }
}
