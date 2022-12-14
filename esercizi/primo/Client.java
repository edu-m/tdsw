import java.net.*;
import java.util.concurrent.TimeoutException;
import java.io.*;

public class Client {
    private Socket socket = new Socket();
    public int port;
    public String hostname = null, str = "";
    public OutputStreamWriter osw = null;
    public InputStreamReader isr = null;
    public BufferedReader in = null;
    public BufferedWriter bw = null;
    public PrintWriter out = null;

    public String getStringByUser(String msg) throws IOException {
        BufferedReader scr = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(msg);
        return scr.readLine();
    }

    public void CreateSocket() throws UnknownHostException, IOException, SocketTimeoutException {
        hostname = getStringByUser("Inserisci hostname");
        port = Integer.parseInt(getStringByUser("Inserisci porta"));
        InetSocketAddress sockaddr = new InetSocketAddress(InetAddress.getByName(hostname), port);
        socket.connect(sockaddr, 2000);
    }

    public Socket getSocket() {
        return socket;
    }

    public void Close() throws IOException {
        System.out.println("Chiusura della socket.");
        socket.close();
        System.out.println("Fatto.");
        System.out.println("Chiusura dello stream in output.");
        out.close();
        osw.close();
        bw.close();
        System.out.println("Fatto.");
        System.out.println("Chiusura dello stream in input.");
        in.close();
        isr.close();
        System.out.println("Fatto.");
        System.out.println("Chiusura completata, chiusura del programma.");
    }

    public static void main(String[] args) throws IOException, UnknownHostException, TimeoutException {
        Client client = new Client();
        try {
            client.CreateSocket();
        } catch (UnknownHostException e) {
            System.err.println("Errore nella connessione con " + client.hostname + ": Host sconosciuto.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Errore nella connessione con " + client.hostname + ": Connessione fallita.");
            System.exit(1);
        }
        System.out.println("Socket creata con " + client.getSocket().getInetAddress());

        client.osw = new OutputStreamWriter(client.getSocket().getOutputStream());
        client.bw = new BufferedWriter(client.osw);
        client.out = new PrintWriter(client.bw, true);

        client.isr = new InputStreamReader(client.getSocket().getInputStream());
        client.in = new BufferedReader(client.isr);

        while (true) {
            client.str = client.getStringByUser(
                    "Inserisci stringa da passare al server. Digita 'end' per chiudere la connessione.");
            if (client.str.equals("end"))
                break;
            String[] buf = new String[1024];
            int i = 0;
            client.out.println(client.str);
            while (client.in.readLine() != null && i < buf.length) {
                buf[i++] = client.in.readLine();
                System.out.println(client.in.readLine());
            }
            if (buf[0] == null) {
                System.out.println("Flusso dati interrotto. Chiusura");
                break;
            } else
                buf = new String[buf.length];
            System.out.println("Fine ricezione dati");
        }

        try {
            client.Close();
        } catch (IOException e) {
            System.err.println("Errore nella chiusura degli stream");
            e.getStackTrace();
        }
    }
}
