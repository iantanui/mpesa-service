import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MpesaClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                System.out.println("Select an option:");
                System.out.println("1: Check balance");
                System.out.println("2: Send money");
                System.out.println("Enter your choice: ");
                String choice = userInput.readLine();

                if ("1".equals(choice)) {
                    out.println("1");
                } else if ("2".equals(choice)) {
                    System.out.println("Enter amount to send: ");
                    String amount = userInput.readLine();
                    out.println("2:" + amount);
                } else {
                    System.out.println("Invalid choice. Please try again.");
                    continue;
                }

                String response = in.readLine();
                System.out.println("Response from server: " + response);
            }
        } catch (IOException e) {
            System.err.println("Error communicating with server: " + e.getMessage());
        }
    }
    
}
