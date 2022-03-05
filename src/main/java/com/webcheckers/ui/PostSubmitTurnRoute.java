package com.webcheckers.ui;


import com.google.gson.Gson;
import com.webcheckers.model.AIPlayer;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.logging.Logger;

import static com.webcheckers.ui.PostSignInRoute.PLAYER_ATTR;
import static spark.Spark.halt;

public class PostSubmitTurnRoute implements Route {

    private final GameCenter gameCenter;

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     * @param gameCenter the current gamecenter
     */
    public PostSubmitTurnRoute(GameCenter gameCenter) {
        this.gameCenter = gameCenter;

    }

    /**
     * renders the post submit turn route
     * @param request the HTTP request
     * @param response the HTTP response
     * @return the rendered HTML for the page post submit turn route

     */
    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session httpSession = request.session();
        Gson gson = new Gson();
        Player player = httpSession.attribute(PLAYER_ATTR);
        Message message;
//        if (gameCenter.isPlayerInGame(player)){
        if (gameCenter.getGame(player).getWhitePlayer().getName().equals("BOT")) {
            if (gameCenter.updateTurn(player)) {
                message = Message.info("Submit turn successfully!");
            } else {
                message = Message.error("You have to make another jump!");
            }
            //response.redirect("/validateMove");
            gameCenter.MakeMoveAI(player);
            return new Gson().toJson(message);
        } else {
            if (gameCenter.isActive(player)) {
                if (gameCenter.updateTurn(player)){
                    gameCenter.getGame(player).setTimeCount(0);
                    gameCenter.setTurnSubmit(true);
                    message = Message.info("Submit turn successfully!");

                } else {
                    message = Message.error("You have to make another jump!");
                }
            } else {
                message = Message.error("You have finished your turn!");
            }

//        }

            return new Gson().toJson(message);
        }
    }
}
