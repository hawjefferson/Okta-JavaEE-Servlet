package com.demo;

import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.authentication.mechanism.http.OpenIdAuthenticationMechanismDefinition;
import jakarta.security.enterprise.authentication.mechanism.http.openid.ClaimsDefinition;
import jakarta.security.enterprise.identitystore.openid.OpenIdContext;
import jakarta.servlet.annotation.HttpConstraint;
import jakarta.servlet.annotation.ServletSecurity;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@OpenIdAuthenticationMechanismDefinition(
    clientId = "0oa6x3x7ahycpCrVh697",
    clientSecret = "1vPwJVU0cqnL249JEqAmoj2eIJ46XP54FApS4tks6ZtDBKUOQe3KWR47kryaMTjT",
    redirectURI = "http://localhost:8080/callback",
    providerURI = "https://tcsdemoh.okta.com/oauth2/default",
    jwksConnectTimeout = 5000,
    jwksReadTimeout = 5000,
    extraParameters = {"audience=api://default"}, 
    claimsDefinition = @ClaimsDefinition(callerGroupsClaim = "roles"),
    scope = {"openid","profile","email","offline_access"}
)
@WebServlet("/protected")
@ServletSecurity(
    @HttpConstraint(rolesAllowed = "Everyone")
)
public class ProtectedServlet extends HttpServlet {

    @Inject
    private OpenIdContext context;

    @Inject
    SecurityContext securityContext;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        var principal = securityContext.getCallerPrincipal();
        var name = principal.getName();

        String html = """
            <div style="margin: 0 10%%; width: 80%%; overflow-wrap: anywhere;">
                <h1>Protected Servlet</h1>
                <p>principal name: %s </p>
                <p>access token (type = %s):</p>
                <p>%s</p>
                <p>id token (type = %s):</p>
                 <p>refresh token (type = %s):</p>
            </div>
            """.formatted(
            name,
            context.getTokenType(),
            context.getAccessToken(),
            context.getIdentityToken(),
            context.getRefreshToken() 
        );

        response.setContentType("text/html");
        response.getWriter().print(html.toString());
    }
}
