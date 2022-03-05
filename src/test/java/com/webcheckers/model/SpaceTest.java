package com.webcheckers.model;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;

import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.*;

public class SpaceTest {

    final Piece piece = mock(Piece.class);
    final Space.Space_color color = Space.Space_color.DARK;

    @Test
    public void testSpaceNotNull() {
        Space space = new Space(0, piece, color);
        assertNotNull(space);
    }
    @Test
    public void equalTest() {
        Space space = new Space(0, piece, color);
        Space space2 = new Space(0, piece, color);
        assertEquals(space,space2);
    }

    @Test
    public void testGetCellId() {
        Space space = new Space(0, piece, color);
        assertEquals(0, space.getCellIdx());
    }

    @Test
    public void testSpaceIsValid() {
        int flag = 0;
        Space space = new Space(0, null, color);
        assertTrue(space.isValid());
    }

    @Test
    public void testSetPiece() {
        Space space = new Space(0, null, color);
        space.setPiece(piece);
        assertEquals(piece, space.getPiece());

    }

    @Test
    public void testGetPiece() {
        Space space = new Space(0, piece, color);
        assertEquals(piece, space.getPiece());
    }
}
//mvn install
//mvn clean test jacoco:report
