-- Chef Table
-- This table stores information about chefs.
-- Fields:
-- 1. id: An auto-incremented primary key to uniquely identify each chef.
-- 2. username: A unique and non-nullable varchar field to store the chef's username.
-- 3. email: A unique and non-nullable varchar field to store the chef's email address.
-- 4. password: A non-nullable varchar field to store the chef's password.
-- 5. isAdmin: A boolean field to indicate if the chef has admin privileges.

CREATE TABLE Chef (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    isAdmin BOOLEAN
);

-- Recipe Table
-- This table stores information about recipes.
-- Fields:
-- 1. id: An auto-incremented primary key to uniquely identify each recipe.
-- 2. name: A unique and non-nullable varchar field to store the recipe's name.
-- 3. instructions: A non-nullable varchar field to store the recipe's instructions.
-- 4. chef_id: A foreign key that references the 'id' field from the Chef table.

CREATE TABLE Recipe (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    instructions VARCHAR(255) NOT NULL,
    chef_id INT,
    FOREIGN KEY (chef_id) REFERENCES Chef(id)
);


-- Ingredient Table
-- This table stores information about ingredients.
-- Fields:
-- 1. id: An auto-incremented primary key to uniquely identify each ingredient.
-- 2. name: A unique and non-nullable varchar field (max 20 characters) to store the ingredient's name.

CREATE TABLE Ingredient (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE
);

-- Recipe_Ingredient Table
-- This table represents the many-to-many relationship between recipes and ingredients.
-- It stores the relationship between a recipe and its ingredients along with the volume and unit.
-- Fields:
-- 1. id: An auto-incremented primary key for each entry in the join table.
-- 2. recipe_id: A non-nullable foreign key that references the 'id' field from the Recipe table.
-- 3. ingredient_id: A non-nullable foreign key that references the 'id' field from the Ingredient table.
-- 4. vol: A decimal field to store the volume of the ingredient used in the recipe.
-- 5. unit: A non-nullable varchar field (max 20 characters) to store the unit of the volume.
-- 6. is_metric: A boolean field to indicate if the unit is in metric. Defaults to false.

CREATE TABLE Recipe_Ingredient (
    id INT AUTO_INCREMENT PRIMARY KEY,
    recipe_id INT NOT NULL,
    ingredient_id INT NOT NULL,
    vol DECIMAL(10,2) NOT NULL,
    unit VARCHAR(20) NOT NULL,
    is_metric BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (recipe_id) REFERENCES Recipe(id),
    FOREIGN KEY (ingredient_id) REFERENCES Ingredient(id)
);
