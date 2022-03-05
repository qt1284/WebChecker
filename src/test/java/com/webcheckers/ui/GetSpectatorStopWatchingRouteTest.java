package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetSpectatorStopWatchingRouteTest {
    private GetSpectatorStopWatchingRoute CuT;
    private GameCenter gameCenter;
    private Request request;
    private Session session;
    private Response response;
    private CheckerGame game;
    private TemplateEngine engine;
    private PlayerLobby playerLobby;
    private final Player player = new Player("player");
    private final Player opponent = new Player("opponent");

    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        engine = mock(TemplateEngine.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        gameCenter = mock(GameCenter.class);
        game = mock(CheckerGame.class);
        playerLobby = mock(PlayerLobby.class);
        CuT = new GetSpectatorStopWatchingRoute(engine, playerLobby,gameCenter);
    }
    @Test
    public void stopWatchingTest() {
        when(request.session().attribute(PostSignInRoute.PLAYER_ATTR)).thenReturn(player);
        CuT.handle(request, response);
        verify(response).redirect("/");
    }
}