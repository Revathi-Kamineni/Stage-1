# Project: RecipeHub (Back-End)

## Background
This project is a Java-based backend application designed to manage recipes, chefs, and ingredients, using object-oriented programming principles and a relational database. The application restricts access to certain functionalities to admin users and uses a layered architecture structure.

## Requirements

### 1: The application should be able to represent entities.
Located within the `main/java/com/revature/model` folder, the files `Recipe.java`, `Chef.java`, and `Ingredient.java` should be present. Their purpose is to represent entities, or domain objects, within our application. Read the javadoc comments in these files in order to complete the below tasks:
- Provide the `Recipe` class with the correct fields, no-args and parameterized constructors, and getter/setter methods.
- Provide the `Recipe` class with the overridden `equals()` and `hashCode()` methods.
- Provide the `Chef` class with the correct fields, no-args and parameterized constructors, and getter/setter methods.
- Provide the `Chef` class with the overridden `equals()` and `hashCode()` methods.
- Provide the `Ingredient` class with the correct fields, no-args and parameterized constructors, and getter/setter methods.
- Provide the `Ingredient` class with the overridden `equals()` and `hashCode()` methods.

### 2: The application should provide a database setup script.
Located within the `main/resources` folder, the file `ddl.sql` should be present. The file's purpose is to contain database setup statements. Read the SQL comments in the file in order to complete the below tasks:
- Provide the `CREATE TABLE` statement necessary to represent a `Recipe` entity in your database.
- Provide the `CREATE TABLE` statement necessary to represent a `Chef` entity in your database.
- Provide the `CREATE TABLE` statement necessary to represent an `Ingredient` entity in your database, as well as a join table that manages the relationships between this table and the `Recipe` table.

### 3: The application should be able to connect to the database.
Located within the `main/java/com/revature/util` folder, the file `ConnectionUtil.java` should be present. The file's purpose is to provide the application with a single valid database connection. Read the comments in the file in order to complete the below tasks:
- Provide the `ConnectionUtil` class with the correct private fields, private constructor, and connection-related methods.

### 4: The application should be able to interact the database and should include pagination for query results.
Located within the `main/java/com/revature/dao` folder, the files `RecipeDAO.java`, `ChefDAO.java`, and `IngredientDAO.java` should be present. Their purpose is to provide an abstraction for simple database CRUD operations for the application.

The `DAO` layer will be expected to handle pagination operations, so you will also provide the implementation for a generic `Page` class and a `PageOption` class.  Pagination is a technique used in applications to break down large sets of results (such as those returned from a database query) into smaller, more manageable chunks, or pages.

Both of the pagination related files can be found under the `main/java/com/revature/util` folder. Before working on the `DAO` files, read the comments in the pagination files in order to complete the below tasks:
- Provide the `Page` class with the correct fields, no-args and parameterized constructors, and getter/setter methods.
- Provide the `Page` class with the overridden `equals()` and `hashCode()` methods.
- Provide the `PageOptions` class with the correct fields, no-args and parameterized constructors, and getter/setter methods.

Next, read the comments in the `DAO` files in order to complete the below tasks:
- Provide the `RecipeDAO` class with the correct fields, constructors, and CRUD operation methods.
- Provide the `ChefDAO` class with the correct fields, constructors, and CRUD operation methods.
- Provide the `IngredientDAO` class with the correct fields, constructors, and CRUD operation methods.

### 5: The application should contain business logic.
Services classes implement the business logic of an application. Business logic can be as complex or simple as the solution requires. In this case we will keep it simple and add a layer of abstraction that separates data access from request handling. Located within the `main/java/com/revature/service` folder, the files `RecipeService.java`, `ChefService.java`, `AuthenticationService.java`, and `IngredientService.java` should be present. Read the comments in these files in order to complete the below tasks:
- Provide the `RecipeService` class with the correct fields, constructors, and business logic methods.
- Provide the `ChefService` class with the correct fields, constructors, and business logic methods.
- Provide the `AuthenticationService` class with the correct fields, constructors, and business logic methods.
- Provide the `IngredientService` class with the correct fields, constructors, and business logic methods.

### 6: The application should be able to route and handle incoming HTTP requests.
A controller class is responsible for handling incoming requests, processing that input, interacting with the service layer, and then returning the appropriate response. Located within the `main/java/com/revature/controller` folder, the files `RecipeController.java` and `AuthenticationController` should be present. Read the comments in these files in order to complete the below tasks:
- Provide the `RecipeController` class with the correct fields, constructors, and handlers.
- Provide the `AuthenticationController` class with the correct fields, constructors, and handlers.
- Provide the `IngredientController` class with the correct fields, constructors, and handlers.

Located within the `main/java/com/revature/util` folder, the file `JavalinAppUtil.java` should be present. Its purpose is to create and configure a Javalin instance with the correct routes. Read the comments in the file in order to complete the below tasks:
- Provide the `JavalinAppUtil` class with the correct field, constructor, and method.

### 7: The application should be able to start.
Located within the `main/java/com/revature` folder, the file `Main.java` should be present. This is the entry point of the program and should contain some initial application configuration. Read the comments in the file in order to complete the below tasks:
- Provide the `Main` class with the correct `private` `static` fields, initialize them in the `main` method, and have the `main` method start a Javalin instance.

### 8: The application should allow only authorized users to perform certain tasks.
For this step, you will be implementing a check to make sure only admins are allowed to access certain endpoints in our application. For this, you will want to take a look at the `RecipeController` and `IngredientController` classes, as well as the `JavalinAppUtil` to see the different endpoints. Your main task will be to create a middleware class that intercepts every request, checks if the username is an admin, checks which endpoint they are trying to access, and decides whether to allow/deny the request.

Located within the `main/java/com/revature/util` folder, the file `AdminMiddleware.java` should be present. Read the comments in the file in order to complete the below tasks:
- Provide the `AdminMiddleware` class the appropriate fields, constructor, and methods so that only admins can create, delete, or update ingredients.
- Provide the `AdminMiddleware` class the appropriate fields, constructor, and methods so that only admins can delete recipes.



