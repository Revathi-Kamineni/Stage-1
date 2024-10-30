// package com.revature.dao;
// import com.revature.util.ConnectionUtil;
// import com.revature.util.Page;
// import com.revature.util.PageOptions;
// import com.revature.model.Chef;
// import java.util.List;
// import java.util.ArrayList;
// import java.sql.ResultSet;
// import java.sql.SQLException;


// /**
//  * The ChefDao class abstracts the CRUD operations for Chef objects.
//  * It provides functionality to interact with the database for performing 
//  * operations such as creating, retrieving, updating, and deleting Chef records. 
//  * The class primarily uses a ConnectionUtil object to connect to the database and 
//  * includes methods for searching, paginating, and mapping results from database queries.
//  */

// public class ChefDAO {

//     /**
//      * A utility class for establishing connections to the database.
//      */
    
//     @SuppressWarnings("unused")
//     private ConnectionUtil connectionUtil;

//     /**
//      * Constructs a ChefDao with the specified ConnectionUtil for database connectivity.
//      * 
//      * @param connectionUtil the utility used to connect to the database
//      */

//     public ChefDAO(ConnectionUtil connectionUtil) {
//         this.connectionUtil = connectionUtil;
//     }

//     /**
//      * Retrieves all chefs from the database.
//      * 
//      * @return a list of all Chef objects
//      */

//     public List<Chef> getAllChefs() {
//      // TODO: Implement the logic to retrieve all chefs
//      return new ArrayList<>(); // Returns an empty list for now
//     }

//     /**
//      * Retrieves a paginated list of all chefs from the database.
//      * 
//      * @param pageOptions options for pagination, including page size and page number
//      * @return a paginated list of Chef objects
//      */

//     public Page<Chef> getAllChefs(PageOptions pageOptions) {
//         // Implementation
//         return null;// Returns an empty list for now
//     }

//     /**
//      * Searches for chefs based on a specified term.
//      * 
//      * @param term the search term to filter chefs by
//      * @return a list of Chef objects that match the search term
//      */

//     public List<Chef> searchChefsByTerm(String term) {
//         // Implementation
//         return null;// Returns an empty list for now
//     }

//     /**
//      * Searches for chefs based on a specified term and returns a paginated result.
//      * 
//      * @param term the search term to filter chefs by
//      * @param pageOptions options for pagination, including page size and page number
//      * @return a paginated list of Chef objects that match the search term
//      */

//     public Page<Chef> searchChefsByTerm(String term, PageOptions pageOptions) {
//         // Implementation
//         return null;// Returns an empty list for now
//     }

//     /**
//      * Retrieves a specific chef by their ID.
//      * 
//      * @param id the ID of the chef to retrieve
//      * @return the Chef object corresponding to the given ID
//      */

//     public Chef getChefById(int id) {
//      // Implementation logic to retrieve the Chef by ID
//      return null; // Return the Chef ID
//     }

//     /**
//      * Creates a new chef in the database.
//      * 
//      * @param chef the Chef object to create
//      * @return the ID of the newly created chef
//      */

//     public int createChef(Chef chef) {
//         // Implementation
//         return(0);  //return the newly created id of chef
//     }

//     /**
//      * Updates an existing chef's information in the database.
//      * 
//      * @param chef the Chef object with updated information
//      */

//     public void updateChef(Chef chef) {
//         // Implementation
//     }

//     /**
//      * Deletes a specific chef from the database.
//      * 
//      * @param chef the Chef object to delete
//      */

//     public void deleteChef(Chef chef) {
//         // Implementation
//     }

//     /**
//      * Maps the rows from the provided ResultSet into a list of Chef objects.
//      * 
//      * @param set the ResultSet from the database query
//      * @return a list of Chef objects mapped from the ResultSet
//      * @throws SQLException if there is an error processing the ResultSet
//      */

//     public List<Chef> mapRows(ResultSet set) throws SQLException {
//         // Implementation
//         return null;// Returns an empty list for now
//     }

//     /**
//      * Paginates the results from the provided ResultSet based on the PageOptions.
//      * 
//      * @param set the ResultSet from the database query
//      * @param pageOptions the options for pagination, including page size and page number
//      * @return a paginated list of Chef objects from the ResultSet
//      * @throws SQLException if there is an error processing the ResultSet
//      */

//     public Page<Chef> pageResults(ResultSet set, PageOptions pageOptions) throws SQLException {
//         // Implementation
//         return null;// Returns an empty list for now
//     }

//     /**
//      * Slices a list of chefs into a sublist based on the provided start and end indices.
//      * 
//      * @param list the list of Chef objects to slice
//      * @param start the starting index of the slice
//      * @param end the ending index of the slice
//      * @return a sublist of Chef objects from the original list
//      */

//     public List<Chef> sliceList(List<Chef> list, int start, int end) {
//         // Implementation
//         return null;// Returns an empty list for now
//     }
// }

package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.model.Chef;
import com.revature.util.ConnectionUtil;
import com.revature.util.Page;
import com.revature.util.PageOptions;

public class ChefDAO {
	private ConnectionUtil connectionUtil;

    public ChefDAO(ConnectionUtil connectionUtil) {
        this.connectionUtil = connectionUtil;
    }

