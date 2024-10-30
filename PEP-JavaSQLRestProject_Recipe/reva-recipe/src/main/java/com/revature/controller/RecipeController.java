// package com.revature.controller;
// import com.revature.service.RecipeService;
// import io.javalin.http.Handler;
// import io.javalin.Javalin;
// import io.javalin.http.Context;



// /**
//  * The RecipeController class provides RESTful endpoints for managing recipes.
//  * It interacts with the RecipeService to fetch, create, update, and delete recipes.
//  * Handlers in this class are fields assigned to lambdas, which define the behavior for each endpoint.
//  */

// public class RecipeController {

//     /**
//      * The service used to interact with the recipe data.
//      */

//     @SuppressWarnings("unused")
//     private RecipeService recipeService;

//     /**
//      * Constructor that initializes the RecipeController with the provided RecipeService.
//      * 
//      * @param recipeService The service that handles the business logic for managing recipes.
//      */

//     public RecipeController(RecipeService recipeService) {
//         this.recipeService = recipeService;
//     }

//     /**
//      * Handler to fetch all recipes.
//      * This handler accepts optional query parameters ?ingredients and ?name to filter the results.
//      */

//     public Handler fetchAllRecipes = ctx -> {
//         // handler logic
//     };

//     /**
//      * Handler to fetch a recipe by its ID.
//      */

//     public Handler fetchRecipeById = ctx -> {
//         // handler logic
//     };

//     /**
//      * Handler to create a new recipe.
//      */

//     public Handler createRecipe = ctx -> {
//         // handler logic
//     };

//     /**
//      * Handler to delete a recipe by its ID.
//      */

//     public Handler deleteRecipe = ctx -> {
//         // handler logic
//     };

//     /**
//      * Handler to update an existing recipe by its ID.
//      */

//     public Handler updateRecipe = ctx -> {
//         // handler logic
//     };

//     /**
//      * A helper method to retrieve a query parameter from the context as a specific class type,
//      * or return a default value if the query parameter is not present.
//      * 
//      * @param <T> The type of the query parameter to be returned.
//      * @param ctx The context of the request.
//      * @param queryParam The query parameter name.
//      * @param clazz The class type of the query parameter.
//      * @param defaultValue The default value to return if the query parameter is not found.
//      * @return The value of the query parameter converted to the specified class type, or the default value.
//      */
//     @SuppressWarnings("unused")
//     private <T> T getParamAsClassOrElse(Context ctx, String queryParam, Class<T> clazz, T defaultValue) {
//         // method logic
//         return defaultValue;
//     }

//     public void configureRoutes(Javalin app) {
//         // TODO Auto-generated method stub
//         throw new UnsupportedOperationException("Unimplemented method 'configureRoutes'");
//     }
// }

package com.revature.controller;

import com.revature.model.Chef;
import com.revature.model.Recipe; // Assuming there's a Recipe model class
import com.revature.service.AuthenticationService;
import com.revature.service.RecipeService;
import com.revature.util.Page;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The RecipeController class provides RESTful endpoints for managing recipes.
 * It interacts with the RecipeService to fetch, create, update, and delete recipes.
 * Handlers in this class are fields assigned to lambdas, which define the behavior for each endpoint.
 */
public class RecipeController {

    /**
     * The service used to interact with the recipe data.
     */
    private RecipeService recipeService;
    private AuthenticationService authService;

    /**
     * Constructor that initializes the RecipeController with the provided RecipeService.
     * 
     * @param recipeService The service that handles the business logic for managing recipes.
     */
    public RecipeController(RecipeService recipeService, AuthenticationService authService) {
        this.recipeService = recipeService;
        this.authService = authService;
    }

