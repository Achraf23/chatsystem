package Model;

import java.net.*;
import java.io.*;

public class Client {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public String sendMessage(String msg) throws IOException{
        out.println(msg);
        String resp = in.readLine();
        return resp;
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    static int readIntFromConsole() throws IOException {
        // System.in is an InputStream which we wrap into the more capable BufferedReader
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            String input = reader.readLine();
            try {
                int n = Integer.parseInt(input);
                return n;
            } catch (NumberFormatException e) {
                System.out.print("Not an integer, try again: ");
            }


        }
    }

    static void writeDoubleToSocket(Socket socket, double number) throws IOException {
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        out.writeDouble(number);
    }

    static double readDoubleFromSocket(Socket socket) throws IOException {
        DataInputStream in = new DataInputStream(socket.getInputStream());
        return in.readDouble();
    }


    public static void main(String[] args) throws IOException {
        Client client1=new Client();
        client1.startConnection("127.0.0.1", 6666);
        boolean run=true;
        System.out.println("Welcome to the averaging client");

        while(run){
            System.out.println("Please enter the next number");
            int n=readIntFromConsole();
            writeDoubleToSocket(client1.clientSocket, n);

            if(n!=0){
                System.out.println("Sending to server awaiting response...");
                System.out.println("Received current average: "+readDoubleFromSocket(client1.clientSocket));
            }else{
                run=false;
            }

        }

        client1.stopConnection();

    }
}