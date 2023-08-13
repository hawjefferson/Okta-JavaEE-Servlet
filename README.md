# Open ID authentication with Jakarta EE 10, Security 3.0, and Okta

## Requirements

Before you start, please make sure you have the following prerequisites installed (or install them now).

- [Java 17](https://adoptium.net/): or use [SDKMAN!](https://sdkman.io/) to manage and install multiple versions (the Jakarta EE spec says 11 and up is supported, but I wrote this tutorial assuming version 17)

- [HTTPie](https://httpie.org/doc#installation): a simple tool for making HTTP requests from a Bash shell

**You will need a free Okta developer account** if you don't already have one. Go ahead and sign up for an Okta account using [their sign-up page](https://developer.okta.com/signup).

 ## Create an OIDC Application
 
You will need to have a free Okta developer account and log in to the account.

Use the following values:

- **Name**: `javartaee-demo`
- **Description**: whatever you like, or leave blank
- **Type**: `Regular Web Application`
- **Callback URLs**: `http://localhost:8080/callback`
- **Allowed Logout URLs**: `http://localhost:8080`

The console output shows you the Auth0 domain and the OIDC client ID. However, you also need the client secret, which you have to get by logging into Auth0. Type the following:



Select the OIDC app (or client) you just created from the list. This will open the OIDC application on the Okta dashboard.

Fill in the three values in `src/main/resources/openid.properties`. Replace the bracketed values with the values from the OIDC application page on the Auth0 dashboard.

```bash
issuerUri=https://<your-okta-oauth-server-issuer-endpoint
clientId=<your-client-id>
clientSecret=<your-client-secret>
```

In `src/main/java/com/demo/ProtectedServlet.java`, replace `<your-okta-domain` in the `@OpenIdAuthenticationMechanismDefinition` annotation with your actual Okta domain.

## Configure Roles on Okta

Login to your Okta Admin console. You need to create a role, assign your user to that role, and create an action that will inject the roles into a custom claim in the JWT. You need to create a custom claim within API Security-> Authorisation Servers -> Claims. The custom claim name should be labelled sa roles and put .*



## Start the project

Use this command to start the project.

```bash
./mvnw wildfly:run
```

Using a browser, open `http://localhost:8080/protected`. You should be prompted to log in.

## Links

This example uses the following open source libraries:

* [WildFly](https://www.wildfly.org/)
* [jwks-rsa-java](https://github.com/auth0/jwks-rsa-java)

## License

Apache 2.0, see [LICENSE](LICENSE).
