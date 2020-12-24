package mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import entity.Employee;
import entity.EntityConstants;
import entity.Order;
import entity.ParameterUpdate;
import entity.Park;
import entity.Subscriber;
import entity.Visitor;
import entity.VisitorReport;
import entity.EntityConstants.OrderStatus;
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

	public static Map<String, VisitorReport> getVisitorReport() throws SQLException {
		Map<String, VisitorReport> reportVisitorMap = new HashMap<String, VisitorReport>();
		// List<VisitorReport> reportVisitorList = new ArrayList<>();
		Statement getReportVisitorStatement;
		getReportVisitorStatement = con.createStatement();
		ResultSet rs = getReportVisitorStatement.executeQuery(
				"SELECT  orders.parkName_fk,orders.type,sum(finishedOrders.actualNumOfVisitors ) as sumvisit "
						+ "FROM orders" + " JOIN finishedOrders ON (orders.orderNum = finishedOrders.orderNum_fk) "
						+ "WHERE (MONTH(NOW()) = MONTH(orders.dateOfOrder)) AND (YEAR(NOW()) = YEAR(orders.dateOfOrder)) "
						+ "GROUP by orders.type, orders.parkName_fk");
		List<Park> parkList = getParks();
		for (Park p : parkList)
			reportVisitorMap.put(p.getParkName(), new VisitorReport(p.getParkName()));
		if (!rs.next()) {
			return reportVisitorMap;
		}
		String namePark = rs.getString(1);
		do {
			if (!rs.getString(1).equals(namePark))
				namePark = rs.getString(1);
			if (rs.getString(2).equals("GUIDE"))
				reportVisitorMap.get(namePark).setCountGuid(Integer.parseInt(rs.getString(3)));
			if (rs.getString(2).equals("SUBSCRIBER"))
				reportVisitorMap.get(namePark).setCountSubscriber(Integer.parseInt(rs.getString(3)));
			if (rs.getString(2).equals("REGULAR"))
				reportVisitorMap.get(namePark).setCountRegular(Integer.parseInt(rs.getString(3)));
		} while (rs.next());
		return reportVisitorMap;

	}
	public static String getIncomeReport(String namePark) throws SQLException
	{
		PreparedStatement GetIncomeReport;
		GetIncomeReport = con
				.prepareStatement("SELECT sum(finishedOrders.actualPrice ) "
						+ "FROM finishedOrders "
						+ "JOIN orders ON (orders.orderNum = finishedOrders.orderNum_fk) "
						+ "WHERE (MONTH(NOW()) = MONTH(orders.dateOfOrder)) AND (YEAR(NOW()) = YEAR(orders.dateOfOrder)) "
						+ "AND orders.parkName_fk=?"
						+ "GROUP by orders.parkName_fk");
		GetIncomeReport.setString(1, namePark);
		ResultSet rs = GetIncomeReport.executeQuery();
		if (rs.next()) {
			String amount=rs.getString(1);
			return amount;
		}
		return "0";
		
		
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

			if (orderToValidate.getNumOfVisitors() + sum > park.getParkMaxVisitorsDefault() - park.getParkDiffFromMax())
				return false;
		}
		return true;
	}
	private static double calculateOrder(Order orderToRequest) throws SQLException
	{
		double priceForTicket=EntityConstants.TICKET_PRICE;
		
		double priceForOrder;
		if(orderToRequest.getType().equals(EntityConstants.OrderType.GUIDE))
		{
			priceForTicket*=0.75;
			priceForTicket*=0.88;
			priceForOrder=(orderToRequest.getNumOfVisitors()-1)*priceForTicket;
			
		}
		else
		{
			priceForTicket*=0.85;
			if(orderToRequest.getType().equals(EntityConstants.OrderType.REGULAR))
				priceForOrder=(orderToRequest.getNumOfVisitors())*priceForTicket;
			else
			{
				String query="SELECT familyMembers FROM subscriber WHERE id_fk=?";
				PreparedStatement familyMembersStatement = con.prepareStatement(query);
				familyMembersStatement.setString(1, orderToRequest.getId());
				ResultSet rs = familyMembersStatement.executeQuery();
				if(rs.next())
				{
					int familyMembers = Integer.parseInt(rs.getString(1));
					if(familyMembers>=orderToRequest.getNumOfVisitors())
					{
						priceForOrder=(orderToRequest.getNumOfVisitors())*priceForTicket*0.8;
						
					}
					else
					{
						priceForOrder=(familyMembers)*priceForTicket*0.8+(orderToRequest.getNumOfVisitors()-familyMembers)*priceForTicket;
					}
				}
				else
					priceForOrder=(orderToRequest.getNumOfVisitors())*priceForTicket;
			}
			
		}
		String query="SELECT discountAmount FROM discounts WHERE startDate<=? AND finishDate>=? AND parkName_fk=? AND status='APPROVED'";
		PreparedStatement discountStatement = con.prepareStatement(query);
		discountStatement.setString(1, orderToRequest.getDateOfOrder());
		discountStatement.setString(2, orderToRequest.getDateOfOrder());
		discountStatement.setString(3, orderToRequest.getParkName());
		ResultSet rs = discountStatement.executeQuery();
		while(rs.next())
			priceForOrder=priceForOrder*((100-Integer.parseInt(rs.getString(1)))/100);
		return priceForOrder;
		
	}
	private static Order insertNewOrder(Order orderToInsert,OrderStatus orderStatus) throws SQLException
	{
		PreparedStatement insertOrderStatement = con.prepareStatement("INSERT INTO orders (id_fk,parkName_fk,orderCreationDate,numOfVisitors,status,type,dateOfOrder, timeOfOrder, price,email) VALUES (?,?,?,?,?,?,?,?,?,?);");
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateNow = formatter.format(date);
		insertOrderStatement.setString(1, orderToInsert.getId());
		insertOrderStatement.setString(2, orderToInsert.getParkName());
		insertOrderStatement.setString(3, dateNow);
		insertOrderStatement.setString(4, String.valueOf(orderToInsert.getNumOfVisitors()));
		insertOrderStatement.setString(5, orderStatus.name());
		insertOrderStatement.setString(6, orderToInsert.getType().name());
		insertOrderStatement.setString(7, orderToInsert.getDateOfOrder());
		insertOrderStatement.setString(8, orderToInsert.getTimeOfOrder());
		double price =calculateOrder(orderToInsert);
		insertOrderStatement.setString(9, String.valueOf((int) price));
		insertOrderStatement.setString(10, orderToInsert.getEmail());
		insertOrderStatement.executeUpdate();
		String query="SELECT orderNum FROM orders where id_fk=? AND parkName_fk=? AND orderCreationDate=?;";
		PreparedStatement getOrderNumStatement=con.prepareStatement(query);
		getOrderNumStatement.setString(1, orderToInsert.getId());
		getOrderNumStatement.setString(2, orderToInsert.getParkName());
		getOrderNumStatement.setString(3, dateNow);
		ResultSet rs = getOrderNumStatement.executeQuery();
		if(rs.next())
		{
			orderToInsert.setOrderNum(rs.getString(1));
			orderToInsert.setOrderCreationDate(dateNow);
			orderToInsert.setPrice((int) price);
			orderToInsert.setStatus(orderStatus);
			return orderToInsert;
		}
		throw new SQLException();
	}
	public static Order createOrder(Order orderRequest) throws SQLException, NumberFormatException, ParseException
	{
		if(validateDate(orderRequest))
		{
			return insertNewOrder(orderRequest,OrderStatus.ACTIVE);
		}
		return null;
	}
	//liron
	public static ParameterUpdate createParameterUpdate(ParameterUpdate parameterUpdate) throws SQLException
	{
		PreparedStatement parameterPreparedStatement=con.prepareStatement("INSERT INTO parameterUpdate (parameter,newValue,parkName_fk) VALUES (?,?,?);");
		parameterPreparedStatement.setString(1, parameterUpdate.getParameter());
		parameterPreparedStatement.setInt(2, parameterUpdate.getNewValue());
		parameterPreparedStatement.setString(3, parameterUpdate.getParkName());
		parameterPreparedStatement.executeUpdate();
		return parameterUpdate;
	public static Order enterWaitingist(Order orderRequest) throws SQLException, NumberFormatException, ParseException
	{
		return insertNewOrder(orderRequest,OrderStatus.WAITING);
	}
	public static Map<String,List<String>> getAvailableDates(Order order) throws ParseException, NumberFormatException, SQLException{
		Map<String,List<String>> dateMap=new LinkedHashMap<>();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		Date date=format.parse(order.getDateOfOrder());
		for(int i=0;i<7;i++) {
			  Calendar cal = Calendar.getInstance();
		      cal.setTime(date);
		      cal.add(Calendar.DATE, i); //minus number would decrement the days
		      Date nextDate= cal.getTime();
		      order.setDateOfOrder(format.format(nextDate));
			for(int j=EntityConstants.PARK_OPEN;j<=EntityConstants.PARK_CLOSED;j++) {
				order.setTimeOfOrder(j);
				if(validateDate(order)) {
					if(!dateMap.containsKey(format.format(nextDate)))
						dateMap.put(format.format(nextDate), new ArrayList<String>());
					dateMap.get(format.format(nextDate)).add(order.getTimeOfOrder());
				}
			}
		}
		return dateMap;
	}
	
	public static void main(String[] args) {
		  Calendar cal = Calendar.getInstance();
		  DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		  Date date=new Date();
	      cal.setTime(date);
	      cal.add(Calendar.DATE, 1); //minus number would decrement the days
	      Date nextDate= cal.getTime();
	      System.out.println(format.format(nextDate));
	}

}
