// package com.revature.test;

// import java.sql.SQLException;
// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.List;

// import org.h2.Driver;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// import com.revature.model.Ingredient;
// import com.revature.dao.IngredientDAO;
// import com.revature.util.ConnectionUtil;
// import com.revature.util.Page;
// import com.revature.util.PageOptions;

// import static com.revature.test.utils.TestingUtils.assertCountDifference;
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertIterableEquals;

// public class IngredientDaoTest {
//     private List<Ingredient> ingredientList = new ArrayList<Ingredient>();
//     private ConnectionUtil connectionUtil;
//     private IngredientDAO ingredientDao;
//     private String countSelStatement = "SELECT COUNT(*) FROM INGREDIENT";
    
//     @BeforeEach
//     void setupTestsData() throws SQLException {
//         DBTestSetUp.RUN_DDL();
//         DBTestSetUp.RUN_DML();
//         ingredientList.clear();
//         connectionUtil = ConnectionUtil.getInstance().configure("sa", "", "jdbc:h2:./h2/db", new Driver());
//         ingredientDao = new IngredientDAO(connectionUtil);
//         ingredientList.addAll(Arrays.asList(
//             new Ingredient(1, "carrot"),
//             new Ingredient(2, "potato"),
//             new Ingredient(3, "tomato"),
//             new Ingredient(4, "lemon"),
//             new Ingredient(5, "rice"),
//             new Ingredient(6, "stone")
//             )
//         );
//     }

//     @Test
//     void createIngredientTest() {
//         Ingredient ingredient = new Ingredient("testIngredient");
//         assertCountDifference(1, "Expected Ingredient count to be 1 more", countSelStatement, () -> {
//             ingredientDao.createIngredient(ingredient);
//         });
//     }

//     @Test
//     void readOne() {
//         Ingredient ingredient = ingredientDao.getIngredientById(1);
//         assertEquals(ingredient, ingredientList.get(0), () -> "The returned ingredient doesn't match the expected ingredient. Expected: " + ingredientList.get(0) + " Actual: " + ingredient);
//     }

//     @Test
//     void deleteIngredientTest() {
//         Ingredient ingredient = ingredientDao.getIngredientById(1);
//         assertCountDifference(-1, "Expected Ingredient count to be 1 less", countSelStatement, () -> {
//             ingredientDao.deleteIngredient(ingredient);
//         });
//     }

//     @Test
//     void updateIngredientTest() {
//         Ingredient ingredient = ingredientDao.getIngredientById(1);
//         ingredient.setName("newName");
//         ingredientDao.updateIngredient(ingredient);
//         Ingredient updatedIngredient = ingredientDao.getIngredientById(1);
//         assertEquals(ingredient, updatedIngredient, () -> "The returned ingredient doesn't match the expected ingredient. Expected: " + ingredient + " Actual: " + updatedIngredient);
//     }

//     @Test
//     void getAllIngredientsTest() {
//         List<Ingredient> ingredients = ingredientDao.getAllIngredients();
//         assertEquals(ingredientList, ingredients, () -> "The returned ingredients don't match the expected ingredients. Expected: " + ingredientList + " Actual: " + ingredients);
//     }

//     @Test
//     void getAndPageAllIngredients() {
//         PageOptions pageOptions = new PageOptions(1, 2);
//         Page<Ingredient> expectedIngredients = new Page<>(1, 2, ingredientList.size()/2, ingredientList.size(), ingredientList.subList(0, 2));
//         Page<Ingredient> ingredients = ingredientDao.getAllIngredients(pageOptions);
//         assertIterableEquals(expectedIngredients.getItems(), ingredients.getItems(), "The returned ingredients don't match the expected ingredients. Expected: " + expectedIngredients.getItems() + " Actual: " + ingredients.getItems());
//     }

//     @Test
//     void searchIngredientsTest() {
//         List<Ingredient> ingredients = ingredientDao.searchIngredients("to");
//         List<Ingredient> expectedIngredients = Arrays.asList(ingredientList.get(1), ingredientList.get(2), ingredientList.get(5));
//         assertIterableEquals(expectedIngredients, ingredients, () -> "The returned ingredients don't match the expected ingredients. Expected: " + expectedIngredients + " Actual: " + ingredients);
//     }

//     @Test
//     void searchAndPageIngredientsTest() {
//         PageOptions pageOptions = new PageOptions(1, 2);
//         Page<Ingredient> expectedIngredients = new Page<>(1, 2, 2, 3, Arrays.asList(ingredientList.get(1), ingredientList.get(2)));
//         Page<Ingredient> ingredients = ingredientDao.searchIngredients("to", pageOptions);
//         ingredients.getItems().forEach(System.out::println);
//         expectedIngredients.getItems().forEach(System.out::println);
//         assertEquals(expectedIngredients, ingredients, "The returned ingredients don't match the expected ingredients. Expected: " + expectedIngredients + " Actual: " + ingredients);
//     }
// }

