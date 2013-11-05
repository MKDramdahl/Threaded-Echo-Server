package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoServer implements Runnable {
    private final ServerSocket serverSocket;
    private final ExecutorService pool;
    protected int connectedClients = 0; 

    public EchoServer(int port, int poolSize) throws IOException {
        serverSocket = new ServerSocket(port);
        pool = Executors.newFixedThreadPool(poolSize);
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            for (;;) {
                pool.execute(new Handler(serverSocket.accept()));
            }
        } catch (IOException ex) {
            pool.shutdown();
        }

    }

    public static void main(String[] args) {
        try {
            //ServerSocket sock = new ServerSocket(6013);
            EchoServer server = new EchoServer(6013, 1000);
            Thread serverThread = new Thread(server);
            serverThread.start();

        } catch (IOException ioe) {
            System.err.println(ioe);
            ioe.printStackTrace();
        }
    }

    class Handler implements Runnable {
        private final Socket socket;
        Handler(Socket socket) { this.socket = socket; }
        @Override
        public void run() {
            connectedClients++;
            System.out.println("Client connected! Clients: " + connectedClients);
            try {
                InputStream isr = socket.getInputStream();
                OutputStream out = socket.getOutputStream();

                int input;
                while ((input = isr.read()) != -1) {
                    //System.out.println("Server input: " + input);
                    out.write(input);
                    out.flush();
                    //System.out.println("Input written");
                }
                isr.close();
                socket.close();
                connectedClients--;
            }catch(Exception e) {
                System.err.println(e);
            }



        }

    }


}