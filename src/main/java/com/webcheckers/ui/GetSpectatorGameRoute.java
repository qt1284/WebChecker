package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static com.webcheckers.ui.GetGameRoute.VIEW_NAME;
import static com.webcheckers.ui.PostSignInRoute.PLAYER_ATTR;
import static com.webcheckers.ui.WebServer.HOME_URL;

public class GetSpectatorGameRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());
    private final TemplateEngine templateEngine;
    private final GameCenter gameCenter;
    private final PlayerLobby playerLobby;
    private final String GAME_ID_ATTR = "gameID";
    /**
     * Create the Spark Route (UI controller) to handle all {@code GET / SPECTATOR/ GAME} HTTP requests.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public GetSpectatorGameRoute(final TemplateEngine templateEngine, GameCenter gameCenter,PlayerLobby playerLobby){
        this.gameCenter = Objects.requireNonNull(gameCenter,"gameCenter is required");
        this.templateEngine = Objects.requireNonNull(templateEngine,"templateEngine is required");
        this.playerLobby = Objects.requireNonNull(playerLobby,"playerLobby is required");
        LOG.config("GetSpectatorGameRoute is initialized.");
    }

    /**
     * renders spectator page
     *
     * @param request
     *   the http request
     * @param response
     *   the http response
     *
     * @return
     *   the rendered HTML for the sign in page
     */
    @Override
    public Object handle(Request request, Response response){
        LOG.finer("GetSpectatorGameRoute is invoked.");
        Map<String, Object> vm = new HashMap<>();
        final Session httpSession = request.session();
        Gson gson = new Gson();
        Player player = httpSession.attribute(PLAYER_ATTR);
        playerLobby.setSpectating(player,true);
        String gameID = request.queryParams(GAME_ID_ATTR);
        CheckerGame game = gameCenter.getGameFromID(gameID);
        if (game == null){
            playerLobby.setSpectating(player,false);
            gameCenter.setSpectatorMax(gameCenter.getSpectatorMax()-1);
            response.redirect(HOME_URL);
            return null;
        }
        gameCenter.setTurnSubmit(false);
        Player red;
        Player white;
        red = game.getRedPlayer();
        white = game.getWhitePlayer();
        BoardView board;
        vm.put("currentUser",player);
        board = game.getBoard();
        vm.put("title", "Play a game");
        vm.put("viewMode", GetGameRoute.PlayMode.SPECTATOR);
        vm.put("redPlayer",red);
        vm.put("whitePlayer",white);
        vm.put("activeColor",game.getActiveColor());
        vm.put("board",board);
        gameCenter.increaseSpectatorMax();
        final Map<String, Object> modeOptions = new HashMap<>(2);
        modeOptions.put("isGameOver", gameCenter.getGameOver());
        modeOptions.put("gameOverMessage", gameCenter.getWinningMessage());
        vm.put("modeOptionsAsJSON", gson.toJson(modeOptions));
        return templateEngine.render(new ModelAndView(vm , VIEW_NAME));

    }
}
