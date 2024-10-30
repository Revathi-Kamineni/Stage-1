// package com.revature.controller;
// import com.revature.service.ChefService;

// import io.javalin.Javalin;

// /**
//  * The AuthenticationController class handles user authentication-related operations.
//  * This includes login, logout, registration, and managing the authorization filter.
//  * It interacts with the ChefService for certain functionalities related to the user.
//  */
// public class AuthenticationController {

//     /**
//      * A service that handles chef-related operations.
//      */

//     @SuppressWarnings("unused")
//     private ChefService chefService;

//     /**
//      * Constructs an AuthenticationController with the specified ChefService.
//      * 
//      * @param chefService the service used to manage chef-related operations
//      */

//     public AuthenticationController(ChefService chefService) {
//         this.chefService = chefService;
//     }

//     /**
//      * Handles the login operation for users.
//      */

//     public void login() {
//         // Implementation
//     }

//     /**
//      * Handles the logout operation for users.
//      */

//     public void logout() {
//         // Implementation
//     }

//     /**
//      * Handles the registration process for new users.
//      */

//     public void register() {
//         // Implementation
//     }

//     /**
//      * Filters requests based on user authorization status.
//      */
	
//     public void authorizationFilter() {
//         // Implementation
//     }

//     public void configureRoutes(Javalin app) {
//         // TODO Auto-generated method stub
//         throw new UnsupportedOperationException("Unimplemented method 'configureRoutes'");
//     }
// }


package com.revature.controller;

import com.revature.model.Chef;
import com.revature.service.AuthenticationService;
import com.revature.service.ChefService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.util.Objects;

/**
 * The AuthenticationController class handles user authentication-related operations.
 * This includes login, logout, registration, and managing the authorization filter.
 * It interacts with the ChefService for certain functionalities related to the user.
 */
public class AuthenticationController {

    /**
     * A service that handles chef-related operations.
     */
    private ChefService chefService;
    private AuthenticationService authService;

    /**
     * Constructs an AuthenticationController with the specified ChefService.
     * 
     * @param chefService the service used to manage chef-related operations
     */
    public AuthenticationController(ChefService chefService, AuthenticationService authService) {
        this.chefService = chefService;
        this.authService = authService;
    }

    /**
     * Handles user registration by creating a new chef account.
     * 
     * @param ctx the context of the HTTP request
     */
    public void register(Context ctx) {
        Chef newChef = ctx.bodyAsClass(Chef.class);
        
        // Check if a chef with the same username already exists
        // if (chefService.findChefByUsername(newChef.getUsername()) != null) {
        //     ctx.status(409).result("Username already exists");
        //     return;
        // }

        // // Register the new chef
        // chefService.saveChef(newChef);
        // ctx.status(201).result("Chef registered successfully");
        if (chefService.searchChefs(newChef.getUsername()).stream().anyMatch(c -> c.getUsername().equals(newChef.getUsername()))) {
            ctx.status(409).result("Username already exists");
            return;
        }

        // Register the new chef
        Chef registeredChef = authService.registerChef(newChef);
        ctx.status(201).json(registeredChef);
    }

    /**
     * Handles user login by validating credentials.
     * 
     * @param ctx the context of the HTTP request
     */
    public void login(Context ctx) {
        // String username = ctx.queryParam("username");
        // String password = ctx.queryParam("password");

        // Chef chef = chefService.findChefByUsername(username).get();
        
        // // Validate credentials
        // if (chef != null && Objects.equals(chef.getPassword(), password)) {
        //     ctx.sessionAttribute("user", chef); // Store user in session
        //     ctx.status(200).result("Login successful");
        // } else {
        //     ctx.status(401).result("Invalid username or password");
        // }
        Chef chefCredentials = ctx.bodyAsClass(Chef.class);
        
        String token = authService.login(chefCredentials);
        
        if (token != null) {
            ctx.status(200).result("Login successful").header("Authorization", token);
        } else {
            ctx.status(401).result("Invalid username or password");
        }
    }

    

    /**
     * Handles user logout by invalidating the session.
     * 
     * @param ctx the context of the HTTP request
     */
    public void logout(Context ctx) {
        ctx.sessionAttribute("user", null); // Clear the user from session
        ctx.status(200).result("Logout successful");
    }

    /**
    * Filters requests based on user authorization status.
    */
	
    public void authorizationFilter(Context ctx) {
        // Implementation
        Chef user = ctx.sessionAttribute("user");
        if (user == null || !user.isAdmin()) { // Replace with role-based check if necessary
            ctx.status(403).result("Access denied");
            ctx.redirect("/login"); // Optionally redirect to login page
            //ctx.abort(); // Prevent further handler execution
        }
    }

    /**
     * Method to configure routes for the AuthenticationController.
     * 
     * @param app the Javalin application instance
     */
    public void configureRoutes(Javalin app) {
        app.post("/register", this::register);
        app.post("/login", this::login);
        app.post("/logout", this::logout);
        app.before("/protected/*", this::authorizationFilter);// Protect routes with authentication
    }
}
