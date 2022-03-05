package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.*;

import static com.webcheckers.ui.GetGameRoute.GAME_ID_ATTR;
import static com.webcheckers.ui.PostSignInRoute.PLAYER_ATTR;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetSpectatorGameRouteTest {
    private GetSpectatorGameRoute CuT;
    private GameCenter gameCenter;
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;
    private PlayerLobby playerLobby;
    private CheckerGame game;
    private final Player player = new Player("player");
    private final Player red = new Player("red");
    private final Player white = new Player("white");
    private final Player opponent = new Player("opponent");
    private GetSpectatorGameRoute getSpectatorGameRoute;

    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        gameCenter = mock(GameCenter.class);
        game = mock(CheckerGame.class);
        engine = mock(TemplateEngine.class);
        playerLobby = mock(PlayerLobby.class);
        CuT = new GetSpectatorGameRoute(engine,gameCenter,playerLobby);
    }
    @Test
    public void spectatorTest() {
        when(request.queryParams(eq(GAME_ID_ATTR))).thenReturn("1111");
        when(request.session().attribute(PostSignInRoute.PLAYER_ATTR)).thenReturn(player);
        when(gameCenter.getGameFromID("1111")).thenReturn(game);
        when(game.getRedPlayer()).thenReturn(red);
        when(game.getWhitePlayer()).thenReturn(white);
        when(gameCenter.checkTwoPlayer(player,red)).thenReturn(Boolean.TRUE);
        TemplateEngineTester templateTester = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(templateTester.makeAnswer());
        CuT.handle(request, response);
        templateTester.assertViewModelExists();
        templateTester.assertViewModelIsaMap();
        templateTester.assertViewModelAttribute(
                "currentUser",player);
    }

}