package com.webcheckers.model;

/**
 * object class for a single space on the board
 */
public class Space {
    /**
     * enum for possible colors of the space
     */
    public enum Space_color{
        DARK,
        LIGHT
    }

    private int cellIdx; //0-7
    private Piece piece;
    private Space_color color;

    /**
     * initializer method for space
     * @param cellIdx
     *   the index of the space
     * @param p
     *   the piece of the space
     * @param c
     *   the color of this space
     */
    public Space(int cellIdx, Piece p, Space_color c){
        this.cellIdx = cellIdx;
        this.piece = p;
        this.color = c;
    }
    public Space(Space space){
        cellIdx = space.getCellIdx();
        color = space.getColor();
        if (space.getPiece() == null) {
            piece = null;
        }
        else {
            piece = new Piece(space.getPiece());
        }
    }

    /**
     * gets the cellIdx of a space
     * @return
     *   an integer from 0 to 7
     */
    public int getCellIdx(){
        return this.cellIdx;
    }

    /**
     * gets the color of a space
     * @return DARK or LIGHT
     */
    public Space_color getColor(){
        return this.color;
    }
    /**
     * checks if a space is valid
     * @return
     *   true if space is dark and does not already have a piece occupying it, false otherwise
     */
    public boolean isValid(){
        return this.piece == null && this.color == Space_color.DARK;
    }

    /**
     * sets a piece in a space
     * @param piece
     *   the piece to be set
     */
    public void setPiece(Piece piece){
        this.piece = piece;
    }

    /**
     * gets the piece occupying a space
     * @return
     *   the Piece if space is occupied, null otherwise
     */
    public Piece getPiece(){
        return this.piece;
    }

    /**
     * setter method for the cellIdx for a space
     * @param cellIdx the cellId
     */
    public void setCellIdx(int cellIdx) {
        this.cellIdx = cellIdx;
    }

    /**
     * checks whether space is equal to another object
     * @param o the other object
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if(!(o instanceof Space)){
            return false;
        }
        Space p = (Space) o;
        return this.cellIdx == p.getCellIdx() && this.color.equals(p.getColor()) && this.piece == p.getPiece();
    }
    @Override
    public String toString(){
        if (this.piece == null)
            return ". ";
        else if (this.piece.getColor().equals(Piece.Color.RED))
            return "R";
        else
            return "W";
    }
}
