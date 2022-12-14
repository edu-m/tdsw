import java.io.*;
import java.net.*;
import java.util.Scanner;

class HelloWorld {
    static Socket socket;
    static Scanner terminalInput = new Scanner(System.in);

    public static void CreateSocket() throws IOException {
        System.out.println("Insert hostname: ");
        socket = new Socket();
        String s = terminalInput.nextLine();
        InetSocketAddress saddr = new InetSocketAddress(s, 79);
        socket.connect(saddr);
        System.out.println(
                "Created socket with " + socket.getLocalAddress() + "\nBound to port " + socket.getLocalPort());

    }

    public static void DataIn() throws IOException {
        PrintStream ps = new PrintStream(socket.getOutputStream());
        System.out.println("Insert data: (hint: smith)");
        String s1 = terminalInput.nextLine();
        ps.println(s1);
        DataOut();
    }

    public static void DataOut() throws IOException {
        InputStreamReader ir = new InputStreamReader(socket.getInputStream());
        BufferedReader br = new BufferedReader(ir);

        String message;
        while ((message = br.readLine()) != null) {
            System.out.println("Message size: "+message.length()*8+" bytes.");
            System.out.println(message);
        }
    }

    public static void CloseManage() throws IOException
    {
        socket.close();
        terminalInput.close();
    }

    public static void main(String args[]) throws IOException {
        CreateSocket();
        DataIn();
        CloseManage();
    }
}