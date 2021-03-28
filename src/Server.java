import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//--------------------------------------------------------------------------------------------------------------KOPIA
public class Server {

    //SERVER VARIABLES
    private static final int PORT = 1234;
    private static ArrayList<ClientHandler> clients = new ArrayList<>();
    private static ExecutorService pool = Executors.newWorkStealingPool();
    private static Socket clientSocket;
    private static BankAppClient bankClient;
    private static ArrayList<BankAppClient> bankAppClientsList = new ArrayList<>();
    private static int request;


    public static void main(String[] args) throws IOException, InterruptedException{
        menu();
    }

    //Start new connection with the server - musiało być nad funkcją menu(), inaczej nadpisywało klientów
    private static void createClientSocketConnection(ServerSocket serverSocket) throws IOException {

        System.out.println("[SERVER] Waiting for client connection... | Please start the client APP");
        bankClient = new BankAppClient();
        clientSocket = serverSocket.accept();
        bankAppClientsList.add(bankClient);
        System.out.println("[SERVER] Connected to the client!");
        System.out.println("[SERVER] Established connection with the: " + clientSocket.toString() + "\n");
        System.out.println("[SERVER] " + bankClient.getInfo());
        ClientHandler clientThread = new ClientHandler(clientSocket, clients, bankClient);
        clients.add(clientThread);

        pool.execute(clientThread);
    }

    //Server menu
    private static void menu() throws IOException, InterruptedException {
        System.out.println("[SERVER] Starting the server...");
        ServerSocket serverSocket = new ServerSocket(PORT);

        Scanner keyboard = new Scanner(System.in);


        while (true) {
            System.out.println("---------------MENU---------------------");
            System.out.println("1. Create new client connection");
            System.out.println("2. See connection list");
            System.out.println("3. See list of bank clients");
            System.out.println("4. Close the server");
            System.out.println("Select the option: ");

            while (keyboard.hasNextLine()) {
                if (keyboard.hasNextInt()) {
                    request = keyboard.nextInt();
                    break;
                } else {
                    System.out.println("You need to type int between 1-4");
                    keyboard.nextLine();
                }

            }
            switch (request) {
                case 1: {
                    createClientSocketConnection(serverSocket);
                    break;
                }

                case 2: {
                    for (int i = 0; i < clients.size(); i++) {
                        System.out.println("Connection nr:" + i + " " + clients.get(i).toString());
                    }
                    break;
                }

                case 3: {
                    System.out.println("List of connected clients: ");
                    for (int i = 0; i < bankAppClientsList.size(); i++) {
                        System.out.println("Client nr." + i + ": " + bankAppClientsList.get(i).getSubName() + " with ID: " + bankAppClientsList.get(i).getClientId());
                        System.out.println("With balance:" + bankAppClientsList.get(i).getBalance());


                    }
                    break;
                }

                case 4: {
                    System.out.println("Closing the Server...");
                    for (int i = 3; i > 0; i--) {
                        System.out.println(i);
                        Thread.sleep(1000);
                    }
                    keyboard.close();
                    serverSocket.close();
                    System.exit(0);
                    break;
                }

                default: {
                    System.out.println("Please enter the value between 1-4");
                    break;
                }


            }

        }
    }


}
