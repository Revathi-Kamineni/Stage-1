package com.revature.model;
import java.util.Objects;
/**
 * The Chef class represents a chef user in the system.
 * It stores the chef's basic information such as ID, username, email, password,
 * and whether the chef has admin privileges. This class provides getter and setter 
 * methods to access and modify the fields, as well as methods for equality checking and 
 * generating hash codes for Chef objects.
 */
public class Chef {

    /**
     * The unique identifier of the chef.
     */
    private int id;

    /**
     * The username of the chef.
     */
    private String username;

    /**
     * The password of the chef.
     */
    private String password;

    /**
     * The email address of the chef.
     */
    private String email;

    /**
     * A flag indicating if the chef has admin privileges.
     */
    private boolean isAdmin;

    /**
     * Default constructor for creating an empty Chef object.
     */
    public Chef() {
        // No-arg constructor
    }

    /**
     * Constructs a Chef with the specified id, username, email, password, and admin status.
     *
     * @param id the unique identifier of the chef
     * @param username the username of the chef
     * @param email the email address of the chef
     * @param password the password of the chef
     * @param isAdmin true if the chef has admin privileges, false otherwise
     */
    public Chef(int id, String username, String email, String password, boolean isAdmin) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    /**
     * Constructs a Chef with the specified username, email, password, and admin status.
     * The id is not provided and will be generated or set later.
     *
     * @param username the username of the chef
     * @param email the email address of the chef
     * @param password the password of the chef
     * @param isAdmin true if the chef has admin privileges, false otherwise
     */
    public Chef(String username, String email, String password, boolean isAdmin) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    // Getter and Setter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**
     * Compares this Chef object with another object for equality.
     * 
     * @param obj the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Check for reference equality
        // if (obj == null || getClass() != obj.getClass()) return false; // Check for null and class match
        // Chef chef = (Chef) obj; // Typecast to Chef
        // return id == chef.id && // Compare IDs
        //        isAdmin == chef.isAdmin && // Compare admin status
        //        username.equals(chef.username) && // Compare usernames
        //        email.equals(chef.email); // Compare emails
        if (!(obj instanceof Chef)) return false;
        Chef chef = (Chef) obj;
        return id == chef.id && 
           username.equals(chef.username) && 
           email.equals(chef.email) && 
           password.equals(chef.password) && 
           isAdmin == chef.isAdmin;

    }

    /**
     * Generates the hash code for this Chef object.
     * 
     * @return the hash code of the chef
     */
    @Override
    public int hashCode() {
        // Use a prime number for better distribution
        // int result = 17; 
        // result = 31 * result + id; // Include ID
        // result = 31 * result + username.hashCode(); // Include username
        // result = 31 * result + email.hashCode(); // Include email
        // result = 31 * result + (isAdmin ? 1 : 0); // Include admin status
        // return result; // Return the computed hash code
        return Objects.hash(id, username, email, password, isAdmin);
    }
}
