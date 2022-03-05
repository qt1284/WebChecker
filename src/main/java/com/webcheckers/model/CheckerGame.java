package com.webcheckers.model;

import com.webcheckers.model.Piece.Color;
import com.webcheckers.ui.BoardView;
import com.webcheckers.ui.GetSignInRoute;
import com.webcheckers.util.Message;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * The class for the current checker game being played
 */
public class CheckerGame {
    
    public final Player redPlayer;
    private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());
    public final Player whitePlayer;
    private final BoardView board;
    private final List<Move> moveList;
    private Color activeColor;
    private int timeCount;
//    private boolean gameOver;
//    private Player winner;

    /**
     * Creates a checker game with two players and initializes a boardView
     *
     * @param redPlayer
     *    The host, or red player
     *
     * @param whitePlayer
     *    The opponent, or white player
     */
    public CheckerGame (Player redPlayer, Player whitePlayer) {
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        this.board = new BoardView();
        this.activeColor = Color.RED;
        moveList = new LinkedList<>();
        timeCount = 0;
//        this.gameOver = false;
//        this.winner = null;
    }
    public CheckerGame(Player redPlayer, Player whitePlayer, int i ){
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        this.board = new BoardView(i);
        this.activeColor = Color.RED;
        moveList = new LinkedList<>();
        timeCount = 0;
    }
    /**
     * gets the list of moves
     * @return a list of moves
     */
    public List<Move> getMoveList() {
        return moveList;
    }

    public void addTimeCount(int timeCount) {
        this.timeCount += timeCount;
    }

    public int getTimeCount() {
        return timeCount;
    }

    public void setTimeCount(int timeCount) {
        this.timeCount = timeCount;
    }

    public String printTime(){
        if (timeCount < 60){
            return "Last turn was " + timeCount + " second(s) ago";
        } else if (timeCount < 3600){
            int minutes = timeCount/60;
            return "Last turn was " + minutes + " minute(s) and " + (timeCount%60) + " second(s) ago";
        } else {
            int hours = timeCount/3600;
            int minutes = timeCount/60;
            return "Last turn was " + hours + " hour(s) and " + minutes + " minute(s) and " + (timeCount%60) + " second(s) ago";
        }
    }
    /**
     * adds a move to the move list
     * @param move the move being added
     */
    public void addMove(Move move){
        moveList.add(move);
    }



    /**
     * applies the move done to the board
     * @return true if the move has been applied, false otherwise
     */

    public boolean applyMove(){

        while (!moveList.isEmpty()) {
            //getting the last move
            Position start = moveList.get(0).getStart();
            Piece.Type startPieceType = board.getBoard().getSpace(start).getPiece().getType();
            Move check = moveList.get(moveList.size()-1);
            //check
            if (board.getBoard().isAnotherJump(check,activeColor,startPieceType) && !board.getBoard().singleMove(check,activeColor))
                return false;
            Move move = moveList.remove(0);
            board.updateBoard(move,this.activeColor);
        }

        if (this.activeColor == Color.RED) {
            this.activeColor = Color.WHITE;
        }else {
            this.activeColor = Color.RED;
        }
        board.getBoard().setAlreadySingleMove(false);
        board.getBoard().setAlreadyJumpMove(false);
        return true;


    }

    /**
     * Return the current board
     * @return the board
     */
    public BoardView getBoardView(){
        return this.board;
    }
    /**
     * getter method for the active color in the game
     * @return the active color
     */
    public Color getActiveColor() {
        return activeColor;
    }


    /**
     * getter method for active player
     * @return the active player of the game (whose turn it is)
     */

    public Player getActivePlayer(){
        if (activeColor.equals(Color.RED)){
            return redPlayer;
        }
        return whitePlayer;
    }
    /**
     * Gets the red Player
     *
     * @return
     *     red Player
     */
    public Player getRedPlayer(){
        return redPlayer;
    }

    /**
     * Gets the boardView
     *
     * @return
     *    the boardView
     */
    public BoardView getBoard(){
        return new BoardView(board,false);
    }

    /**
     * Returns a copy of the board that is flipped
     *
     * @return
     *    The flipped board
     */
    public BoardView getFlippedBoard(){
        return new BoardView(board,true);
    }

    /**
     * Gets the white Player
     *
     * @return
     *    The white Player
     */
    public Player getWhitePlayer(){
        return whitePlayer;
    }

    /**
     * checks whether the move performed is valid and produces a message
     * @param move the move being performed
     * @return a message description of whether the move is valid
     */
    public Message isValidMove(Move move ){
        BoardView copy = new BoardView(board,false);
        //check validate in BoardView
        Message message;
        Move inverseMove = move.getInverse();

        //Updating board for multiple jump moves
        for (Move getMove : moveList) {
            copy.updateBoard(getMove, this.activeColor);
        }

        if (this.activeColor == Color.RED)
            message = copy.validate(move,this.activeColor);
        else{
            message = copy.validate(inverseMove,this.activeColor);
        }

        //If move is valid, add to moveList (i.e. list of move)
        if (message.getType().equals(Message.Type.INFO)){
            if (this.activeColor == Color.RED)
                moveList.add(move);
            else
                moveList.add(inverseMove);
        }
        return message;
    }

    /**
     * removes the move
     */
    public void removeMove(){
        if (!moveList.isEmpty()){
            moveList.remove(moveList.size()-1);
        }
    }
    /**
     * print out which players are playing
     *
     * @return the printing string
     */
    @Override
    public String toString(){
        return this.redPlayer.getName() + "_challenges_" + this.whitePlayer.getName();
    }
    /**
     * checks whether the checkergame is equal to another object
     * @param o the other object
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CheckerGame game = (CheckerGame) o;
        return redPlayer.equals(game.getRedPlayer()) && whitePlayer.equals(game.getWhitePlayer());
    }

    /**
     * gets the hashcode of the checkergame
     * @return the hashcode of the red and white player
     */
    @Override
    public int hashCode() {
        return Objects.hash(redPlayer, whitePlayer);
    }

}
