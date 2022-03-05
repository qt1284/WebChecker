package com.webcheckers.ui;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import static com.webcheckers.ui.WebServer.HOME_URL;
import static com.webcheckers.ui.WebServer.SIGN_IN_URL;
import static spark.Spark.halt;

/**
 * The {@code POST /SignIn} route handler
 */
public class PostSignInRoute implements Route {

    private PlayerLobby playerLobby;
    static final String PLAYER_ATTR = "player";
    private final TemplateEngine templateEngine;
    static final String ERROR_TYPE = "error";
    static final String VIEW_SIGN_IN = "signin.ftl";
    static final String ERROR_EXISTED_PLAYER = "This name is already existed!";
    static final String ERROR_PLAYER  = "Please enter a valid user name!";


    /**
     * The constructor for the {@code POST /SignIn} route handler.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     * @param playerLobby
     *   PlayerLobby object that holds all players currently logged into webcheckers application
     *
     * @throws NullPointerException
     *   when {@code templateEngine} parameter is null
     */
    PostSignInRoute (TemplateEngine templateEngine, PlayerLobby playerLobby) {
        this.playerLobby = playerLobby;
        Objects.requireNonNull(playerLobby, "gameCenter must not be null");
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        this.templateEngine = templateEngine;
    }


    /**
     * renders post signin page
     *
     * @param request
     *   the http request
     * @param response
     *   the http response
     *
     * @return
     *   the rendered HTML for the post sign in page
     */
    @Override
    public String handle(Request request, Response response) {
        // start building the View-Model
        Map<String, Object> vm = new HashMap<>();

        final Session httpSession = request.session();
        final String userName = request.queryParams(PLAYER_ATTR);

        Player player = new Player(userName);

        if (playerLobby.containsName(userName)){
            vm.put("title", "Sign in!");
            vm.put(ERROR_TYPE,ERROR_EXISTED_PLAYER);
            return templateEngine.render(new ModelAndView(vm, VIEW_SIGN_IN));
        }

        if (playerLobby.checkValidName(userName)){
            httpSession.attribute(PLAYER_ATTR,player);
            playerLobby.addPlayer(player);
            response.redirect(HOME_URL);
            return null;
        } else{
            vm.put("title", "Sign in!");
            vm.put(ERROR_TYPE,ERROR_PLAYER);
            return templateEngine.render(new ModelAndView(vm, VIEW_SIGN_IN));
        }

    }


}

