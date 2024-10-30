// package com.revature.dao;
// import com.revature.util.ConnectionUtil;
// import com.revature.util.Page;
// import com.revature.util.PageOptions;
// import com.revature.model.Ingredient;
// import java.util.List;



// /**
//  * The IngredientDao class handles the CRUD operations for Ingredient objects.
//  * It provides methods for creating, retrieving, updating, and deleting Ingredient records
//  * from the database. This class relies on the ConnectionUtil class for database connectivity
//  * and also supports searching and paginating through Ingredient records.
//  */

// public class IngredientDAO {

//     /**
//      * A utility class used for establishing connections to the database.
//      */

//     @SuppressWarnings("unused")
//     private ConnectionUtil connectionUtil;

//     /**
//      * Constructs an IngredientDao with the specified ConnectionUtil for database connectivity.
//      * 
//      * @param connectionUtil the utility used to connect to the database
//      */

//     public IngredientDAO(ConnectionUtil connectionUtil) {
//         this.connectionUtil = connectionUtil;
//     }

//     /**
//      * Creates a new ingredient in the database.
//      * 
//      * @param ingredient the Ingredient object to create
//      * @return the ID of the newly created ingredient
//      */

//     public int createIngredient(Ingredient ingredient) {
//         // Implementation
//         return(0); //returns the new created id
//     }

//     /**
//      * Retrieves all ingredients from the database.
//      * 
//      * @return a list of all Ingredient objects
//      */

//     public List<Ingredient> getAllIngredients() {
//         // Implementation
//         return(null);//returns the list of all Ingredient objects
//     }

//     /**
//      * Retrieves a paginated list of all ingredients from the database.
//      * 
//      * @param pageOptions options for pagination, including page size and page number
//      * @return a paginated list of Ingredient objects
//      */

//     public Page<Ingredient> getAllIngredients(PageOptions pageOptions) {
//         // Implementation
//         return(null);//returns a paginated list of Ingredient objects
//     }

//     /**
//      * Searches for ingredients based on a specified term.
//      * 
//      * @param term the search term to filter ingredients by
//      * @return a list of Ingredient objects that match the search term
//      */

//     public List<Ingredient> searchIngredients(String term) {
//         // Implementation
//         return(null);//returns the list of Ingredient objects that match the search term
//     }

//     /**
//      * Searches for ingredients based on a specified term and returns a paginated result.
//      * 
//      * @param term the search term to filter ingredients by
//      * @param pageOptions options for pagination, including page size and page number
//      * @return a paginated list of Ingredient objects that match the search term
//      */

//     public Page<Ingredient> searchIngredients(String term, PageOptions pageOptions) {
//         // Implementation
//         return(null);//returns a paginated list of Ingredient objects that match the search term
//     }

//     /**
//      * Retrieves a specific ingredient by its ID.
//      * 
//      * @param id the ID of the ingredient to retrieve
//      * @return the Ingredient object corresponding to the given ID
//      */

//     public Ingredient getIngredientById(int id) {
//         // Implementation
//         return(null);//returns the Ingredient object corresponding to the given ID
//     }

//     /**
//      * Updates an existing ingredient in the database.
//      * 
//      * @param ingredient the Ingredient object with updated data
//      */

//     public void updateIngredient(Ingredient ingredient) {
//         // Implementation
//     }

//     /**
//      * Deletes a specific ingredient from the database.
//      * 
//      * @param ingredient the Ingredient object to delete
//      */
    
//     public void deleteIngredient(Ingredient ingredient) {
//         // Implementation
//     }
// }

package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.model.Ingredient;
import com.revature.util.ConnectionUtil;
import com.revature.util.Page;
import com.revature.util.PageOptions;

public class IngredientDAO {
    private ConnectionUtil connectionUtil;

    public IngredientDAO(ConnectionUtil connectionUtil) {
        this.connectionUtil = connectionUtil;
    }

    public Ingredient getIngredientById(int id) {
        String sql = "SELECT * FROM INGREDIENT WHERE ID = ?";
        try(Connection connection = connectionUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next() ? mapSingleRow(resultSet) : null;
        } catch(SQLException ex) {
            throw new RuntimeException("Unable to find ingredient with id " + id, ex);
        }
    }

