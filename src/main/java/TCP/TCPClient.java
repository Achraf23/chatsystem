package TCP;

import ContactDiscovery.Contact;
import GUI.ChatSessionView;
import GUI.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

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
            startConnection(recipient.ip(),TCPServer.TCP_Server_Port);
            sendMessage(new TCPMessage(msg,InetAddress.getLocalHost()));
            stopConnection();
        }catch (IOException e){
            System.out.println("Send message observer error: "+ e);
        }
    }

}
