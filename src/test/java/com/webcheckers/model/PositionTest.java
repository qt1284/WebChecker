package com.webcheckers.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.model.Piece.Color;


class PositionTest {

    @Test
    public void testOutOfRangePositionHorizonalHigh(){
        Position pos = new Position(4,10);
        assertEquals(true, pos.checkOutOfRange());
    }

    @Test
    public void testOutOfRangePositionHorizonalLow(){
        Position pos = new Position(2,-3);
        assertEquals(true, pos.checkOutOfRange());
    }

    @Test
    public void testOutOfRangePositionVerticalHigh(){
        Position pos = new Position(13,5);
        assertEquals(true, pos.checkOutOfRange());
    }

    @Test
    public void testOutOfRangePositionVerticalLow(){
        Position pos = new Position(-1,2);
        assertEquals(true, pos.checkOutOfRange());
    }

    @Test
    public void testToString(){
        Position pos = new Position(5,4);
        assertEquals("54", pos.toString());
    }

    @Test
    public void testEqualsEqualPositions(){
        Position pos1 = new Position(5,4);
        Position pos2 = new Position(5,4);
        assertEquals(true, pos1.equals(pos2));
    }

    @Test
    public void testEqualsUnEqualPositionsRow(){
        Position pos1 = new Position(3,4);
        Position pos2 = new Position(5,4);
        assertEquals(false, pos1.equals(pos2));
    }

    @Test
    public void testEqualsUnEqualPositionsCel(){
        Position pos1 = new Position(5,4);
        Position pos2 = new Position(5,6);
        assertEquals(false, pos1.equals(pos2));
    }

    @Test
    public void testEqualsNullPosition(){
        Position pos1 = new Position(3,4);
        Position pos2 = null;
        assertEquals(false, pos1.equals(pos2));
    }

    @Test
    public void testEqualsNotPosition(){
        Position pos1 = new Position(3,4);
        Color pos2 = Color.RED;
        assertEquals(false, pos1.equals(pos2));
    }

}