// package com.revature.test;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertIterableEquals;
// import static org.junit.jupiter.api.Assertions.assertTrue;

// import java.sql.SQLException;
// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.List;

// import org.h2.Driver;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// import com.revature.model.Chef;
// import com.revature.model.Recipe;
// import com.revature.dao.ChefDAO;
// import com.revature.dao.RecipeDAO;
// import com.revature.util.ConnectionUtil;
// import com.revature.util.Page;
// import com.revature.util.PageOptions;

// class RecipeDaoTest {

// 	private List<Recipe> recipeList = new ArrayList<Recipe>();
//     private List<Recipe> recipeWithPotatoList = new ArrayList<Recipe>();
// 	private List<Chef> chefList = new ArrayList<Chef>();
// 	private ConnectionUtil connectionUtil;
// 	private RecipeDAO recipeDao;
// 	private ChefDAO chefDao;

// 	@BeforeEach
// 	void setUpTestsData() throws SQLException {
// 		DBTestSetUp.RUN_DDL();
// 		DBTestSetUp.RUN_DML();
// 		recipeList.clear();
// 		recipeList.addAll(Arrays.asList(new Recipe("carrot soup", "Put carrot in water.  Boil.  Maybe salt."),
// 				new Recipe("potato soup", "Put potato in water.  Boil.  Maybe salt."),
// 				new Recipe("tomato soup", "Put tomato in water.  Boil.  Maybe salt."),
// 				new Recipe("lemon rice soup", "Put lemon and rice in water.  Boil.  Maybe salt."),
// 				new Recipe("stone soup", "Put stone in water.  Boil.  Maybe salt.")));
// 		recipeWithPotatoList.add(new Recipe("potato soup", "Put potato in water.  Boil.  Maybe salt."));
//         connectionUtil = ConnectionUtil.getInstance().configure("sa", "", "jdbc:h2:./h2/db", new Driver());
// 		recipeDao = new RecipeDAO(connectionUtil, null, null);
// 		chefList.addAll(Arrays.asList(
// 	            new Chef(1, "JoeCool", "snoopy@null.com", "redbarron", false),
// 	            new Chef(2, "CharlieBrown", "goodgrief@peanuts.com", "thegreatpumpkin", false),
// 	            new Chef(3, "RevaBuddy", "revature@revature.com", "codelikeaboss", false),
// 	            new Chef(4, "ChefTrevin", "trevin@revature.com", "trevature", true)
// 	        ));
// 		recipeList.addAll(Arrays.asList(new Recipe(1, "carrot soup", "Put carrot in water.  Boil.  Maybe salt.", chefList.get(0)),
// 				new Recipe(2, "potato soup", "Put potato in water.  Boil.  Maybe salt.", chefList.get(1)),
// 				new Recipe(3, "tomato soup", "Put tomato in water.  Boil.  Maybe salt.", chefList.get(1)),
// 				new Recipe(4, "lemon rice soup", "Put lemon and rice in water.  Boil.  Maybe salt.", chefList.get(3)),
// 				new Recipe(5, "stone soup", "Put stone in water.  Boil.  Maybe salt.", chefList.get(3))));
// 		connectionUtil = ConnectionUtil.getInstance().configure("sa", "", "jdbc:h2:./h2/db", new Driver());
// 		chefDao = new ChefDAO(connectionUtil);
// 		recipeDao = new RecipeDAO(connectionUtil, chefDao, null);
// 	}

// 	@Test
// 	void readOneRecipe() {
// 		Recipe recipe = recipeDao.getRecipeById(1);
// 		assertEquals(recipe, recipeList.get(0), () -> "The returned recipe doesn't match the expected recipe. Expected: " + recipeList.get(0) + " Actual: " + recipe);
// 	}

// 	@Test
// 	void readAllRecipes() {
// 		List<Recipe> recipes = recipeDao.getAllRecipes();
// 		assertIterableEquals(recipeList, recipes, () -> "The returned list of recipes doesn't match the expected list of recipes.");
// 	}

// 	@Test
// 	void createRecipe() {
// 		Recipe recipeToInsert = new Recipe(0, "test recipe", "test instructions", chefList.get(0));
// 		int id = recipeDao.createRecipe(recipeToInsert);
// 		Recipe recipe = recipeDao.getRecipeById(id);
// 		assertTrue(id > 0, () -> "The returned id is not greater than 0. Actual: " + id);
// 		assertEquals(recipe, recipeToInsert, () -> "The database doesn't include the expected recipe Expected: " + recipeToInsert + " Actual: " + recipe);
// 	}

// 	@Test
// 	void deleteRecipe() {
// 		int count = recipeDao.getAllRecipes().size();
// 		Recipe recipeToDelete = recipeDao.getRecipeById(1);
// 		recipeDao.deleteRecipe(recipeToDelete);
// 		assertEquals(count - 1, recipeDao.getAllRecipes().size(), () -> "The database doesn't have the expected number of recipes after deleting a recipe. Expected: " + (count - 1) + " Actual: " + recipeDao.getAllRecipes().size());
// 	}

