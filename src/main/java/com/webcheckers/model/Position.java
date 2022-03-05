package com.webcheckers.model;

/**
 * object class for a position in webcheckers game
 */
public class Position {

    private final int row;
    private final int cell;

    /**
     * initializer method for a position
     * @param row the row of the position
     * @param cell the cell of the position
     */
    public Position (int row, int cell){
        this.row = row;
        this.cell = cell;
    }

    /**
     * gets the cell of the position
     * @return the cell of the position
     */
    public int getCell() {
        return cell;
    }

    /**
     * determines whether a position is out of range
     * @return true is position is out of range, false otherwise
     */
    public boolean checkOutOfRange(){
        return (cell < 0 || cell > 7) ||
                (row < 0 || row > 7);
    }

    /**
     * gets the row of the position
     * @return the row of the position
     */
    public int getRow() {
        return row;
    }

    /**
     * gets the string form of the position
     * @return the string form of the position
     */
    @Override
    public String toString(){
        return this.row + "" + this.cell;
    }

    /**
     * checks whether a move is equal to another object
     * @param other the other object
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other == null || getClass() != other.getClass()) return false;
        Position move = (Position) other;
        return this.row == move.getRow() && this.cell == move.getCell();

    }
}
