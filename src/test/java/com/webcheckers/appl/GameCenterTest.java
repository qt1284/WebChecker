package com.webcheckers.appl;

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.ui.BoardView;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.Player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

@Tag("Application-tier")
public class GameCenterTest {
  GameCenter gCenter;
  Player redPlayer;
  Player whitePlayer;

  @BeforeEach
  public void setup(){
    gCenter = new GameCenter();
    redPlayer = new Player("red");
    whitePlayer = new Player("white");
    gCenter.addPlayersPlaying(redPlayer,  whitePlayer);
  }
  /**
   * Test creation of a GameCenter
   */
  @Test
  public void testMakeGameCenter() {
    assertNotNull(gCenter);
  }

  @Test
  public void testSpectatorCount(){
    gCenter.setSpectatorCount(2);
    assertEquals(2, gCenter.getSpectatorCount());
    gCenter.increaseSpectatorCount();
    assertEquals(3, gCenter.getSpectatorCount());
  }

  @Test
  public void testSpectatorMax(){
    assertFalse(gCenter.checkSpectator());
    gCenter.increaseSpectatorMax();
    gCenter.increaseSpectatorMax();
    gCenter.setSpectatorCount(1);
    assertTrue(gCenter.checkSpectator());
  }

  @Test
  public void testTurnSubmit(){
    gCenter.setTurnSubmit(true);
    assertTrue(gCenter.isTurnSubmit());
  }

  @Test
  public void testCheckTwoPlayer() {
    final PlayerLobby playerLobby = new PlayerLobby();
    final GameCenter gameCenter = new GameCenter();
    Player player = new Player("Jack");
    playerLobby.addPlayer(player);
    assert(gameCenter.checkTwoPlayer(player, player));
  }

  @Test
  public void testGetBoard() {
    CheckerGame game = new CheckerGame (redPlayer, whitePlayer);
    BoardView board = game.getBoardView();
    assertEquals(board, gCenter.getBoard(game));
  }

  @Test
  public void testGetFlippedBoard() {
    CheckerGame game = new CheckerGame (redPlayer, whitePlayer);
    BoardView board = game.getBoard();
    assertNotEquals(board, gCenter.getFlippedBoard(game));
  }

  @Test
  public void testAddPlayingPlayers() {
    assertEquals(gCenter.isPlayerInGame(redPlayer), gCenter.isPlayerInGame(whitePlayer));
  }
   
  @Test
  public void testGetGame() {
    assertEquals(gCenter.getGame(redPlayer), gCenter.getGame(whitePlayer));
  }

  @Test
  public void testGameID() {
    CheckerGame game = gCenter.getGame(redPlayer);
    gCenter.addGameID("11",game);
    assertEquals(game, gCenter.getGameFromID("11"));
//    String a = gCenter.getIDFromGame(game);
//    gCenter.gamesPlaying().remove(a);
    gCenter.removePlayerPlaying(redPlayer,whitePlayer);
    assertNull(gCenter.gamesPlaying());
  }

  @Test
  public void testGetGameFromID(){
    CheckerGame game = gCenter.getGame(redPlayer);

  }

  @Test
  public void testRemovePlayersPlaying() {
    gCenter.removePlayerPlaying(redPlayer,  whitePlayer);
    assertNull(gCenter.getGame(redPlayer));
  }
  @Test
  public void testUpdateWinner(){
    gCenter.updateWinner(true, redPlayer, whitePlayer);
    assertTrue(gCenter.isGameOver());
    assertEquals(redPlayer, gCenter.getWinner());
    assertEquals(whitePlayer, gCenter.getLoser());
  }

  @Test
  public void testNoMorePieces(){
    BoardView board = new BoardView();
    assertFalse(gCenter.ifNoMorePieces(board, redPlayer,whitePlayer));
  }

  @Test
  public void testSetGameOver(){
    gCenter.setGameOver(true);
    assertTrue(gCenter.isGameOver());
    assertTrue(gCenter.getGameOver());
  }

  @Test
  public void testIsGameOver() {
    gCenter.removePlayerPlaying(redPlayer,  whitePlayer);
    assertFalse(gCenter.isGameOver(redPlayer));
  }

//  /**
//  @Test
//  public void testGetWinner() {
//    final PlayerLobby playerLobby = new PlayerLobby();
//    final GameCenter gCenter = new GameCenter(playerLobby);
//    Player redPlayer = new Player("red");
//    Player whitePlayer = new Player("white");
//    gCenter.addPlayersPlaying(redPlayer,  whitePlayer);
//    Player winner = redPlayer;
//    assertEquals(winner, gCenter.getWinner());
//  }*/

  @Test
  public void testUpdateTurn() {
    assertTrue(gCenter.updateTurn(redPlayer));
  }

  @Test
  public void testIsActive() {
    assertTrue(gCenter.isActive(redPlayer));
  }

  @Test
  public void testGetOpponent(){
    assertEquals(whitePlayer, gCenter.getOpponent(redPlayer));
    assertEquals(redPlayer, gCenter.getOpponent(whitePlayer));
  }
}


