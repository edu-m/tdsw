import java.net.*;
import java.io.*;

public class Server {
    public static final int PORT = 8080;
    private ServerSocket server = null;
    private Socket client = null;

    public Socket CreateSocket() throws IOException {
        ServerSocket server = new ServerSocket(PORT);
        client = server.accept();
        return client;
    }

    public ServerSocket getServerSocket() {
        return server;
    }

    public Socket getClientSocket()
    {
        return client;
    }

    public static void main(String[] args) throws IOException {
        Server s = new Server();
        try {
            s.CreateSocket();
        } catch (IOException e) {
            System.err.println("Accept fallito");
            System.exit(1);
        }
        OutputStreamWriter osw = new OutputStreamWriter(s.getClientSocket().getOutputStream());
        BufferedWriter bw = new BufferedWriter(osw);
        PrintWriter out = new PrintWriter(bw, true);

        InputStreamReader isr = new InputStreamReader(s.getClientSocket().getInputStream());
        BufferedReader in = new BufferedReader(isr);

        
        System.out.println("Chiusura socket");
        s.getServerSocket().close();
        s.getClientSocket().close();
    }
}
