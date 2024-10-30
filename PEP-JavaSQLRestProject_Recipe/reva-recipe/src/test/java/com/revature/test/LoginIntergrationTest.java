// package com.revature.test;
// package com.revature.test;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.assertj.core.api.Assertions.*;

// import java.io.IOException;
// import java.sql.SQLException;

// import org.h2.Driver;
// import org.junit.jupiter.api.AfterEach;
// import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// import com.revature.controller.AuthenticationController;
// import com.revature.controller.IngredientController;
// import com.revature.controller.RecipeController;
// import com.revature.model.Chef;
// import com.revature.model.Recipe;
// import com.revature.dtos.LoginRequest;
// import com.revature.dao.ChefDao;
// import com.revature.dao.IngredientDao;
// import com.revature.dao.RecipeDao;
// import com.revature.service.AuthenticationService;
// import com.revature.service.ChefService;
// import com.revature.service.IngredientService;
// import com.revature.service.RecipeService;
// import com.revature.util.AdminMiddleware;
// import com.revature.util.ConnectionUtil;
// import com.revature.util.JavalinAppUtil;

// import io.javalin.Javalin;
// import io.javalin.json.JavalinJackson;
// import okhttp3.MediaType;
// import okhttp3.OkHttpClient;
// import okhttp3.Request;
// import okhttp3.RequestBody;
// import okhttp3.Response;

// class LoginIntegrationTest {
	
// 	private static int PORT = 8082;
// 	private static String BASE_URL = "http://localhost:" + PORT;
	
// 	private ConnectionUtil connectionUtil;
// 	private RecipeDao recipeDao;
// 	private RecipeService recipeService;
// 	private RecipeController recipeController;
// 	private ChefDao chefDao;
// 	private ChefService chefService;
// 	private AuthenticationService authService;
// 	private AuthenticationController authController;
// 	private IngredientDao ingredientDao;
// 	private IngredientService ingredientService;
// 	private IngredientController ingredientController;
// 	private AdminMiddleware adminMiddleware;
// 	private JavalinAppUtil appUtil;
// 	private Javalin app;
// 	private OkHttpClient client;
	
// 	@BeforeAll
// 	static void setUpDB() {
// 		DBTestSetUp.RUN_DDL();
// 	}
	
// 	@BeforeEach
// 	void setUpTestsData() throws SQLException {
// 		DBTestSetUp.RUN_DML();

// 		connectionUtil = ConnectionUtil.getInstance().configure("sa", "", "jdbc:h2:./h2/db", new Driver());

// 		chefDao = new ChefDao(connectionUtil);
// 		ingredientDao = new IngredientDao(connectionUtil);
// 		recipeDao = new RecipeDao(connectionUtil, chefDao, ingredientDao);
// 		recipeService = new RecipeService(recipeDao);
// 		ingredientService = new IngredientService(ingredientDao);
// 		chefService = new ChefService(chefDao);
// 		authService = new AuthenticationService(chefService);
// 		authController = new AuthenticationController(chefService, authService);
// 		recipeController = new RecipeController(recipeService, authService);
// 		ingredientController = new IngredientController(ingredientService);
// 		appUtil = new JavalinAppUtil(recipeController, authController, ingredientController, adminMiddleware);
// 		app = appUtil.getApp();
// 		app.start(PORT);
// 		client = new OkHttpClient();
		
// 	}
	
// 	@AfterEach
// 	void tearDownTestsData() {
		
// 		app.close();
		
// 	}
	
// 	@Test
// 	void testSuccessfulLogin() throws IOException {
		
// 		Chef chef = new Chef(1, "JoeCool", "snoopy@null.com", "redbarron", false);
// 		LoginRequest loginRequestDTO = new LoginRequest(chef.getUsername(), chef.getPassword());
// 		Recipe newRecipe = new Recipe(6, "fried fish", "fish, oil, stove", chef);
// 		RequestBody chefBody = RequestBody.create(new JavalinJackson().toJsonString(loginRequestDTO, LoginRequest.class), MediaType.get("application/json; charset=utf-8"));
// 		Request loginRequest = new Request.Builder().url(BASE_URL + "/login").post(chefBody).build();
// 		Response loginResponse = client.newCall(loginRequest).execute();
// 		String token = loginResponse.body().string();
// 		RequestBody recipeBody = RequestBody.create(new JavalinJackson().toJsonString(newRecipe, Recipe.class), MediaType.get("application/json; charset=utf-8"));
// 		Request recipeRequest = new Request.Builder().url("http://localhost:8082/recipe").addHeader("Authorization", token).post(recipeBody).build();
// 		assertEquals(200, loginResponse.code(), () -> "login should return a success status code.  Expected: 200, Actual: " + loginResponse.code());
// 		Response postResponse = client.newCall(recipeRequest).execute();
// 		assertEquals(201, postResponse.code(), postResponse.body().string());
// 		Request getRequest = new Request.Builder().url(BASE_URL + "/recipe/6").addHeader("Authorization", token).get().build();
// 		Response getResponse = client.newCall(getRequest).execute();
// 		assertEquals(200, getResponse.code());
// 		assertEquals(new JavalinJackson().toJsonString(newRecipe, Recipe.class), getResponse.body().string() , "Newly created Recipe should be returned a json");
		
