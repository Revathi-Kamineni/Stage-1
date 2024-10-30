package com.revature.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Connection;
import java.sql.SQLException;

import org.h2.Driver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.revature.util.ConnectionUtil;

class ConnectionTest {
	private ConnectionUtil connectionUtil;
	@BeforeEach
	public void setup() throws SQLException {
		connectionUtil = ConnectionUtil.getInstance().configure("sa", "", "jdbc:h2:./h2/db", new Driver());
	}

	@Test
	void getConnectionTest() throws SQLException {
		Connection connection = connectionUtil.getConnection();
		assertNotNull(connection, () -> "Connection should not be null");
		connection.close();
	}

}

