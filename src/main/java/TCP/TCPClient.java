package TCP;

import ChatController.ChatSessionController;
import ChatController.DatabaseManager;
import ContactDiscovery.Contact;
import GUI.ChatSessionView;
import GUI.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClient implements View.Observer{
    private Socket clientSocket;
    private PrintWriter out;

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
    }

    public void sendMessage(TCPMessage msg){
        out.println(msg.content());
    }

    public void stopConnection() throws IOException {
        out.close();
        clientSocket.close();
    }

   @Override 
    public void sendMessage(String msg, Contact recipient){
        try {

                TCPMessage tcpMessage = new TCPMessage(msg,InetAddress.getLocalHost());
                startConnection(recipient.ip(),TCPServer.TCP_Server_Port);
                sendMessage(tcpMessage);
                stopConnection();

                ChatSessionController.storeMessage(tcpMessage,recipient);

        }catch (Exception error){
            System.out.println("Send message observer error: "+ error);
        }
    }

    @Override
    public void changeUsername(String username) {

    }



}
