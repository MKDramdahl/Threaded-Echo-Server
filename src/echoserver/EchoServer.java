package echoserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    
    public static void main(String[] args) {
        try {
            ServerSocket sock = new ServerSocket(6013);
            
            while (true) {
                Socket client = sock.accept();
                InputStreamReader isr = new InputStreamReader(client.getInputStream());
                while (true) {
                    byte input = (byte) isr.read();
                    client.getOutputStream().write(input);
                }
            }
        } catch (IOException ioe) {
            System.err.println(ioe);
        }
    }
}