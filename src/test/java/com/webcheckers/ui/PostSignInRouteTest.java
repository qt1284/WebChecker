package com.webcheckers.ui;

import static com.webcheckers.ui.PostHomeRoute.OPPONENT_ATTR;
import static com.webcheckers.ui.PostSignInRoute.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;

import spark.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import spark.TemplateEngine;

@Tag("UI-tier")
public class PostSignInRouteTest {
        private PostSignInRoute CuT;
        private GameCenter gameCenter;
        private PlayerLobby playerLobby;
        private Request request;
        private Session session;
        private Response response;
        private TemplateEngine engine;
        private String userName = "user";
        private Player player = new Player("player");
    
        @BeforeEach
        public void setup() {
                request = mock(Request.class);
                session = mock(Session.class);
                when(request.session()).thenReturn(session);
                engine = mock(TemplateEngine.class);
                response = mock(Response.class);
                playerLobby = mock(PlayerLobby.class);
                gameCenter = mock(GameCenter.class);
                CuT = new PostSignInRoute(engine, playerLobby);
        }

        /**
        * Test descripting
        */
        @Test
        public void testPostSignInRouteNotNull(){
                PostSignInRoute PostSignInRoute = new PostSignInRoute(engine, playerLobby);
                assertNotNull(PostSignInRoute, "PostSignInRoute is not null.");
        }


        @Test
        public void error_1(){
                when(request.queryParams(eq(PLAYER_ATTR))).thenReturn(String.valueOf(player));
                when(playerLobby.containsName(player.getName())).thenReturn(false);

                TemplateEngineTester testHelper = new TemplateEngineTester();
                when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

                CuT.handle(request, response);
                testHelper.assertViewModelExists();
                testHelper.assertViewModelIsaMap();
                //   * model contains all necessary View-Model data
                testHelper.assertViewModelAttribute(
                        GetHomeRoute.TITLE_ATTR, "Sign in!");
                testHelper.assertViewModelAttribute(
                        ERROR_TYPE, ERROR_PLAYER);
                testHelper.assertViewName(VIEW_SIGN_IN);
        }

        @Test
        public void error_2(){
                when(request.queryParams(eq(PLAYER_ATTR))).thenReturn(String.valueOf(player));
                when(playerLobby.containsName(player.getName())).thenReturn(true);

                TemplateEngineTester testHelper = new TemplateEngineTester();
                when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

                CuT.handle(request, response);
                testHelper.assertViewModelExists();
                testHelper.assertViewModelIsaMap();
                //   * model contains all necessary View-Model data
                testHelper.assertViewModelAttribute(
                        GetHomeRoute.TITLE_ATTR, "Sign in!");
                testHelper.assertViewModelAttribute(
                        ERROR_TYPE, ERROR_EXISTED_PLAYER);
                testHelper.assertViewName(VIEW_SIGN_IN);
        }
 




}
