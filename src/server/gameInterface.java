package server;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface gameInterface extends Remote {
    String sayGameStart() throws RemoteException;
    String joinGame(String name) throws RemoteException;
    String makeMove(String name, int x, int y) throws RemoteException;
    String getBoard() throws RemoteException;
    String getWinner() throws RemoteException;
    String getStatus() throws RemoteException;
    
}
