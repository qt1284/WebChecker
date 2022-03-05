package com.webcheckers.model;

/**
 * object class for a single piece in webcheckers game
 */
public class Piece {

    /**
     * enum for possible types of a piece
     */
    public enum Type{
        SINGLE,
        KING
    }

    /**
     * enum for possible colors of a piece
     */
    public enum Color{
        RED,
        WHITE
    }

    private Type t;
    private Color c;

    /**
     * initializer method for piece
     * @param type
     *   the type of the piece
     * @param color
     *   the color of the piece
     */
    public Piece(Type type, Color color){
        this.t = type;
        this.c = color;
    }
    public Piece(Piece piece){
        this.t = piece.t;
        this.c = piece.c;
    }

    /**
     * sets the type of a piece
     * @param type
     *   the type the piece will be set to
     */
    public void setType(Type type){
        this.t = type;
    }

    /**
     * sets the color of a piece
     * @param color
     *   the color the piece will be set to
     */
    public void setColor(Color color){
        this.c = color;
    }

    /**
     * gets the type of a piece
     * @return
     *   the type of the piece
     */
    public Type getType(){
        return this.t;
    }

    /**
     * gets the color of piece
     * @return
     *   the color of the piece
     */
    public Color getColor(){
        return this.c;
    }

    /**
     * checks whether a move is equal to another object
     * @param o the other object
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if(!(o instanceof Piece)){
            return false;
        }
        Piece p = (Piece) o;
        return this.t.equals(p.t) && this.c.equals(p.c);
    }
}