// 	@Test
// 	void updateRecipe() {
// 		Recipe recipeToUpdate = recipeDao.getRecipeById(1);
// 		recipeToUpdate.setName("updated name");
// 		recipeToUpdate.setInstructions("updated instructions");
// 		recipeDao.updateRecipe(recipeToUpdate);
// 		Recipe recipe = recipeDao.getRecipeById(1);
// 		assertEquals(recipe, recipeToUpdate, () -> "The database doesn't include the expected recipe after updating. Expected: " + recipeToUpdate + " Actual: " + recipe);
// 	}

// 	@Test
// 	void searchRecipesByTerm() {
// 		List<Recipe> recipes = recipeDao.searchRecipesByTerm("soup");
// 		assertIterableEquals(recipes, recipeList, () -> "The returned list of recipes doesn't match the expected list of recipes.");
// 	}

//     // @Test
// 	// void searchRecipesByIngredient() {
// 	// 	List<Recipe> recipes = recipeDao.searchRecipesByIngredient("potato");
// 	// 	assertIterableEquals(recipes, recipeWithPotatoList, () -> "The returned list of recipes doesn't match the expected list of recipes.");
// 	// }

//     // @Test
// 	// void searchRecipesByIngredientAndNamePotatoSoup() {
// 	// 	List<Recipe> recipes = recipeDao.searchRecipesByIngredientAndName("soup", "potato");
// 	// 	assertIterableEquals(recipes, recipeWithPotatoList, () -> "The returned list of recipes doesn't match the expected list of recipes.");
// 	// }

//     // @Test
// 	// void searchRecipesByIngredientAndNameNoMatch() {
// 	// 	List<Recipe> recipes = recipeDao.searchRecipesByIngredientAndName("soup", "this ingredient doesn't exist");
// 	// 	assertEquals(0, recipes.size(), () -> "The returned list of recipes doesn't match the expected list of recipes.");
// 	// }

// 	@Test
// 	void getAndPageAllRecipes() {
// 		PageOptions pageable = new PageOptions(1, 2);
// 		Page<Recipe> expectedPage = new Page<>(1, 2, recipeList.size() / 2, recipeList.size(), Arrays.asList(recipeList.get(0), recipeList.get(1)));
// 		Page<Recipe> recipes = recipeDao.getAllRecipes(pageable);
// 		assertIterableEquals(expectedPage.getItems(), recipes.getItems(), () -> "The returned page of recipes doesn't match the expected page of recipes.");
// 	}

// 	@Test
// 	void searchAndPageAllRecipesByTerm() {
// 		PageOptions pageable = new PageOptions(1, 2);
// 		Page<Recipe> expectedPage = new Page<>(1, 2, recipeList.size() / 2, recipeList.size(), Arrays.asList(recipeList.get(0), recipeList.get(1)));
// 		Page<Recipe> recipes = recipeDao.searchRecipesByTerm("soup", pageable);
// 		assertIterableEquals(expectedPage.getItems(), recipes.getItems(), () -> "The returned page of recipes doesn't match the expected page of recipes.");
// 		assertEquals(expectedPage, recipes, () -> "The returned page of recipes doesn't match the expected page of recipes.");
// 	}

// 	@Test
// 	void searchPageAndSortAllRecipesByTerm() {
// 		PageOptions pageable = new PageOptions(1, 2, "name", "desc");
// 		Page<Recipe> expectedPage = new Page<>(1, 2, recipeList.size() / 2, recipeList.size(), Arrays.asList(recipeList.get(2), recipeList.get(4)));
// 		Page<Recipe> recipes = recipeDao.searchRecipesByTerm("soup", pageable);
// 		assertIterableEquals(expectedPage.getItems(), recipes.getItems(), () -> "The returned page of recipes doesn't match the expected page of recipes.");
// 		assertEquals(expectedPage, recipes, () -> "The returned page of recipes doesn't match the expected page of recipes.");
// 	}

// }

package com.revature.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.h2.Driver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.revature.model.Chef;
import com.revature.model.Recipe;
import com.revature.dao.ChefDAO;
import com.revature.dao.RecipeDAO;
import com.revature.util.ConnectionUtil;
import com.revature.util.Page;
import com.revature.util.PageOptions;

class RecipeDaoTest {

	private List<Recipe> recipeList = new ArrayList<>();
	private List<Recipe> recipeWithPotatoList = new ArrayList<>();
	private List<Chef> chefList = new ArrayList<>();
	private ConnectionUtil connectionUtil;
	private RecipeDAO recipeDao;
	private ChefDAO chefDao;

