package com.revature.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.h2.Driver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.revature.controller.AuthenticationController;
import com.revature.controller.IngredientController;
import com.revature.controller.RecipeController;
import com.revature.model.Chef;
import com.revature.model.Recipe;
// import com.revature.dtos.LoginRequest;
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
import com.revature.util.Page;

import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

class RecipeIntegrationTest {

	private static int PORT = 8082;
	private static String BASE_URL = "http://localhost:" + PORT;
	
	private List<Recipe> recipeList = new ArrayList<Recipe>();
	private List<Chef> chefList = new ArrayList<Chef>();
	private String jsonRecipeList;
	private JavalinAppUtil appUtil;
	private ConnectionUtil connectionUtil;
	private RecipeDAO recipeDao;
	private RecipeService recipeService;
	private RecipeController recipeController;
	private ChefDAO chefDao;
	private ChefService chefService;
	private IngredientDAO ingredientDao;
	private IngredientService ingredientService;
	private IngredientController ingredientController;
	private AuthenticationService authService;
	private AuthenticationController authController;
	private AdminMiddleware adminMiddleware;
	private String token;
	private Javalin app;
	private OkHttpClient client;
	
	@BeforeAll
	static void setUpDB() {
		DBTestSetUp.RUN_DDL();
	}

	@BeforeEach
	void setUpTestsData() throws SQLException, IOException {
		DBTestSetUp.RUN_DML();
		recipeList.clear();
		chefList.addAll(Arrays.asList(
	            new Chef(1, "JoeCool", "snoopy@null.com", "redbarron", false),
	            new Chef(2, "CharlieBrown", "goodgrief@peanuts.com", "thegreatpumpkin", false),
	            new Chef(3, "RevaBuddy", "revature@revature.com", "codelikeaboss", false),
	            new Chef(4, "ChefTrevin", "trevin@revature.com", "trevature", true)
	        ));
		recipeList.addAll(Arrays.asList(new Recipe(1, "carrot soup", "Put carrot in water.  Boil.  Maybe salt.", chefList.get(0)),
				new Recipe(2, "potato soup", "Put potato in water.  Boil.  Maybe salt.", chefList.get(1)),
				new Recipe(3, "tomato soup", "Put tomato in water.  Boil.  Maybe salt.", chefList.get(1)),
				new Recipe(4, "lemon rice soup", "Put lemon and rice in water.  Boil.  Maybe salt.", chefList.get(3)),
				new Recipe(5, "stone soup", "Put stone in water.  Boil.  Maybe salt.", chefList.get(3))));

		jsonRecipeList = new JavalinJackson().toJsonString(recipeList.toArray(), Recipe[].class);

		connectionUtil = ConnectionUtil.getInstance().configure("sa", "", "jdbc:h2:./h2/db", new Driver());

		chefDao = new ChefDAO(connectionUtil);
		recipeDao = new RecipeDAO(connectionUtil, chefDao, ingredientDao);
		recipeService = new RecipeService(recipeDao);
		chefService = new ChefService(chefDao);
		authService = new AuthenticationService(chefService);
		recipeController = new RecipeController(recipeService, authService);
		authController = new AuthenticationController(chefService, authService);
		ingredientDao = new IngredientDAO(connectionUtil);
		ingredientService = new IngredientService(ingredientDao);
		ingredientController = new IngredientController(ingredientService);
		adminMiddleware = new AdminMiddleware(chefService);
		appUtil = new JavalinAppUtil(recipeController, authController, ingredientController, adminMiddleware);
		app = appUtil.getApp();
		app.start(PORT);
		client = new OkHttpClient();
	// 	RequestBody chefBody = RequestBody.create(new JavalinJackson().toJsonString(new LoginRequest(chefList.get(3).getUsername(), chefList.get(3).getPassword()), LoginRequest.class), MediaType.get("application/json; charset=utf-8"));
	// 	Request loginRequest = new Request.Builder().url(BASE_URL + "/login").post(chefBody).build();
	// 	Response loginResponse = client.newCall(loginRequest).execute();
	// 	token = loginResponse.body().string();
		
	 }
	
	@AfterEach
	void tearDownTestsData() {
		
		app.close();
		
	}
	