// package com.revature.test;

// import java.sql.SQLException;
// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.List;

// import org.h2.Driver;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// import com.revature.model.Ingredient;
// import com.revature.dao.IngredientDAO;
// import com.revature.util.ConnectionUtil;
// import com.revature.util.Page;
// import com.revature.util.PageOptions;

// import static com.revature.test.utils.TestingUtils.assertCountDifference;
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertIterableEquals;

// public class IngredientDaoTest {
//     private List<Ingredient> ingredientList = new ArrayList<>();
//     private ConnectionUtil connectionUtil;
//     private IngredientDAO ingredientDao;
//     private String countSelStatement = "SELECT COUNT(*) FROM INGREDIENT";
    
//     @BeforeEach
//     void setupTestsData() throws SQLException {
//         DBTestSetUp.RUN_DDL();
//         DBTestSetUp.RUN_DML();
//         ingredientList.clear();
//         connectionUtil = ConnectionUtil.getInstance().configure("sa", "", "jdbc:h2:./h2/db", new Driver());
//         ingredientDao = new IngredientDAO(connectionUtil);
//         ingredientList.addAll(Arrays.asList(
//             new Ingredient(1, "carrot"),
//             new Ingredient(2, "potato"),
//             new Ingredient(3, "tomato"),
//             new Ingredient(4, "lemon"),
//             new Ingredient(5, "rice"),
//             new Ingredient(6, "stone")
//         ));
//     }

//     @Test
//     void createIngredientTest() {
//         Ingredient ingredient = new Ingredient("testIngredient");
//         int newId = ingredientDao.createIngredient(ingredient);
//         // Verify that the ingredient was created with the new ID
//         Ingredient createdIngredient = ingredientDao.getIngredientById(newId);
//         assertEquals(ingredient.getName(), createdIngredient.getName(), "The created ingredient does not match the expected ingredient.");
//     }

//     @Test
//     void readOne() {
//         Ingredient ingredient = ingredientDao.getIngredientById(1);
//         assertEquals(ingredientList.get(0), ingredient, "The returned ingredient doesn't match the expected ingredient.");
//     }

//     @Test
//     void deleteIngredientTest() {
//         Ingredient ingredient = ingredientDao.getIngredientById(1);
//         assertCountDifference(-1, "Expected Ingredient count to be 1 less", countSelStatement, () -> {
//             ingredientDao.deleteIngredient(ingredient);
//         });
//         // Verify that the ingredient has been deleted
//         Ingredient deletedIngredient = ingredientDao.getIngredientById(1);
//         assertEquals(null, deletedIngredient, "Ingredient was not deleted properly.");
//     }

//     @Test
//     void updateIngredientTest() {
//         Ingredient ingredient = ingredientDao.getIngredientById(1);
//         ingredient.setName("newName");
//         ingredientDao.updateIngredient(ingredient);
//         Ingredient updatedIngredient = ingredientDao.getIngredientById(1);
//         assertEquals("newName", updatedIngredient.getName(), "The ingredient name was not updated correctly.");
//     }

//     @Test
//     void getAllIngredientsTest() {
//         List<Ingredient> ingredients = ingredientDao.getAllIngredients();
//         assertEquals(ingredientList.size(), ingredients.size(), "The size of the returned ingredients list doesn't match the expected size.");
//         assertIterableEquals(ingredientList, ingredients, "The returned ingredients don't match the expected ingredients.");
//     }

//     @Test
//     void getAndPageAllIngredients() {
//         PageOptions pageOptions = new PageOptions(1, 2);
//         Page<Ingredient> expectedIngredients = new Page<>(1, 2, ingredientList.size()/2, ingredientList.size(), ingredientList.subList(0, 2));
//         Page<Ingredient> ingredients = ingredientDao.getAllIngredients(pageOptions);
//         assertIterableEquals(expectedIngredients.getItems(), ingredients.getItems(), "The returned ingredients don't match the expected ingredients.");
//     }

//     @Test
//     void searchIngredientsTest() {
//         List<Ingredient> ingredients = ingredientDao.searchIngredients("to");
//         List<Ingredient> expectedIngredients = Arrays.asList(ingredientList.get(1), ingredientList.get(2), ingredientList.get(5));
//         assertIterableEquals(expectedIngredients, ingredients, "The returned ingredients don't match the expected ingredients.");
//     }

//     @Test
//     void searchAndPageIngredientsTest() {
//         PageOptions pageOptions = new PageOptions(1, 2);
//         Page<Ingredient> expectedIngredients = new Page<>(1, 2, 2, 3, Arrays.asList(ingredientList.get(1), ingredientList.get(2)));
//         Page<Ingredient> ingredients = ingredientDao.searchIngredients("to", pageOptions);
//         assertEquals(expectedIngredients, ingredients, "The returned ingredients don't match the expected ingredients.");
//     }
// }

