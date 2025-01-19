package server;

import java.rmi.Naming;
import java.rmi.Remote;


public class serversideMain {
    @SuppressWarnings("CallToPrintStackTrace")
    public static void main(String[] args) {
        try {
            gameServer server = new gameServer();
            Naming.rebind("Tic-Tac-Toe", (Remote) server);
            System.out.println("Tic-Tac-Toe game server is running............");
        } catch (Exception e) {
            System.out.println("Server failed to start");
            e.printStackTrace();
        }
    }
}
