package com.webcheckers.appl;

import com.webcheckers.model.*;
import com.webcheckers.ui.BoardView;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.*;


/**
 * The GameCenter that keeps track of players and the games they are playing
 */
public class GameCenter {

    private final Map<String,CheckerGame> playersPlaying;
    private final Map<String,CheckerGame> gameID;
    private Player winner;
    private Player loser;
    private int spectatorCount;
    private int spectatorMax;
    private boolean isGameOver;
    private boolean turnSubmit;
    private boolean isResign;
    /**
     * Creates a new GameCenter with new players and no new games
     */
    public GameCenter(){
        this.playersPlaying = new HashMap<>();
        gameID = new HashMap<>();
        winner = null;
        loser = null;
        isGameOver = false;
        turnSubmit = false;
        spectatorCount = 0;
        spectatorMax = 0;
        isResign = false;
    }


    /**
     * determines if it is the player's turn
     * @param player a player in the game
     * @return true if it is the player's turn, false otherwise
     */
    public boolean updateTurn(Player player){
        CheckerGame game = getGame(player);
        return game.applyMove();
    }

    /**
     * adds the gameID to a hashmap of gameIDs
     * @param gameID the gameID
     * @param game the current game
     */
    public void addGameID(String gameID,CheckerGame game){
        if (!this.gameID.containsValue(game))
            this.gameID.put(gameID, game);
    }

    /**
     * gets a game from a gameID
     * @param gameID the gameID
     * @return the game associated with the gameID
     */
    public CheckerGame getGameFromID(String gameID){
        return this.gameID.get(gameID);
    }


    /**
     * gets the hashmap with gameIDs and games
     * @return the list of game currently playing
     */
    public Map<String,CheckerGame> gamesPlaying() {
        if (gameID.isEmpty())
            return null;
        return gameID;
    }


    /**
     * counts the number of spectators
     * @param spectatorCount the number of spectators
     */
    public void setSpectatorCount(int spectatorCount) {
        this.spectatorCount = spectatorCount;
    }

    /**
     * getter method for spectator count
     * @return number of spectators
     */
    public int getSpectatorCount(){
        return this.spectatorCount;
    }

    /**
     * increases the number of spectators
     */
    public void increaseSpectatorCount() {
        this.spectatorCount++;
    }


    /**
     * gets the max number of spectators
     */
    public void increaseSpectatorMax() {

        this.spectatorMax++;
    }

    public int getSpectatorMax() {
        return spectatorMax;
    }

    public void setSpectatorMax(int spectatorMax) {
        this.spectatorMax = spectatorMax;
    }

    /**
     * compares the spectator count to the max number of spectators
     * @return true if spectator count is less than max spectator count, false otherwise
     */
    public boolean checkSpectator(){
        return this.spectatorCount < this.spectatorMax;
    }

