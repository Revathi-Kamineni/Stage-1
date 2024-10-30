package com.revature.model;

/**
 * The Recipe class represents the domain object for a recipe.
 * It adheres to the principles of a simple Java Bean, containing only fields,
 * getter methods, setter methods, and constructors. This class does not include
 * any application business logic; its sole purpose is to hold the data related
 * to a Recipe in Java. Fields include the recipe's ID, name, instructions,
 * and the author (Chef).
 */
public class Recipe {

    /**
     * The unique identifier of the recipe.
     */
    private int id;

    /**
     * The name of the recipe.
     */
    private String name;

    /**
     * The instructions for preparing the recipe.
     */
    private String instructions;

    /**
     * The author of the recipe, represented as a Chef object.
     */
    private Chef author;

    /**
     * Default constructor for creating an empty Recipe object.
     */
    public Recipe() {
        // No-arg constructor
    }

    /**
     * Constructs a Recipe with the specified name.
     *
     * @param name the name of the recipe
     */
    public Recipe(String name) {
        this.name = name;
    }

    /**
     * Constructs a Recipe with the specified name and instructions.
     *
     * @param name the name of the recipe
     * @param instructions the instructions for preparing the recipe
     */
    public Recipe(String name, String instructions) {
        this.name = name;
        this.instructions = instructions;
    }

    /**
     * Constructs a Recipe with the specified ID, name, instructions, and author.
     *
     * @param id the unique identifier of the recipe
     * @param name the name of the recipe
     * @param instructions the instructions for preparing the recipe
     * @param author the author of the recipe
     */
    public Recipe(int id, String name, String instructions, Chef author) {
        this.id = id;
        this.name = name;
        this.instructions = instructions;
        this.author = author;
    }

    /**
     * Retrieves the ID of the recipe.
     *
     * @return the unique identifier of the recipe
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the recipe.
     *
     * @param id the unique identifier to set for the recipe
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves the name of the recipe.
     *
     * @return the name of the recipe
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the recipe.
     *
     * @param name the name to set for the recipe
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the instructions for the recipe.
     *
     * @return the instructions for preparing the recipe
     */
    public String getInstructions() {
        return instructions;
    }

    /**
     * Sets the instructions for the recipe.
     *
     * @param instructions the instructions to set for the recipe
     */
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    /**
     * Retrieves the author of the recipe.
     *
     * @return the author of the recipe
     */
    public Chef getAuthor() {
        return author;
    }

    /**
     * Sets the author of the recipe.
     *
     * @param author the author to set for the recipe
     */
    public void setAuthor(Chef author) {
        this.author = author;
    }

    /**
     * Generates the hash code for this Recipe object.
     *
     * @return the hash code of the recipe
     */
    @Override
    public int hashCode() {
        int result = 17; // Starting with a prime number
        result = 31 * result + id; // Include ID
        result = 31 * result + (name != null ? name.hashCode() : 0); // Include name, handling null
        return result; // Return the computed hash code
    }

    /**
     * Compares this Recipe object with another object for equality.
     *
     * @param obj the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Check for reference equality
        if (obj == null || getClass() != obj.getClass()) return false; // Check for null and class match
        Recipe recipe = (Recipe) obj; // Typecast to Recipe
        return id == recipe.id && // Compare IDs
               name.equals(recipe.name); // Compare names
    }

    /**
     * Returns a string representation of the Recipe object.
     * 
     * @return string representation of the recipe
     */
    @Override
    public String toString() {
        return "Recipe{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", instructions='" + instructions + '\'' +
               ", author=" + (author != null ? author.getUsername() : "Unknown") +
               '}';
    }
}
