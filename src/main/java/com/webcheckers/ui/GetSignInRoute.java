package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static com.webcheckers.ui.PostSignInRoute.PLAYER_ATTR;
import static com.webcheckers.ui.WebServer.HOME_URL;

/**
 * The UI Controller to GET the SignIn page.
 */
public class GetSignInRoute implements Route {

    private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());
    private final TemplateEngine templateEngine;

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public GetSignInRoute(final TemplateEngine templateEngine){
        this.templateEngine = Objects.requireNonNull(templateEngine,"templateEngine is required");
        LOG.config("GetLoginRoute is initialized.");
    }

    /**
     * renders signin page
     *
     * @param request
     *   the http request
     * @param response
     *   the http response
     *
     * @return
     *   the rendered HTML for the sign in page
     */
    @Override
    public Object handle(Request request, Response response){
        LOG.finer("GetSignInRoute is invoked.");
        Map<String, Object> vm = new HashMap<>();
        final Session httpSession = request.session();
        Player player = httpSession.attribute(PLAYER_ATTR);
        if (player == null){
            vm.put("title", "Sign in!");
            return templateEngine.render(new ModelAndView(vm , "signin.ftl"));
        } else {
            response.redirect(HOME_URL);
            return null;
        }

    }
}

