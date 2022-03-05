package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

class GetGameRouteTest {
    private TemplateEngine template;
    private GameCenter gameCenter;
    private Session session;
    private Request request;
    private Response response;
    private Player player= new Player("player");
    private CheckerGame game;
    private Player red;// = new Player("red");
    private Player white;// = new Player("white");
    private BoardView board;
    private GetGameRoute CuT;
    private PlayerLobby playerLobby;
    @BeforeEach
    public void setUp(){
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        template = mock(TemplateEngine.class);
        response = mock(Response.class);
        red = new Player("red");
        white = new Player("white");
        playerLobby = mock(PlayerLobby.class);
        game = mock(CheckerGame.class);
        gameCenter = mock(GameCenter.class);
        board = mock(BoardView.class);
        CuT = new GetGameRoute(template,gameCenter);
    }

    @Test
    public void testGameRouteNotNull(){
        GetGameRoute getGameRoute = new GetGameRoute(template, gameCenter);
        assertNotNull(getGameRoute, "getGameRoute is not null.");
    }

    @Test
    public void playerIsRed(){
        when(request.session().attribute(PostSignInRoute.PLAYER_ATTR)).thenReturn(player);
        TemplateEngineTester templateTester = new TemplateEngineTester();
        when(template.render(any(ModelAndView.class))).thenAnswer(templateTester.makeAnswer());

        when(gameCenter.checkTwoPlayer(player,red)).thenReturn(Boolean.TRUE);
        when(gameCenter.getGame(player)).thenReturn(game);
        when(game.getRedPlayer()).thenReturn(red);
        when(game.getWhitePlayer()).thenReturn(white);
        CuT.handle(request, response);
        templateTester.assertViewModelExists();
        templateTester.assertViewModelIsaMap();
        templateTester.assertViewModelAttribute(
                "currentUser",red);

    }

    @Test
    public void playerIsWhite(){
        when(request.session().attribute(PostSignInRoute.PLAYER_ATTR)).thenReturn(player);
        TemplateEngineTester templateTester = new TemplateEngineTester();
        when(template.render(any(ModelAndView.class))).thenAnswer(templateTester.makeAnswer());

        when(gameCenter.checkTwoPlayer(player,red)).thenReturn(Boolean.FALSE);
        when(gameCenter.getGame(player)).thenReturn(game);
        when(game.getRedPlayer()).thenReturn(red);
        when(game.getWhitePlayer()).thenReturn(white);
        CuT.handle(request, response);
        templateTester.assertViewModelExists();
        templateTester.assertViewModelIsaMap();
        templateTester.assertViewModelAttribute(
                "currentUser",white);

    }
}