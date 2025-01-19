package server;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface gameInterface extends Remote {
    String sayGameStart() throws RemoteException;
    String joinGame(String name) throws RemoteException;
    String makeMove(int x, int y,String name) throws RemoteException;
    String getBoard() throws RemoteException;
    String getWinner() throws RemoteException;
    String getStatus() throws RemoteException;
    
}
