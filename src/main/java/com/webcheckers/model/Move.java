package com.webcheckers.model;

/**
 * object class for a single move in webcheckers game
 */
public class Move {
    private final Position start;
    private final Position end;

    /**
     * initializer method for Move
     * @param start the starting position of a given piece
     * @param end the ending position of a given piece
     */
    public Move(Position start, Position end){
        this.start = start;
        this.end = end;
    }

    /**
     * gets the ending position of a piece
     * @return the ending position of the piece
     */
    public Position getEnd() {
        return end;
    }

    /**
     * gets the starting position of a piece
     * @return the starting position of the piece
     */
    public Position getStart() {
        return start;
    }

    /**
     * gets the inverse of the board
     * @return the inverse of the move for the opponent's view
     */
    public Move getInverse(){
        Position startNew = new Position(7-start.getRow(), 7- start.getCell());
        Position endNew = new Position(7-end.getRow(), 7 - end.getCell());
        return new Move(startNew,endNew);
    }

    /**
     * checks whether a move is equal to another object
     * @param other the other object
     * @return true if the move is equal, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other == null || getClass() != other.getClass()) return false;
        Move move = (Move) other;
        return this.start.getRow() == move.start.getRow() && this.start.getCell() == move.start.getCell()
                && this.end.getRow() == move.end.getRow() && this.end.getCell() == move.end.getCell();

    }
    @Override
    public String toString(){
        return this.start.toString() + this.end.toString();
    }

}
