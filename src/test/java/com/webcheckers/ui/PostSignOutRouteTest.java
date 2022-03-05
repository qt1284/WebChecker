package com.webcheckers.ui;

import static com.webcheckers.ui.PostSignInRoute.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static com.webcheckers.ui.PostHomeRoute.OPPONENT_ATTR;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;

import spark.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.TemplateEngine;

@Tag("UI-tier")
public class PostSignOutRouteTest {
    private PostSignOutRoute CuT;
    private GameCenter gameCenter;
    private PlayerLobby playerLobby;
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;
    private String userName = "user";
    private Player player = new Player("player");
    private Player opponent = new Player("opponent");

    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        engine = mock(TemplateEngine.class);
        response = mock(Response.class);
        playerLobby = mock(PlayerLobby.class);
        gameCenter = mock(GameCenter.class);
        CuT = new PostSignOutRoute(engine, playerLobby, gameCenter);

    }

    @Test
    public void testPostSignOutRouteNotNull() {
        assertNotNull(CuT, "PostSignOutRoute is not null.");
    }

    @Test
    public void error1() {
        when(request.session().attribute(PostSignOutRoute.PLAYER_ATTR)).thenReturn(player);
        when(request.session().attribute(OPPONENT_ATTR)).thenReturn(opponent);
        when(gameCenter.isPlayerInGame(player)).thenReturn(true);
        CuT.handle(request, response);

        verify(response).redirect("/");

    }

    @Test
    public void error2() {
        when(gameCenter.isPlayerInGame(player)).thenReturn(true);
        CuT.handle(request, response);

        verify(response).redirect("/");
    }
}

