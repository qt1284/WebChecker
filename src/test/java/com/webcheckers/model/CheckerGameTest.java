package com.webcheckers.model;

import com.webcheckers.model.Piece.Color;
import com.webcheckers.ui.BoardView;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@Tag("Model-tier")
public class CheckerGameTest {
    @Test
    public void testNull(){
        Player redPlayer = new Player("red");
        Player whitePlayer = new Player("white");
        CheckerGame game = new CheckerGame (redPlayer, whitePlayer);
        assertNotNull(game);
    }

    @Test
    public void testGetRedPlayer(){
        Player redPlayer = new Player("red");
        Player whitePlayer = new Player("white");
        CheckerGame game = new CheckerGame (redPlayer, whitePlayer);
        assertEquals(redPlayer, game.getRedPlayer());
    }

    @Test
    public void testGetWhitePlayer(){
        Player redPlayer = new Player("red");
        Player whitePlayer = new Player("white");
        CheckerGame game = new CheckerGame (redPlayer, whitePlayer);
        assertEquals(whitePlayer, game.getWhitePlayer());
    }
    @Test
    public void testEquals(){
        Player redPlayer = new Player("red");
        Player whitePlayer = new Player("white");
        CheckerGame game = new CheckerGame (redPlayer, whitePlayer);
        CheckerGame game2 = new CheckerGame (redPlayer, whitePlayer);
        assertEquals(game,game2);
    }
    @Test
    public void testisValid(){
        Player redPlayer = new Player("red");
        Player whitePlayer = new Player("white");
        CheckerGame game = new CheckerGame (redPlayer, whitePlayer);
        Position start = new Position(5,2);

        Position end = new Position(3,4);

        Move move = new Move(start,end);

        assertEquals(game.isValidMove(move).getText(), Message.info("You can't move here").getText());
    }
    @Test
    public void testisValid2(){
        Player redPlayer = new Player("red");
        Player whitePlayer = new Player("white");
        CheckerGame game = new CheckerGame (redPlayer, whitePlayer);
        Position start = new Position(5,2);

        Position end = new Position(4,3);

        Move move = new Move(start,end);
        game.removeMove();
        //assertNull(game.getGameWinner());
        assertEquals(game.isValidMove(move).getText(), Message.info("You make a valid single move.").getText());
    }

    @Test
    public void testGetBoard(){
        Player redPlayer = new Player("red");
        Player whitePlayer = new Player("white");
        CheckerGame game = new CheckerGame (redPlayer, whitePlayer);
        BoardView board = game.getBoardView();
        assertEquals(board, game.getBoardView()); //The board will be the same because they are both retrieved from game
    }

    @Test
    public void testGetBoardFlipped(){
        Player redPlayer = new Player("red");
        Player whitePlayer = new Player("white");
        CheckerGame game = new CheckerGame (redPlayer, whitePlayer);
        BoardView board = game.getBoard();
        assertNotEquals(board, game.getFlippedBoard()); //The board will not be the same because the new one should be flipped
    }

    @Test
    public void testGetActivePlayerRed() {
        Player redPlayer = new Player("red");
        Player whitePlayer = new Player("white");
        CheckerGame game = new CheckerGame(redPlayer, whitePlayer);
        assertEquals(redPlayer, game.getActivePlayer());
    }

    @Test
    public void testGetActivePlayerWhite() {
        Player redPlayer = new Player("red");
        Player whitePlayer = new Player("white");
        CheckerGame game = new CheckerGame(redPlayer, whitePlayer);
        Move move = new Move(new Position(5,0), new Position(4,1));
        game.addMove(move);
        game.applyMove();
        assertEquals(whitePlayer, game.getActivePlayer());
    }

    @Test
    public void testGetActiveColor() {
        Player redPlayer = new Player("red");
        Player whitePlayer = new Player("white");
        CheckerGame game = new CheckerGame(redPlayer, whitePlayer);        
        assertEquals(Color.RED, game.getActiveColor());
    }

    @Test
    public void testHashCode() {
        Player redPlayer = new Player("red");
        Player whitePlayer = new Player("white");
        CheckerGame game = new CheckerGame(redPlayer, whitePlayer);
        Player redPlayer2 = new Player("red");
        Player whitePlayer2 = new Player("white");
        CheckerGame game2 = new CheckerGame(redPlayer2, whitePlayer2);            
        assertEquals(game2.hashCode(), game.hashCode());
    }

    @Test
    public void testApplyMove() {
        Player redPlayer = new Player("red");
        Player whitePlayer = new Player("white");
        CheckerGame game = new CheckerGame(redPlayer, whitePlayer);
        Move move = new Move(new Position(5,0), new Position(4,1));
        game.addMove(move);
        assertTrue(game.applyMove()); //testing applying a simple movement
    }
    
    @Test
    public void testGetMoveList() {
        Player redPlayer = new Player("red");
        Player whitePlayer = new Player("white");
        CheckerGame game = new CheckerGame(redPlayer, whitePlayer);
        Move move = new Move(new Position(5,0), new Position(4,1));
        game.addMove(move);
        List<Move> moves = game.getMoveList();
        assertEquals(move, moves.get(0));
    }

    @Test
    public void testTimeCount() {
        Player redPlayer = new Player("red");
        Player whitePlayer = new Player("white");
        CheckerGame game = new CheckerGame(redPlayer, whitePlayer);        
        game.setTimeCount(30);
        game.addTimeCount(30);
        assertEquals(60, game.getTimeCount());
    }

    @Test
    public void testTimeCountPrintSec() {
        Player redPlayer = new Player("red");
        Player whitePlayer = new Player("white");
        CheckerGame game = new CheckerGame(redPlayer, whitePlayer);        
        game.setTimeCount(30);
        String time = game.printTime();
        assertEquals("Last turn was " + game.getTimeCount() + " second(s) ago", time);
    }

    @Test
    public void testTimeCountPrintMin() {
        Player redPlayer = new Player("red");
        Player whitePlayer = new Player("white");
        CheckerGame game = new CheckerGame(redPlayer, whitePlayer);        
        game.setTimeCount(70);
        String time = game.printTime();
        assertEquals("Last turn was " + (game.getTimeCount()/60) + " minute(s) and " + (game.getTimeCount()%60) + " second(s) ago", time);
    }

    @Test
    public void testTimeCountPrintHour() {
        Player redPlayer = new Player("red");
        Player whitePlayer = new Player("white");
        CheckerGame game = new CheckerGame(redPlayer, whitePlayer);        
        game.setTimeCount(5000);
        String time = game.printTime();
        assertEquals("Last turn was " + (game.getTimeCount()/3600) + " hour(s) and " + (game.getTimeCount()/60) + " minute(s) and " + (game.getTimeCount()%60) + " second(s) ago", time);
    }
    
    @Test
    public void testToString() {
        Player redPlayer = new Player("red");
        Player whitePlayer = new Player("white");
        CheckerGame game = new CheckerGame(redPlayer, whitePlayer);        
        assertEquals("red_challenges_white", game.toString());
    }

    @Test
    public void testRemoveMove() {
        Player redPlayer = new Player("red");
        Player whitePlayer = new Player("white");
        CheckerGame game = new CheckerGame(redPlayer, whitePlayer); 
        Move move = new Move(new Position(5,0), new Position(4,1));
        game.addMove(move);  
        game.removeMove();        
        assertEquals("red_challenges_white", game.toString());
    }
}
