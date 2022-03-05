package com.webcheckers.model;


import com.webcheckers.ui.GetSignInRoute;
import com.webcheckers.ui.Row;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Logger;

import static com.webcheckers.model.Piece.Type.KING;
import static com.webcheckers.model.Piece.Type.SINGLE;

public class Board {
    private Space[][] spaces;
    private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());
    private boolean alreadySingleMove;
    private boolean alreadyJumpMove;


    /**
     * checks if move is already a jump move
     * @return true if already a jump move, false otherwise
     */
    public boolean isAlreadyJumpMove() {
        return alreadyJumpMove;
    }

    /**
     * checks if move is already a single move
     * @return true if already a single move, false otherwise
     */
    public boolean isAlreadySingleMove() {
        return alreadySingleMove;
    }

    public Board(int i ){
        initializeBoard("src/main/java/com/webcheckers/model/BoardTest/board"+i+".txt");
    }
    /**
     * manipulates board so that pieces are initialized in certain positions making it easier to test
     */
    public Board(){
        initializeBoard("src/main/java/com/webcheckers/model/BoardTest/board1.txt");

    }

    /**
     * initializes the board so that positions of pieces are manipulated based on a text file
     * @param fileName the name of the text file
     */
    public void initializeBoard(String fileName){
        try {
            Scanner sc = new Scanner(new File(fileName));
            Piece piece;
            boolean black = false;//white
            alreadySingleMove = false;
            String space;
            alreadyJumpMove = false;
            spaces = new Space[8][8];
            int row = 0;
            while(sc.hasNextLine()) {
                space = sc.nextLine();
                for (int col = 0; col < 8; col++) {
                    char pieceChar = space.charAt(col);
                    if (pieceChar == 'W'){
                        piece = new Piece(SINGLE, Piece.Color.WHITE);
                        spaces[row][col] = new Space(col, piece, Space.Space_color.DARK);
                    } else if (pieceChar == 'R'){
                        piece = new Piece(SINGLE, Piece.Color.RED);
                        spaces[row][col] = new Space(col, piece, Space.Space_color.DARK);
                    } else if (!black) {
                        spaces[row][col] = new Space(col, null, Space.Space_color.LIGHT);
                    } else {
                        spaces[row][col] = new Space(col, null, Space.Space_color.DARK);
                    }
                    black = !black;


                }
                black = !black;
                row++;
            }
        } catch (FileNotFoundException e){
            System.out.println("file not found");
        }
    }

    /**
     * copies the already created board
     * @param copy the board being copied
     */
    public Board(Board copy){
        this.alreadySingleMove = copy.alreadySingleMove;
        this.alreadyJumpMove = copy.alreadyJumpMove;
        this.spaces = new Space[8][8];
        for (int i = 0; i < 8; i++){
            System.arraycopy(copy.spaces[i], 0, this.spaces[i], 0, 8);
        }
    }
    /**
     * setter method for alreadySingleMove
     * @param alreadySingleMove true if alreadySingleMove is a already a single move, false otherwise
     */
    public void setAlreadySingleMove(boolean alreadySingleMove) {
        this.alreadySingleMove = alreadySingleMove;
    }

    /**
     * a setter for boolean alreadyJumpMove
     * @param alreadyJumpMove the passed in boolean
     */
    public void setAlreadyJumpMove(boolean alreadyJumpMove){this.alreadyJumpMove = alreadyJumpMove;}


    /**
     * gets the space of a particular position
     * @param position the position to get the space from
     * @return the space of the specified position
     */
    public Space getSpace(Position position){
        return spaces[position.getRow()][position.getCell()];
    }

    /**
     * Getting a middle piece's position base on a jump move
     * @param move the passed in move
     * @return the middle position
     */
    public Position getMiddle(Move move){
        int rowStart = move.getStart().getRow();
        int cellStart = move.getStart().getCell();
        int rowEnd = move.getEnd().getRow();
        int cellEnd = move.getEnd().getCell();
        int rowMiddle = (rowStart + rowEnd)/2;
        int cellMiddle = (cellStart + cellEnd)/2;
        return new Position(rowMiddle,cellMiddle);
    }


    public boolean checkKingJump(Move move, Piece.Color color){
        Position middle = getMiddle(move);
        Piece checkPiece = getSpace(middle).getPiece();
        return checkPiece!=null && !checkPiece.getColor().equals(color);

    }
    public boolean checkSingleJumpColor(Move move, Piece.Color color){
        int startRow = move.getStart().getRow();
        int startCell = move.getStart().getCell();
        int endRow = move.getEnd().getRow();
        int endCell = move.getEnd().getCell();
        if (color == Piece.Color.RED) {
            //go right
            if (startRow - endRow == 2 && endCell - startCell == 2) {
                Position checkRight = new Position(startRow-1, startCell+1);
                Space spaceRight = getSpace(checkRight);
                Piece checkPiece = spaceRight.getPiece();
                return checkPiece != null && !checkPiece.getColor().equals(color);
            } else if (startRow - endRow == 2 && endCell - startCell == -2) {
                Position checkLeft = new Position(startRow-1,startCell-1);
                Space spaceLeft = getSpace(checkLeft);
                Piece checkPiece = spaceLeft.getPiece();
                return checkPiece != null && !checkPiece.getColor().equals(color);
            }
        } else {
            //go right
            if (startRow - endRow == -2 && endCell - startCell == -2) {
                Position checkRight = new Position(startRow+1, startCell-1);
                Space spaceRight = getSpace(checkRight);
                Piece checkPiece = spaceRight.getPiece();
                return checkPiece != null && !checkPiece.getColor().equals(color);
            } else if (startRow - endRow == -2 && endCell - startCell == 2) {
                Position checkLeft = new Position(startRow+1,startCell+1);
                Space spaceLeft = getSpace(checkLeft);
                Piece checkPiece = spaceLeft.getPiece();
                return checkPiece != null && !checkPiece.getColor().equals(color);
            }
        }
        return false;
    }
    /**
     * check if a movement is a single jump move
     * @param move the passed in move
     * @param color the active color of the player
     * @return true if it's a valid jump, false otherwise
     */
    public boolean singleJumpMove(Move move, Piece.Color color){

        Position start = move.getStart();
        int startRow = move.getStart().getRow();
        int startCell = move.getStart().getCell();
        int endRow = move.getEnd().getRow();
        int endCell = move.getEnd().getCell();
        Piece piece = getSpace(start).getPiece();
        if (singleMove(move,color)) {
            return false;
        }

        if (piece.getType().equals(KING)){
            if (Math.abs(startRow - endRow)==2 && Math.abs(startCell-endCell) == 2){
                return checkKingJump(move,color);
            }
        } else {
            return checkSingleJumpColor(move, color);
        }


        return false;
    }

    /**
     * determines whether a move is a single move
     * @param move the move being performed
     * @param color the color of the piece
     * @return true if the move is a single move, false otherwise
     */
    public boolean singleMove(Move move, Piece.Color color){

        Position start = move.getStart();
        Position end = move.getEnd();
        int startRow = start.getRow();
        int startCell = start.getCell();
        int endRow = end.getRow();
        int endCell = end.getCell();
        Piece piece = getSpace(start).getPiece();
        if (piece != null){
            if (piece.getType().equals(SINGLE)){
                if (color == Piece.Color.RED)
                    return startRow-endRow == 1 && Math.abs(endCell - startCell) == 1;
                else
                    return startRow-endRow == -1 && Math.abs(endCell - startCell) == 1;
            } else {
                return Math.abs(startRow-endRow) == 1 && Math.abs(endCell - startCell) == 1;
            }
        }
        return false;
    }

    /**
     * determines whether a move may be another jump move
     * @param move the move being made
     * @param color the color of the piece
     * @return true if move is another jump move, false otherwise
     */
    public boolean isAnotherJump(Move move, Piece.Color color,Piece.Type startPieceType){
        Position end = move.getEnd();
        Space endSpace = getSpace(end);
        if (startPieceType.equals(SINGLE))
            endSpace.setPiece(new Piece(SINGLE, color));
        else {
            endSpace.setPiece(new Piece(KING, color));
            Position mid = getMiddle(move);
            Space middle = getSpace(mid);
            Piece middlePiece = middle.getPiece();
            middle.setPiece(null);
            if (isSingleJump(end, color)){
                middle.setPiece(middlePiece);
                endSpace.setPiece(null);
                return true;
            }
            middle.setPiece(middlePiece);
            endSpace.setPiece(null);
            return false;

        }
        if (isSingleJump(end, color)){
            endSpace.setPiece(null);
            return true;
        }
        endSpace.setPiece(null);
        return false;
    }

    /**
     * determines whether a single jump move is possible
     * @param playerColor the color of the piece
     * @return true if a single jump is possible, false otherwise
     */
    public boolean isSingleJumpPossible(Piece.Color playerColor){
        for(int row = 0; row < 8; row++){
            for (int col = 0; col < 8; col++){
                Position position = new Position(row,col);
                Space space = getSpace(position);
                if (space.getPiece() != null){
                    Piece.Color color = space.getPiece().getColor();
                    if (color.equals(playerColor)){
                        if (isSingleJump(position,playerColor)){
                            return true;
                        }
                    }

                }
            }
        }
        return false;
    }
    /**
     * Ending the game if only have one piece left
     * @return true if game ends, false otherwise
     */
    public boolean endGame(Piece.Color playerColor){
        int count = 0;
        for(int row = 0; row < 8; row++){
            for (int col = 0; col < 8; col++){
                Position position = new Position(row,col);
                Space space = getSpace(position);
                if (space.getPiece() != null){
                    Piece.Color color = space.getPiece().getColor();
                    if (color.equals(playerColor)){
                        count++;
                    }

                }
            }
        }
        return count == 0;
    }

    /**
     * Check if there could be a jump from a particular position
     * @param opp the position in between
     * @param emptyAfter the position after the opponent's piece
     * @param player the piece of current player
     * @return yes if we could jump
     */
    public boolean checkPieceJump(Position opp, Position emptyAfter, Piece player){
        if (!emptyAfter.checkOutOfRange()){
            Piece opponent = getSpace(opp).getPiece();
            Piece empty = getSpace(emptyAfter).getPiece();
            return ( opponent != null && empty == null && !opponent.getColor().equals(player.getColor()));
        } return false;

    }

    /**
     * Check any possible jump move for a King piece
     * @param start the starting position of the King piece
     * @param startPiece the piece of the King
     * @return true if there is any jump for a King piece, false otherwise
     */
    public boolean checkKingJump(Position start,Piece startPiece){
        int startRow = start.getRow();
        int startCell = start.getCell();
        Position checkUpLeft = new Position(startRow-1,startCell-1);
        Position checkUpEmptyLeft = new Position(startRow-2,startCell-2);
        Position checkUpRight = new Position(startRow-1, startCell+1);
        Position checkUpEmptyRight = new Position(startRow-2,startCell+2);
        Position checkDownRight = new Position(startRow+1,startCell+1);
        Position checkDownEmptyRight = new Position(startRow+2,startCell+2);
        Position checkDownLeft = new Position(startRow+1, startCell-1);
        Position checkDownEmptyLeft = new Position(startRow+2,startCell-2);
        return (checkPieceJump(checkUpLeft,checkUpEmptyLeft,startPiece)
                || checkPieceJump(checkUpRight,checkUpEmptyRight,startPiece)
                || checkPieceJump(checkDownRight,checkDownEmptyRight,startPiece)
                || (checkPieceJump(checkDownLeft,checkDownEmptyLeft,startPiece)))   ;
    }


