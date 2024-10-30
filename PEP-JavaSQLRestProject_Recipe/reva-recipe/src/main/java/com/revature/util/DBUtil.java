package com.revature.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class DBUtil {
	
	public static Connection GET_CONNECTION() {
		try {
			return DriverManager.getConnection("jdbc:h2:./h2/db", "sa", "");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static StringBuilder ddlScript = new StringBuilder();

	static {
		try {
			String filepath = "./src/main/resources/ddl.sql";
			File file = new File(filepath);
			if (!file.exists()) {
				filepath = "./reva-recipe/src/main/resources/ddl.sql";
				file = new File(filepath);
			}
			Scanner sc = new Scanner(new FileInputStream(file));
			while (sc.hasNextLine()) {
				ddlScript.append(sc.nextLine() + " ");
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static StringBuilder dmlScript = new StringBuilder();

	static {
		try {
			Scanner sc = new Scanner(new FileInputStream("./reva-recipe/src/main/resources/dml.sql"));
			while (sc.hasNextLine()) {
				dmlScript.append(sc.nextLine() + " ");
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void RUN_DDL() {
		try(Connection conn = GET_CONNECTION()) {
			conn.prepareStatement("DROP ALL OBJECTS").executeUpdate();
			conn.prepareStatement(ddlScript.toString()).executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void RUN_DML() {
		try(Connection conn = GET_CONNECTION()) {
			conn.prepareStatement(dmlScript.toString()).executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}