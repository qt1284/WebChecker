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

class PostResignGameTest {
    private PostSignInRoute CuT;
    private GameCenter gameCenter;
    private Request request;
    private Session session;
    private Response response;
    private CheckerGame game;
    private Player player = new Player("player");
    private final String winner = "winner";
    private final Player opponent = new Player("opponent");
    private PostResignGame postResignGame;
    private TemplateEngine engine;

    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        gameCenter = mock(GameCenter.class);
        game = mock(CheckerGame.class);
        postResignGame = new PostResignGame(gameCenter);
    }
    
    @Test
    public void messageError(){
        when(request.session().attribute(PostSignInRoute.PLAYER_ATTR)).thenReturn(player);
        when(gameCenter.getGame(player)).thenReturn(game);
        when(game.getActivePlayer()).thenReturn(opponent);
        Object json = postResignGame.handle(request,response);
        assertEquals("{\"text\":\"It is not your turn yet!\",\"type\":\"ERROR\"}",json);

    }
    @Test
    public void messageSuccess(){
        when(request.session().attribute(PostSignInRoute.PLAYER_ATTR)).thenReturn(player);
        when(gameCenter.getGame(player)).thenReturn(game);
        when(game.getActivePlayer()).thenReturn(player);
        when(gameCenter.getOpponent(player)).thenReturn(opponent);
        when(gameCenter.getWinner()).thenReturn(player);
        Object json = postResignGame.handle(request,response);
        assertEquals("{\"text\":\"player\",\"type\":\"INFO\"}",json);

    }
}