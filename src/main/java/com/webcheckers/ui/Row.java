package com.webcheckers.ui;

import com.webcheckers.model.Space;

import java.util.Arrays;
import java.util.Iterator;

/**
 * object class for a single row on the board
 */
public class Row implements Iterable<Space>{
    private int index; //0-7
    private Space[] spaces;


    /**
     * initializer method for row
     * @param spaces
     *   the spaces in the row
     */
    public Row(Space[] spaces,int index){
        this.spaces = spaces;
        this.index = index;
    }

    /**
     * gets the index of the current row within the board
     * @return
     *   an integer from 0 to 7
     */
    public int getIndex(){
        return this.index;
    }

    /**
     * gets the spaces
     * @return list of spaces
     */
    public Space[] getSpaces(){
        return this.spaces;
    }

    /**
     * creates a java Iterator of the Spaces within a single row
     * @return
     *   an Iterator of Spaces
     */
    @Override
    public Iterator<Space> iterator() {
        return Arrays.asList(spaces).iterator();
    }

    /**
     * checks whether the row is equal to another object
     * @param o the other object
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object o){
        if (o == this) {
            return true;
        }
        if(!(o instanceof Row)){
            return false;
        }
        Row p = (Row) o;
        return this.index == p.index && Arrays.equals(this.spaces,p.spaces);
    }
}
