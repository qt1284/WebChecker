package com.webcheckers.ui;


import com.google.gson.Gson;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.logging.Logger;

import static com.webcheckers.ui.PostSignInRoute.PLAYER_ATTR;
import static spark.Spark.halt;

public class PostBackupMoveRoute implements Route {

    private final GameCenter gameCenter;

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     * @param gameCenter the current gamecenter
     */
    public PostBackupMoveRoute(GameCenter gameCenter) {
        this.gameCenter = gameCenter;

    }

    /**
     * Renders the post backup move route
     * @param request the HTTP request
     * @param response the HTTP response
     * @return the rendered HTML for the page post the backup move route
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {
        Gson gson = new Gson();
        String a = "asd";
        final Session httpSession = request.session();
        Player player = httpSession.attribute(PLAYER_ATTR);
        CheckerGame game = gameCenter.getGame(player);
        game.removeMove();
        Message message = Message.info("Back up successfully!");
        return new Gson().toJson(message);
    }
}
