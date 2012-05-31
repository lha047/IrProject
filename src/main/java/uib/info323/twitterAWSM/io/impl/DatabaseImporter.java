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
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.core.task.TaskExecutor;

import com.mysql.jdbc.Statement;

public class DatabaseImporter {

	public DatabaseImporter() {

		start();

	}

	private void start() {
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
			
			ExecutorService pool = Executors.newFixedThreadPool(40);
			for(int i = 0; i < 40; i++) {
				pool.execute(new DatabaseInserter(insertQueries, connection));
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

	private class DatabaseInserter implements Runnable {
		
		private LinkedList<String> insertQueries;
		private Connection connection;
		

		public DatabaseInserter(LinkedList<String> insertQueries, Connection connection) {
			this.insertQueries = new LinkedList<String>();
			this.insertQueries.addAll(insertQueries);
			this.connection = connection;
		}

		public void run() {
			for(String query : insertQueries) {
				Statement stat;
				try {
					stat = (Statement) connection.createStatement();
					long start = System.currentTimeMillis();
					stat.execute(query);
					long duration = System.currentTimeMillis() -start;
					System.out.println("Batch insert time: " + duration + " Thread: " + Thread.currentThread().getName());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		new DatabaseImporter();
	}


}
