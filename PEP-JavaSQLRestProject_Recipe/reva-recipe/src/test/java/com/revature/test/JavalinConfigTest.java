package com.revature.test;

import java.sql.SQLException;

import org.h2.Driver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.revature.controller.AuthenticationController;
import com.revature.controller.IngredientController;
import com.revature.controller.RecipeController;
import com.revature.dao.ChefDAO;
import com.revature.dao.IngredientDAO;
import com.revature.dao.RecipeDAO;
import com.revature.service.AuthenticationService;
import com.revature.service.ChefService;
import com.revature.service.IngredientService;
import com.revature.service.RecipeService;
import com.revature.util.AdminMiddleware;
import com.revature.util.ConnectionUtil;
import com.revature.util.JavalinAppUtil;

class JavalinConfigTest {

	private ConnectionUtil connectionUtil;
	private RecipeDAO recipeDao;
	private RecipeService recipeService;
	private RecipeController recipeController;
	private ChefDAO chefDao;
	private ChefService chefService;
	private AuthenticationService authService;
	private AuthenticationController authController;
	private IngredientDAO ingredientDao;
	private IngredientService ingredientService;
	private IngredientController ingredientController;
	private AdminMiddleware adminMiddleware;

	@BeforeEach
	void setUpTestsData() throws SQLException {
		
		connectionUtil = ConnectionUtil.getInstance().configure("sa", "", "jdbc:h2:./h2/db", new Driver());
		
		chefDao = new ChefDAO(connectionUtil);
		chefService = new ChefService(chefDao);
		authService = new AuthenticationService(chefService);
		authController = new AuthenticationController(chefService, authService);

		ingredientDao = new IngredientDAO(connectionUtil);
		ingredientService = new IngredientService(ingredientDao);
		ingredientController = new IngredientController(ingredientService);
		adminMiddleware = new AdminMiddleware(chefService, null);
		
		
		recipeDao = new RecipeDAO(connectionUtil, chefDao, ingredientDao);
		recipeService = new RecipeService(recipeDao);
		recipeController = new RecipeController(recipeService, authService);
	}
	
	
	@Test
	void test() {
		
		new JavalinAppUtil(recipeController, authController, ingredientController, adminMiddleware).getApp().start();
		
	}

}