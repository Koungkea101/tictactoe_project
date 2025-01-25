package client;

import java.rmi.Naming;
import java.util.Scanner;
import server.gameInterface;

public class clientsideMain {
    @SuppressWarnings({"UseSpecificCatch", "CallToPrintStackTrace"})
    public static void main(String[] args) {
        System.out.println("Hello from clientsideMain");
        try {
            gameInterface newgame = (gameInterface) Naming.lookup("rmi://localhost/TicTacToe");

            //input player name
            String playername="";
            Scanner s = new Scanner(System.in);
            
            System.out.println("Enter your name: ");
            playername=s.nextLine();
            //joining game
            String respone=newgame.joinGame(playername);
            System.out.println(respone);

            //check if the player has joined or not
            if(respone.startsWith("Joined")){
                while (true) { 
                    // Wait for the game to start (both players to join)
                    String status = newgame.getStatus();
                    if (status.contains("Game started!")) {
                        System.out.println(status);
                        break; 
                    } else {
                        System.out.println("Waiting for other player to join...");
                        Thread.sleep(5000); 
                    }
                }
                String player1 = newgame.getPlayer1();
                String player2 = newgame.getPlayer2();
                while (true) { 
                    System.out.println("/nBoard:");
                    System.out.println(newgame.getBoard());
                    System.out.println(newgame.getStatus());

                    //check if the game is over by checking with status return in code line 110 in gameserver
                    if(!newgame.getStatus().endsWith("turn.")){
                        break;
                    }
                    //check if it is the player's turn
                    if((newgame.getStatus().contains("Player 1's turn") && playername.equals(player1)) ||
                        (newgame.getStatus().contains("Player 2's turn") && playername.equals(player2)) || 
                        newgame.getStatus().contains("Game started") && newgame.getStatus().contains(playername)){
                        System.out.println("Enter the x and y coordinates of your move: ");
                        int x=s.nextInt();
                        int y=s.nextInt();
                        String status = newgame.makeMove(x, y,playername);
                        System.out.println(status);
                    }else{
                        System.out.println("Waiting for other player to make a move.....");
                        Thread.sleep(4000);
                    }
                }
                s.close();
            }else{
                System.out.println("Unable to join the game!!!");
            }
        } catch (Exception e) {
            System.out.println("Failed to connect to server");
            e.printStackTrace();
        }
    }
}
