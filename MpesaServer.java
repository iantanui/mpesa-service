import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MpesaServer {
    private static final int PORT = 12345;
    private static double balance = 1000.00;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("M-Pesa server started. Waiting for clients...");

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                    System.out.println("Client connected.");
                    String request = in.readLine();
                    System.out.println("Received request: " + request);

                    String[] requestParts = request.split(":");
                    String action = requestParts[0];

                    if ("1".equals(action)) { // Check balance
                        out.println("Your balance is: KES " + balance);
                    } else if ("2".equals(action)) { // Send money
                        double amount = Double.parseDouble(requestParts[1]);
                        if (amount <= balance) {
                            balance -= amount;
                            out.println("Transaction successful. Your new balance is: KES " + balance);
                        } else {
                            out.println("Transaction failed. Insufficient funds.");
                        }
                    } else {
                        out.println("Invalid request.");
                    }
                } catch (IOException e) {
                    System.err.println("Error handling client: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Could not start server: " + e.getMessage());
        }
    }

}