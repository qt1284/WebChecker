package com.webcheckers.appl;

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.model.Player;

import org.junit.jupiter.api.Test;
//import org.mockito.internal.matchers.Null;
import org.junit.jupiter.api.Tag;

@Tag("Application-tier")
public class PlayerLobbyTest {
  //Stubbing out test file
    
  /**
   * Test creation of a PlayerLobby
   */
  @Test
  public void testCreatePlayerLobby() {
    final PlayerLobby playerLobby = new PlayerLobby();
    assertNotNull(playerLobby);
  }

  @Test
  public void testPlayerLobbyAddPlayer() {
    final PlayerLobby playerLobby = new PlayerLobby();
    Player player = new Player("Jack");
    playerLobby.addPlayer(player);
    assert(playerLobby.containsName("Jack"));            
  }
  @Test
  public void testPlayerSpectating() {
    final PlayerLobby playerLobby = new PlayerLobby();
    Player player = new Player("Jack");
    playerLobby.setSpectating(player,true);
    Player player2 = new Player("Jack");
    assertEquals(playerLobby.getPlayerSpectating(player2),true);
  }

  @Test
  public void testPlayerLobbyNumOfOther() {
    final PlayerLobby playerLobby = new PlayerLobby();
    Player player = new Player("Jack");
    Player player2 = new Player("John");
    Player player3 = new Player("Joe");
    playerLobby.addPlayer(player);
    playerLobby.addPlayer(player2);
    playerLobby.addPlayer(player3);
    //because of BOT
    assertEquals(4, playerLobby.getNumOfOthers());
  }
  
  @Test
  public void testPlayerLobbyListOfOther() {
    final PlayerLobby playerLobby = new PlayerLobby();
    Player player = new Player("Jack");
    Player player2 = new Player("John");
    Player player3 = new Player("Joe");
    playerLobby.addPlayer(player);
    playerLobby.addPlayer(player2);
    playerLobby.addPlayer(player3);
    assertEquals(3, playerLobby.listOfPlayer(player).size());
  }

  @Test
  public void testPlayerLobbyListOfOtherIfNull() {
    final PlayerLobby playerLobby = new PlayerLobby();
    Player player = new Player("Jack");
    playerLobby.addPlayer(player);
    assertNotNull(playerLobby.listOfPlayer(player));
  }

  @Test
  public void testCheckValidName() {
    final PlayerLobby playerLobby = new PlayerLobby();
    Player player = new Player(".");
    assertFalse(playerLobby.checkValidName(player.getName()));
  }

  @Test
  public void testCheckValidName2() {
    final PlayerLobby playerLobby = new PlayerLobby();
    Player player = new Player("g5");
    assertTrue(playerLobby.checkValidName(player.getName()));
  }

  @Test
  public void testCheckValidName3() {
    final PlayerLobby playerLobby = new PlayerLobby();
    Player player = new Player("a5."); // name is valid
    assertTrue(playerLobby.checkValidName(player.getName()));
  }

  @Test
  public void testCheckValidName4() {
    final PlayerLobby playerLobby = new PlayerLobby();
    Player player = new Player(".;5");
    assertFalse(playerLobby.checkValidName(player.getName()));
  }

  @Test
  public void testPlayerLobbyRemovePlayer() {
    final PlayerLobby playerLobby = new PlayerLobby();
    Player player = new Player("Jack");
    playerLobby.addPlayer(player);
    playerLobby.removePlayer(player);
    assertFalse(playerLobby.containsName("Jack"));            
  }

  @Test
  public void testPlayerLobbyUpdateNumOfPlayer() {
    final PlayerLobby playerLobby = new PlayerLobby();
    Player player = new Player("Jack");
    Player player2 = new Player("John");
    playerLobby.addPlayer(player);
    playerLobby.addPlayer(player2);
    playerLobby.removePlayer(player);
    playerLobby.updateNumOfPlayers(0);
    //because of BOT
    assertEquals(2,playerLobby.getNumOfOthers());
  }

}