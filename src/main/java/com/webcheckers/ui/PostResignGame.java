package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import static com.webcheckers.ui.GetHomeRoute.LIST_OF_PLAYERS;
import static com.webcheckers.ui.GetHomeRoute.MESSAGE;
import static com.webcheckers.ui.PostHomeRoute.OPPONENT_ATTR;
import static com.webcheckers.ui.PostSignInRoute.PLAYER_ATTR;
import static com.webcheckers.ui.WebServer.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PostResignGame implements Route {
    private final GameCenter gameCenter;


    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     * @param gameCenter the current gamecenter
     */

    public PostResignGame(GameCenter gameCenter){
        Objects.requireNonNull(gameCenter, "gameCenter must not be null");
        this.gameCenter = gameCenter;
    }



    /**
     * renders the post resign game route
     * @param request the HTTP request
     * @param response the HTTP response
     * @return the rendered HTML for the page post resign game route

     */
    @Override
    public Object handle(Request request, Response response) {
        final Session httpSession = request.session();
        Player player = httpSession.attribute(PLAYER_ATTR);
        Player opponent;
        Message message;
        CheckerGame game = gameCenter.getGame(player);
        if (!game.getActivePlayer().equals(player)){
            message = Message.error("It is not your turn yet!");
        } else {
            gameCenter.setResign(true);
            opponent = gameCenter.getOpponent(player);
            gameCenter.updateWinner(true, opponent, player);
            message = Message.info(gameCenter.getWinner().getName());
        }
        return new Gson().toJson(message);
    }
}
