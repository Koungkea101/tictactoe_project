package server;

import java.rmi.RemoteException;

public class gameServer {
    private char[][] board= new char[3][3];
    private String status="Waiting for players to join in..............";
    private String winner="No Winner Yet";
    private int numPlayer=0;
    private boolean player0Turn=true;
    private String player0=null,player1=null;

    public gameServer() throws Exception{
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                board[i][j]='-';
            }
        }
    }
    
    // @Override
    public synchronized String joinGame(String name) throws RemoteException{
        if(numPlayer==0){
            player0=name;
            player1=name;
            numPlayer++;
            status="Waiting for another player to join in..............";
            return "Joined as Player 1";
        }
        else if(numPlayer==1){
            player0=name;
            numPlayer++;
            status="Player 1's turn";
            return "Joined as Player 2";
        }
        else{
            return "Game is full";
        }
    }
    private boolean checkWinner(){
        for(int i=0;i<3;i++){
            if(board[i][0]==board[i][1] && board[i][1]==board[i][2] && board[i][0]!='-'){
                return true;
            }
            if(board[0][i]==board[1][i] && board[1][i]==board[2][i] && board[0][i]!='-'){
                return true;
            }
        }
        return (board[0][0]==board[1][1] && board[1][1]==board[2][2] && board[0][0]!='-') 
                || (board[0][2]==board[1][1] && board[1][1]==board[2][0] && board[0][2]!='-');
    };
    private boolean fullBoard(){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(board[i][j]=='-'){
                    return false;
                }
            }
        }
        return true;
    }
    public synchronized String getBoard() throws RemoteException{
        StringBuilder sb = new StringBuilder();
        for (char[] row : board) {
            for (char cell : row) {
                sb.append(cell).append(' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public String getStatus() throws RemoteException{
        return status;
    }

    // making move
    // @Override
    public synchronized String makeMove(int x, int y, String name) throws RemoteException{
        //check for player
        if(player0==null||player1==null){
            return "No player has joined yet. Waiting for player to join.......";
        }
        //check for turn
        if((player0Turn && !name.equals(player0)) || (!player0Turn && !name.equals(player1))){
            return "It's not your turn";
        }
        //check for valid move
        if(x<0 || x>2 || y<0 || y>2 || board[x][y]!='-'){
            return "Invalid Move";
        }

        //make move
        board[x][y]=player0Turn?'X':'O';
        player0Turn=!player0Turn;
        if(checkWinner()){
            winner=player0Turn?player0:player1;
            status=winner+" wins. Game Over!!!";
            return status;
        }
        else{
            status=player0Turn?"Player 1's turn":"Player 2's turn";
        }

        if(fullBoard()){
            status="Game Over!!! It's a Draw";
            return status;
        }

        status = player0Turn?"Player 1's turn":"Player 2's turn";
        return  "Move is accepted";
    }

}
