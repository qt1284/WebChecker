package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import com.webcheckers.util.Message;

import static com.webcheckers.ui.GetHomeRoute.*;
import static com.webcheckers.ui.PostSignInRoute.*;
import static com.webcheckers.ui.WebServer.GAME_URL;
import static com.webcheckers.ui.WebServer.SPECTATOR_GAME_URL;

public class PostHomeRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());
    static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");
    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;
    private final GameCenter gameCenter;
    static final String ERROR_PLAYER_IN_GAME = "This player is currently in a game!";
    static final String ERROR_PLAYER_IN_SPECTATOR = "This player is currently spectating a game!";
    static final String ERROR_PLAYER_REPEATED = "You CAN'T pick yourself!";
    static final String OPPONENT_ATTR = "opponent";

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public PostHomeRoute(final TemplateEngine templateEngine, PlayerLobby playerLobby, GameCenter gameCenter) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        Objects.requireNonNull(playerLobby, "gameCenter must not be null");
        //
        this.playerLobby = playerLobby;
        this.gameCenter = gameCenter;
        LOG.config("GetHomeRoute is initialized.");
    }

    /**
     * Render the WebCheckers Home page.
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   the rendered HTML for the Home page
     */
    @Override
    public Object handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();
        final Session httpSession = request.session();
        final String opp = request.queryParams(OPPONENT_ATTR);
        Player player = httpSession.attribute(PLAYER_ATTR);
        final String spec = request.queryParams("spectator");
        Player opponent = new Player(opp);
        Player spectator = new Player(spec);



        if (opp!= null){
            if (gameCenter.isPlayerInGame(opponent)) {
                display(vm, player);
                vm.put(ERROR_TYPE, ERROR_PLAYER_IN_GAME);
                return templateEngine.render(new ModelAndView(vm, GetHomeRoute.VIEW_NAME));
            } else if (playerLobby.isSpectating(opponent)){

                display(vm, player);
                vm.put(ERROR_TYPE, ERROR_PLAYER_IN_SPECTATOR);
                return templateEngine.render(new ModelAndView(vm, GetHomeRoute.VIEW_NAME));
            } else {
                gameCenter.addPlayersPlaying(player,opponent);
                httpSession.attribute(OPPONENT_ATTR,opponent);
                response.redirect(GAME_URL);
                return null;
            }
        } else {
            if (gameCenter.checkTwoPlayer(spectator,player)) {
                display(vm, player);
                vm.put(ERROR_TYPE, ERROR_PLAYER_REPEATED);
                return templateEngine.render(new ModelAndView(vm, GetHomeRoute.VIEW_NAME));
            } else {
                response.redirect(SPECTATOR_GAME_URL);
                return null;
            }
        }


    }

    /**
     * Updates the vm map with information to display
     *
     * @param vm
     *    the hash map of messages and attributes
     * @param player
     *     A player tha is currently playing in the lobby
     */
    private void display(final Map<String,Object> vm, Player player){
        vm.put("title","Welcome!");
        vm.put(MESSAGE, WELCOME_MSG);
        vm.put(GAME_PLAYING,gameCenter.gamesPlaying());
        vm.put(LIST_OF_PLAYERS,playerLobby.listOfPlayer(player));
        vm.put(PLAYER_ATTR,player.getName());
        vm.put("currentUser",player);
    }

}