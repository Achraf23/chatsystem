package Model;
import java.util.ArrayList;
import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
public class EchoOtherUsers {

    public void listenOtherUsers() {
        //Implemente un thread

        //different scénario ?
            //demande d'envoyer son pseudo
            //Updater la contactList
    }

    public void updateContactList( ArrayList<String> listOfContacts) {
        //On ajoute le otherUser qui s'est connecté (message recu est le broadcast associé à la connexion réussie)
    }

    public static void main(String[] args) throws IOException {
        EchoServer server = new EchoServer();
        server.start();
    }

    private static class EchoServer extends Thread {

        private DatagramSocket socket;
        private boolean running;
        private byte[] buf = new byte[256];

        public EchoServer() throws  SocketException{
            socket = new DatagramSocket(6666);
        }

        public void run() {
            running = true;

            while (running) {
                DatagramPacket packet
                        = new DatagramPacket(buf, buf.length);

                try{
                    socket.receive(packet);
                }catch (IOException e){
                    System.out.println("receive method error");

                }

                // byte[] to string

                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                packet = new DatagramPacket(buf, buf.length, address, port);
                String received
                        = new String(packet.getData(), 0, packet.getLength());

                if (received.equals("end")) {
                    running = false;
                    continue;
                }

//                try{
//                    socket.send(packet);
//                }catch (IOException e){
//                    System.out.println("send method error");
//                }
            }
            socket.close();
        }
    }
}