    /**
     * Handler to fetch all recipes.
     * This handler accepts optional query parameters ?ingredients and ?name to filter the results.
     */
    public Handler fetchAllRecipes = ctx -> {
        // String ingredients = ctx.queryParam("ingredients");
        // String name = ctx.queryParam("name");
        // List<Recipe> recipes = recipeService.searchRecipes(ingredients, name);
        // ctx.json(recipes);
        // ctx.status(200);

        String term = getParamAsClassOrElse(ctx, "term", String.class, null);

		if (ctx.queryParam("page") != null) {

			int page = getParamAsClassOrElse(ctx, "page", Integer.class, 1);
			int pageSize = getParamAsClassOrElse(ctx, "pageSize", Integer.class, 10);
			String sortBy = getParamAsClassOrElse(ctx, "sortBy", String.class, "id");
			String sortDirection = getParamAsClassOrElse(ctx, "sortDirection", String.class, "asc");

			Page<Recipe> recipePage = recipeService.searchRecipes(term, page, pageSize, sortBy, sortDirection);

			ctx.json(recipePage);

		} else {

			String ingredient = ctx.queryParam("ingredient");
            String recipeName = ctx.queryParam("name");

            List<Recipe> recipes = new ArrayList<>();
            if(ingredient == null && recipeName == null) recipes = recipeService.searchRecipes("");
            else if(ingredient == null && recipeName != null) recipes = recipeService.searchRecipes(recipeName);
            // else if(ingredient != null && recipeName == null) recipes = recipeService.searchRecipesByIngredient(ingredient);
            // else recipes = recipeService.searchRecipesByIngredientAndName(recipeName, ingredient);

            if(recipes.isEmpty()) {
                ctx.status(404);
                ctx.result("No recipes found");
            }
            else {
                ctx.status(200);
                ctx.json(recipes);
            }
		}
    };

    /**
     * Handler to fetch a recipe by its ID.
     */
    public Handler fetchRecipeById = ctx -> {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Optional<Recipe> recipe = recipeService.findRecipe(id);
        if (recipe != null) {
            ctx.json(recipe);
            ctx.status(200);
        } else {
            ctx.status(404).result("Recipe not found");
        }
    };

    /**
     * Handler to create a new recipe.
     */
    public Handler createRecipe = ctx -> {
        // Recipe recipe = ctx.bodyAsClass(Recipe.class);
        // Recipe createdRecipe = recipeService.saveRecipe(recipe);
        // ctx.json(createdRecipe);
        // ctx.status(201); // Created
        Chef chef = authService.getChefFromSessionToken(ctx.header("Authorization"));

		if (chef == null) {
			ctx.status(401);
		} else {

			Recipe recipe = ctx.bodyAsClass(Recipe.class);

			recipe.setId(0);
			
			recipe.setAuthor(chef);

			recipeService.saveRecipe(recipe);

			ctx.status(201);

		}
    };

    /**
     * Handler to delete a recipe by its ID.
     */
    public Handler deleteRecipe = ctx -> {
        int id = Integer.parseInt(ctx.pathParam("id"));
        // if (recipeService.deleteRecipe(id)) {
        //     ctx.status(204); // No content
        // } else {
        //     ctx.status(404).result("Recipe not found");
        // }
        recipeService.deleteRecipe(id);
    };

    /**
     * Handler to update an existing recipe by its ID.
     */
    public Handler updateRecipe = ctx -> {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Recipe recipe = ctx.bodyAsClass(Recipe.class);
        recipe.setId(id); // Assuming Recipe has a setId method
        // if (recipeService.saveRecipe(recipe)) {
        //     ctx.json(recipe);
        //     ctx.status(200);
        // } else {
        //     ctx.status(404).result("Recipe not found");
        // }
        recipeService.saveRecipe(recipe);
    };

    /**
     * A helper method to retrieve a query parameter from the context as a specific class type,
     * or return a default value if the query parameter is not present.
     * 
     * @param <T> The type of the query parameter to be returned.
     * @param ctx The context of the request.
     * @param queryParam The query parameter name.
     * @param clazz The class type of the query parameter.
     * @param defaultValue The default value to return if the query parameter is not found.
     * @return The value of the query parameter converted to the specified class type, or the default value.
     */
    private <T> T getParamAsClassOrElse(Context ctx, String queryParam, Class<T> clazz, T defaultValue) {
        String paramValue = ctx.queryParam(queryParam);
        if (paramValue != null) {
            if (clazz == Integer.class) {
                return clazz.cast(Integer.valueOf(paramValue));
            } else if (clazz == Boolean.class) {
                return clazz.cast(Boolean.valueOf(paramValue));
            } else {
                return clazz.cast(paramValue);
            }
        }
        return defaultValue;
    }

    /**
     * Configure the routes for recipe operations.
     *
     * @param app the Javalin application
     */
    public void configureRoutes(Javalin app) {
        app.get("/recipes", fetchAllRecipes);
        app.get("/recipes/{id}", fetchRecipeById);
        app.post("/recipes", createRecipe);
        app.put("/recipes/{id}", updateRecipe);
        app.delete("/recipes/{id}", deleteRecipe);
    }
}