package com.revature.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.h2.Driver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.revature.model.Ingredient;
import com.revature.dao.IngredientDAO;
import com.revature.util.ConnectionUtil;
import com.revature.util.Page;
import com.revature.util.PageOptions;

import static com.revature.test.utils.TestingUtils.assertCountDifference;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class IngredientDaoTest {
    private List<Ingredient> ingredientList = new ArrayList<>();
    private ConnectionUtil connectionUtil;
    private IngredientDAO ingredientDao;
    private String countSelStatement = "SELECT COUNT(*) FROM INGREDIENT";

    @BeforeEach
    void setupTestsData() throws SQLException {
        DBTestSetUp.RUN_DDL(); // Create table
        DBTestSetUp.RUN_DML(); // Insert data
        ingredientList.clear();
        connectionUtil = ConnectionUtil.getInstance().configure("sa", "", "jdbc:h2:./h2/db", new Driver());
        ingredientDao = new IngredientDAO(connectionUtil);
        ingredientList.addAll(Arrays.asList(
            new Ingredient(1, "carrot"),
            new Ingredient(2, "potato"),
            new Ingredient(3, "tomato"),
            new Ingredient(4, "lemon"),
            new Ingredient(5, "rice"),
            new Ingredient(6, "stone")
        ));
    }

    @Test
    void createIngredientTest() {
        Ingredient ingredient = new Ingredient("testIngredient");
        assertCountDifference(1, "Expected Ingredient count to be 1 more", countSelStatement, () -> {
            int newId = ingredientDao.createIngredient(ingredient);
            // Verify that the created ingredient has a valid ID
            assertEquals(7, newId, "The ID of the newly created ingredient should be 7");
        });
    }

    @Test
    void readOneTest() {
        Ingredient ingredient = ingredientDao.getIngredientById(1);
        assertEquals(ingredientList.get(0), ingredient, () -> 
            "The returned ingredient doesn't match the expected ingredient. Expected: " + ingredientList.get(0) + " Actual: " + ingredient);
    }

    @Test
    void deleteIngredientTest() {
        Ingredient ingredient = ingredientDao.getIngredientById(1);
        assertCountDifference(-1, "Expected Ingredient count to be 1 less", countSelStatement, () -> {
            ingredientDao.deleteIngredient(ingredient);
        });

        // Ensure that the ingredient no longer exists in the database
        assertEquals(null, ingredientDao.getIngredientById(1), "The ingredient with ID 1 should have been deleted.");
    }

    @Test
    void updateIngredientTest() {
        Ingredient ingredient = ingredientDao.getIngredientById(1);
        ingredient.setName("newName");
        ingredientDao.updateIngredient(ingredient);
        Ingredient updatedIngredient = ingredientDao.getIngredientById(1);
        assertEquals(ingredient.getName(), updatedIngredient.getName(), () -> 
            "The returned ingredient name doesn't match the expected name. Expected: " + ingredient.getName() + " Actual: " + updatedIngredient.getName());
    }

    @Test
    void getAllIngredientsTest() {
        List<Ingredient> ingredients = ingredientDao.getAllIngredients();
        assertEquals(ingredientList.size(), ingredients.size(), "The number of returned ingredients should match the expected size.");
        assertIterableEquals(ingredientList, ingredients, () -> 
            "The returned ingredients don't match the expected ingredients. Expected: " + ingredientList + " Actual: " + ingredients);
    }

    @Test
    void getAndPageAllIngredientsTest() {
        PageOptions pageOptions = new PageOptions(1, 2);
        Page<Ingredient> expectedIngredients = new Page<>(1, 2, ingredientList.size()/2, ingredientList.size(), ingredientList.subList(0, 2));
        Page<Ingredient> ingredients = ingredientDao.getAllIngredients(pageOptions);
        assertIterableEquals(expectedIngredients.getItems(), ingredients.getItems(), "The returned ingredients don't match the expected ingredients.");
    }

    @Test
    void searchIngredientsTest() {
        List<Ingredient> ingredients = ingredientDao.searchIngredients("to");
        List<Ingredient> expectedIngredients = Arrays.asList(ingredientList.get(1), ingredientList.get(2), ingredientList.get(5));
        assertIterableEquals(expectedIngredients, ingredients, () -> 
            "The returned ingredients don't match the expected ingredients. Expected: " + expectedIngredients + " Actual: " + ingredients);
    }

    @Test
    void searchAndPageIngredientsTest() {
        PageOptions pageOptions = new PageOptions(1, 2);
        Page<Ingredient> expectedIngredients = new Page<>(1, 2, 2, 3, Arrays.asList(ingredientList.get(1), ingredientList.get(2)));
        Page<Ingredient> ingredients = ingredientDao.searchIngredients("to", pageOptions);
        assertEquals(expectedIngredients, ingredients, "The returned ingredients don't match the expected ingredients.");
    }
}
