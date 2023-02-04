import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;
//import java.util.*;
public class BoardDemo {
    static int pruneCount;
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String playAgain = null;
        GameState gameState = GameState.IN_PROGRESS;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        int depth = 0;
        while(gameState==GameState.IN_PROGRESS || Objects.equals(playAgain, "y")) {
            System.out.print("Run part A, B, or C? ");
            String algChoice = s.nextLine();

            System.out.print("Include debugging info? (y/n) ");
            String debugInfo = s.nextLine();

            System.out.print("Enter Rows: ");
            int numRows = Integer.parseInt(s.nextLine());

            System.out.print("Enter Columns: ");
            int numCols = Integer.parseInt(s.nextLine());

            System.out.print("Enter number in a row to win: ");
            int numToWin = Integer.parseInt(s.nextLine());

            if(algChoice.equals("C")){
                System.out.print("Number of  moves to look ahead (depth): ");
                depth = Integer.parseInt(s.nextLine());
            }


            Board board = new Board(numRows, numCols, numToWin); // standard connect 4 size
            HashMap<Board, MinimaxInfo> table = new HashMap<>();
            MinimaxInfo mxSearch;
            if (algChoice.equals("A")){
                mxSearch = miniMaxSearch(board, table);
            }
            else if(algChoice.equals("B")){
                mxSearch = alphaBetaSearch(board, alpha, beta, table);
            }
            else{
                mxSearch = null;
            }

            if(!algChoice.equals("C")){
                System.out.println("Transposition table has " + table.size() + " states.");

                if (algChoice.equals("B")) {
                    System.out.println("The tree was pruned " + pruneCount + " times");
                }
                assert mxSearch != null;
                if (mxSearch.getMiniMaxValue() > 0) {
                    System.out.println("First person/computer to play has a guaranteed win with perfect play");
                } else if (mxSearch.getMiniMaxValue() < 0) {
                    System.out.println("Second person/computer to play has a guaranteed win with perfect play");
                } else {
                    System.out.println("Neither player has a guaranteed win; game will end in tie with perfect play on both sides");
                }
            }
            if (debugInfo.equals("y")) {
                System.out.println(table);
            }

            System.out.print("who plays first? 1=human, 2=computer: ");
            int firstPtoGo = Integer.parseInt(s.nextLine());

            System.out.println(board.to2DString());
            if(!algChoice.equals("C")) {
                    System.out.println("Minimax value for this state: " + table.get(board).getMiniMaxValue() + ", optimal move: " + table.get(board).getBestAction());
            }
            mxSearch = alphaBetaHeuristics(board,alpha,beta,depth,table);
            int move;
            while (gameState == GameState.IN_PROGRESS) {
                if(algChoice.equals("C")){
                    System.out.println("Transposition table has " + table.size() + " states.");
                    System.out.println("Minimax value for this state: " + table.get(board).getMiniMaxValue() + ", optimal move: " + table.get(board).getBestAction());
                }
                System.out.println("It is MAX's turn!");
                if (getMaxPlayer(firstPtoGo).equals("computer")) {
                    move = table.get(board).getBestAction();//bestAction
                    System.out.println(getMaxPlayer(firstPtoGo) + " chooses move: " + move);
                } else {
                    System.out.print(getMaxPlayer(firstPtoGo) + " choose move: ");
                    move = Integer.parseInt(s.nextLine());
                }

                if(algChoice.equals("C")){
                    mxSearch = alphaBetaHeuristics(board,alpha,beta,depth,table);
                }
                if (!board.isColumnFull(move)) {
                    board = board.makeMove(move);
                    gameState = board.getGameState();
                    if (gameState == GameState.MAX_WIN) {
                        System.out.println("Game Over. " + board.getWinner()  + " (" + getMaxPlayer(firstPtoGo) + ") wins!");
                        System.out.println(board.to2DString());
                        System.out.print("Play again? (y/n): ");
                        playAgain = s.nextLine();
                        break;
                    } else if (gameState == GameState.TIE) {
                        System.out.println("Game over. Tie");
                        System.out.println(board.to2DString());
                        System.out.print("Play again? (y/n): ");
                        playAgain = s.nextLine();
                        break;
                    }
                    System.out.println(board.to2DString());
                    if(!table.containsKey(board) && (algChoice.equals("B"))){
                        table.clear();
                        alphaBetaSearch(board,alpha,beta,table);
                        System.out.println("This is a state that was previously pruned; re-running alpha beta from here.");
                    }
                    if(!algChoice.equals("C")) {
                        System.out.println("Minimax value for this state: " + table.get(board).getMiniMaxValue()
                                + ", optimal move: " + table.get(board).getBestAction());
                    }
                    else{
                        //System.out.println(board.to2DString());
                        System.out.println("Transposition table has " + table.size() + " states.");
                        System.out.println("Minimax value for this state: " + table.get(board).getMiniMaxValue() + ", optimal move: " + table.get(board).getBestAction());
                    }
                }

                System.out.println("It is MIN's turn!");
                if (getMinPlayer(firstPtoGo).equals("computer")) {
                    move = table.get(board).getBestAction();//bestAction
                    System.out.println(getMaxPlayer(firstPtoGo) + " chooses move: " + move);

                } else {
                    System.out.print(getMinPlayer(firstPtoGo) + " choose move: ");
                    move = Integer.parseInt(s.nextLine());
                }
                if(algChoice.equals("C")){
                    mxSearch = alphaBetaHeuristics(board,alpha,beta,depth,table);
                    System.out.println(board.to2DString());
                }
                if (!board.isColumnFull(move)) {
                    board = board.makeMove(move);
                    gameState = board.getGameState();
                    if (gameState == GameState.MIN_WIN) {
                        System.out.println("Game Over. " + board.getWinner()  + " (" + getMinPlayer(firstPtoGo) + ") wins!");
                        System.out.println(board.to2DString());
                        System.out.print("Play again? (y/n): ");
                        playAgain = s.nextLine();
                        break;
                    } else if (gameState == GameState.TIE) {
                        System.out.println("Game over. Tie");
                        System.out.println(board.to2DString());
                        System.out.print("Play again? (y/n): ");
                        playAgain = s.nextLine();
                        break;
                    }
                    System.out.println(board.to2DString());
                    if(!table.containsKey(board) && (algChoice.equals("B"))){
                        table.clear();
                        alphaBetaSearch(board,alpha,beta,table);
                        System.out.println("This is a state that was previously pruned; re-running alpha beta from here.");
                    }
                    if(!algChoice.equals("C")) {
                        System.out.println("Minimax value for this state: " + table.get(board).getMiniMaxValue()
                                + ", optimal move: " + table.get(board).getBestAction());
                    }

                }
            }
        }
    }
    public static String getMaxPlayer(int firstToGo){
        if(firstToGo==1){
            return "you";
        }
        else{
            return "computer";
        }
    }
    public static String getMinPlayer(int firstToGo){
                if(firstToGo==1){
                    return "computer";
                }
                else{
                    return "you";
        }
    }

    public static MinimaxInfo miniMaxSearch(Board board, HashMap<Board,MinimaxInfo>table){
        if(table.containsKey(board)){
            return table.get(board);
        }
        else if(board.getGameState()!=GameState.IN_PROGRESS){// is terminal state
            int util = utility(board);
            MinimaxInfo info = new MinimaxInfo(util, -1);
            table.put(board, info);
            return info;
        }
        else if(board.getPlayerToMoveNext()==Player.MAX){
            int v = Integer.MIN_VALUE;
            int bestMove = -1;
            for(int action: actions(board)){
                Board childBoard = board.makeMove(action);//RESULT
                MinimaxInfo childInfo = miniMaxSearch(childBoard,table);
                int v2 = childInfo.getMiniMaxValue();
                if(v2 > v){
                    v = v2;
                    bestMove = action;
                }
            }
            MinimaxInfo info = new MinimaxInfo(v,bestMove);
            table.put(board,info);
            return info;
        }
        else{//TO-MOVE(board)==MIN
            int v = Integer.MAX_VALUE;
            int bestMove = -1;
            for(int action: actions(board)){
                Board childBoard = board.makeMove(action);//RESULT
                MinimaxInfo childInfo = miniMaxSearch(childBoard,table);
                assert childInfo != null;
                int v2 = childInfo.getMiniMaxValue();
                if(v2 < v){
                    v = v2;
                    bestMove = action;
                }
            }
            MinimaxInfo info = new MinimaxInfo(v,bestMove);
            table.put(board,info);
            return info;
        }
    }

    public static MinimaxInfo alphaBetaSearch(Board board, int alpha, int beta, HashMap<Board, MinimaxInfo> table){
        if(table.containsKey(board)){
            return table.get(board);
        }
        else if(board.getGameState()!=GameState.IN_PROGRESS){// is terminal state
            int util = utility(board);
            MinimaxInfo info = new MinimaxInfo(util, -1);
            table.put(board, info);
            return info;
        }
        else if(board.getPlayerToMoveNext()==Player.MAX){
            int v = Integer.MIN_VALUE;
            int bestMove = -1;
            for(int action: actions(board)){
                Board childBoard = board.makeMove(action);//RESULT
                MinimaxInfo childInfo = alphaBetaSearch(childBoard,alpha,beta,table);
                int v2 = childInfo.getMiniMaxValue();
                if(v2 > v){
                    v = v2;
                    bestMove = action;
                    alpha = Math.max(alpha,v);
                }
                if(v>=beta){ //prune tree babb
                    pruneCount +=1;
                    return new MinimaxInfo(v,bestMove);
                }
            }
            MinimaxInfo info = new MinimaxInfo(v,bestMove);
            table.put(board,info);
            return info;
        }
        else{//TO-MOVE(board)==MIN
            int v = Integer.MAX_VALUE;
            int bestMove = -1;
            for(int action: actions(board)){
                Board childBoard = board.makeMove(action);//RESULT
                MinimaxInfo childInfo = alphaBetaSearch(childBoard,alpha,beta,table);
                int v2 = childInfo.getMiniMaxValue();
                if(v2 < v){
                    v = v2;
                    bestMove = action;
                    beta = Math.min(beta,v);
                }
                if(v<=alpha){//prune baby
                    pruneCount +=1;
                    return new MinimaxInfo(v,bestMove);
                }
            }
            MinimaxInfo info = new MinimaxInfo(v,bestMove);
            table.put(board,info);
            return info;
        }
    }
    public static MinimaxInfo alphaBetaHeuristics(Board board, int alpha, int beta, int depth, HashMap<Board,MinimaxInfo>table){
        if(table.containsKey(board)){
            return table.get(board);
        }
        else if(board.getGameState()!=GameState.IN_PROGRESS){// is terminal state
            int util = utility(board);
            MinimaxInfo info = new MinimaxInfo(util, -1);
            table.put(board, info);
            return info;
        }
        else if(isCutOff(board,depth)){
            int heuristic = board.eval(board);
            MinimaxInfo info = new MinimaxInfo(heuristic,-1);
            table.put(board,info);
            return info;
        }
        else if(board.getPlayerToMoveNext()==Player.MAX){
            int v = Integer.MIN_VALUE;
            int bestMove = -1;
            for(int action: actions(board)){
                Board childBoard = board.makeMove(action);//RESULT
                MinimaxInfo childInfo = alphaBetaHeuristics(childBoard,alpha,beta,depth++,table);
                int v2 = childInfo.getMiniMaxValue();
                if(v2 > v){
                    v = v2;
                    bestMove = action;
                    alpha = Math.max(alpha,v);
                }
                if(v>=beta){ //prune tree babb
                    pruneCount +=1;
                    return new MinimaxInfo(v,bestMove);
                }
            }
            MinimaxInfo info = new MinimaxInfo(v,bestMove);
            table.put(board,info);
            return info;
        }
        else{//TO-MOVE(board)==MIN
            int v = Integer.MAX_VALUE;
            int bestMove = -1;
            for(int action: actions(board)){
                Board childBoard = board.makeMove(action);//RESULT
                MinimaxInfo childInfo = alphaBetaHeuristics(childBoard,alpha,beta,depth++,table);
                int v2 = childInfo.getMiniMaxValue();
                if(v2 < v){
                    v = v2;
                    bestMove = action;
                    beta = Math.min(beta,v);
                }
                if(v<=alpha){//prune baby
                    pruneCount +=1;
                    return new MinimaxInfo(v,bestMove);
                }
            }
            MinimaxInfo info = new MinimaxInfo(v,bestMove);
            table.put(board,info);
            return info;
        }
    }

    public static int utility(Board board){
        int rows = board.getRows();
        int cols = board.getCols();
        int moves = board.getNumberOfMoves();
        int divBy = 10000;
        if(board.getGameState()==GameState.MAX_WIN){
            return (divBy * rows * cols)/moves;
        }
        else if(board.getGameState()==GameState.MIN_WIN){
            return -(divBy * rows * cols)/moves;
        }
        else{
            return 0;
        }
    }
    public static ArrayList<Integer> actions(Board board){
        ArrayList<Integer> acts = new ArrayList<>();
        for(int i = 0; i< board.getCols();i++){
            if(!board.isColumnFull(i)){
                acts.add(i);
            }
        }
        return acts;
    }

    public static boolean isCutOff(Board board, int depth){
        return board.getNumberOfMoves() == depth;
    }

}
