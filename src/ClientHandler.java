import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;


public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private BufferedReader in;
    private BufferedReader keyboardInput;
    private PrintWriter out;
    private ArrayList<ClientHandler> clients;
    private BankAppClient bankClient;
    private BankAppClient sendToClient;
    private static ArrayList<BankAppClient> bankAppClientsList = new ArrayList<>();

    ClientHandler(Socket clientSocket, ArrayList<ClientHandler> clients, BankAppClient bankClient) throws IOException {
        this.clientSocket = clientSocket;
        this.clients = clients;
        this.bankClient = bankClient;

        bankAppClientsList.add(bankClient);

        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        keyboardInput = new BufferedReader(new InputStreamReader(System.in));

        out.println(bankClient.getName() + " " + bankClient.getSubName());
    }

    //Client Handler menu function
    private BankAppClient sendToList() throws IOException {
        out.println("Select the client to send the money to");
        out.println("-------------LIST---------------------");
        for (int i = 0; i < bankAppClientsList.size(); i++) {
            out.println("Client nr." + i + ", " + bankAppClientsList.get(i).getSubName() + ", with balance of: " + bankAppClientsList.get(i).getBalance() + "$");
        }
        out.println("Type the client nr: ");
        int index = Integer.parseInt(in.readLine());

        return bankAppClientsList.get(index);
    }

    private void outToAll(String substring) {
        for (ClientHandler aClient : clients) {
            aClient.out.println(substring);
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                String request = in.readLine().toLowerCase();
                if (request.contains("deposit")) {
                    out.println("Please enter the value to deposit: ");
                    double value = Double.parseDouble(in.readLine());
                    bankClient.deposit(value);
                    out.println(bankClient.getName() + " " + bankClient.getSubName() + ": balance is  " + bankClient.getBalance());
                } else if (request.contains("withdraw")) {
                    out.println("Please enter the value to withdraw: ");
                    double value = Double.parseDouble(in.readLine());
                    bankClient.withdraw(value);
                    out.println(bankClient.getName() + " " + bankClient.getSubName() + ": balance is  " + bankClient.getBalance());
                } else if (request.contains("send")) {
                    sendToClient = sendToList();
                    out.println("Please enter the value to send ");
                    double value = Double.parseDouble(in.readLine());
                    bankClient.send(sendToClient, value);
                    out.println("You have send " + value + "$ to " + sendToClient.getName());
                    out.println("[RESULT] " + bankClient.getName() + " balance: " + (bankClient.getBalance() + value) + " - " + value + " = " + bankClient.getBalance() + ", " + sendToClient.getName() + " balance: " + (sendToClient.getBalance() - value) + " + " + value + " = " + sendToClient.getBalance());
                } else if (request.contains("see info")) {
                    out.println(bankClient.getInfo());

                } else if (request.contains("say")) {
                    request = request.replace("say ", "");
                    outToAll(request);
                } else {
                    out.println("Please write a correct request...");
                }
            }
        } catch (IOException e) {
            System.err.println("\nIO exception in client handler");
            System.err.println(Arrays.toString(e.getStackTrace()));
        } finally {
            out.close();
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
