package com.webcheckers.model;

import com.webcheckers.appl.GameCenter;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
@Tag ("model-tier")
class AIPlayerTest {
    @Test
    public void testPlayer(){
        AIPlayer AI = new AIPlayer("BOT");
        Player red = new Player("RED");
        CheckerGame game = new CheckerGame(red,AI);
        String s = game.getWhitePlayer().getName();
        System.out.println("expected BOT, got "+ s);
        assertEquals("BOT",s);
    }

    @Test
    public void TestMove(){
        GameCenter gameCenter = new GameCenter();
        AIPlayer AI = new AIPlayer("BOT");
        Player red = new Player("RED");
        gameCenter.addPlayersPlaying(red,AI);
        CheckerGame game = new CheckerGame(red,AI);
        game.getBoard().getBoard().initializeBoard("src/main/java/com/webcheckers/model/BoardTest/board1.txt");
        game.addMove(new Move(new Position(2,5),new Position(3,3)).getInverse());
        game.applyMove();
        gameCenter.MakeMoveAI(game.getWhitePlayer());
        System.out.println("done");
        assertNotNull(game);

    }
    @Test
    public void TestMulti(){
        GameCenter gameCenter = new GameCenter();
        AIPlayer AI = new AIPlayer("BOT");
        Player red = new Player("RED");
        gameCenter.addPlayersPlaying(red,AI,4);
        CheckerGame game = gameCenter.getGame(AI);
        game.addMove(new Move(new Position(4,5),new Position(3,4)));
        game.applyMove();
        gameCenter.MakeMoveAI(game.getWhitePlayer());
        System.out.println("done");
        assertNotNull(game);
    }
    
    @Test
    public void TestKing(){
        GameCenter gameCenter = new GameCenter();
        AIPlayer AI = new AIPlayer("BOT");
        Player red = new Player("RED");
        gameCenter.addPlayersPlaying(red,AI,2);
        CheckerGame game = gameCenter.getGame(AI);
        game.addMove(new Move(new Position(7,4),new Position(6,3)));
        game.applyMove();
        gameCenter.MakeMoveAI(game.getWhitePlayer());
        game.addMove(new Move(new Position(4,1),new Position(5,2)));
        game.applyMove();
        gameCenter.MakeMoveAI(game.getWhitePlayer());
        System.out.println("done with king ");
        assertNotNull(game);
    }
    @Test
    public void TestEndOnNoMove(){
        GameCenter gameCenter = new GameCenter();
        AIPlayer AI = new AIPlayer("BOT");
        Player red = new Player("RED");
        gameCenter.addPlayersPlaying(red,AI,12);
        CheckerGame game = gameCenter.getGame(AI);
        game.addMove(new Move(new Position(6,1),new Position(5,2)));
        game.applyMove();
        gameCenter.MakeMoveAI(game.getWhitePlayer());
        assertSame(gameCenter.getWinner(),red);
        System.out.println("done with endGame ");
    }
    @Test
    public void TestEndOnCapture(){
        GameCenter gameCenter = new GameCenter();
        AIPlayer AI = new AIPlayer("BOT");
        Player red = new Player("RED");
        gameCenter.addPlayersPlaying(red,AI,13);
        CheckerGame game = gameCenter.getGame(AI);
        Move m = new Move(new Position(4,3),new Position(2,5));
        game.addMove(m);
        game.applyMove();
        gameCenter.MakeMoveAI(game.getWhitePlayer());
        assertSame(gameCenter.getWinner(),red);
        System.out.println("done with capture ");
    }
    @Test
    public void TestRedLose(){
        GameCenter gameCenter = new GameCenter();
        AIPlayer AI = new AIPlayer("BOT");
        Player red = new Player("RED");
        gameCenter.addPlayersPlaying(red,AI,13);
        CheckerGame game = gameCenter.getGame(AI);
        gameCenter.MakeMoveAI(AI);
        gameCenter.MakeMoveAI(AI);
        assertSame(gameCenter.getWinner(),AI);

    }
    @Test
    public void moveKingPiece(){
        GameCenter gameCenter = new GameCenter();
        AIPlayer AI = new AIPlayer("BOT");
        Player red = new Player("RED");
        gameCenter.addPlayersPlaying(red,AI,14);
        CheckerGame game = gameCenter.getGame(AI);
        game.addMove(new Move(new Position(7,4),new Position(6,3)));
        game.applyMove();
        gameCenter.MakeMoveAI(game.getWhitePlayer());
        game.addMove(new Move(new Position(4,1),new Position(3,2)));
        game.applyMove();
        gameCenter.MakeMoveAI(game.getWhitePlayer());
        game.addMove(new Move(new Position(3,2),new Position(2,3)));
        game.applyMove();
        gameCenter.MakeMoveAI(game.getWhitePlayer());
        //assertFalse(gameCenter.isGameOver());
    }

}