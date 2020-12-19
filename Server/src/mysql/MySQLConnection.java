package mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.Employee;
import entity.EntityConstants;
import entity.Subscriber;
import entity.Visitor;

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
        	con = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/S7BzDq6Xs6?serverTimezone=IST", "S7BzDq6Xs6", "puC0UgMgeM");
            System.out.println("SQL connection succeed");
     	} catch (SQLException ex) 
     	    {/* handle any errors*/
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            }
   	}
	public static Visitor validateVisitor(String id) throws SQLException {
		PreparedStatement logInPreparedStatement;
		logInPreparedStatement = con.prepareStatement("SELECT * FROM visitor where id=? LIMIT 1;");
		logInPreparedStatement.setString(1, id);
		ResultSet rs = logInPreparedStatement.executeQuery();
		if (rs.next()) {
			return new Visitor(rs.getString(1));
		}
		else {
			PreparedStatement insertVisitorPreparedStatement;
			insertVisitorPreparedStatement=con.prepareStatement("INSERT INTO visitor (id) VALUES (?);");
			insertVisitorPreparedStatement.setString(1, id);
			insertVisitorPreparedStatement.executeUpdate();
			return new Visitor(id);
			}
	}
	public static Subscriber validateSubscriber(String subNum) throws SQLException {
		PreparedStatement logInPreparedStatement;
		logInPreparedStatement = con.prepareStatement("SELECT * FROM subscriber where subNum=? LIMIT 1;");
		logInPreparedStatement.setString(1, subNum);
		ResultSet rs = logInPreparedStatement.executeQuery();
		if (rs.next()) {
			return new Subscriber(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(6),
					Integer.parseInt(rs.getString(7)),rs.getString(8),
					rs.getString(9).contentEquals("0")?false:true);
		}
		return null;
	}
	public static Employee validateEmployee(String[] idAndPassword) throws SQLException {
		PreparedStatement logInPreparedStatement;
		logInPreparedStatement = con.prepareStatement("SELECT * FROM employee where employeeNum=? AND password=? LIMIT 1;");
		logInPreparedStatement.setString(1, idAndPassword[0]);
		logInPreparedStatement.setString(2, idAndPassword[1]);
		ResultSet rs = logInPreparedStatement.executeQuery();
		if (rs.next()) {
			return new Employee(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),EntityConstants.EmployeeRole.valueOf(rs.getString(6)),rs.getString(8));
		}
		return null;
	}
}
