package com.webcheckers.model;

/**
 * The object class for Player aka a single player
 */
public class Player {
    public String name;
    public int moveCount;

    /**
     * setter method for username
     * @param name
     *   the username to be set
     */
    public Player(String name){

        this.name = name;
        this.moveCount = 0;
    }

    /**
     * getter method for username
     *
     * @return
     *   the username
     */
    public String getName() {
        return name;
    }

    /**
     * renders player
     *
     * @return
     *    the rendered player
     */
    @Override
    public String toString(){
        return name;
    }

    /**
     * checks whether a move is equal to another object
     * @param o the other object
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object o){
        if (o == this) {
            return true;
        }
        if(!(o instanceof Player)){
            return false;
        }
        Player p = (Player) o;
        return this.name.equals(p.getName());
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

}