	@Test
	void testGetRecipe() throws IOException {
		Request request = new Request.Builder().url(BASE_URL + "/recipe/2").addHeader("Authorization", token).get().build();
		Response response = client.newCall(request).execute();
		assertEquals(200, response.code(), "Should return with a success status code.  Expected: 200 Actual: " + response.code());
		assertEquals(new JavalinJackson().toJsonString(recipeList.get(1), Recipe.class),
				response.body().string(), "Single recipe should be returned a json");
	}


	@Test
	void testGetAllRecipe() throws IOException {
		Request request = new Request.Builder().url(BASE_URL + "/recipe").addHeader("Authorization", token).get().build();
		Response response = client.newCall(request).execute();
		assertEquals(200, response.code(), "Should return with a success status code.  Expected: 200 Actual: " + response.code());
		assertEquals(jsonRecipeList, response.body().string(),
				"Full list of recipe should be returned a json");

	}


	@Test
	void testPostRecipe() throws Exception {
		
		Recipe newRecipe = new Recipe(6, "fried fish", "fish, oil, stove", chefList.get(3));
		RequestBody recipeBody = RequestBody.create(new JavalinJackson().toJsonString(newRecipe, Recipe.class), MediaType.get("application/json; charset=utf-8"));
		Request recipeRequest = new Request.Builder().url(BASE_URL + "/recipe").addHeader("Authorization", token).post(recipeBody).build();
		Response postResponse = client.newCall(recipeRequest).execute();
		assertEquals(201, postResponse.code(), postResponse.body().string());
		Request getRequest = new Request.Builder().url(BASE_URL + "/recipe/6").addHeader("Authorization", token).get().build();
		Response getResponse = client.newCall(getRequest).execute();
		assertEquals(200, getResponse.code());
		assertEquals(new JavalinJackson().toJsonString(newRecipe, Recipe.class), getResponse.body().string() , "Newly created Recipe should be returned a json");
		
	}

	@Test
	void testPutRecipe() throws IOException {
		
		Recipe updatedRecipe = recipeList.get(0);
		updatedRecipe.setInstructions("Don't add salt");
		RequestBody recipeBody = RequestBody.create(new JavalinJackson().toJsonString(updatedRecipe, Recipe.class), MediaType.get("application/json; charset=utf-8"));
		Request recipeRequest = new Request.Builder().url(BASE_URL + "/recipe/1").addHeader("Authorization", token).put(recipeBody).build();
		Response putResponse = client.newCall(recipeRequest).execute();
		assertEquals(200, putResponse.code(), putResponse.body().string());
		Request request = new Request.Builder().url(BASE_URL + "/recipe/1").addHeader("Authorization", token).get().build();
		Response response = client.newCall(request).execute();		
		assertEquals(200, response.code(), () -> "Get should return a successful status code");
		assertEquals(new JavalinJackson().toJsonString(updatedRecipe, Recipe.class),
				response.body().string(), "Single recipe should be returned a json");

	}

	@Test
	void testDeleteRecipe() throws IOException {
		
		Request request = new Request.Builder().url(BASE_URL + "/recipe/2").addHeader("Authorization", token).delete().build();
		Response response = client.newCall(request).execute();
		assertEquals(200, response.code(), () -> "Recipe should delete successfully");
		Request getRequest = new Request.Builder().url(BASE_URL + "/reicpe/2").get().addHeader("Authorization", token).build();
		Response getResponse = client.newCall(getRequest).execute();
		assertEquals(404, getResponse.code(), () -> "After deletion, reicpe should non be found");
		

	}

	@Test
	void testFilteredPageOfRecipes() throws IOException {
			
			List<Recipe> filteredResult = List.of(recipeList.get(2));
			Page<Recipe> filteredResultPage = new Page<Recipe>(2, 1, 2, 2, filteredResult);
			String filteredResultJSON = new JavalinJackson().toJsonString(filteredResultPage, Page.class);
			Request request = new Request.Builder().url(BASE_URL + "/recipe?term=ato&page=2&pageSize=1&sortBy=name&sortDirection=asc").get().addHeader("Authorization", token).build();
			Response response = client.newCall(request).execute();
			assertEquals(filteredResultJSON,
					response.body().string(),
					"The single result should be returned");
	}

}