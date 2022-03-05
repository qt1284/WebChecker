package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import spark.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.webcheckers.ui.PostHomeRoute.OPPONENT_ATTR;
import static com.webcheckers.ui.WebServer.SPECTATOR_GAME_URL;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Tag("UI-tier")
class PostHomeRouteTest {
    private PostHomeRoute CuT;
    private GameCenter gameCenter;
    private PlayerLobby playerLobby;
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;
    private final Player player = new Player("player");
    private final Player opponent = new Player("opponent");
    private final Player spectator = new Player("spectator");
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        engine = mock(TemplateEngine.class);
        response = mock(Response.class);
        playerLobby = mock(PlayerLobby.class);
        gameCenter = mock(GameCenter.class);
        CuT = new PostHomeRoute(engine, playerLobby, gameCenter);
    }

    /**
     * Test error when opponent is not in a game
     */
    @Test
    public void error_1(){
        when(request.session().attribute(PostSignInRoute.PLAYER_ATTR)).thenReturn(player);
        when(request.queryParams(eq(OPPONENT_ATTR))).thenReturn(String.valueOf(opponent));
        when(playerLobby.containsName(opponent.getName())).thenReturn(true);
        when(gameCenter.isPlayerInGame(opponent)).thenReturn(true);

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        //   * model contains all necessary View-Model data
        testHelper.assertViewModelAttribute(
                GetHomeRoute.TITLE_ATTR, GetHomeRoute.WELCOME);
        testHelper.assertViewModelAttribute(
                GetHomeRoute.MESSAGE, PostHomeRoute.WELCOME_MSG);
        testHelper.assertViewModelAttribute(
                PostSignInRoute.ERROR_TYPE, PostHomeRoute.ERROR_PLAYER_IN_GAME);
        testHelper.assertViewName(GetHomeRoute.VIEW_NAME);

    }
    @Test
    public void error_2(){
        when(request.session().attribute(PostSignInRoute.PLAYER_ATTR)).thenReturn(player);
        when(request.queryParams(eq(OPPONENT_ATTR))).thenReturn(String.valueOf(opponent));
        when(gameCenter.isPlayerInGame(opponent)).thenReturn(false);
        playerLobby.setSpectating(player,true);
        when(playerLobby.isSpectating(opponent)).thenReturn(true);
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        CuT.handle(request, response);
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        //   * model contains all necessary View-Model data
        testHelper.assertViewModelAttribute(
                GetHomeRoute.TITLE_ATTR, GetHomeRoute.WELCOME);
        testHelper.assertViewModelAttribute(
                GetHomeRoute.MESSAGE, PostHomeRoute.WELCOME_MSG);
        testHelper.assertViewModelAttribute(
                PostSignInRoute.ERROR_TYPE, PostHomeRoute.ERROR_PLAYER_IN_SPECTATOR);
        testHelper.assertViewName(GetHomeRoute.VIEW_NAME);

    }

    /**
     * tests to make sure it redirects to game when conditions are met
     */
    @Test
    public void redirectGame() {
        when(request.session().attribute(PostSignInRoute.PLAYER_ATTR)).thenReturn(player);
        when(request.queryParams(eq(OPPONENT_ATTR))).thenReturn(String.valueOf(opponent));
        when(playerLobby.containsName(opponent.getName())).thenReturn(true);
        when(gameCenter.isPlayerInGame(opponent)).thenReturn(false);
        when(gameCenter.checkTwoPlayer(opponent,player)).thenReturn(false);
        CuT.handle(request, response);
        verify(response).redirect("/game");

    }

    @Test
    public void error_3(){
        when(request.session().attribute(PostSignInRoute.PLAYER_ATTR)).thenReturn(player);
        when(request.queryParams(eq(OPPONENT_ATTR))).thenReturn(null);
        when(gameCenter.checkTwoPlayer(spectator,player)).thenReturn(true);
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        CuT.handle(request, response);
        verify(response).redirect(SPECTATOR_GAME_URL);
//        testHelper.assertViewModelExists();
//        testHelper.assertViewModelIsaMap();
//        //   * model contains all necessary View-Model data
//        testHelper.assertViewModelAttribute(
//                GetHomeRoute.TITLE_ATTR, GetHomeRoute.WELCOME);
//        testHelper.assertViewModelAttribute(
//                GetHomeRoute.MESSAGE, PostHomeRoute.WELCOME_MSG);
//        testHelper.assertViewModelAttribute(
//                PostSignInRoute.ERROR_TYPE, PostHomeRoute.ERROR_PLAYER_REPEATED);
//        testHelper.assertViewName(GetHomeRoute.VIEW_NAME);

    }

}