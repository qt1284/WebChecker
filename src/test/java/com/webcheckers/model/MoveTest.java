package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-tier")
public class MoveTest {
    @Test
    public void testEqualMove(){
        Move a = new Move(new Position(1,2), new Position(2,3));
        Move b = new Move(new Position(1,2), new Position(2,3));
        assertEquals(true,a.equals(b));
    }

    public void testUnEqualMoves(){
        Move a = new Move(new Position(6,4), new Position(3,7));
        Move b = new Move(new Position(1,2), new Position(2,3));
        assertEquals(false,a.equals(b));
    }

    public void testNullMove(){
        Move a = new Move(new Position(1,6), new Position(2,3));
        Move b = null;
        assertEquals(false,a.equals(b));
    }

    public void testNotMove(){
        Move a = new Move(new Position(1,6), new Position(2,3));
        Position b = new Position(1,6);
        assertEquals(false,a.equals(b));
    }

    @Test
    public void testInverse(){
        Move a = new Move(new Position(1,2), new Position(2,3));
        Move b = new Move(new Position(6,5), new Position(5,4));
        assertEquals(true,a.equals(b.getInverse()));
    }


}