	@BeforeEach
	void setUpTestsData() throws SQLException {
		DBTestSetUp.RUN_DDL();
		DBTestSetUp.RUN_DML();
		
    
		recipeList.clear();
		recipeList.addAll(Arrays.asList(new Recipe("carrot soup", "Put carrot in water. Boil. Maybe salt."),
				new Recipe("potato soup", "Put potato in water. Boil. Maybe salt."),
				new Recipe("tomato soup", "Put tomato in water. Boil. Maybe salt."),
				new Recipe("lemon rice soup", "Put lemon and rice in water. Boil. Maybe salt."),
				new Recipe("stone soup", "Put stone in water. Boil. Maybe salt.")));

		connectionUtil = ConnectionUtil.getInstance().configure("sa", "", "jdbc:h2:./h2/db", new Driver());
		chefDao = new ChefDAO(connectionUtil);
		recipeDao = new RecipeDAO(connectionUtil, chefDao, null);
	}

	@Test
	void readOneRecipe() {
		Recipe recipe = recipeDao.getRecipeById(1);
		assertEquals(recipe, recipeList.get(0), "The returned recipe doesn't match the expected recipe.");
	}

	@Test
	void readAllRecipes() {
		List<Recipe> recipes = recipeDao.getAllRecipes();
		assertIterableEquals(recipeList, recipes, "The returned list of recipes doesn't match the expected list.");
	}

	@Test
	void createRecipe() {
		Recipe recipeToInsert = new Recipe(0, "test recipe", "test instructions", chefList.get(0));
		int id = recipeDao.createRecipe(recipeToInsert);
		Recipe recipe = recipeDao.getRecipeById(id);
		assertTrue(id > 0, "The returned id should be greater than 0.");
		assertEquals(recipe, recipeToInsert, "The database doesn't include the expected recipe.");
	}

	@Test
	void deleteRecipe() {
		int count = recipeDao.getAllRecipes().size();
		Recipe recipeToDelete = recipeDao.getRecipeById(1);
		recipeDao.deleteRecipe(recipeToDelete);
		assertEquals(count - 1, recipeDao.getAllRecipes().size(), "The number of recipes did not reduce as expected.");
	}

	@Test
	void updateRecipe() {
		Recipe recipeToUpdate = recipeDao.getRecipeById(1);
		recipeToUpdate.setName("updated name");
		recipeToUpdate.setInstructions("updated instructions");
		recipeDao.updateRecipe(recipeToUpdate);
		Recipe recipe = recipeDao.getRecipeById(1);
		assertEquals(recipe, recipeToUpdate, "The recipe was not updated as expected.");
	}

	@Test
	void searchRecipesByTerm() {
		List<Recipe> recipes = recipeDao.searchRecipesByTerm("soup");
		assertIterableEquals(recipes, recipeList, "The search result does not match the expected list.");
	}

	@Test
	void getAndPageAllRecipes() {
		PageOptions pageable = new PageOptions(1, 2);
		Page<Recipe> expectedPage = new Page<>(1, 2, recipeList.size() / 2, recipeList.size(), Arrays.asList(recipeList.get(0), recipeList.get(1)));
		Page<Recipe> recipes = recipeDao.getAllRecipes(pageable);
		assertIterableEquals(expectedPage.getItems(), recipes.getItems(), "The page of recipes did not match.");
	}

	@Test
	void searchAndPageAllRecipesByTerm() {
		PageOptions pageable = new PageOptions(1, 2);
		Page<Recipe> expectedPage = new Page<>(1, 2, recipeList.size() / 2, recipeList.size(), Arrays.asList(recipeList.get(0), recipeList.get(1)));
		Page<Recipe> recipes = recipeDao.searchRecipesByTerm("soup", pageable);
		assertIterableEquals(expectedPage.getItems(), recipes.getItems(), "The page of recipes did not match.");
		assertEquals(expectedPage, recipes, "The page of recipes did not match.");
	}

	@Test
	void searchPageAndSortAllRecipesByTerm() {
		PageOptions pageable = new PageOptions(1, 2, "name", "desc");
		Page<Recipe> expectedPage = new Page<>(1, 2, recipeList.size() / 2, recipeList.size(), Arrays.asList(recipeList.get(2), recipeList.get(4)));
		Page<Recipe> recipes = recipeDao.searchRecipesByTerm("soup", pageable);
		assertIterableEquals(expectedPage.getItems(), recipes.getItems(), "The sorted page of recipes did not match.");
		assertEquals(expectedPage, recipes, "The sorted page of recipes did not match.");
	}

	@Test
	void handleNonExistentRecipeById() {
		Exception exception = assertThrows(RuntimeException.class, () -> recipeDao.getRecipeById(-1));
		assertTrue(exception.getMessage().contains("Unable to retrieve recipe by id"));
	}

	@Test
	void handleSqlExceptionInGetAllRecipes() throws SQLException{
		ConnectionUtil mockConnectionUtil = ConnectionUtil.getInstance().configure("sa", "", "jdbc:invalid_url", new Driver());
		RecipeDAO mockRecipeDao = new RecipeDAO(mockConnectionUtil, chefDao, null);
		assertThrows(RuntimeException.class, mockRecipeDao::getAllRecipes, "Expected a SQL exception due to invalid connection URL.");
	}
}

