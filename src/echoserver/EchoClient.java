package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


public class EchoClient {

    Socket socket;

    public EchoClient() {
        
    }
    
    public static void main(String[] args) throws InterruptedException {
        try {
            EchoClient ec = new EchoClient();
            ec.socket = new Socket((args.length > 0) ? args[0]:"localhost", 6013);

            Thread localInput = new Thread(ec.new inputThread());
            localInput.start();
            Thread localOutput = new Thread(ec.new outputThread());
            localOutput.start();
            
            localInput.join();
            localOutput.join();
            
            ec.socket.close();
        } catch (IOException ioe) {
            System.out.println("We caught an exception");
            System.out.println(ioe);
        }
    }

    private class outputThread implements Runnable {

        public outputThread() {
            
        }
        
        @Override
        public void run() {

            int outputVal;
            try {
                InputStream input = socket.getInputStream();
                while ((outputVal = input.read()) != -1) {
                    System.out.write(outputVal);
                }
                System.out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private class inputThread implements Runnable {

        public inputThread(){
        
        }
        
        @Override
        public void run() {
            int input;
            try {

                OutputStream output = socket.getOutputStream();
                while ((input = System.in.read()) != -1) {
                    output.write(input);
                }
                output.flush();
                socket.shutdownOutput();

            } catch (Exception e) {
                System.err.println(e);
            }
        }

    }

}