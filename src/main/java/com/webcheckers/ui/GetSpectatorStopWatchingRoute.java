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

public class GetSpectatorStopWatchingRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());
    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;
    private final GameCenter gameCenter;
    /**
     * Create the Spark Route (UI controller) to handle all {@code GET / SPECTATOR/ GAME} HTTP requests.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public GetSpectatorStopWatchingRoute(final TemplateEngine templateEngine, PlayerLobby playerLobby, GameCenter gameCenter){
        this.gameCenter = Objects.requireNonNull(gameCenter,"gameCenter is required");
        this.templateEngine = Objects.requireNonNull(templateEngine,"templateEngine is required");
        this.playerLobby = Objects.requireNonNull(playerLobby,"playerLobby is required");
        LOG.config("GetSpectatorStopWatchingRoute is initialized.");
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
        Gson gson = new Gson();
        LOG.finer("GetSpectatorStopWatchingRoute is invoked.");
        final Session httpSession = request.session();
        Player player = httpSession.attribute(PLAYER_ATTR);
        playerLobby.setSpectating(player,false);
        gameCenter.setSpectatorMax(gameCenter.getSpectatorMax()-1);
        response.redirect(HOME_URL);
        return null;

    }
}
