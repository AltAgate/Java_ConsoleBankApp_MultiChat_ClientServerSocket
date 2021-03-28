import java.io.*;
import java.net.Socket;
//--------------------------------------------------------------------------------------------------------------KOPIA

public class Client {
    //CLIENT VARIABLES
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 1234;
    private static BufferedReader in;

    public static void main(String[] args) throws IOException{


        System.out.println("------Welcome to your bank client console!");
        Socket socket = new Socket(SERVER_IP, SERVER_PORT);
        System.out.println("------Connecting to the server...");

        ServerConnection serverConn = new ServerConnection(socket);

        BufferedReader keyboardInput = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String accountName = in.readLine();
        new Thread(serverConn).start();

        System.out.println("------Connected to the server!");
        System.out.println("Type your request | Options : {\"deposit\", \"withdraw\", \"say + your_text\", \"send\"} ");

        //CLIENT CONSOLE
        while (true) {
            System.out.print(accountName + "'s account> \n");
            String command = keyboardInput.readLine();

            if (command.equals("break")) break;
            output.println(command);
        }

        socket.close();
        System.exit(0);
    }


}
