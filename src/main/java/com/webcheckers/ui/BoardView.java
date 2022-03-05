package com.webcheckers.ui;

import com.webcheckers.model.*;
import com.webcheckers.util.Message;

import java.util.Iterator;
import java.util.Arrays;

/**
 * object class for a boardView in a webcheckers game
 */
public class BoardView implements Iterable<Row> {

    private final Row[] rows;
    private final Board board;
    public static final String ERROR_AVAILABLE_PIECE ="You must choose an available piece.";
    public static final String ERROR_OCCUPIED_SPACE = "You can't move to an occupied space.";
    public static final String ERROR_INVALID_MOVE = "That's not a valid move.";
    public static final String ERROR_POSSIBLE_JUMP = "There is a possible jump, you have to make that jump";
    public static final String ERROR_UNABLE_TO_MOVE = "You can't move here";
    public static final String SINGLE_JUMP ="You make a valid single jump move.";


    /**
     * initializer method for a BoardView
     */
    public BoardView(){
        rows = new Row[8];
        //getBoard(Piece.Color.RED,Piece.Color.WHITE);
        board = new Board();
        for (int i = 0; i < 8; i++){
            rows[i] = new Row(board.getRow(i),i);
        }
    }
    public BoardView(int i ){
        rows = new Row[8];
        //getBoard(Piece.Color.RED,Piece.Color.WHITE);
        board = new Board(i);
        for (int t = 0; t < 8; t++){
            rows[t] = new Row(board.getRow(t),t);
        }
    }


    public Board getBoard() {
        return board;
    }

    public BoardView (BoardView boardView, boolean flip){
        this.board = new Board(boardView.board);
        if (!flip){
            this.rows = boardView.getRows().clone();
        } else {
            int rowFlip;
            int colFlip;
            this.rows = new Row[8];
            Row[] copyRow = boardView.rows.clone();
            for(int row = 0; row < 8; row++){
                rowFlip = 7-row;
                Space[] oldSpaces = copyRow[row].getSpaces().clone();
                Space[] flipSpace = new Space[8];
                for(int col = 0; col < 8; col++) {
                    colFlip = 7 - col;
                    flipSpace[colFlip] = new Space(oldSpaces[col]);
                    flipSpace[colFlip].setCellIdx(colFlip);
                }
                rows[rowFlip] = new Row(flipSpace,rowFlip);
            }
        }
    }
    /**
     * getter method for number of rows
     * @return the number of rows
     */
    public Row[] getRows(){
        return rows;
    }

    /**
     * determines if the move made is a valid move
     * @param move the move that was made
     * @param color the color of the piece being moved
     * @return a Message that indicates whether the move is valid or not
     */
    public Message validate(Move move, Piece.Color color){
        Position start = move.getStart();
        Position end = move.getEnd();
        Space startSpace = board.getSpace(start);
        Space endSpace = board.getSpace(end);
        Piece startPiece = startSpace.getPiece();
        Piece endPiece = endSpace.getPiece();
        if (startPiece == null){
            return Message.error(ERROR_AVAILABLE_PIECE);
        }
        if (endPiece != null){
            return Message.error(ERROR_OCCUPIED_SPACE);
        }
        if (board.isAlreadyJumpMove()){
            return Message.error(ERROR_INVALID_MOVE);
        }
        if (board.isSingleJumpPossible(color)){

            if (board.singleJumpMove(move, color)) {
                board.setAlreadyJumpMove(false);
                return Message.info(SINGLE_JUMP);
            } else {
                return Message.error(ERROR_POSSIBLE_JUMP);
            }

        } else {
            if (board.isAlreadySingleMove()){
                return Message.error("You just made a single move. You can't move after that!");
            }
            if (board.singleMove(move,color)){
                board.setAlreadySingleMove(false);
                return Message.info("You make a valid single move.");
            } else {
                return Message.error(ERROR_UNABLE_TO_MOVE);
            }
        }

    }

    /**
     * Ending the game if only have one piece left
     * @return true if game ends, false otherwise
     */
    public boolean endGame(Piece.Color playerColor){
        return board.endGame(playerColor);
    }

    /**
     * updates the board
     * @param move the move being performed
     * @param color the color of the piece
     */
    public void updateBoard(Move move, Piece.Color color){
        board.updateBoard(move, color);

    }


    /**
     * creates a java Iterator of the rows within a checkers board
     *
     * @return
     *    An Iterator of rows
     */
    @Override
    public Iterator<Row> iterator() {
        return Arrays.asList(rows).iterator();
    }
}
