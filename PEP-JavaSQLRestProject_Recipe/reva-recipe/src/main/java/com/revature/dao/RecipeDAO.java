// package com.revature.dao;
// import com.revature.util.ConnectionUtil;
// import com.revature.util.Page;
// import com.revature.util.PageOptions;
// import com.revature.model.Recipe;
// import java.util.List;


// /**
//  * The RecipeDao class abstracts the CRUD operations for Recipe objects.
//  * This class utilizes the previously created classes and primarily functions
//  * as a pure functional class, meaning it doesn't store state apart from a 
//  * reference to ConnectionUtil for database connection purposes. 
//  * 
//  * Although the implementation may seem extensive for simple functionality, 
//  * this design improves testability, maintainability, and extensibility of 
//  * the overall infrastructure.
//  */

// public class RecipeDAO {

//     /**
//      * A utility class for establishing connections to the database.
//      */

//     @SuppressWarnings("unused")
//     private ConnectionUtil connectionUtil;

//     /**
//      * Constructs a RecipeDao with the specified ConnectionUtil for database connectivity.
//      * 
//      * @param connectionUtil the utility used to connect to the database
//      */

//     public RecipeDAO(ConnectionUtil connectionUtil) {
//         this.connectionUtil = connectionUtil;
//     }

//     /**
//      * Retrieves all recipes from the database.
//      * 
//      * @return a list of all Recipe objects
//      */

//     public List<Recipe> getAllRecipes() {
//         // Implementation
//         return(null);//returns a list of all Recipe objects
//     }

//     /**
//      * Retrieves a paginated list of all recipes from the database.
//      * 
//      * @param pageOptions options for pagination, including page size and page number
//      * @return a paginated list of Recipe objects
//      */

//     public Page<Recipe> getAllRecipes(PageOptions pageOptions) {
//         // Implementation
//         return null;// Returns a paginated list of Recipe objects
//     }

//     /**
//      * Searches for recipes that match a specified term.
//      * 
//      * @param term the search term to filter recipes by
//      * @return a list of Recipe objects that match the search term
//      */

//     public List<Recipe> searchRecipesByTerm(String term) {
//         // Implementation
//         return null;// Returns a list of Recipe objects that match the search term
//     }

//     /**
//      * Searches for recipes that match a specified term and returns a paginated result.
//      * 
//      * @param term the search term to filter recipes by
//      * @param pageOptions options for pagination, including page size and page number
//      * @return a paginated list of Recipe objects that match the search term
//      */

//     public Page<Recipe> searchRecipesByTerm(String term, PageOptions pageOptions) {
//         // Implementation
//         return null; // return a paginated list of Recipe objects that match the search term
//     }

//     /**
//      * Retrieves a specific recipe by its ID.
//      * 
//      * @param id the ID of the recipe to retrieve
//      * @return the Recipe object corresponding to the given ID
//      */

//     public Recipe getRecipeById(int id) {
//         // Implementation
//         return null; //return the Recipe object corresponding to the given ID

//     }
        

//     /**
//      * Creates a new recipe in the database.
//      * 
//      * @param recipe the Recipe object to create
//      * @return the ID of the newly created recipe
//      */

//     public int createRecipe(Recipe recipe) {
//         // Implementation
//         return(0); //return the ID of the newly created recipe
//     }

//     /**
//      * Updates an existing recipe in the database.
//      * 
//      * @param recipe the Recipe object with updated data
//      */

//     public void updateRecipe(Recipe recipe) {
//         // Implementation
//     }

//     /**
//      * Deletes a specific recipe from the database.
//      * 
//      * @param recipe the Recipe object to delete
//      */

//     public void deleteRecipe(Recipe recipe) {
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
import java.util.LinkedList;
import java.util.List;

import com.revature.model.Chef;
import com.revature.model.Ingredient;
import com.revature.model.Recipe;
//import com.revature.domains.RecipeIngredient;
import com.revature.util.ConnectionUtil;
import com.revature.util.Page;
import com.revature.util.PageOptions;

public class RecipeDAO {
	private ConnectionUtil connectionUtil;
	private ChefDAO chefDao;
	private IngredientDAO ingredientDao;

	public RecipeDAO(ConnectionUtil connectionUtil, ChefDAO chefDao, IngredientDAO ingredientDao) {
		this.connectionUtil = connectionUtil;
		this.chefDao = chefDao;
		this.ingredientDao = ingredientDao;
	}

	public List<Recipe> getAllRecipes() {
		try (Connection connection = connectionUtil.getConnection();
				Statement statement = connection.createStatement()) {
			String sql = "SELECT * FROM recipe ORDER BY id";
			ResultSet resultSet = statement.executeQuery(sql);
			return mapRows(resultSet);

		} catch (SQLException e) {
			throw new RuntimeException("Unable to retrieve all recipes", e);
		}
	}

