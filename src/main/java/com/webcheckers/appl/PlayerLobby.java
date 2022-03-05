package com.webcheckers.appl;

import com.webcheckers.model.AIPlayer;
import com.webcheckers.model.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * the object for PlayerLobby aka all players that are currently signed into the webcheckers application
 */
public class PlayerLobby {

    private final Map<String,Player> playersList;
    private final Map<Player, Boolean> playerSpectating;
    private int numOfOthers;
    /**
     * Initializes the number of players signed into the webcheckers application not including the user and an empty hashmap of players
     */
    public PlayerLobby(){
        this.numOfOthers = 0;
        this.playersList = new HashMap<>();

        Player AI = new AIPlayer("BOT");
        this.playersList.put("BOT",AI);

        playerSpectating = new HashMap<>();
    }

    /**
     * checks if a player is spectating
     * @param player the player to be checked as a spectator
     * @return true if player is spectating, false otherwise
     */
    public boolean isSpectating(Player player) {
        if (playerSpectating.get(player) == null)
            return false;
        return playerSpectating.get(player);
    }

    /**
     * checks if a player is in the hashmap of spectating players
     * @param player the player to be checked for in the hashmap
     * @return true if player is in hashmap, false otherwise
     */
    public Boolean getPlayerSpectating(Player player) {
        return playerSpectating.get(player);
    }

    /**
     * puts a player in the hashmap of spectators
     * @param player the player to be put into the hashmap
     * @param spectating boolean value of whether if player is spectating; true if spectating, false otherwise
     */
    public void setSpectating(Player player, boolean spectating) {
        playerSpectating.put(player,spectating);
    }
    /**
     * Check valid name
     * @param userName passed in name
     * @return true if name is valid, false otherwise
     */
    public boolean checkValidName(String userName){
        return userName.matches(".*[a-zA-Z0-9]+.*") && !(userName.matches(".*[?!()@#&{};:',*].*"));
    }
    /**
     * adds a player into the hashmap of all players
     *
     * @param player
     *   the player being added to the hashmap
     */
    public void addPlayer(Player player){
        playersList.put(player.getName(),player);
    }
    /**
     * creates a list of all players currently signed into the webcheckers application excluding the current user
     *
     * @param player
     *   the current user
     *
     * @return
     *    a string of all other players currently signed into the webcheckers application separated by spaces
     */
    public Collection<String> listOfPlayer(Player player){
        Collection<String> list = new LinkedList<>();
        for (String name: playersList.keySet()){
            if (!name.equals(player.getName())){
                list.add(name);
            }
        }
        if (list.isEmpty()){
            return null;
        }
        return list;
    }

    /**
     * gets the number of other players currently signed into the webcheckers application
     *
     * @return
     *    the number of other players currently signed into the webcheckers application
     */
    public int getNumOfOthers(){
        numOfOthers = 0;
        for (String name: playersList.keySet()){
            numOfOthers++;
        }
        return numOfOthers;
    }

    /**
     * updates the number of players currently signed into the webcheckers application
     * @param numOfOthers the number of other players currently signed into the webcheckers application
     */
    public void updateNumOfPlayers(int numOfOthers) {
        this.numOfOthers = numOfOthers;
    }

    /**
     * removes a player from the list of players currently signed into the webcheckers application
     * @param player the player that is to be removed
     */
    public void removePlayer(Player player){
        playersList.remove(player.getName());
    }
    /**
     * checks if a name is already in the hashmap of all players signed into the webcheckers app excluding the user
     *
     * @param name
     *    the name to be checked
     *
     * @return
     *    true if the hashmap contains the name, false otherwise
     */
    public boolean containsName(String name){
        return playersList.containsKey(name);
    }

}
