package uib.info323.twitterAWSM.io.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

import com.mysql.jdbc.Statement;

public class DatabaseImporter {
	
	public static void main(String[] args) {
		
		String url = "jdbc:mysql://localhost:3306/";
		String driver = "com.mysql.jdbc.Driver";
		String db = "feedjam";
		String user = "root";
		String pass = "info323";
		Connection connection;
		
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url+db, user, pass);
			
			BufferedReader reader = new BufferedReader(new FileReader(new File("feedjam.sql")));
			
			LinkedList<String> insertQueries = new LinkedList<String>();
			StringBuilder insertString;
			
			insertString = new StringBuilder();
			insertString.append(reader.readLine());
			
			String line;
			while((line = reader.readLine()) != null) {
				if(!line.startsWith("INSERT")) {
					insertString.append(line);
				} else {
					insertQueries.add(insertString.toString());
					insertString = new StringBuilder();
					insertString.append(line);
				}
				
				
			}
			
			for(String query : insertQueries) {
				Statement stat = (Statement) connection.createStatement();
				long start = System.currentTimeMillis();
				stat.execute(query);
				long duration = System.currentTimeMillis() -start;
				System.out.println("Batch insert time: " + duration);
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