//    /**
//     * determines whether a move is a single jump move
//     * @param start starting position of piece on the board
//     * @param color color of the piece
//     * @return true if move is a single jump move, false otherwise
//     */
    public boolean checkJumpRight(Position checkRight, Position checkEmptyRight, Piece.Color color){
        Space spaceRight = getSpace(checkRight);
        Space emptyRight = getSpace(checkEmptyRight);
        Piece right = spaceRight.getPiece();
        if (right == null){
            return false;
        } else {
            return (spaceRight.getPiece() != null && emptyRight.getPiece() == null && !right.getColor().equals(color));
        }
    }
    public boolean checkJumpLeft(Position checkLeft, Position checkEmptyLeft, Piece.Color color){
        Space spaceLeft = getSpace(checkLeft);
        Space emptyLeft = getSpace(checkEmptyLeft);
        Piece left = spaceLeft.getPiece();
        if (left == null){
            return false;
        } else {
            return (spaceLeft.getPiece() != null && emptyLeft.getPiece() == null && !left.getColor().equals(color));
        }
    }
    public boolean isSingleJump(Position start, Piece.Color color){
        int startRow = start.getRow();
        int startCell = start.getCell();
        Position checkLeft, checkRight,
                checkEmptyLeft,checkEmptyRight;

        if (getSpace(start).getPiece() != null){
            Piece startPiece = getSpace(start).getPiece();
            if (startPiece.getType().equals(KING)){
                return checkKingJump(start,startPiece);
            }
        }

        if (color == Piece.Color.RED){
            checkLeft = new Position(startRow-1,startCell-1);
            checkEmptyLeft = new Position(startRow-2,startCell-2);
            checkRight = new Position(startRow-1, startCell+1);
            checkEmptyRight = new Position(startRow-2,startCell+2);
        } else {
            checkLeft = new Position(startRow+1,startCell+1);
            checkEmptyLeft = new Position(startRow+2,startCell+2);
            checkRight = new Position(startRow+1, startCell-1);
            checkEmptyRight = new Position(startRow+2,startCell-2);
        }
        if (checkEmptyLeft.checkOutOfRange() && checkEmptyRight.checkOutOfRange()){
            return false;
        } else if (checkEmptyLeft.checkOutOfRange()){

            return checkJumpRight(checkRight,checkEmptyRight,color);

        } else if (checkEmptyRight.checkOutOfRange()) {

            return checkJumpLeft(checkLeft,checkEmptyLeft,color);

        } else {

            return checkJumpRight(checkRight,checkEmptyRight,color) || checkJumpLeft(checkLeft,checkEmptyLeft,color);
        }

    }
    public boolean checkSingle(Position position){
        boolean result = false;
        if(position.checkOutOfRange()){
            return false;
        }
        else{
            if(this.getSpace(position).getPiece()== null){
                result = true;
            }
        }
    return result;}
    /**
     * updates the board
     * @param move the move being performed
     * @param color the color of the piece
     */
    public void updateBoard(Move move, Piece.Color color){
        Position start = move.getStart();
        Position end = move.getEnd();
        int startCell = start.getCell();
        int endCell = end.getCell();
        Space startSpace = getSpace(start);
        Space endSpace;
        Space opponent;
        Position between;
        Piece startPiece = startSpace.getPiece();
        if (singleMove(move,color)){

            startSpace = new Space(startCell,null, Space.Space_color.DARK);
            updateSpace(start,startSpace);
            endSpace = new Space(endCell,startPiece, Space.Space_color.DARK);
            updateSpace(end,endSpace);
            alreadySingleMove = true;


        } else if (singleJumpMove(move, color)){

            endSpace = new Space(endCell,startPiece, Space.Space_color.DARK);
            updateSpace(end,endSpace);

            startSpace = new Space(startCell,null, Space.Space_color.DARK);
            updateSpace(start,startSpace);

            between = getMiddle(move);
            opponent = new Space(between.getCell(),null, Space.Space_color.DARK);
            updateSpace(between,opponent);
            //if there can't be another jump or single move
            if (!isSingleJump(move.getEnd(),color)){
                alreadyJumpMove = true;
            }

        }
        if (color == Piece.Color.RED){
            if (end.getRow() == 0){
                Piece king = new Piece(Piece.Type.KING, Piece.Color.RED);
                endSpace = new Space(endCell,king, Space.Space_color.DARK);
                updateSpace(end,endSpace);
            }
        } else {
            if (end.getRow() == 7){
                Piece king = new Piece(Piece.Type.KING, Piece.Color.WHITE);
                endSpace = new Space(endCell,king, Space.Space_color.DARK);
                updateSpace(end,endSpace);
            }
        }

    }

    /**
     * upates the row of the board
     * @param position a single position in the row
     * @param space a single space in the row
     */

    public void updateSpace(Position position, Space space){
        spaces[position.getRow()][position.getCell()] = space;
    }

    /**
     * gets the space from a position on the board
     * @param position the position of the row
     * @return the space of the row
     */
    public Space[] getRow(int position){
        return spaces[position];
    }

    /**
     * gets the spaces
     * @return the spaces
     */
    public Space[][] getSpaces() {
        return spaces;
    }

    /**
     * checks whether the checkergame is equal to another object
     * @param o the other object
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object o){
        if (o == this) {
            return true;
        }
        if(!(o instanceof Board)){
            return false;
        }
        Board p = (Board) o;
        boolean result = true;
        if (!(this.alreadySingleMove == p.alreadySingleMove && this.alreadyJumpMove == p.alreadyJumpMove)){
            result = false;
        }
        for (int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++)
                if (!(this.spaces[i][j] == p.spaces[i][j])) {
                    result = false;
                    break;
                }
        }
        return result;
    }
    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        for (int i = 0 ; i < 8; i++){
            for (int j = 0; j < 8; j++){
                str.append(spaces[i][j]);
            }
            str.append("\t");
        }
        return str.toString();
    }
}
