package echoserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class EchoServer {
    
    public static void main(String[] args) {
        try {
            ServerSocket sock = new ServerSocket(6013);
            
            while (true) {
                try {
                Socket client = sock.accept();
                InputStreamReader isr = new InputStreamReader(client.getInputStream());
                int input;
                while ((input = isr.read()) != -1) {
                    //System.out.println(input);
                    client.getOutputStream().write(input);
                    client.getOutputStream().flush();
                }
                isr.close();
                client.close();
                }catch(SocketException e) {
                    System.out.println(e);
                    continue;
                }
            }
        } catch (IOException ioe) {
            System.err.println(ioe);
            ioe.printStackTrace();
        }
    }
}