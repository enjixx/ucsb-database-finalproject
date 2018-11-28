package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataInitialization {
	private Connection conn;
	
	DataInitialization() throws SQLException
	{

		try {
			DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
		} catch (SQLException e) {
			System.out.println("Set up a class path to ojdbc6.jar");
			System.exit(1);
		}
		
		String strConn = "jdbc:oracle:thin:@cloud-34-133.eci.ucsb.edu:1521:XE";
		String strUsername = "e_khurelbaatar";
		String strPassword = "enjilove";
		System.out.println("Connecting...");

		conn = DriverManager.getConnection(strConn,strUsername,strPassword);

		System.out.println("Connected!");

	}
	
	public void initialData()
	{
		try {
			System.out.println("Initializing Banking System tables");
			
			//Account table 
			Statement st = conn.createStatement();
			String createTable =  "CREATE TABLE Account( aid INTEGER," +
									"Interest_rate FLOAT," +
									"Balance FLOAT," +
									"PRIMARY KEY (aid))";
			ResultSet rs = st.executeQuery(createTable);
			System.out.println("Account table created");
			
			//Saving_Account 
			createTable =  "CREATE TABLE Saving_Account( aid INTEGER," + "PRIMARY KEY (aid))";
			rs = st.executeQuery(createTable);
			System.out.println("Saving Account table created");
			
			//Checking Account
			createTable =  "CREATE TABLE Checking_Account( aid INTEGER," + "PRIMARY KEY (aid))";
			rs = st.executeQuery(createTable);
			System.out.println("Checking Account table created");
			
			//Pocket Account
			createTable =  "CREATE TABLE Pocket_Account( pid INTEGER," + "aid INTEGER," + "flat_rate FLOAT," + "PRIMARY KEY (pid)," + "FOREIGN KEY (aid) REFERENCES Account ON DELETE CASCADE)";
			rs = st.executeQuery(createTable);
			System.out.println("Pocket Account table created");
			
			//Student Account 
			createTable =  "CREATE TABLE Student_Account( aid INTEGER," + "PRIMARY KEY (aid))";
			rs = st.executeQuery(createTable);
			System.out.println("Student Account table created");
			
			//InterestChecking Account
			createTable =  "CREATE TABLE InterestChecking_Account( aid INTEGER," + "PRIMARY KEY (aid))";
			rs = st.executeQuery(createTable);
			System.out.println("Interest Checking Account table created");
			
			//Customer Table
			createTable = "CREATE TABLE Customer ( taxID CHAR(9)," + 
						   							"PIN INTEGER," + 
						   							"Address CHAR(50)," +
						   							"Name CHAR(20)," +
						   							"PRIMARY KEY(taxID))";
			rs= st.executeQuery(createTable);
			System.out.println("Customer table created");
			
			//Primary_Ownwer
			createTable = "CREATE TABLE Primary_Owner( taxID CHAR(9)," +
							"aid INTEGER," + 
							"PRIMARY KEY( taxID, aid)," + 
							"FOREIGN KEY (taxID) REFERENCES Customer ON DELETE CASCADE," +
							"FOREIGN KEY(aid) REFERENCES Account)";
			rs = st.executeQuery(createTable);
			System.out.println("Primary Owner table created");
			
			//CoOwner table
			
			createTable = "CREATE TABLE Co_Owner( taxID CHAR(9)," + 
												"aid INTEGER," +
												"PRIMARY KEY (taxID, aid)," +
												"FOREIGN KEY (taxID) REFERENCES Customer ON DELETE CASCADE," +
												"FOREIGN KEY (aid) REFERENCES Account)";
			
			rs = st.executeQuery(createTable);
			System.out.println("Co Owner table created");
			
			
		} catch (Exception e ) {
			System.out.println("error");
			System.exit(0);
		}
	}
	
	public void destroyData() {
		
		try {
			Statement st = conn.createStatement();
			String deleteTable = "DROP TABLE Account";
			ResultSet rs = st.executeQuery(deleteTable);
			
			deleteTable = "DROP TABLE Customer";
			rs = st.executeQuery(deleteTable);
			
			deleteTable = "DROP TABLE Saving_Account";
			rs = st.executeQuery(deleteTable);
			
			deleteTable = "DROP TABLE Checking_Account";
			rs = st.executeQuery(deleteTable);
			
			deleteTable = "DROP TABLE Pocket_Account";
			rs = st.executeQuery(deleteTable);
			
			deleteTable = "DROP TABLE Student_Account";
			rs = st.executeQuery(deleteTable);
			
			deleteTable = "DROP TABLE InterestChecking_Account";
			rs = st.executeQuery(deleteTable);
			
			deleteTable = "DROP TABLE Primary_Owner";
			rs = st.executeQuery(deleteTable);
			
			deleteTable = "DROP TABLE Co_Owner";
			rs = st.executeQuery(deleteTable);
			System.out.println("Tables are deleted");
			
		} catch (Exception e) {
		}
	}
	
	public Connection getConnection() {
		return conn;
	}
	
	public static void main(String [] args) throws SQLException
	{
		DataInitialization data = new DataInitialization();
		if ( args[0].equals("destroy"))
		{
			data.destroyData();
		}
		else if (args[0].equals("init")) {
			data.initialData();
		}
	}
	

} 

