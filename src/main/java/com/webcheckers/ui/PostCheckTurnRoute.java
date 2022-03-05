package com.webcheckers.ui;


import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;


import static com.webcheckers.ui.PostSignInRoute.PLAYER_ATTR;


public class PostCheckTurnRoute implements Route {

    private final GameCenter gameCenter;

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     * @param gameCenter the current gamecenter
     */
    public PostCheckTurnRoute(GameCenter gameCenter) {
        this.gameCenter = gameCenter;

    }

    /**
     * Renders the post check turn route
     * @param request the HTTP request
     * @param response the HTTP response
     * @return the rendered HTML for the page post the check turn in route
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session httpSession = request.session();
        Gson gson = new Gson();
        Player player = httpSession.attribute(PLAYER_ATTR);
        Message message;
        Player winner = gameCenter.getWinner();
        if (player.equals(winner)){
            message = Message.info("true");
        } else {
            if (gameCenter.isActive(player)) {
                message = Message.info("true");
            } else {
                message = Message.info("false");
            }
        }
        gameCenter.getGame(player).addTimeCount(5);

        return new Gson().toJson(message);
    }
}
