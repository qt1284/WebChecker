package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.webcheckers.ui.PostHomeRoute.OPPONENT_ATTR;
import static com.webcheckers.ui.PostSignInRoute.PLAYER_ATTR;
import static com.webcheckers.ui.WebServer.HOME_URL;

public class PostSignOutRoute implements Route {
    private final PlayerLobby playerLobby;
    static final String PLAYER_ATTR = "player";
    private final TemplateEngine templateEngine;
    static final String VIEW_SIGN_IN = "signin.ftl";
    private final GameCenter gameCenter;


    /**
     * The constructor for the {@code POST /SignOut} route handler.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     * @param playerLobby
     *   PlayerLobby object that holds all players currently logged into webcheckers application
     *
     * @throws NullPointerException
     *   when {@code templateEngine} parameter is null
     */
    public PostSignOutRoute (TemplateEngine templateEngine, PlayerLobby playerLobby,GameCenter gameCenter) {
        this.playerLobby = playerLobby;
        this.gameCenter = gameCenter;
        Objects.requireNonNull(playerLobby, "gameCenter must not be null");
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        this.templateEngine = templateEngine;
    }


    /**
     * renders post signin page
     *
     * @param request
     *   the http request
     * @param response
     *   the http response
     *
     * @return
     *   the rendered HTML for the post sign in page
     */
    @Override
    public String handle(Request request, Response response) {
        // start building the View-Model
        final Session httpSession = request.session();
        Map<String, Object> vm = new HashMap<>();
        Player player = httpSession.attribute(PLAYER_ATTR);
        Player opponent = httpSession.attribute(OPPONENT_ATTR);
        CheckerGame game = gameCenter.getGame(player);
        if (gameCenter.isPlayerInGame(player)){
            gameCenter.updateWinner(true,gameCenter.getOpponent(player),player);
        }
        playerLobby.updateNumOfPlayers(playerLobby.getNumOfOthers()-1);
        playerLobby.removePlayer(player);
        //gameCenter.removePlayerPlaying(player,opponent);
        httpSession.attribute(PLAYER_ATTR,null);
        response.redirect(HOME_URL);
        return null;

    }
}
