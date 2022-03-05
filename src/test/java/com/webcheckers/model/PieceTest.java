package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.model.Piece.Type;
import com.webcheckers.model.Piece.Color;
@Tag("Model-tier")
public class PieceTest {
    @Test
    public void testNull(){
        Type t = Type.SINGLE;
        Color c = Color.RED;
        Piece piece = new Piece(t, c);
        assertNotNull(piece);
    }

    @Test
    public void testGetType(){
        Type t = Type.SINGLE;
        Color c = Color.RED;
        Piece piece = new Piece(t, c);
        assertEquals(Type.SINGLE, piece.getType());
    }

    @Test
    public void testGetColor(){
        Type t = Type.SINGLE;
        Color c = Color.RED;
        Piece piece = new Piece(t, c);
        assertEquals(Color.RED, piece.getColor());
    }

    @Test
    public void testSetType(){
        Type t = Type.SINGLE;
        Color c = Color.RED;
        Piece piece = new Piece(t, c);
        piece.setType(Type.KING);
        assertEquals(Type.KING, piece.getType());
    }
    @Test
    public void equalTest(){
        Piece a = new Piece(Type.SINGLE,Color.RED);
        Piece b = new Piece(Type.SINGLE,Color.RED);
        assertEquals(a,b);
    }
    @Test
    public void testSetColor(){
        Type t = Type.SINGLE;
        Color c = Color.RED;
        Piece piece = new Piece(t, c);
        piece.setColor(Color.WHITE);
        assertEquals(Color.WHITE, piece.getColor());
    }


}
