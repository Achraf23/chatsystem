package TCP;

import ContactDiscovery.ContactList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TCPServer {

    private static final Logger LOGGER = LogManager.getLogger(TCPServer.class);
    private ServerSocket serverSocket;
    public static int TCP_Server_Port=6666;


    public interface Observer{
        void messageReceived(TCPMessage msg);
    }
    ArrayList<TCPServer.Observer> observers;

    public TCPServer(){
        this.observers = new ArrayList<Observer>();
    }

    /** starts listening on port 'port' for new connections
     * launches a separate thread for each new connection
     */
    public void start(int port) throws IOException{

        serverSocket = new ServerSocket(port);
        Thread server= new Thread(){
            public void run(){

                while (true){
                    try{
                        Socket sock;
                        new EchoClientHandler((sock = serverSocket.accept())).start();


                    }catch (IOException e){
                        System.out.println("Server Accept exception: " + e);
                    }
                }


            }
        };

        server.start();
    }

    /** stops listening on serverSocket
     */
    public void stop() throws IOException{
        serverSocket.close();
    }

    public synchronized void addObserver(TCPServer.Observer obs){
        this.observers.add(obs);
    }


    /** Thread to handle a single connection
     */
    private class EchoClientHandler extends Thread {
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
                String msg;;
                while ((msg = in.readLine()) != null) {
                    LOGGER.trace("Received on port " + clientSocket.getLocalPort() + ": " + msg + " from " + clientSocket.getInetAddress());

                    //Notify the observers that the server received a message
                    for(TCPServer.Observer obs:observers){
                        obs.messageReceived(new TCPMessage(msg, clientSocket.getInetAddress()));
                    }


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
