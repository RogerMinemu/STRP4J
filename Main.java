import java.io.*;
import java.net.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        Properties prop = new Properties();
        InputStream input = new FileInputStream("config.properties");
        prop.load(input);

        String host = prop.getProperty("host");
        int port = Integer.parseInt(prop.getProperty("port"));

        System.out.println("Minemu Proxy Started");
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Listening for connections...");
        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Connected: " + clientSocket.getInetAddress());
            Socket server = new Socket(host, port);

            // Send PROXY protocol header to destination server
            OutputStream out = server.getOutputStream();
            out.write(("PROXY TCP4 " + clientSocket.getInetAddress().getHostAddress() + " " + server.getInetAddress().getHostAddress() + " " + clientSocket.getPort() + " " + server.getPort() + "\r\n").getBytes());
            out.flush();

            // Start threads to forward data
            new ProxyThread(clientSocket, server).start();
            new ProxyThread(server, clientSocket).start();
        }
    }
}

class ProxyThread extends Thread {
    private Socket in;
    private Socket out;

    public ProxyThread(Socket in, Socket out) {
        this.in = in;
        this.out = out;
    }

    public void run() {
        try {
            InputStream inStream = in.getInputStream();
            OutputStream outStream = out.getOutputStream();
            byte[] buffer = new byte[4096];
            int read;
            while ((read = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, read);
                outStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}