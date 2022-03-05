package com.webcheckers.ui;

import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import spark.*;
import org.junit.jupiter.api.Test;

import static com.webcheckers.ui.PostHomeRoute.OPPONENT_ATTR;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GetSignInRouteTest {

    private Response response;
    private GetSignInRoute CuT;
    private Request request;
    private Session session;
    private TemplateEngine engine;

    @BeforeEach
    public void setup() {

        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        engine = mock(TemplateEngine.class);
        response = mock(Response.class);
        CuT = new GetSignInRoute(engine);

    }

    @Test
    public void testSignInRouteNotNull(){
        GetSignInRoute getSignInRoute = new GetSignInRoute(engine);
        assertNotNull(getSignInRoute, "getSignInRoute is not null.");
    }

    @Test
    public void testNullPlayer() {
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        when(request.session().attribute("player")).thenReturn(null);
        assertNotNull(when(request.session().attribute("player")).thenReturn(null));
    }

    @Test
    public void error() {
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        CuT.handle(request,response);
    }
    @Test
    public void redirectGame() {
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        Player player = new Player("asd");
        when(request.session().attribute("player")).thenReturn(player);
        CuT.handle(request, response);
        verify(response).redirect("/");

    }

}