    public List<Chef> getAllChefs() {
        try(Connection connection = connectionUtil.getConnection(); Statement statement = connection.createStatement()) {
            String sql = "SELECT * FROM chef ORDER BY id";
            ResultSet resultSet = statement.executeQuery(sql);
            return mapRows(resultSet);
            
        } catch(SQLException e) {
            throw new RuntimeException("Unable to retrieve all chefs", e);
        }
    }

    public Page<Chef> getAllChefs(PageOptions pageOptions) {
        String sql = String.format("SELECT * FROM chef ORDER BY %s %s", pageOptions.getSortBy(), pageOptions.getSortDirection());
        try(Connection connection = connectionUtil.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet chefSet = statement.executeQuery();
            return pageResults(chefSet, pageOptions);
        } catch(SQLException e) {
            throw new RuntimeException("Unable to retrieve all chefs", e);
        }
    }

    public List<Chef> searchChefsByTerm(String term) {
        String sql = "SELECT * FROM chef WHERE username LIKE ?";
        try(Connection connection = connectionUtil.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + term + "%");
            ResultSet resultSet = statement.executeQuery();
            return mapRows(resultSet);
        } catch(SQLException e) {
            throw new RuntimeException("Unable to search chefs by term", e);
        }
    }

    public Page<Chef> searchChefsByTerm(String term, PageOptions pageOptions) {
        String sql = String.format("SELECT * FROM chef WHERE name LIKE ? ORDER BY %s %s", pageOptions.getSortBy(), pageOptions.getSortDirection());
        try(Connection connection = connectionUtil.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + term + "%");
            ResultSet resultSet = statement.executeQuery();
            return pageResults(resultSet, pageOptions);
        } catch(SQLException e) {
            throw new RuntimeException("Unable to search chefs by term", e);
        }
    }

    public Chef getChefById(int id) {
        String sql = "SELECT * FROM chef WHERE id = ?";
        try(Connection connection = connectionUtil.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return mapSingleRow(resultSet);
        } catch(SQLException e) {
            throw new RuntimeException("Unable to retrieve chef by id", e);
        }
    }

    // public Chef getChefByUsername(String username) {
    //     String sql = "SELECT * FROM chef WHERE username = ?";
    //     try(Connection connection = connectionUtil.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
    //         statement.setString(1, username);
    //         ResultSet resultSet = statement.executeQuery();
    //         resultSet.next();
    //         return mapSingleRow(resultSet);
    //     } catch(SQLException e) {
    //         throw new RuntimeException("Unable to retrieve chef by username", e);
    //     }
    // }

    public int createChef(Chef chef) {
        String sql = "INSERT INTO chef (username, email, password, is_admin) VALUES (?, ?, ?, ?)";
        try(Connection connection = connectionUtil.getConnection(); PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, chef.getUsername());
            statement.setString(2, chef.getEmail());
            statement.setString(3, chef.getPassword());
            statement.setBoolean(4, chef.isAdmin());
            int affectedRows = statement.executeUpdate();

            if(affectedRows < 1) {
                throw new SQLException("Creating chef failed, no rows affected");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if(generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Creating chef failed, no ID obtained");
            }
            
        } catch(SQLException e) {
            throw new RuntimeException("Unable to create chef", e);
        }
    }

    public void updateChef(Chef chef) {
        String sql = "UPDATE chef SET username = ?, email = ?, password = ?, is_admin = ? WHERE id = ?";
        try(Connection connection = connectionUtil.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, chef.getUsername());
            statement.setString(2, chef.getEmail());
            statement.setString(3, chef.getPassword());
            statement.setBoolean(4, chef.isAdmin());
            statement.setInt(5, chef.getId());
            statement.executeUpdate();
        } catch(SQLException e) {
            throw new RuntimeException("Unable to update chef", e);
        }
    }

    
    public void deleteChef(Chef chef) {
        String sql = "DELETE FROM chef WHERE id = ?";
        try(Connection connection = connectionUtil.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, chef.getId());
            statement.executeUpdate();
        } catch(SQLException e) {
            throw new RuntimeException("Unable to delete chef", e);
        }
    }
    private Chef mapSingleRow(ResultSet set) throws SQLException {
        int id = set.getInt("id");
        String username = set.getString("username");
        String email = set.getString("email");
        String password = set.getString("password");
        boolean isAdmin = set.getBoolean("is_admin");
        return new Chef(id, username, email, password, isAdmin);
    }


    private List<Chef> mapRows(ResultSet set) throws SQLException {
        List<Chef> chefs = new ArrayList<>();
        while(set.next()) {
            chefs.add(mapSingleRow(set));
        }
        return chefs;
    }

    private Page<Chef> pageResults(ResultSet set, PageOptions pageOptions) throws SQLException {
        List<Chef> chefs = mapRows(set);
        int offset = (pageOptions.getPageNumber() - 1) * pageOptions.getPageSize();
        int limit = offset + pageOptions.getPageSize();
        List<Chef> slicedList = sliceList(chefs, offset, limit);
        return new Page<>(pageOptions.getPageNumber(), pageOptions.getPageSize(), chefs.size() / pageOptions.getPageSize(), chefs.size(), slicedList);
    }

    private List<Chef> sliceList(List<Chef> list, int start, int end) {
        List<Chef> sliced = new ArrayList<>();
        for(int i = start; i < end; i++) {
            sliced.add(list.get(i));
        }
        return sliced;
    }
}