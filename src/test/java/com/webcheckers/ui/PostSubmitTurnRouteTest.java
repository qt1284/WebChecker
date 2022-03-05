package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

import static com.webcheckers.ui.PostHomeRoute.OPPONENT_ATTR;
import static com.webcheckers.ui.PostSignInRoute.PLAYER_ATTR;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("UI-tier")
class PostSubmitTurnRouteTest {
    private PostSignInRoute CuT;
    private GameCenter gameCenter;
    private PlayerLobby playerLobby;
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;
    private final Player player = new Player("player");
    private final Player white  = new Player("white");
    private PostSubmitTurnRoute postSubmitTurnRoute;
    private CheckerGame game;
    //private final Player player2 = new Player("player2");

    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        game = mock(CheckerGame.class);
        when(request.session()).thenReturn(session);
        engine = mock(TemplateEngine.class);
        response = mock(Response.class);
        playerLobby = mock(PlayerLobby.class);
        gameCenter = mock(GameCenter.class);
        CuT = new PostSignInRoute(engine, playerLobby);
        postSubmitTurnRoute = new PostSubmitTurnRoute(gameCenter);
    }
    @Test
    public void messageInfo() throws Exception {
        when(request.session().attribute(PLAYER_ATTR)).thenReturn(player);
        when(gameCenter.getGame(player)).thenReturn(game);
        when(gameCenter.isActive(player)).thenReturn(true);
        when(game.getWhitePlayer()).thenReturn(white);
        when(gameCenter.updateTurn(player)).thenReturn(true);
        Object json = postSubmitTurnRoute.handle(request,response);
        assertEquals("{\"text\":\"Submit turn successfully!\",\"type\":\"INFO\"}",json);
    }
    @Test
    public void messageError1() throws Exception {
        when(request.session().attribute(PLAYER_ATTR)).thenReturn(player);
        when(gameCenter.getGame(player)).thenReturn(game);
        when(game.getWhitePlayer()).thenReturn(white);
        when(gameCenter.isActive(player)).thenReturn(false);

        Object json = postSubmitTurnRoute.handle(request,response);
        assertEquals("{\"text\":\"You have finished your turn!\",\"type\":\"ERROR\"}",json);
    }

    @Test
    public void messageError2() throws Exception {
        when(request.session().attribute(PLAYER_ATTR)).thenReturn(player);
        when(gameCenter.getGame(player)).thenReturn(game);
        when(game.getWhitePlayer()).thenReturn(white);
        when(gameCenter.isActive(player)).thenReturn(true);
        when(gameCenter.updateTurn(player)).thenReturn(false);

        Object json = postSubmitTurnRoute.handle(request,response);
        assertEquals("{\"text\":\"You have to make another jump!\",\"type\":\"ERROR\"}",json);
    }

}