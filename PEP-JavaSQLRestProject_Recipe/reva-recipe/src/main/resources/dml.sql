DELETE FROM RECIPE_INGREDIENT;

DELETE FROM RECIPE;

DELETE FROM CHEF;

DELETE FROM INGREDIENT;

ALTER TABLE CHEF ALTER COLUMN id RESTART WITH 1;


ALTER TABLE INGREDIENT ALTER COLUMN id RESTART WITH 1;

ALTER TABLE RECIPE ALTER COLUMN id RESTART WITH 1;

ALTER TABLE RECIPE_INGREDIENT ALTER COLUMN id RESTART WITH 1;

INSERT INTO 
CHEF 
	(username, email, password, is_admin) 
VALUES 
	('JoeCool', 'snoopy@null.com', 'redbarron',false),
	('CharlieBrown', 'goodgrief@peanuts.com', 'thegreatpumpkin', false),
	('RevaBuddy', 'revature@revature.com', 'codelikeaboss', false),
	('ChefTrevin', 'trevin@revature.com', 'trevature', true);

INSERT INTO 
RECIPE
	(name, instructions, author) 
VALUES 
	('carrot soup', 'Put carrot in water.  Boil.  Maybe salt.',1),
	('potato soup', 'Put potato in water.  Boil.  Maybe salt.', 2),
	('tomato soup', 'Put tomato in water.  Boil.  Maybe salt.', 2),
	('lemon rice soup', 'Put lemon and rice in water.  Boil.  Maybe salt.', 4),
	('stone soup', 'Put stone in water.  Boil.  Maybe salt.', 4);



INSERT INTO
INGREDIENT
	(name)
VALUES
	('carrot'),
	('potato'),
	('tomato'),
	('lemon'),
	('rice'),
	('stone');


INSERT INTO
RECIPE_INGREDIENT
    (id, recipe_id, ingredient_id, vol, measure)
VALUES
    (default, 1, 1, 1, 'cups'),
    (default, 2, 2, 2, 'cups'),
    (default, 3, 3, 2, 'cups'),
    (default, 4, 4, 1, 'Tbs'),
    (default, 4, 5, 2, 'cups');