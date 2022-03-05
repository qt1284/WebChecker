package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;



import java.util.LinkedList;
import java.util.List;

import static com.webcheckers.model.Piece.Color.RED;
import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-tier")
class BoardTest {
    @Test
    public void test(){
        Board board = new Board();
        List<Move> moveList = new LinkedList<>();
        Position start = new Position(5,2);
        Position end = new Position(3,4);
        Move move = new Move(start,end);
        moveList.add(move);
        assertEquals(board.getMiddle(move), new Position(4,3));
        Board flip = new Board(board);
        for (Move getMove: moveList){
            board.updateBoard(getMove, Piece.Color.RED);
        }
        Space[][] spaces = board.getSpaces();
        CheckerGame game = new CheckerGame(new Player("a"),new Player("b"));
        game.addMove(move);
        assertTrue(game.applyMove());

        start = new Position(5,2);
        end = new Position(4,3);
        Space startSpace = flip.getSpace(start);
        Space endSpace = flip.getSpace(end);
        assertNotNull(startSpace);
        Piece startPiece = startSpace.getPiece();
        assertNotNull(startPiece);
        Piece endPiece = endSpace.getPiece();

        assertArrayEquals(spaces,board.getSpaces().clone());
    }
    @Test
    public void testEndGame(){
        Board board = new Board();
        assertFalse(board.endGame(RED));
    }
    @Test
    public void kingMoveTest(){
        Board board = new Board();
        Position start = new Position(5,2);

        Position end = new Position(3,4);

        Move move = new Move(start,end);
        Position middle = board.getMiddle(move);
        Space space = new Space(2,new Piece(Piece.Type.KING, Piece.Color.WHITE), Space.Space_color.DARK);
        board.updateSpace(middle,space);
        assertTrue(board.checkKingJump(start, board.getSpace(start).getPiece()));
    }
    @Test
    public void singleJumpMoveTest(){
        Board board = new Board();
        Position start = new Position(5,2);
        Position end = new Position(4,3);
        Move move = new Move(start,end);
        assertFalse(board.singleJumpMove(move, Piece.Color.RED));
    }
    @Test
    public void singleJumpMoveTest2(){
        Board board = new Board();
        Position start = new Position(5,2);
        Position end = new Position(3,4);
        Move move = new Move(start,end);
        Position middle = board.getMiddle(move);
        Space space;
        space = new Space(3,new Piece(Piece.Type.KING, Piece.Color.WHITE), Space.Space_color.DARK);
        board.updateSpace(middle,space);
        space = new Space(2,new Piece(Piece.Type.KING, Piece.Color.RED), Space.Space_color.DARK);
        board.updateSpace(start,space);
        assertTrue(board.singleJumpMove(move, Piece.Color.RED));
    }
    @Test
    public void singleJumpMoveTest3(){
        Board board = new Board();
        Position start = new Position(5,2);
        Position end = new Position(3,0);

        Move move = new Move(start,end);
        Position middle = board.getMiddle(move);
        Space space = new Space(2,new Piece(Piece.Type.KING, Piece.Color.WHITE), Space.Space_color.DARK);
        board.updateSpace(middle,space);
        assertTrue(board.singleJumpMove(move, Piece.Color.RED));
    }
    @Test
    public void singleJumpMoveTest4(){
        Board board = new Board();
        Position start = new Position(2,3);
        Position end = new Position(4,1);

        Move move = new Move(start,end);
        Position middle = board.getMiddle(move);
        Space space = new Space(1,new Piece(Piece.Type.KING, Piece.Color.RED), Space.Space_color.DARK);
        board.updateSpace(middle,space);
        assertTrue(board.singleJumpMove(move, Piece.Color.WHITE));
    }
    @Test
    public void singleJumpMoveTest5(){
        Board board = new Board();
        Position start = new Position(2,3);
        Position end = new Position(4,5);

        Move move = new Move(start,end);
        Position middle = board.getMiddle(move);
        Space space = new Space(1,new Piece(Piece.Type.KING, Piece.Color.RED), Space.Space_color.DARK);
        board.updateSpace(middle,space);
        assertTrue(board.singleJumpMove(move, Piece.Color.WHITE));
    }

    @Test
    public void testEqual(){
        Board a = new Board();
        Board b = new Board(a);
        assertEquals(a,b);
        assertEquals(a,a);

        Board c = new Board();
        assertNotEquals(c,a);
    }

    @Test
    public void testGetSpaces(){
        Board a = new Board();
        Space[][] spaces = a.getSpaces();
        assertEquals(8, spaces.length); //there are 8 rows
    }

    @Test
    public void testGetMiddle(){
        Board a = new Board();
        Move move = new Move(new Position(5,0), new Position(3,2));
        Position midA = a.getMiddle(move);
        Position midE = new Position(4, 1);
        assertEquals(midE, midA); 
    }


}