// 	}
	
// 	@Test
// 	void testUnsuccessfulLogin() throws IOException {
		
// 		Chef chef = new Chef(1, "JoeCool", "snoopy@null.com", "woodstock", false);
// 		LoginRequest loginRequestDTO = new LoginRequest(chef.getUsername(), chef.getPassword());
// 		Recipe newRecipe = new Recipe(6, "fried fish", "fish, oil, stove", chef);
// 		RequestBody chefBody = RequestBody.create(new JavalinJackson().toJsonString(loginRequestDTO, LoginRequest.class), MediaType.get("application/json; charset=utf-8"));
// 		Request loginRequest = new Request.Builder().url(BASE_URL + "/login").post(chefBody).build();
// 		Response loginResponse = client.newCall(loginRequest).execute();
// 		String token = loginResponse.body().string();
// 		RequestBody recipeBody = RequestBody.create(new JavalinJackson().toJsonString(newRecipe, Recipe.class), MediaType.get("application/json; charset=utf-8"));
// 		Request recipeRequest = new Request.Builder().url("http://localhost:8082/recipe").addHeader("Authorization", token).post(recipeBody).build();
// 		Response postResponse = client.newCall(recipeRequest).execute();
// 		assertEquals(401, loginResponse.code(), () -> "login should return unauthorized status code.  Expected: 401, Actual: " + loginResponse.code());
// 		assertThat(token).isIn("", "Invalid credentials");
// 		assertEquals(401, postResponse.code());
		
// 	}
	
// 	@Test
// 	void testLogout() throws IOException {
		
// 		Chef chef = new Chef(1, "JoeCool", "snoopy@null.com", "redbarron", false);
// 		LoginRequest loginRequestDTO = new LoginRequest(chef.getUsername(), chef.getPassword());
// 		Recipe newRecipe = new Recipe(6, "fried fish", "fish, oil, stove", chef);
// 		RequestBody chefBody = RequestBody.create(new JavalinJackson().toJsonString(loginRequestDTO, LoginRequest.class), MediaType.get("application/json; charset=utf-8"));
// 		Request loginRequest = new Request.Builder().url(BASE_URL + "/login").post(chefBody).build();
// 		Response loginResponse = client.newCall(loginRequest).execute();
// 		String token = loginResponse.body().string();
// 		Request logoutRequest = new Request.Builder().url(BASE_URL + "/logout").post(chefBody).addHeader("Authorization", token).build();
// 		Response logoutResponse = client.newCall(logoutRequest).execute();
// 		RequestBody recipeBody = RequestBody.create(new JavalinJackson().toJsonString(newRecipe, Recipe.class), MediaType.get("application/json; charset=utf-8"));
// 		Request recipeRequest = new Request.Builder().url("http://localhost:8082/recipe").addHeader("Authorization", token).post(recipeBody).build();
// 		Response postResponse = client.newCall(recipeRequest).execute();
// 		Request getRequest = new Request.Builder().url(BASE_URL + "/reicpe/6").get().build();
// 		Response getResponse = client.newCall(getRequest).execute();
// 		assertEquals(200, loginResponse.code(), () -> "login should return a success status code.  Expected: 200, Actual: " + loginResponse.code());
// 		assertEquals(200, logoutResponse.code(), () -> "Logout should be successful");
// 		assertEquals(401, postResponse.code(), postResponse.body().string());
// 		assertEquals(404, getResponse.code(), () -> "recipe should not have been created");
		
// 	}
	
// 	@Test
// 	void testRegister() throws IOException {
		
// 		Chef chef = new Chef(0, "new chef", "newchef@chefmail.com", "1234abc", false);
// 		RequestBody chefBody = RequestBody.create(new JavalinJackson().toJsonString(chef, Chef.class), MediaType.get("application/json; charset=utf-8"));
// 		Request registerRequest = new Request.Builder().url(BASE_URL + "/register").post(chefBody).build();
// 		Response registerResponse = client.newCall(registerRequest).execute();
// 		assertEquals(201, registerResponse.code(), () -> "Should successfully register user");
// 		LoginRequest loginRequestDTO = new LoginRequest(chef.getUsername(), chef.getPassword());
// 		RequestBody loginBody = RequestBody.create(new JavalinJackson().toJsonString(loginRequestDTO, LoginRequest.class), MediaType.get("application/json; charset=utf-8"));
// 		Request loginRequest = new Request.Builder().url(BASE_URL + "/login").post(loginBody).build();
// 		Response loginResponse = client.newCall(loginRequest).execute();
// 		assertEquals(200, loginResponse.code(), () -> "login should return a success status code.  Expected: 200, Actual: " + loginResponse.code());
// 		assertNotNull(loginResponse.body().toString(), () -> "login should return a token in the body");

// 	}

// }