package mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.Employee;
import entity.EntityConstants;
import entity.Order;
import entity.Park;
import entity.Subscriber;
import entity.Visitor;
import entity.EntityConstants.OrderType;

/*
 * class that holds static methods related to database actions such as connection, queries,updates and more
 */
public class MySQLConnection {
	private static Connection con;

	public static void connectToDB() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			System.out.println("Driver definition succeed");
		} catch (Exception ex) {
			/* handle the error */
			System.out.println("Driver definition failed");
		}

		try {
			con = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/S7BzDq6Xs6?serverTimezone=IST",
					"S7BzDq6Xs6", "puC0UgMgeM");
			System.out.println("SQL connection succeed");
		} catch (SQLException ex) {/* handle any errors */
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}

	/*
	 * 
	 */
	public static Visitor validateVisitor(String id) throws SQLException {
		PreparedStatement logInPreparedStatement;
		logInPreparedStatement = con.prepareStatement("SELECT * FROM visitor where id=? LIMIT 1;");
		logInPreparedStatement.setString(1, id);
		ResultSet rs = logInPreparedStatement.executeQuery();
		if (rs.next()) {
			return new Visitor(rs.getString(1));
		} else {
			PreparedStatement insertVisitorPreparedStatement;
			insertVisitorPreparedStatement = con.prepareStatement("INSERT INTO visitor (id) VALUES (?);");
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
			return new Subscriber(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
					rs.getString(6), Integer.parseInt(rs.getString(7)), rs.getString(8),
					rs.getString(9).contentEquals("0") ? false : true);
		}
		return null;
	}

	public static Employee validateEmployee(String[] idAndPassword) throws SQLException {
		PreparedStatement logInPreparedStatement;
		logInPreparedStatement = con
				.prepareStatement("SELECT * FROM employee where employeeNum=? AND password=? LIMIT 1;");
		logInPreparedStatement.setString(1, idAndPassword[0]);
		logInPreparedStatement.setString(2, idAndPassword[1]);
		ResultSet rs = logInPreparedStatement.executeQuery();
		if (rs.next()) {
			return new Employee(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
					EntityConstants.EmployeeRole.valueOf(rs.getString(6)), rs.getString(8));
		}
		return null;
	}

	public static List<Park> getParks() throws SQLException {
		ArrayList<Park> parkList = new ArrayList<>();
		Statement getParkStatement;
		getParkStatement = con.createStatement();
		ResultSet rs = getParkStatement.executeQuery("SELECT * FROM park;");
		while (rs.next()) {
			parkList.add(new Park(rs.getString(1), Integer.parseInt(rs.getString(2)), Integer.parseInt(rs.getString(3)),
					Integer.parseInt(rs.getString(4)), Integer.parseInt(rs.getString(5))));
		}
		return parkList;
	}

	private static Park getCertainPark(String parkName) throws NumberFormatException, SQLException {
		PreparedStatement getParkStatement;
		getParkStatement = con.prepareStatement("SELECT * FROM park where parkName=?;");
		getParkStatement.setString(1, parkName);
		ResultSet rs = getParkStatement.executeQuery();
		if (rs.next()) {
			return new Park(rs.getString(1), Integer.parseInt(rs.getString(2)), Integer.parseInt(rs.getString(3)),
					Integer.parseInt(rs.getString(4)), Integer.parseInt(rs.getString(5)));
		}
		return null;
	}

	public static boolean validateDate(Order orderToValidate)
			throws NumberFormatException, SQLException, ParseException {
		Park park = getCertainPark(orderToValidate.getParkName());
		if (park == null)
			throw new SQLException();
		String[] splitTime = orderToValidate.getTimeOfOrder().split(":");
		String minimalHour, maximalHour;
		if (Integer.parseInt(splitTime[0]) - park.getParkVisitDuration() < EntityConstants.PARK_OPEN)
			minimalHour = "08:00:00";
		else
			minimalHour = String.valueOf(Integer.parseInt(splitTime[0]) - park.getParkVisitDuration()) + ":00:00";
		maximalHour = String.valueOf(Integer.parseInt(splitTime[0]) + park.getParkVisitDuration()) + ":00:00";
		LocalTime lcl = LocalTime.of(Integer.parseInt(splitTime[0]), Integer.parseInt(splitTime[1]),
				Integer.parseInt(splitTime[2]));
		String query = "SELECT timeOfOrder,SUM(numOfVisitors) FROM orders WHERE (status='ACTIVE' OR status='PENDING_APPROVAL_FROM_WAITING_LIST'"
				+ " OR status='PENDING_FINAL_APPROVAL') AND orders.dateOfOrder=?"
				+ " AND timeOfOrder>=? AND timeOfOrder <=? GROUP BY timeOfOrder;";
		PreparedStatement validateDatePrepStmt = con.prepareStatement(query);
		validateDatePrepStmt.setString(1, orderToValidate.getDateOfOrder());
		validateDatePrepStmt.setString(2, minimalHour);
		validateDatePrepStmt.setString(3, maximalHour);
		ResultSet rs = validateDatePrepStmt.executeQuery();
		Map<Integer, Integer> timeOfOrderForHour = new HashMap<Integer, Integer>();
		while (rs.next()) {
			timeOfOrderForHour.put(Integer.parseInt(rs.getString(1).split(":")[0]), Integer.parseInt(rs.getString(2)));
		}
		for (int i = Integer.parseInt(minimalHour.split(":")[0]); i <= Integer
				.parseInt(maximalHour.split(":")[0]); i++) {
			int sum = 0;
			for (int j = park.getParkVisitDuration(); j >= 0; j--) {
				if (timeOfOrderForHour.containsKey(i - j))
					sum += timeOfOrderForHour.get(i - j);
			}
			System.out.println(i+" "+sum);
			if (orderToValidate.getNumOfVisitors() + sum > park.getParkMaxVisitorsDefault() - park.getParkDiffFromMax())
				return false;
		}
		return true;
	}

	public static void main(String[] args) {
		connectToDB();
		/*
		 * public Order(String id,String parkName,int numOfVisitors, OrderType
		 * type,String dateOfOrder,String timeOfOrder,int price) {
		 */
		try {
			System.out.println(validateDate(
					new Order("123123123", "1", 161, EntityConstants.OrderType.REGULAR, "2020-12-23", "14:00:00", 2)));
		} catch (NumberFormatException | SQLException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