    public int createIngredient(Ingredient ingredient) {
        String sql = "INSERT INTO INGREDIENT (NAME) VALUES (?)";
        try(Connection connection = connectionUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, ingredient.getName());
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            if(resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                throw new RuntimeException("Unable to create ingredient");
            }

        } catch(SQLException ex) {
            throw new RuntimeException("Unable to create ingredient", ex);
        }
    }

    public void deleteIngredient(Ingredient ingredient) {
        String sql = "DELETE FROM INGREDIENT WHERE ID = ?";
        try(Connection connection = connectionUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, ingredient.getId());
            statement.executeUpdate();
        } catch(SQLException ex) {
            throw new RuntimeException("Unable to delete ingredient", ex);
        }
    }

    public void updateIngredient(Ingredient ingredient) {
        String sql = "UPDATE INGREDIENT SET NAME = ? WHERE ID = ?";
        try(Connection connection = connectionUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, ingredient.getName());
            statement.setInt(2, ingredient.getId());
            statement.executeUpdate();
        } catch(SQLException ex) {
            throw new RuntimeException("Unable to update ingredient", ex);
        }
    }

    public List<Ingredient> getAllIngredients() {
        String sql = "SELECT * FROM INGREDIENT ORDER BY ID";
        try(Connection connection = connectionUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            return mapRows(resultSet);
        } catch(SQLException ex) {
            throw new RuntimeException("Unable to get all ingredients", ex);
        }
    }

    public Page<Ingredient> getAllIngredients(PageOptions pageOptions) {
        String sql = String.format("SELECT * FROM ingredient ORDER BY %s %s", pageOptions.getSortBy(), pageOptions.getSortDirection());
        try(Connection connection = connectionUtil.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            return pageResults(resultSet, pageOptions);
        } catch(SQLException e) {
            throw new RuntimeException("Unable to retrieve all ingredients", e);
        }
    }

    public List<Ingredient> searchIngredients(String term) {
        String sql = "SELECT * FROM INGREDIENT WHERE NAME LIKE ? ORDER BY ID";
        try(Connection connection = connectionUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + term + "%");
            ResultSet resultSet = statement.executeQuery();
            return mapRows(resultSet);
        } catch(SQLException ex) {
            throw new RuntimeException("Unable to search ingredients", ex);
        }
    }

    public Page<Ingredient> searchIngredients(String term, PageOptions pageOptions) {
        String sql = String.format("SELECT * FROM ingredient WHERE name LIKE ? ORDER BY %s %s", pageOptions.getSortBy(), pageOptions.getSortDirection());
        try(Connection connection = connectionUtil.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + term + "%");
            ResultSet resultSet = statement.executeQuery();
            return pageResults(resultSet, pageOptions);
        } catch(SQLException e) {
            throw new RuntimeException("Unable to search ingredients by term", e);
        }
    }

    private Ingredient mapSingleRow(ResultSet resultSet) throws SQLException {
        return new Ingredient(resultSet.getInt("ID"), resultSet.getString("NAME"));
    }

    private List<Ingredient> mapRows(ResultSet resultSet) throws SQLException {
        List<Ingredient> ingredients = new ArrayList<Ingredient>();
        while(resultSet.next()) {
            ingredients.add(mapSingleRow(resultSet));
        }
        return ingredients;
    }

    private Page<Ingredient> pageResults(ResultSet resultSet, PageOptions pageOptions) throws SQLException {
        List<Ingredient> ingredients = mapRows(resultSet);
        int offset = (pageOptions.getPageNumber() - 1) * pageOptions.getPageSize();
        int limit = offset + pageOptions.getPageSize();
        List<Ingredient> subList = ingredients.subList(offset, limit);
        return new Page<>(pageOptions.getPageNumber(), pageOptions.getPageSize(), (int)Math.ceil(ingredients.size()/((float)pageOptions.getPageSize())), ingredients.size(), subList);
    }
}