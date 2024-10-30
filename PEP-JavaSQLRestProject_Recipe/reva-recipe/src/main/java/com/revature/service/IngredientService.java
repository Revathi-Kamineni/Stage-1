// package com.revature.service;
// import java.util.List;
// import java.util.Optional;

// import com.revature.dao.IngredientDAO;
// import com.revature.model.Ingredient;
// import com.revature.util.Page;


// /**
//  * The IngredientService class provides services related to Ingredient 
//  * objects, including CRUD operations and search functionalities. It serves 
//  * as a mediator between the data access layer (IngredientDao) and the 
//  * application logic, ensuring that all operations on Ingredient objects 
//  * are performed consistently and efficiently.
//  */

// public class IngredientService {

//     /**
//      * The data access object used for performing operations on Ingredient entities.
//      */

//     @SuppressWarnings("unused")
//     private IngredientDAO ingredientDao;

//     /**
//      * Constructs an IngredientService with the specified IngredientDao.
//      *
//      * @param ingredientDao the IngredientDao to be used by this service for data access
//      */

//     public IngredientService(IngredientDAO ingredientDao) {
//         this.ingredientDao = ingredientDao;
//     }

//     /**
//      * Saves an Ingredient object to the data store.
//      *
//      * @param ingredient the Ingredient object to be saved
//      */

//     public void saveIngredient(Ingredient ingredient) {
//         // Implementation
//     }

//     /**
//      * Searches for ingredients with pagination and sorting options.
//      *
//      * @param term the search term used to find ingredients
//      * @param page the page number to retrieve
//      * @param pageSize the number of ingredients per page
//      * @param sortBy the field by which to sort the results
//      * @param sortDirection the direction of sorting (ascending or descending)
//      * @return a Page containing the results of the search
//      */

//     public Page<Ingredient> searchIngredients(String term, int page, int pageSize, String sortBy, String sortDirection) {
//         // Implementation
//         return null; //return a Page containing the results of the search
//     }

//     /**
//      * Searches for ingredients based on a search term.
//      *
//      * @param term the search term used to find ingredients
//      * @return a list of Ingredient objects that match the search term
//      */

//     public List<Ingredient> searchIngredients(String term) {
//         // Implementation
//         return null; //return a list of Ingredient objects that match the search term

//     }

//     /**
//      * Deletes an Ingredient by its unique identifier.
//      *
//      * @param id the unique identifier of the ingredient to be deleted
//      */

//     public void deleteIngredient(int id) {
//         // Implementation
//     }

//     /**
//      * Finds an Ingredient by its unique identifier.
//      *
//      * @param id the unique identifier of the ingredient to be found
//      * @return an Optional containing the found Ingredient if present; 
//      *         an empty Optional if not found
//      */

//     public Optional<Ingredient> findIngredient(int id) {
//         // Implementation
//         return null; //return an Optional containing the found Ingredient if present; an empty Optional if not found
//     }
// }


package com.revature.service;

import java.util.List;
import java.util.Optional;

import com.revature.model.Ingredient;
import com.revature.dao.IngredientDAO;
import com.revature.util.Page;
import com.revature.util.PageOptions;

public class IngredientService {
    private IngredientDAO ingredientDao;

    public IngredientService(IngredientDAO ingredientDao) {
        this.ingredientDao = ingredientDao;
    }

    public Optional<Ingredient> findIngredient(int id) {
        return Optional.ofNullable(ingredientDao.getIngredientById(id));
    }

    public void saveIngredient(Ingredient ingredient) {
        if(ingredient.getId() == 0) {
            int id = ingredientDao.createIngredient(ingredient);
            ingredient.setId(id);
        } else {
            ingredientDao.updateIngredient(ingredient);
        }
    }

    public void deleteIngredient(int id) {
        Ingredient ingredient = ingredientDao.getIngredientById(id);
        if(ingredient != null) {
            ingredientDao.deleteIngredient(ingredient);
        }
    }

    public List<Ingredient> searchIngredients(String term) {
        if(term == null ) { //|| term.isBlank()
            return ingredientDao.getAllIngredients();
        } else {
            return ingredientDao.searchIngredients(term);
        }
    }

    public Page<Ingredient> searchIngredients(String term, int page, int pageSize, String sortBy, String sortDirection) {
        PageOptions pageOptions = new PageOptions(page, pageSize, sortBy, sortDirection);
        if(term == null) { //|| term.isBlank()
            return ingredientDao.getAllIngredients(pageOptions);
        } else {
            return ingredientDao.searchIngredients(term, pageOptions);
        }
    }
}