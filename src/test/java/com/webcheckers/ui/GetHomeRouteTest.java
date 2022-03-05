package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import spark.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static com.webcheckers.ui.PostHomeRoute.OPPONENT_ATTR;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
@Tag("UI-tier")

class GetHomeRouteTest {
    private Response response;
    private GetHomeRoute CuT;
    private Request request;
    private Session session;
    private TemplateEngine engine;
    private PlayerLobby playerLobby;
    private GameCenter gameCenter;

    @BeforeEach
    public void setup(){
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        engine = mock(TemplateEngine.class);
        response = mock(Response.class);
        playerLobby = mock(PlayerLobby.class);
        gameCenter = mock(GameCenter.class);
        CuT = new GetHomeRoute(engine, playerLobby, gameCenter);


    }
    @Test
    public void nullPlayer(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        when(request.session().attribute("player")).thenReturn(null);
        assertNotNull(when(request.session().attribute("player")).thenReturn(null));

    }
    @Test
    public void error(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request,response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute("title","Welcome!");
        testHelper.assertViewModelAttributeIsAbsent("currentUser");

    }
    @Test
    public void player(){
        Player player = new Player("test");
        Player other = new Player("other");
        when(request.session().attribute(PostSignInRoute.PLAYER_ATTR)).thenReturn(player);
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        request.session().attribute("player","test");
        session.attribute("player","test");
        CuT.handle(request,response);

    }
    @Test
    public void playerInGame(){
        Player player = new Player("test");
        Player other = new Player("other");
        when(request.session().attribute(PostSignInRoute.PLAYER_ATTR)).thenReturn(player);
        when(request.session().attribute(PostSignInRoute.PLAYER_ATTR)).thenReturn(other);
        when(gameCenter.isPlayerInGame(other)).thenReturn(true);
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        request.session().attribute("player","test");
        session.attribute("player","test");
        CuT.handle(request,response);
    }
}