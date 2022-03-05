package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import spark.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.webcheckers.ui.PostHomeRoute.OPPONENT_ATTR;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PostCheckTurnRouteTest {
    private PostHomeRoute CuT;
    private GameCenter gameCenter;
    private Request request;
    private Session session;
    private Response response;
    private CheckerGame game;
    private final Player player = new Player("player");
    private final Player opponent = new Player("opponent");
    private PostCheckTurnRoute postCheckTurnRoute;

    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        gameCenter = mock(GameCenter.class);
        game = mock(CheckerGame.class);
        postCheckTurnRoute = new PostCheckTurnRoute(gameCenter);
    }
    @Test
    public void checkTurn() throws Exception {
        when(request.session().attribute(PostSignInRoute.PLAYER_ATTR)).thenReturn(player);
        when(gameCenter.isActive(player)).thenReturn(true);
        when(gameCenter.getGame(player)).thenReturn(game);
        Object json = postCheckTurnRoute.handle(request,response);
        assertEquals("{\"text\":\"true\",\"type\":\"INFO\"}",json);
    }
}