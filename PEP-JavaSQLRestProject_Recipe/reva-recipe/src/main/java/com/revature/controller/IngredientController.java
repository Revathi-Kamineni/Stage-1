// package com.revature.controller;
// import com.revature.service.IngredientService;

// import io.javalin.Javalin;

// /**
//  * The IngredientController class handles operations related to ingredients.
//  * It allows for creating, retrieving, updating, and deleting individual ingredients,
//  * as well as retrieving a list of all ingredients. The class interacts with the 
//  * IngredientService to perform these operations.
//  */

// public class IngredientController {

//     /**
//      * A service that manages ingredient-related operations.
//      */

//     @SuppressWarnings("unused")
//     private IngredientService IngredientService;

//     /**
//      * Constructs an IngredientController with the specified IngredientService.
//      *
//      * @param ingredientService the service used to manage ingredient-related operations
//      */

//     public IngredientController(IngredientService ingredientService) {
//         this.IngredientService = ingredientService;
//     }

//     /**
//      * Handler for retrieving a single ingredient.
//      * 
//      * @return the ingredient details
//      */

//     public void getIngredient() {
//         // Implementation
//     }

//     /**
//      * Handler for deleting a specific ingredient.
//      */

//     public void deleteIngredient() {
//         // Implementation
//     }

//     /**
//      * Handler for updating a specific ingredient's details.
//      */

//     public void updateIngredient() {
//         // Implementation
//     }

//     /**
//      * Handler for creating a new ingredient.
//      */

//     public void createIngredient() {
//         // Implementation
//     }

//     /**
//      * Handler for retrieving a list of all ingredients.
//      * 
//      * @return the list of ingredients
//      */
    
//     public void getIngredients() {
//         // Implementation
//     }

//     public void configureRoutes(Javalin app) {
//         // TODO Auto-generated method stub
//         throw new UnsupportedOperationException("Unimplemented method 'configureRoutes'");
//     }
// }

package com.revature.controller;

import com.revature.model.Ingredient; // Assuming there's an Ingredient model class
import com.revature.service.IngredientService;
import com.revature.util.Page;
import com.revature.util.PageOptions;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.util.List;
import java.util.Optional;

/**
 * The IngredientController class handles operations related to ingredients.
 * It allows for creating, retrieving, updating, and deleting individual ingredients,
 * as well as retrieving a list of all ingredients. The class interacts with the 
 * IngredientService to perform these operations.
 */

public class IngredientController {

    /**
     * A service that manages ingredient-related operations.
     */
    private IngredientService ingredientService;

    /**
     * Constructs an IngredientController with the specified IngredientService.
     *
     * @param ingredientService the service used to manage ingredient-related operations
     */
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    /**
     * Handler for retrieving a single ingredient.
     * 
     * @param ctx the Javalin context
     */
    public void getIngredient(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Optional<Ingredient> ingredient = ingredientService.findIngredient(id);
        if (ingredient != null) {
            ctx.json(ingredient);
            ctx.status(200);
        } else {
            ctx.status(404).result("Ingredient not found");
        }
    }

     
    /**
     * Handler for deleting a specific ingredient.
     * 
     * @param ctx the Javalin context
     */
    public void deleteIngredient(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        // if (ingredientService.deleteIngredient(id)) {
        //     ctx.status(204); // No content
        // } else {
        //     ctx.status(404).result("Ingredient not found");
        // }
        ingredientService.deleteIngredient(id);
        ctx.status(204);
    }

    /**
     * Handler for updating a specific ingredient's details.
     * 
     * @param ctx the Javalin context
     */
    public void updateIngredient(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        // Ingredient ingredient = ctx.bodyAsClass(Ingredient.class);
        // ingredient.setId(id); // Assuming Ingredient has a setId method

        // if (ingredientService.updateIngredient(ingredient)) {
        //     ctx.json(ingredient);
        //     ctx.status(200);
        // } else {
        //     ctx.status(404).result("Ingredient not found");
        // }
        Optional<Ingredient> ingredient = ingredientService.findIngredient(id);
        if(ingredient.isPresent()) {
            Ingredient updatedIngredient = ctx.bodyAsClass(Ingredient.class);
            ingredientService.saveIngredient(updatedIngredient);
            ctx.status(204);
        } else {
            ctx.status(404);
        }
    }

    /**
     * Handler for creating a new ingredient.
     * 
     * @param ctx the Javalin context
     */
    public void createIngredient(Context ctx) {
        // Ingredient ingredient = ctx.bodyAsClass(Ingredient.class);
        // Ingredient createdIngredient = ingredientService.createIngredient(ingredient);
        // ctx.json(createdIngredient);
        // ctx.status(201); // Created
        Ingredient ingredient = ctx.bodyAsClass(Ingredient.class);
        ingredientService.saveIngredient(ingredient);
        ctx.status(201);
    }

    /**
     * Handler for retrieving a list of all ingredients.
     * 
     * @param ctx the Javalin context
     */
    public void getIngredients(Context ctx) {
       // List<Ingredient> ingredients = ingredientService.searchIngredients();
       
        // Page<Ingredient> ingredients = ingredientService.searchIngredients(term, page, pageSize, sortBy, sortDirection);
        // ctx.json(ingredients);
        // ctx.status(200);

        String term = getParamAsClassOrElse(ctx, "term", String.class, null);
        if(ctx.queryParam("page") != null) {
            int page = getParamAsClassOrElse(ctx, "page", Integer.class, 1);
            int pageSize = getParamAsClassOrElse(ctx, "pageSize", Integer.class, 10);
            String sortBy = getParamAsClassOrElse(ctx, "sortBy", String.class, "id");
            String sortDirection = getParamAsClassOrElse(ctx, "sortDirection", String.class, "asc");
            Page<Ingredient> ingredients = ingredientService.searchIngredients(term, page, pageSize, sortBy, sortDirection);
            ctx.json(ingredients);
            return;
        }
        ctx.json(ingredientService.searchIngredients(term));
    }

   
    private <T> T getParamAsClassOrElse(Context ctx, String queryParam, Class<T> clazz, T defaultValue) {
        if(ctx.queryParam(queryParam) != null) {
            return ctx.queryParamAsClass(queryParam, clazz).get();
        } else {
            return defaultValue;
        }
    }
    /**
     * Configure the routes for ingredient operations.
     *
     * @param app the Javalin application
     */
    public void configureRoutes(Javalin app) {
        app.get("/ingredients", this::getIngredients);
        app.get("/ingredients/{id}", this::getIngredient);
        app.post("/ingredients", this::createIngredient);
        app.put("/ingredients/{id}", this::updateIngredient);
        app.delete("/ingredients/{id}", this::deleteIngredient);
    }
}
