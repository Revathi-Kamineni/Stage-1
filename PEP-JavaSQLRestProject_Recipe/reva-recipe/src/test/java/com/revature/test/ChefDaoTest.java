// package com.revature.test;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertIterableEquals;

// import java.sql.SQLException;
// import java.util.ArrayList;
// import java.util.List;

// import org.h2.Driver;
// import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// import com.revature.model.Chef;
// import com.revature.dao.ChefDAO;
// import com.revature.util.ConnectionUtil;

// public class ChefDaoTest {
	
// 	private List<Chef> chefList = new ArrayList<Chef>();
// 	private ConnectionUtil connectionUtil;
// 	private ChefDAO chefDao;
	
// 	@BeforeAll
// 	void setUpDB() {
// 		DBTestSetUp.RUN_DDL();
// 	}
	
// 	@BeforeEach
// 	void setUpTestsData() throws SQLException {
// 		DBTestSetUp.RUN_DML();
// 		chefList.clear();
// 		chefList.addAll(List.of(new Chef("JoeCool", "Snoop@null.com", "redbarron", false), 
// 				new Chef("CharlieBrown", "goodgrief@peanuts.com", "thegreatpumpkin", false), 
// 				new Chef("RevaBuddy", "revature@revature.com", "codelikeaboss", false), 
// 				new Chef("ChefTrevin", "trevin@revature.com", "trevature", true)));
		
// 		connectionUtil = ConnectionUtil.getInstance().configure("sa", "", "jdbc:h2:./h2/db", new Driver());
// 		chefDao = new ChefDAO(connectionUtil);
// 	}

// 	@Test
// 	void readAllChefs() {
		
// 		List<Chef> chefs = chefDao.getAllChefs();
// 		System.out.println("Expected chefs: " + chefList);
//     System.out.println("Returned chefs: " + chefs);
// 		assertIterableEquals(chefs, chefList, () -> "the returned list of chefs doesn't match the expected list of chefs.");
		
// 	}
	
// 	@Test
// 	void readOneChef() {
		
// 		Chef chef = chefDao.getChefById(1);
// 		assertEquals(chef, chefList.get(0), () -> "The returned chef doesn't match the expected chef.  Expected: " + chefList.get(0) + " Actual: " + chef);
	
// 	}
	
// 	@Test
// 	void createChef() {
		
// 		Chef chefToInsert = new Chef("RoboChef", "robochef@ccp.com", "ToServeThePublic", false);
// 		int id = chefDao.createChef(chefToInsert);
// 		Chef chef = chefDao.getChefById(id);
// 		assertEquals(chef, chefToInsert, () -> "The database doesn't include the expected chef Expected: " + chefToInsert + " Actual: " + chef);
		
// 	}
	
// 	@Test
// 	void deleteChef() {
// 		int count = chefDao.getAllChefs().size();
// 		Chef chefToDelete = chefDao.getChefById(1);
// 		chefDao.deleteChef(chefToDelete);
// 		assertEquals(count - 1, chefDao.getAllChefs().size(), () -> "The database doesn't have the expected number of chefs after deleting a chef. Expected: " + (count - 1) + " Actual: " + chefDao.getAllChefs().size());
// 	}
	
// 	@Test
// 	void updatechef() {
// 		Chef chefToUpdate = chefDao.getChefById(1);
// 		chefToUpdate.setUsername("updated username");
// 		chefToUpdate.setEmail("updated email");
// 		chefDao.updateChef(chefToUpdate);
// 		Chef chef = chefDao.getChefById(1);
// 		assertEquals(chef, chefToUpdate, () -> "The database doesn't include the expected chef after updating. Expected: " + chefToUpdate + " Actual: " + chef);
// 	}
	
// }


package com.revature.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.h2.Driver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.revature.model.Chef;
import com.revature.dao.ChefDAO;
import com.revature.util.ConnectionUtil;
//import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;


import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revature.model.Chef;
import com.revature.util.ConnectionUtil;

public class ChefDaoTest {
    
    @Mock
    private ConnectionUtil connectionUtil;
    
    @Mock
    private Connection connection;
    
    @Mock
    private PreparedStatement preparedStatement;
    
    @Mock
    private Statement statement;
    
    @Mock
    private ResultSet resultSet;
    
    private ChefDAO chefDAO;
    
    private Chef testChef;
    
    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        
        // Setup common mock behaviors
        when(connectionUtil.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatement);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(connection.createStatement()).thenReturn(statement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        
        chefDAO = new ChefDAO(connectionUtil);
        
        // Create test chef data
        testChef = new Chef(1, "testChef", "test@chef.com", "password123", false);
    }
    
    @Test
    public void testGetAllChefs() throws Exception {
        // Arrange
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getInt("id")).thenReturn(1, 2);
        when(resultSet.getString("username")).thenReturn("chef1", "chef2");
        when(resultSet.getString("email")).thenReturn("chef1@test.com", "chef2@test.com");
        when(resultSet.getString("password")).thenReturn("pass1", "pass2");
        when(resultSet.getBoolean("is_admin")).thenReturn(false, false);
        
        // Act
        List<Chef> chefs = chefDAO.getAllChefs();
        
        // Assert
        assertNotNull(chefs);
        assertEquals(2, chefs.size());
        assertEquals("chef1", chefs.get(0).getUsername());
        assertEquals("chef2", chefs.get(1).getUsername());
        
        verify(statement).executeQuery("SELECT * FROM chef ORDER BY id");
    }
    
    @Test
    public void testGetChefById() throws Exception {
        // Arrange
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("username")).thenReturn("testChef");
        when(resultSet.getString("email")).thenReturn("test@chef.com");
        when(resultSet.getString("password")).thenReturn("password123");
        when(resultSet.getBoolean("is_admin")).thenReturn(false);
        
        // Act
        Chef chef = chefDAO.getChefById(1);
        
        // Assert
        assertNotNull(chef);
        assertEquals(1, chef.getId());
        assertEquals("testChef", chef.getUsername());
        assertEquals("test@chef.com", chef.getEmail());
        
        verify(preparedStatement).setInt(1, 1);
    }
    
    @Test
    public void testCreateChef() throws Exception {
        // Arrange
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(1);
        
        // Act
        int newId = chefDAO.createChef(testChef);
        
        // Assert
        assertEquals(1, newId);
        verify(preparedStatement).setString(1, testChef.getUsername());
        verify(preparedStatement).setString(2, testChef.getEmail());
        verify(preparedStatement).setString(3, testChef.getPassword());
        verify(preparedStatement).setBoolean(4, testChef.isAdmin());
    }
    
    @Test
    public void testUpdateChef() throws Exception {
        // Arrange
        when(preparedStatement.executeUpdate()).thenReturn(1);
        
        // Act
        chefDAO.updateChef(testChef);
        
        // Assert
        verify(preparedStatement).setString(1, testChef.getUsername());
        verify(preparedStatement).setString(2, testChef.getEmail());
        verify(preparedStatement).setString(3, testChef.getPassword());
        verify(preparedStatement).setBoolean(4, testChef.isAdmin());
        verify(preparedStatement).setInt(5, testChef.getId());
    }
    
    @Test
    public void testDeleteChef() throws Exception {
        // Arrange
        when(preparedStatement.executeUpdate()).thenReturn(1);
        
        // Act
        chefDAO.deleteChef(testChef);
        
        // Assert
        verify(preparedStatement).setInt(1, testChef.getId());
        verify(preparedStatement).executeUpdate();
    }
}