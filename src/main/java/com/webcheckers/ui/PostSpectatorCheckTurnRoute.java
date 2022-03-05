package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static com.webcheckers.ui.GetGameRoute.VIEW_NAME;
import static com.webcheckers.ui.PostSignInRoute.PLAYER_ATTR;
import static com.webcheckers.ui.WebServer.HOME_URL;

public class PostSpectatorCheckTurnRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());
    private final TemplateEngine templateEngine;
    private final GameCenter gameCenter;
    private final PlayerLobby playerLobby;
    /**
     * Create the Spark Route (UI controller) to handle all {@code POST / SPECTATOR/ GAME} HTTP requests.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public PostSpectatorCheckTurnRoute(final TemplateEngine templateEngine, GameCenter gameCenter, PlayerLobby playerLobby){
        this.playerLobby = Objects.requireNonNull(playerLobby,"PlayerLobby is required");
        this.gameCenter = Objects.requireNonNull(gameCenter,"gameCenter is required");
        this.templateEngine = Objects.requireNonNull(templateEngine,"templateEngine is required");
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
        String gameID = request.queryParams("gameID");
        CheckerGame game = gameCenter.getGameFromID(gameID);
        final Session httpSession = request.session();
        if (game == null){
            return new Gson().toJson(Message.info("true"));
        }
        if (!gameCenter.isGameOver() && !gameCenter.isTurnSubmit())
        {

            return new Gson().toJson(Message.info(game.printTime()));
        } else {
            gameCenter.increaseSpectatorCount();
            if (gameCenter.checkSpectator()){
                gameCenter.setTurnSubmit(true);
            } else {
                gameCenter.setSpectatorCount(0);
                gameCenter.setTurnSubmit(false);
            }
            return new Gson().toJson(Message.info("true"));
        }


    }
}
