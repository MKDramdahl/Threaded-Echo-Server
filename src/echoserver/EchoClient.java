package echoserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;


public class EchoClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket((args[0]!=null)? args[0]:"localhost", 6013);

            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();
            InputStreamReader reader = new InputStreamReader(input);

            byte line;
            while (true) {
                output.write(input.read());
            }

            //socket.close();
        } catch (IOException ioe) {
            System.out.println("We caught an exception");
            System.err.println(ioe);
        }
    }
    
}