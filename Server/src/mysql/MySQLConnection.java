package mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
	private static Connection con;
	public static void ConnectToDB()
	{
		try 
		{
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            System.out.println("Driver definition succeed");
        } catch (Exception ex) {
        	/* handle the error*/
        	 System.out.println("Driver definition failed");
        	 }
        
        try 
        {
        	con = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/gonatue_team18?serverTimezone=IST", "gonature", "goNatureTeam!8");
            System.out.println("SQL connection succeed");
     	} catch (SQLException ex) 
     	    {/* handle any errors*/
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            }
   	}
	public static boolean validateVisitor(String id) {
		return true;
	}
	public static boolean validateSubscriber(String id) {
		return true;
	}
	public static boolean validateEmployee(String[] idAndPassword) {
		return true;
	}
}