	public Page<Recipe> getAllRecipes(PageOptions pageOptions) {
		String sql = String.format("SELECT * FROM recipe ORDER BY %s %s", pageOptions.getSortBy(),
				pageOptions.getSortDirection());
		try (Connection connection = connectionUtil.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			ResultSet resultSet = statement.executeQuery();
			return pageResults(resultSet, pageOptions);
		} catch (SQLException e) {
			throw new RuntimeException("Unable to retrieve all recipes", e);
		}
	}

	public List<Recipe> searchRecipesByTerm(String term) {
		String sql = "SELECT * FROM recipe WHERE name LIKE ?";
		try (Connection connection = connectionUtil.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, "%" + term + "%");
			ResultSet resultSet = statement.executeQuery();
			return mapRows(resultSet);
		} catch (SQLException e) {
			throw new RuntimeException("Unable to search recipes by term", e);
		}
	}

	// public List<Recipe> searchRecipesByIngredient(String ingredient) {
	// 	String sql = "SELECT r.id, r.name, r.instructions FROM recipe r JOIN recipe_ingredient ir ON r.id = ir.recipe_id JOIN ingredient i ON ir.ingredient_id = i.id WHERE i.name LIKE ?;";
	// 	try (Connection connection = connectionUtil.getConnection();
	// 			PreparedStatement statement = connection.prepareStatement(sql)) {
	// 		statement.setString(1, "%" + ingredient + "%");
	// 		ResultSet resultSet = statement.executeQuery();
	// 		return mapRows(resultSet);
	// 	} catch (SQLException e) {
	// 		throw new RuntimeException("Unable to search recipes by ingredient", e);
	// 	}
	// }

	// public List<Recipe> searchRecipesByIngredientAndName(String name, String ingredient) {
	// 	String sql = "SELECT r.id, r.name, r.instructions FROM recipe r JOIN recipe_ingredient ir ON r.id = ir.recipe_id JOIN ingredient i ON ir.ingredient_id = i.id WHERE i.name LIKE ? AND r.name LIKE ?;";
	// 	try (Connection connection = connectionUtil.getConnection();
	// 			PreparedStatement statement = connection.prepareStatement(sql)) {
	// 		statement.setString(1, "%" + ingredient + "%");
	// 		statement.setString(2, "%" + name + "%");
	// 		;
	// 		ResultSet resultSet = statement.executeQuery();
	// 		return mapRows(resultSet);
	// 	} catch (SQLException e) {
	// 		throw new RuntimeException("Unable to search recipes by ingredient", e);
	// 	}
	// }

	public Page<Recipe> searchRecipesByTerm(String term, PageOptions pageOptions) {
		String sql = String.format("SELECT * FROM recipe WHERE name LIKE ? ORDER BY %s %s", pageOptions.getSortBy(),
				pageOptions.getSortDirection());
		try (Connection connection = connectionUtil.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, "%" + term + "%");
			ResultSet resultSet = statement.executeQuery();
			return pageResults(resultSet, pageOptions);
		} catch (SQLException e) {
			throw new RuntimeException("Unable to search recipes by term", e);
		}
	}

	public Recipe getRecipeById(int id) {
		String sql = "SELECT * FROM recipe WHERE id = ?";
		try (Connection connection = connectionUtil.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			return mapSingleRow(resultSet);
		} catch (SQLException e) {
			throw new RuntimeException("Unable to retrieve recipe by id", e);
		}
	}

	// public int createRecipe(Recipe recipe) {
	// 	String sql = "INSERT INTO recipe (name, instructions, author) VALUES (?, ?, ?)";
	// 	try (Connection connection = connectionUtil.getConnection();
	// 			PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	// 		statement.setString(1, recipe.getName());
	// 		statement.setString(2, recipe.getInstructions());
	// 		statement.setInt(3, recipe.getAuthor().getId());
	// 		int affectedRows = statement.executeUpdate();

	// 		if (affectedRows < 1) {
	// 			throw new SQLException("Creating recipe failed, no rows affected");
	// 		}
	// 		ResultSet generatedKeys = statement.getGeneratedKeys();
	// 		if (generatedKeys.next()) {
	// 			recipe.setId(generatedKeys.getInt(1));
	// 			if (recipe.getInstructions() != null) {
	// 				saveIngredients(recipe);
	// 			}
	// 			return generatedKeys.getInt(1);
	// 		} else {
	// 			throw new SQLException("Creating recipe failed, no ID obtained");
	// 		}

	// 	} catch (SQLException e) {
	// 		throw new RuntimeException("Unable to create recipe", e);
	// 	}
	// }

	
	 public int createRecipe(Recipe recipe) {
        String sql = "INSERT INTO recipe (name, instructions, author) VALUES (?, ?, ?)";
        int generatedId = 0;

        try (Connection conn = connectionUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

           statement.setString(1, recipe.getName());
			statement.setString(2, recipe.getInstructions());
			statement.setInt(3, recipe.getAuthor().getId());
		//	int affectedRows = statement.executeUpdate();
		statement.executeUpdate();

            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    generatedId = rs.getInt(1); // Get generated ID
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return generatedId;
}
	 

	public void updateRecipe(Recipe recipe) {
		String sql = "UPDATE recipe SET name = ?, instructions = ?, author = ? WHERE id = ?";
		try (Connection connection = connectionUtil.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, recipe.getName());
			statement.setString(2, recipe.getInstructions());
			statement.setInt(3, recipe.getAuthor().getId());
			statement.setInt(4, recipe.getId());
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("Unable to update recipe", e);
		}
	}

	public void deleteRecipe(Recipe recipe) {
		// Delete ingredient relations
		String ingredientSql = "DELETE FROM recipe_ingredient WHERE recipe_id = ?";
		String sql = "DELETE FROM recipe WHERE id = ?";
		try (Connection connection = connectionUtil.getConnection()) {
			connectionUtil.getConnection();
			connection.setAutoCommit(false);
			PreparedStatement ingredientStatement = connection.prepareStatement(ingredientSql);
			ingredientStatement.setInt(1, recipe.getId());
			ingredientStatement.executeUpdate();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, recipe.getId());
			statement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			throw new RuntimeException("Unable to delete recipe", e);
		}
	}

	private Recipe mapSingleRow(ResultSet set) throws SQLException {
		int id = set.getInt("id");
		String name = set.getString("name");
		String instructions = set.getString("instructions");
		Chef author = chefDao.getChefById(set.getInt("author"));
		return new Recipe(id, name, instructions, author);
	}

	private List<Recipe> mapRows(ResultSet set) throws SQLException {
		List<Recipe> recipes = new ArrayList<>();
		while (set.next()) {
			recipes.add(mapSingleRow(set));
		}
		return recipes;
	}

	private Page<Recipe> pageResults(ResultSet set, PageOptions pageOptions) throws SQLException {
		List<Recipe> recipes = mapRows(set);
		int offset = (pageOptions.getPageNumber() - 1) * pageOptions.getPageSize();
		int limit = offset + pageOptions.getPageSize();
		List<Recipe> slicedList = sliceList(recipes, offset, limit);
		return new Page<>(pageOptions.getPageNumber(), pageOptions.getPageSize(),
				recipes.size() / pageOptions.getPageSize(), recipes.size(), slicedList);
	}

	private List<Recipe> sliceList(List<Recipe> list, int start, int end) {
		List<Recipe> sliced = new ArrayList<>();
		for (int i = start; i < end; i++) {
			sliced.add(list.get(i));
		}
		return sliced;
	}

	// private List<Recipe> getIngredients(int id) {

	// 	String sql = "SELECT ingredient_id, vol, measure FROM recipe_ingredient WHERE recipe_id = ?";
	// 	List<Recipe> ingredients = new LinkedList<Recipe>();
	// 	try (Connection connection = connectionUtil.getConnection();
	// 			PreparedStatement statement = connection.prepareStatement(sql)) {
	// 		statement.setInt(1, id);
	// 		ResultSet resultSet = statement.executeQuery();
	// 		while (resultSet.next()) {
	// 			ingredients.add(new RecipeIngredient(ingredientDao.getIngredientById(resultSet.getInt(1)),
	// 					resultSet.getDouble(2), resultSet.getString(3)));
	// 		}
	// 	} catch (SQLException e) {
	// 		throw new RuntimeException("Unable to retrieve all recipes", e);
	// 	}
	// 	return ingredients;
	// }

	// private void saveIngredients(Recipe recipe) {

	// 	String sql = "INSERT INTO  recipe_ingredient (recipe_id, ingredient_id, vol, measure) VALUES (?,?,?,?)";
	// 	for (Recipe i : recipe.getIngredients()) {
	// 		try (Connection connection = connectionUtil.getConnection();
	// 				PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	// 			statement.setInt(1, recipe.getId());
	// 			statement.setInt(2, i.getId());
	// 			statement.setString(3, i.getName());
	// 			//statement.setString(4, i.getMeasure());
	// 			int affectedRows = statement.executeUpdate();

	// 		} catch (SQLException e) {
	// 			throw new RuntimeException("Unable to create recipe", e);
	// 		}
	// 	}
	// }
}