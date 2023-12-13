package TCP;
import ContactDiscovery.Contact;
import ContactDiscovery.ContactList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class TCPServer {

    private ServerSocket serverSocket;
    public static int TCP_Server_Port=6666;


    public interface Observer{
        void newContactAdded(Contact contact);
    }
    ArrayList<ContactList.Observer> observers;

    public void start() throws IOException{
        serverSocket = new ServerSocket(TCP_Server_Port);
        while (true)
            new EchoClientHandler(serverSocket.accept()).start();
    }

    public void stop() throws IOException{
        serverSocket.close();
    }

    private static class EchoClientHandler extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;

        public EchoClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try{
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));

            }catch(IOException e){
                System.out.println("Init Input & Output stream error");
            }

            try{
                String inputLine;;
                while ((inputLine = in.readLine()) != null) {

                    if (".".equals(inputLine)) {
                        out.println("bye");
                        break;
                    }
                    out.println(inputLine);
                    System.out.println("fini");

                }
            }catch(IOException e){
                System.out.println("error Input readLine");
            }


            try{
                in.close();
                out.close();
                clientSocket.close();
            }catch(IOException e){
                System.out.println("closing Input stream error OR clientSocket closing error");
            }

        }
    }

}
