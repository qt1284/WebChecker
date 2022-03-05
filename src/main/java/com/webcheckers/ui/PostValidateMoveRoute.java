package com.webcheckers.ui;


import com.google.gson.Gson;
import com.webcheckers.model.AIPlayer;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.Objects;
import java.util.logging.Logger;

import static com.webcheckers.ui.PostSignInRoute.PLAYER_ATTR;
import static spark.Spark.halt;

public class PostValidateMoveRoute implements Route {

    private final GameCenter gameCenter;

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     * @param gameCenter the current gamecenter
     */
    public PostValidateMoveRoute(GameCenter gameCenter) {
        Objects.requireNonNull(gameCenter, "gameCenter must not be null");
        this.gameCenter = gameCenter;

    }

    /**
     * renders the post validate move route
     * @param request the HTTP request
     * @param response the HTTP response
     * @return the rendered HTML for the page post validate move route

     */
    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session httpSession = request.session();
        Gson gson = new Gson();
        String param = request.queryParams("actionData");
        Player player = httpSession.attribute(PLAYER_ATTR);
            //get game between 2 player
            CheckerGame game = gameCenter.getGame(player);
            //store move from browser
            Move move = gson.fromJson(param, Move.class);
            //check if move is valid, return INFO message if true, else ERROR
            Message message = game.isValidMove(move);
            return new Gson().toJson(message);
        }

}