    //get id from a particular game
    public String getIDFromGame(CheckerGame value){
        for (Map.Entry<String,CheckerGame> entry : gameID.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * Check if a player has resigned a game
     * @return true if has resigned, false otherwise
     */
    public boolean isResign() {
        return isResign;
    }

    /**
     * A setter for isResign
     * @param resign the value to set
     */
    public void setResign(boolean resign) {
        isResign = resign;
    }

    /**
     * Getting the winning message depends of different situation
     * @return resign message if a player has resigned, normal message otherwise
     */
    public String getWinningMessage(){
        if (isResign)
            return this.loser.getName() + " has resigned the game. " + this.winner.getName() + " wins the game!";
        else
            return this.winner + " has won the game!. Good luck next time " + this.loser;
    }

    /**
     * checks if a turn has been submitted
     * @return true if turn has been submitted, false otherwise
     */
    public boolean isTurnSubmit() {
        return turnSubmit;
    }


    /**
     * setter method for submitting a turn
     * @param turnSubmit true if turn has been submitted, false otherwise
     */
    public void setTurnSubmit(boolean turnSubmit) {
        this.turnSubmit = turnSubmit;
    }

    /**
     * determines whether the player is active
     * @param player a player in the game
     * @return true if the player is active, false otherwise
     */
    public boolean isActive(Player player){
        CheckerGame game = getGame(player);
        return player.equals(game.getActivePlayer());

    }

    /**
     * checks if the game is a two player game
     * @param current the current player
     * @param other the other player
     * @return true if the game is a two player game, false otherwise
     */
    public boolean checkTwoPlayer(Player current, Player other){
        return current.equals(other);
    }

    /**
     * getter method for the game board
     * @param game the current game
     * @return the current game board
     */
    public BoardView getBoard(CheckerGame game){
        return game.getBoardView();
    }

    /**
     * getter method for the flipped board
     * @param game the current game
     * @return the flipped game board
     */
    public BoardView getFlippedBoard(CheckerGame game){
        return game.getFlippedBoard();
    }

    /**
     * removes players from the game
     * @param red the red player
     * @param white the white player
     */
    public void removePlayerPlaying(Player red, Player white){
        String ID = getIDFromGame(getGame(red));
        gameID.remove(ID);
        ID = getIDFromGame(getGame(white));
        gameID.remove(ID);
        playersPlaying.remove(red.getName());
        playersPlaying.remove(white.getName());
    }

    /**
     * updates the winner of the game
     * @param winner the winning player
     */
    public void updateWinner(boolean result,Player winner,Player loser){
        this.isGameOver = result;
        this.winner = winner;
        this.loser = loser;
    }
    /**
     * Getting the winner when there is no more move
     * @return the winner
     */
    public boolean ifNoMorePieces(BoardView board, Player redPlayer,Player whitePlayer){

        if (board.endGame(Piece.Color.RED)) {
            this.winner = whitePlayer;
            return true;
        } else if (board.endGame(Piece.Color.WHITE)) {
            this.winner = redPlayer;
            return true;
        }
        return false;
    }

    /**
     * sets the variable indicating whether or not the game is over
     * @param gameOver true if game is over, false otherwise
     */
    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    /**
     * getter method for variable indicating if game is over
     * @return true if game is over, false otherwise
     */
    public boolean getGameOver(){
        return isGameOver;
    }

    /**
     * checks if the current game is over
     * @return true if game is over, false otherwise
     */
    public boolean isGameOver(){
        return this.isGameOver;
    }

    /**
     * gets the losing player of the game
     * @return the player that lost the game
     */
    public Player getLoser() {
        return loser;
    }


    /**
     * checks if the game is over
     * @param red the red player
     * @return true if game is over, false otherwise
     */
    public boolean isGameOver(Player red){
        CheckerGame game = getGame(red);
        if (game == null){
            return false;
        }
        return isGameOver;
    }

    /**
     * gets the winner of the game
     * @return the winning player
     */
    public Player getWinner() {
        return winner;
    }

    public void MakeMoveAI(Player p){
        AIPlayer AI = new AIPlayer("BOT");
        AI.MakeMove(getGame(p),this);
    }

    /**
     * adds players to the game
     * @param red the red player
     * @param white the white player
     */
    public void addPlayersPlaying(Player red, Player white){
        CheckerGame game = new CheckerGame(red,white);
        playersPlaying.put(red.getName(),game);
        playersPlaying.put(white.getName(),game);
    }
    public void addPlayersPlaying(Player red, Player white,int i ){
        CheckerGame game = new CheckerGame(red,white,i);
        playersPlaying.put(red.getName(),game);
        playersPlaying.put(white.getName(),game);
    }
    /**
     * getter method for the game
     * @param player the current player
     * @return the game for the player
     */
    public synchronized CheckerGame getGame(Player player){
        return playersPlaying.get(player.getName());
    }

    public synchronized Player getOpponent(Player player) {
        CheckerGame game = getGame(player);

        Player red = game.getRedPlayer();
        Player white = game.getWhitePlayer();
        if ( red != null && !red.equals(player) ) {
            return red;
        } else if ( white != null && !white.equals(player) ) {
            return white;
        } else{
            return null;
        }
    }

    /**
     * checks whether the player is currently in a game
     * @param player the player to check
     * @return true if player is in a game, false otherwise
     */
    public synchronized boolean isPlayerInGame(Player player){
        return playersPlaying.containsKey(player.getName());
    }
}