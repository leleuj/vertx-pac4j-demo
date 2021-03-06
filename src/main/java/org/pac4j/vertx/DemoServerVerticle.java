/*
  Copyright 2014 - 2014 pac4j organization

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package org.pac4j.vertx;

import org.pac4j.vertx.handlers.CallbackHandler;
import org.pac4j.vertx.handlers.DemoHandlers;
import org.pac4j.vertx.handlers.HandlerHelper;
import org.pac4j.vertx.handlers.LogoutHandler;
import org.pac4j.vertx.handlers.RequiresAuthenticationHandler;
import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.RouteMatcher;
import org.vertx.java.platform.Verticle;

import com.campudus.vertx.sessionmanager.java.SessionHelper;

/**
 * Stateful server example.
 * 
 * @author Michael Remond
 * @since 1.0.0
 *
 */
public class DemoServerVerticle extends Verticle {

    @Override
    public void start() {

        SessionHelper sessionHelper = new SessionHelper(vertx);
        Pac4jHelper pac4jHelper = new Pac4jHelper(vertx);

        RouteMatcher rm = new RouteMatcher();

        DemoHandlers.AuthenticatedHandler authenticatedHandler = new DemoHandlers.AuthenticatedHandler();
        rm.get("/facebook/index.html", new RequiresAuthenticationHandler("FacebookClient", authenticatedHandler,
                pac4jHelper, sessionHelper));
        rm.get("/twitter/index.html", new RequiresAuthenticationHandler("TwitterClient", authenticatedHandler,
                pac4jHelper, sessionHelper));
        rm.get("/form/index.html", new RequiresAuthenticationHandler("FormClient", authenticatedHandler, pac4jHelper,
                sessionHelper));
        rm.get("/form/index.html.json", new RequiresAuthenticationHandler("FormClient", true,
                new DemoHandlers.AuthenticatedJsonHandler(), pac4jHelper, sessionHelper));
        rm.get("/basicauth/index.html", new RequiresAuthenticationHandler("BasicAuthClient", authenticatedHandler,
                pac4jHelper, sessionHelper));
        rm.get("/cas/index.html", new RequiresAuthenticationHandler("CasClient", authenticatedHandler, pac4jHelper,
                sessionHelper));
        rm.get("/saml2/index.html", new RequiresAuthenticationHandler("Saml2Client", authenticatedHandler, pac4jHelper,
                sessionHelper));
        rm.get("/oidc/index.html", new RequiresAuthenticationHandler("OidcClient", authenticatedHandler, pac4jHelper,
                sessionHelper));

        rm.get("/theForm.html", DemoHandlers.formHandler);

        Handler<HttpServerRequest> callback = new CallbackHandler(pac4jHelper, sessionHelper);
        rm.get("/callback", callback);
        rm.post("/callback", callback);

        rm.get("/logout", new LogoutHandler(sessionHelper));

        rm.get("/", new DemoHandlers.IndexHandler(pac4jHelper, sessionHelper));

        rm.get("/assets/js/app.js", new Handler<HttpServerRequest>() {
            @Override
            public void handle(HttpServerRequest req) {
                req.response().sendFile("./app.js");
            }
        });

        vertx.createHttpServer().requestHandler(HandlerHelper.addFormParsing(rm)).listen(8080, "localhost");

    }

}
