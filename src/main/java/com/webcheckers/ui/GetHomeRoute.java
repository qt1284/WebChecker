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

import static com.webcheckers.ui.PostHomeRoute.OPPONENT_ATTR;
import static com.webcheckers.ui.PostSignInRoute.*;
import static com.webcheckers.ui.WebServer.GAME_URL;
import static com.webcheckers.ui.WebServer.HOME_URL;

    /**
    * The UI Controller to GET the Home page.
    *
    * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
    */
    public class GetHomeRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());
    private static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");
    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;
    static final String TITLE_ATTR = "title";
    static final String VIEW_NAME = "home.ftl";
    static final String WELCOME = "Welcome!";
    static final String MESSAGE = "message";
    static final String LIST_OF_PLAYERS = "listOfPlayers";
        static final String GAME_PLAYING = "listOfGames";
    static final String NUMBER_OF_PLAYER = "numOfPlayer";
    private final GameCenter gameCenter;
    /**
    * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
    *
    * @param templateEngine
    *   the HTML template rendering engine
    */
    public GetHomeRoute(final TemplateEngine templateEngine, PlayerLobby playerLobby, GameCenter gameCenter) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    Objects.requireNonNull(playerLobby, "gameCenter must not be null");
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
        LOG.finer("GetHomeRoute is invoked.");
        final Session httpSession = request.session();
        Map<String, Object> vm = new HashMap<>();
        vm.put(TITLE_ATTR, WELCOME);
        vm.put(MESSAGE, WELCOME_MSG);
        Player player = httpSession.attribute(PLAYER_ATTR);
        Player opponent;
        Boolean gameOver = httpSession.attribute("isGameOver");
        if (player != null){

            if (gameCenter.isPlayerInGame(player)){
                opponent = gameCenter.getOpponent(player);
                httpSession.attribute(OPPONENT_ATTR,opponent);
                if (gameOver!=null && gameOver && gameCenter.isGameOver()){
                    gameCenter.removePlayerPlaying(player,opponent);
                    gameCenter.setTurnSubmit(false);
                    gameCenter.setGameOver(false);
                    gameCenter.setResign(false);
                    httpSession.attribute("isGameOver",false);
                }
                response.redirect(GAME_URL);
                return null;
            }


            vm.put(PLAYER_ATTR,player.getName());
            vm.put(GAME_PLAYING,gameCenter.gamesPlaying());
            vm.put(LIST_OF_PLAYERS,playerLobby.listOfPlayer(player));
            vm.put("currentUser",player);


        } else {
            String numOfPlayer = String.valueOf(playerLobby.getNumOfOthers()-1);
            vm.put(NUMBER_OF_PLAYER  ,numOfPlayer);
        }


        // render the View
        return templateEngine.render(new ModelAndView(vm , VIEW_NAME));
  }

}
