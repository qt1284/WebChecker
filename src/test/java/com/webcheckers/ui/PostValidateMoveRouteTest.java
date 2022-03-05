package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

import static com.webcheckers.ui.PostHomeRoute.OPPONENT_ATTR;
import static com.webcheckers.ui.PostSignInRoute.PLAYER_ATTR;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PostValidateMoveRouteTest {
    private PostHomeRoute CuT;
    private GameCenter gameCenter;
    private Request request;
    private Session session;
    private Response response;
    private CheckerGame game;
    private Player player = new Player("player");
    private final String winner = "winner";
    private Move move;
    private Message message;
    private Gson gson;
    private PostValidateMoveRoute postValidateMoveRoute;
    private TemplateEngine engine;

    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        gson = new Gson();
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        gameCenter = mock(GameCenter.class);
        game = mock(CheckerGame.class);
        postValidateMoveRoute = new PostValidateMoveRoute(gameCenter);
    }
    @Test
    public void notNullTest() throws Exception {
        when(request.session().attribute(PLAYER_ATTR)).thenReturn(player);
        when(request.queryParams(eq("actionData"))).thenReturn(String.valueOf(move));
        when(gameCenter.getGame(player)).thenReturn(game);
        when(game.isValidMove(move)).thenReturn(message);
        Object json = postValidateMoveRoute.handle(request,response);
        assertNotNull(json);
    }
}