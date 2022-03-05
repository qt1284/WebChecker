package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;

import static com.webcheckers.ui.PostSignInRoute.PLAYER_ATTR;
import static com.webcheckers.ui.WebServer.HOME_URL;

/**
 * UI Controller to GET the GameRoute
 */
public class GetGameRoute implements Route {

    private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());
    private final TemplateEngine templateEngine;
    private final GameCenter gameCenter;
    static final String VIEW_NAME = "game.ftl";
    static final String GAME_ID_ATTR = "gameID";
    public enum PlayMode {
        PLAY,
        SPECTATOR
    }
    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     * @param templateEngine the HTML template rendering engine
     * @param gameCenter the current active gamecenter
     */

    public GetGameRoute(final TemplateEngine templateEngine,GameCenter gameCenter){
        this.templateEngine = Objects.requireNonNull(templateEngine,"templateEngine is required");
        Objects.requireNonNull(gameCenter, "gameCenter must not be null");
        LOG.config("GetLoginRoute is initialized.");
        this.gameCenter = gameCenter;
    }

    /**
     * Render the webcheckers game page
     * @param request the HTTP request
     * @param response the HTTP response
     * @return the rendered HTML for the Game page
     */
    @Override
    public Object handle(Request request, Response response){
        LOG.finer("GetGameRoute is invoked.");
        Map<String, Object> vm = new HashMap<>();
        Session httpSession = request.session();
        Player player = httpSession.attribute(PLAYER_ATTR);
        String gameID;
        Player red;
        Player white;
        if ( player == null ) {
            response.redirect(HOME_URL);
            return null;
        } else {
            Gson gson = new Gson();

            CheckerGame game = gameCenter.getGame(player);
            if (game == null){
                response.redirect(HOME_URL);
                return null;
            } else {
                gameID = UUID.randomUUID().toString();
                gameCenter.addGameID(gameID,game);
            }

            red = game.getRedPlayer();
            white = game.getWhitePlayer();

            BoardView board;
            vm.put("currentUser",red);

            if (gameCenter.checkTwoPlayer(player,red)){
                vm.put("currentUser",red);
                board = game.getBoard();
            } else {
                vm.put("currentUser",white);
                board = game.getFlippedBoard();
            }
            vm.put("title", "Play a game");
            vm.put("viewMode",PlayMode.PLAY);
            vm.put("redPlayer",red);
            vm.put("whitePlayer",white);
            vm.put("activeColor",game.getActiveColor());
            vm.put("board",board);

            if (gameCenter.ifNoMorePieces(board,red,white)){
                gameCenter.updateWinner(true,player,gameCenter.getOpponent(player));
            }

            final Map<String, Object> modeOptions = new HashMap<>(2);
            modeOptions.put("isGameOver", gameCenter.getGameOver());
            modeOptions.put("gameOverMessage", gameCenter.getWinningMessage());
            vm.put("modeOptionsAsJSON", gson.toJson(modeOptions));
            httpSession.attribute("isGameOver",gameCenter.getGameOver());
            return templateEngine.render(new ModelAndView(vm , VIEW_NAME));
        }



    }
}